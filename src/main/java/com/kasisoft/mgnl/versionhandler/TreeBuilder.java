package com.kasisoft.mgnl.versionhandler;

import info.magnolia.jcr.util.*;

import org.apache.commons.lang3.*;
import org.yaml.snakeyaml.*;

import javax.annotation.*;

import java.util.function.*;

import java.util.*;

import java.net.*;

import java.io.*;

import java.nio.charset.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This convenience class allows to setup a tree in order to transform it for various purposes.
 * Be aware that each method prefixed with a lower case 's' opens a scope and thus requires to be closed
 * using an {@link #sEnd()}. The last sequence of {@link #sEnd()} doesn't need to be provided explicitly
 * as it's called automatically.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TreeBuilder {

  private static final String PN_KEY = "key";
  private static final String PN_ID = "id";
  private static final String PN_NAME = "name";
  private static final String FMT_ERROR_YAML = "the yaml source '%s' could not be loaded. Cause: %s";
  private static final String FMT_MISSING_YAML = "the yaml source '%s' is not on the classpath.";

  private enum ScopeToken {
    
    SubNodes,
    NodeType,
    ;
    
  }
  
  List<NodeDescriptor>          root;
  Stack<List<NodeDescriptor>>   current;
  Stack<ScopeToken>             scopes;
  Stack<String>                 defaultNodetype;
  Yaml                          yaml;

  public TreeBuilder() {
    yaml            = new Yaml();
    root            = new ArrayList<>();
    scopes          = new Stack<>();
    current         = new Stack<>();
    defaultNodetype = new Stack<>();
    pushNodes( root );
    sNode( "ROOT" );
  }

  private NodeDescriptor descriptor() {
    List<NodeDescriptor> list = current.peek();
    return list.get( list.size() - 1 );
  }

  private List<NodeDescriptor> subnodes( NodeDescriptor record ) {
    List<NodeDescriptor> result = record.getSubnodes();
    if( result.isEmpty() ) {
      result = new ArrayList<>();
      record.setSubnodes( result );
    }
    return result;
  }

  private Map<String, Object> properties( NodeDescriptor record ) {
    Map<String, Object> result = record.getProperties();
    if( result.isEmpty() ) {
      result = new HashMap<>();
      record.setProperties( result );
    }
    return result;
  }

  private String getDefaultNodeType() {
    String result = NodeTypes.ContentNode.NAME;
    if( ! defaultNodetype.isEmpty() ) {
      result = defaultNodetype.peek();
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
  private TreeBuilder sNode( @Nonnull String name ) {
    NodeDescriptor descriptor = new NodeDescriptor();
    descriptor.setName( name );
    descriptor.setProperties( Collections.<String, Object>emptyMap() );
    descriptor.setSubnodes( Collections.<NodeDescriptor>emptyList() );
    descriptor.setNodeType( getDefaultNodeType() );
    current.peek().add( descriptor );
    return this;
  }

  /**
   * Opens a node on a sublevel.
   *  
   * @param name   The name of the sublevel node.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder sSubnode( @Nonnull String name ) {
    NodeDescriptor       record   = descriptor();
    List<NodeDescriptor> subnodes = subnodes( record );
    pushNodes( subnodes );
    return sNode( name );
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
    return sSubnode( name ).nodetype( NodeTypes.Content.NAME );
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
    return sSubnode( name ).nodetype( NodeTypes.ContentNode.NAME );
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

  private void pushNodes( List<NodeDescriptor> descriptors ) {
    current.push( descriptors );
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
  public TreeBuilder yaml( @Nonnull String resource, String encoding ) {
    NodeDescriptor record = descriptor();
    record.setYamlSource   ( resource );
    record.setYamlEncoding ( encoding );
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
   * Changes the nodetype for the current node.
   * 
   * @param nodetype   The new nodetype.
   * 
   * @return   this
   */
  @Nonnull
  public TreeBuilder nodetype( @Nonnull String nodetype ) {
    NodeDescriptor record = descriptor();
    record.setNodeType( nodetype );
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
    NodeDescriptor      record     = descriptor();
    Map<String, Object> properties = properties( record );
    properties.put( name, value );
    return this;
  }
  
  private Map<String, Object> allProperties( NodeDescriptor descriptor ) {
    Map<String, Object> result = null;
    if( descriptor.getYamlSource() != null ) {
      URL url = Thread.currentThread().getContextClassLoader().getResource( descriptor.getYamlSource() );
      if( url == null ) {
        String msg = String.format( FMT_MISSING_YAML, descriptor.getYamlSource() );
        log.error( msg );
        throw errorHandler( new IllegalStateException( msg ) );
      } else {
        String encoding = StringUtils.defaultIfBlank( descriptor.getYamlEncoding(), "UTF-8" );
        try( BufferedReader reader = new BufferedReader( new InputStreamReader( url.openStream(), Charset.forName( encoding ) ) ) ) {
          result = new HashMap<>( (Map<String, Object>) yaml.load( reader ) );
        } catch( Exception ex ) {
          String msg = String.format( FMT_ERROR_YAML, descriptor.getYamlSource(), ex.getLocalizedMessage() );
          log.error( msg, ex );
          throw errorHandler(ex);
        }
      }
    }
    if( result == null ) {
      result = properties( descriptor );
    } else {
      result.putAll( properties( descriptor ) );
    }
    return result;
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
      R result = producer.getRootNode();
      NodeDescriptor nd = root.get(0);
      for( NodeDescriptor record : nd.subnodes ) {
        addNode( producer, result, record, fail );
      }
      return result;
    } catch( Exception ex ) {
      throw errorHandler(ex);
    }
  }

  private <R> void addNode( Producer<R> producer, R parent, NodeDescriptor child, boolean fail ) {
    // create/get the node
    String nodename  = child.getName();
    if( nodename.indexOf('/') != -1 ) {
      String[] parts = nodename.split("/");
      for( int i = 0; i < parts.length - 1; i++ ) {
        parent = producer.getChild( parent, parts[i], child.getNodeType(), fail );
      }
      nodename       = parts[ parts.length - 1 ];
    }
    R childNode = producer.getChild( parent, nodename, child.getNodeType(), fail );
    // set the properties
    Map<String, Object> allProperties = allProperties( child );
    for( Map.Entry<String, Object> entry : allProperties.entrySet() ) {
      setProperty( producer, childNode, entry.getKey(), entry.getValue(), fail );
    }
    // process child elements
    List<NodeDescriptor> children = subnodes( child );
    for( NodeDescriptor record : children ) {
      addNode( producer, childNode, record, fail );
    }
  }
  
  private <R> void setProperty( Producer<R> producer, R node, String key, Object value, boolean fail ) {
    if( value instanceof Map ) {
      setMapProperty( producer, node, key, value, fail );
    } else if( value instanceof List ) {
      setListProperty( producer, node, key, value, fail );
    } else if( value instanceof Supplier ) {
      Object newvalue = ((Supplier) value).get();
      if( newvalue != value ) {
        setProperty( producer, node, key, newvalue, fail );
      }
    } else {
      producer.setBasicProperty( node, key, value );
    }
  }

  private <R> void setListProperty( Producer<R> producer, R node, String key, Object value, boolean fail ) {
    List list      = (List) value;
    R    childNode = producer.getChild( node, key, NodeTypes.ContentNode.NAME, fail );
    if( ! list.isEmpty() ) {
      boolean basictypes = !(list.get(0) instanceof Map);
      if( basictypes ) {
        int i = 0;
        for( Object obj : list ) {
          setProperty( producer, childNode, String.valueOf(i), obj, fail );
          i++;
        }
      } else {
        int i = 0;
        for( Object obj : list ) {
          Map    map  = (Map) obj;
          String name = toName( map, i );
          setProperty( producer, childNode, name, map, fail );
          i++;
        }
      }
    }
  }

  private <R> void setMapProperty( Producer<R> producer, R node, String key, Object value, boolean fail ) {
    Map<String, Object> map       = (Map<String, Object>) value;
    R                   childNode = producer.getChild( node, key, NodeTypes.ContentNode.NAME, fail );
    for( Map.Entry<String, Object> pair : map.entrySet() ) {
      setProperty( producer, childNode, pair.getKey(), pair.getValue(), fail );
    }
  }
  
  @Getter @Setter
  private static class NodeDescriptor {

    String                 name;
    String                 nodeType;
    Map<String, Object>    properties;
    List<NodeDescriptor>   subnodes;
    String                 yamlSource;
    String                 yamlEncoding;

  } /* ENDCLASS */

} /* ENDCLASS */
