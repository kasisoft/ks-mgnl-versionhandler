package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.jcr.util.*;

import com.google.gson.*;
import com.kasisoft.libs.common.constants.*;
import com.kasisoft.libs.common.text.*;

import org.apache.commons.lang3.*;
import org.yaml.snakeyaml.*;

import javax.annotation.*;

import java.util.function.*;

import java.util.*;

import java.net.*;

import java.io.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This convenience class allows to setup a tree in order to transform it for various purposes.
 * Be aware that each method prefixed with a lower case 's' opens a scope and thus requires to be closed
 * using an {@link #sEnd()}. The last sequence of {@link #sEnd()} doesn't need to be provided explicitly
 * as it's called automatically.
 * Be aware that misuse of the tree construction might cause an error you have to deal with.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TreeBuilder {

  static final String PN_CLASS = "class";
  static final String PN_ID     = "id";
  static final String PN_KEY    = "key";
  static final String PN_NAME   = "name";
  
  private enum ScopeToken {
    
    SubNodes,
    NodeType,
    ;
    
  }
  
  private enum ImportType {
    
    Yaml,
    Json,
    ;
    
  }
  
  NodeDescriptor          root;
  Stack<NodeDescriptor>   current;
  Stack<ScopeToken>       scopes;
  Stack<String>           defaultNodetype;
  Yaml                    yaml;
  Gson                    gson;
  Map<String, String>     substitution;

  public TreeBuilder() {
    yaml            = new Yaml();
    gson            = new GsonBuilder().create();
    substitution    = new HashMap<>();
    scopes          = new Stack<>();
    current         = new Stack<>();
    defaultNodetype = new Stack<>();
    root            = newNodeDescriptor( "ROOT" );
    pushNodes( root );
  }

  private String getDefaultNodeType() {
    String result = NodeTypes.ContentNode.NAME;
    if( ! defaultNodetype.isEmpty() ) {
      result = defaultNodetype.peek();
    }
    return result;
  }
  
  /**
   * Registers a substitution token.
   *  
   * @param key         The key to be replaced.
   * @param substitute  The substitution.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder substitution( @Nonnull String key, @Nonnull String substitute ) {
    substitution.put( String.format( "${%s}", key ), substitute );
    return this;
  }
  
  private String substitute( String input ) {
    String result = input;
    if( (result != null) && (!substitution.isEmpty()) ) {
      StringBuilder builder = new StringBuilder( result );
      StringFunctions.replace( builder, substitution );
      result = builder.toString();
    }
    return result; 
  }
  
  /**
   * Opens a toplevel node.
   *  
   * @param name   The name of the toplevel node.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sNode( @Nonnull String name ) {
    name                      = substitute( name );
    NodeDescriptor descriptor = newNodeDescriptor( name );
    if( current().subnodes.isEmpty() ) {
      current().subnodes = new ArrayList<>();
    }
    current().subnodes.add( descriptor );
    current.push( descriptor );
    scopes.push( ScopeToken.SubNodes );
    return this;
  }

  private NodeDescriptor current() {
    return current.peek();
  }
  
  private NodeDescriptor newNodeDescriptor( String name ) {
    name                  = substitute( name );
    NodeDescriptor result = new NodeDescriptor();
    result.name           = name;
    result.properties     = Collections.emptyMap();
    result.subnodes       = Collections.emptyList();
    result.nodeType       = getDefaultNodeType();
    return result;
  }

  /**
   * Opens a node on a sublevel with the node type <code>NodeTypes.Content.NAME</code>.
   *  
   * @param name   The name of the sublevel node.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sFolder( @Nonnull String name ) {
    return sNode( name ).nodetype( NodeTypes.Content.NAME );
  }

  /**
   * Opens a node on a sublevel with the node type <code>NodeTypes.ContentNode.NAME</code>.
   *  
   * @param name   The name of the sublevel node.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sContentNode( @Nonnull String name ) {
    return sNode( name ).nodetype( NodeTypes.ContentNode.NAME );
  }

  /**
   * Closes the current scope.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sEnd() {
    ScopeToken token = scopes.pop();
    if( token == ScopeToken.SubNodes ) {
      current.pop();
    } else if( token == ScopeToken.NodeType ) {
      defaultNodetype.pop();
    }
    return this;
  }

  private void pushNodes( NodeDescriptor descriptor ) {
    current.push( descriptor );
    scopes.push( ScopeToken.SubNodes );
  }

  /**
   * Opens a scope with a different default node type.
   * 
   * @param nodetype   The new nodetype to be used.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sDefaultNodetype( @Nonnull String nodetype ) {
    nodetype = substitute( nodetype );
    defaultNodetype.push( nodetype );
    scopes.push( ScopeToken.NodeType );
    return this;
  }
  
  /**
   * Loads the yaml content into the current node.
   * 
   * @param resource   The resource on the classpath.
   * @param encoding   The encoding to be used.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder yaml( @Nonnull String resource, Encoding encoding ) {
    resource                = substitute( resource );
    NodeDescriptor record   = current();
    record.importSource     = resource;
    record.importEncoding   = encoding;
    record.importType       = ImportType.Yaml;
    return this;
  }
  
  /**
   * Loads the yaml content into the current node.
   * 
   * @param resource   The resource on the classpath (assumed to be UTF-8 encoded).
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder yaml( @Nonnull String resource ) {
    return yaml( resource, null );
  }

  /**
   * Loads the json content into the current node.
   * 
   * @param resource   The resource on the classpath.
   * @param encoding   The encoding to be used.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder json( @Nonnull String resource, Encoding encoding ) {
    resource                = substitute( resource );
    NodeDescriptor record   = current();
    record.importSource     = resource;
    record.importEncoding   = encoding;
    record.importType       = ImportType.Json;
    return this;
  }
  
  /**
   * Loads the json content into the current node.
   * 
   * @param resource   The resource on the classpath (assumed to be UTF-8 encoded).
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder json( @Nonnull String resource ) {
    return json( resource, null );
  }

  /**
   * Changes the nodetype for the current node.
   * 
   * @param nodetype   The new nodetype.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder nodetype( @Nonnull String nodetype ) {
    nodetype              = substitute( nodetype );
    NodeDescriptor record = current();
    record.nodeType       = nodetype;
    return this;
  }

  /**
   * Changes a property for the current node. The value will be processed in the following way:
   * 
   * <ul>
   *   <li>Map - The map structure will be considered as a complete subtree</li>
   *   <li>List - The list will be considered as a complete subsequence</li>
   *   <li>Supplier - The supplier will be called to provide the value</li>
   *   <li>An actual value.</li>
   * </ul>
   * 
   * @param name    The name of the property.
   * @param value   The value of the property.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder property( @Nonnull String name, @Nullable Object value ) {
    if( value instanceof String ) {
      value = substitute( (String) value );
    }
    NodeDescriptor record = current();
    if( record.properties.isEmpty() ) {
      record.properties = new HashMap<>();
    }
    record.properties.put( name, value );
    return this;
  }
  
  @Nonnull
  public TreeBuilder clazz( @Nullable Class<?> clazz ) {
    if( clazz != null ) {
      property( PN_CLASS, clazz.getName() );
    }
    return this;
  }
  
  private Map<String, Object> allProperties( NodeDescriptor descriptor ) {
    Map<String, Object> result = null;
    if( descriptor.importSource != null ) {
      result = loadProperties( descriptor );
    }
    if( (result == null) || result.isEmpty() ) {
      result = descriptor.properties;
    } else {
      result.putAll( descriptor.properties );
    }
    return result;
  }

  private Map<String, Object> loadProperties( NodeDescriptor descriptor ) {
    
    URL url = Thread.currentThread().getContextClassLoader().getResource( descriptor.importSource );
    if( url == null ) {
      String msg = error_missing_resource.format( descriptor.importSource );
      log.error( msg );
      throw errorHandler( new IllegalStateException( msg ) );
    }
    
    Map<String, Object> result = null; 
    try( Reader reader = Encoding.openReader( url.openStream(), descriptor.importEncoding ) ){
      result = loadProperties( reader, descriptor.importType );
    } catch( Exception ex ) {
      String msg = error_loading.format( descriptor.importSource, ex.getLocalizedMessage() );
      log.error( msg, ex );
      throw errorHandler(ex);
    }
    return result;
  }
  
  private Map<String, Object> loadProperties( Reader source, ImportType importType ) {
    if( importType == ImportType.Yaml ) {
      return new HashMap<>( (Map<String, Object>) yaml.load( source ) );
    } else {
      return new HashMap<>( gson.fromJson( source, Map.class ) );
    }
  }

  private String toName( Map<String, Object> map, Integer idx ) {
    String result = StringUtils.trimToNull( (String) map.get( PN_NAME ) );
    if( result == null ) {
      result = StringUtils.trimToNull( (String) map.get( PN_ID ) );
    }
    if( result == null ) {
      result = StringUtils.trimToNull( (String) map.get( PN_KEY ) );
    }
    return result;
  }
  
  private IllegalStateException errorHandler( Exception ex ) {
    if( ex instanceof IllegalStateException ) {
      return (IllegalStateException) ex;
    } else {
      return new IllegalStateException(ex);
    }
  }

  /**
   * Produces a data structure according to the current tree configuration.
   * 
   * @param producer   The {@link Producer} instance used to drive the creation process.
   * 
   * @return   The root strcture of the creation process.
   */
  @Nonnull
  public <R> R build( @Nonnull Producer<R> producer ) {
    return build( producer, false );
  }
  
  /**
   * Produces a data structure according to the current tree configuration.
   * 
   * @param producer   The {@link Producer} instance used to drive the creation process.
   * @param fail       <code>true</code> <=> Consider inconsistent nodetypes as an error.
   * 
   * @return   The root strcture of the creation process.
   */
  @Nonnull
  public <R> R build( @Nonnull Producer<R> producer, boolean fail ) {
    try {
      R              result = producer.getRootNode();
      StringFBuilder path   = new StringFBuilder("/");
      for( NodeDescriptor record : root.subnodes ) {
        addNode( producer, result, record, fail, path );
      }
      return result;
    } catch( Exception ex ) {
      throw errorHandler(ex);
    }
  }

  private <R> void addNode( Producer<R> producer, R parent, NodeDescriptor child, boolean fail, StringFBuilder path ) {
    // create/get the node
    int    pathlen   = path.length();
    String nodename  = child.name;
    if( nodename.indexOf('/') != -1 ) {
      String[] parts = nodename.split("/");
      for( int i = 0; i < parts.length - 1; i++ ) {
        parent = producer.getChild( parent, parts[i], child.nodeType, fail );
        path.appendF( "%s/", parts[i] );
      }
      nodename = parts[ parts.length - 1 ];
    }
    path.appendF( "%s/", nodename );
    R childNode = producer.getChild( parent, nodename, child.nodeType, fail );
    // set the properties
    Map<String, Object> allProperties = allProperties( child );
    for( Map.Entry<String, Object> entry : allProperties.entrySet() ) {
      setProperty( producer, childNode, entry.getKey(), entry.getValue(), fail, path );
    }
    // process child elements
    List<NodeDescriptor> children = child.subnodes;
    for( NodeDescriptor record : children ) {
      addNode( producer, childNode, record, fail, path );
    }
    path.setLength( pathlen );
  }
  
  private <R> void setProperty( Producer<R> producer, R node, String key, Object value, boolean fail, StringFBuilder path ) {
    if( value instanceof Map ) {
      setMapProperty( producer, node, key, value, fail, path );
    } else if( value instanceof List ) {
      setListProperty( producer, node, key, value, fail, path );
    } else if( value instanceof Supplier ) {
      Object newvalue = ((Supplier) value).get();
      if( newvalue != value ) {
        setProperty( producer, node, key, newvalue, fail, path );
      }
    } else {
      producer.setBasicProperty( node, key, value );
    }
  }

  private <R> void setListProperty( Producer<R> producer, R node, String key, Object value, boolean fail, StringFBuilder path ) {
    List list      = (List) value;
    R    childNode = producer.getChild( node, key, NodeTypes.ContentNode.NAME, fail );
    if( ! list.isEmpty() ) {
      boolean basictypes = !(list.get(0) instanceof Map);
      if( basictypes ) {
        int i = 0;
        for( Object obj : list ) {
          setProperty( producer, childNode, String.valueOf(i), obj, fail, path );
          i++;
        }
      } else {
        int i = 0;
        for( Object obj : list ) {
          Map    map  = (Map) obj;
          String name = toName( map, i );
          if( name == null ) {
            throw errorHandler( new IllegalStateException( error_cannot_determine_name.format( path ) ) );
          }
          setProperty( producer, childNode, name, map, fail, path );
          i++;
        }
      }
    }
  }

  private <R> void setMapProperty( Producer<R> producer, R node, String key, Object value, boolean fail, StringFBuilder path ) {
    Map<String, Object> map       = (Map<String, Object>) value;
    R                   childNode = producer.getChild( node, key, NodeTypes.ContentNode.NAME, fail );
    for( Map.Entry<String, Object> pair : map.entrySet() ) {
      setProperty( producer, childNode, pair.getKey(), pair.getValue(), fail, path );
    }
  }
  
  @ToString
  private static class NodeDescriptor {

    String                 name;
    String                 nodeType;
    Map<String, Object>    properties;
    List<NodeDescriptor>   subnodes;
    ImportType             importType;
    String                 importSource;
    Encoding               importEncoding;

  } /* ENDCLASS */

} /* ENDCLASS */
