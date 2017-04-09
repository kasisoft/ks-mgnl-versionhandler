package com.kasisoft.mgnl.versionhandler;

import com.kasisoft.libs.common.text.*;

import java.util.function.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @todo [09-Apr-2017:KASI]   Base the description process upon the {@link MapProducer} as altering/extensions
 *                            to the tree is somewhat complicated using a {@link StringFBuilder}.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = {"text"})
public class DescriptiveProducer implements Producer<StringFBuilder> {

  Function<Exception, IllegalStateException>   handler;
  StringFBuilder                               text;
  boolean                                      nodeTypes;
  
  public DescriptiveProducer() {
    this( false );
  }
  
  public DescriptiveProducer( boolean nt ) {
    handler   = $ -> new IllegalStateException($);
    nodeTypes = nt;
  }
  
  @Override
  public StringFBuilder getRootNode() {
    text = new StringFBuilder();
    return text;
  }
  
  @Override
  public StringFBuilder getChild( String parentPath, StringFBuilder parent, String name, String nodeType, boolean fail ) {
    if( "/".equals( parentPath ) ) {
      parentPath = "";
    } else if( parentPath.endsWith("/") ) {
      parentPath = parentPath.substring( 0, parentPath.length() - 1 );
    }
    String line = String.format( "(new) %s/%s%s\n", parentPath, name, nodeTypes ? ("[" + nodeType + "]") : "" );
    if( parent.indexOf( line ) == -1 ) {
      parent.appendF( line );
    }
    return parent;
  }

  @Override
  public void setBasicProperty( StringFBuilder node, String key, Object value ) {
    if( value == null ) {
      node.appendF( "@%s = null\n", key );
    } else {
      node.appendF( "@%s = '%s'\n", key, value );
    }
  }

  @Override
  public void setErrorHandler( Function<Exception, IllegalStateException> errorHandler ) {
    handler = errorHandler;
    if( handler == null ) {
      handler = $ -> new IllegalStateException($);
    }
  }
  
} /* ENDCLASS */
