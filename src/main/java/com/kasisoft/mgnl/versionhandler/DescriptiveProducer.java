package com.kasisoft.mgnl.versionhandler;

import com.kasisoft.libs.common.text.*;

import java.util.function.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class DescriptiveProducer implements Producer<StringFBuilder> {

  private Function<Exception, IllegalStateException>   handler;
  private StringFBuilder                               text;
  
  public DescriptiveProducer() {
    handler = $ -> new IllegalStateException($);
    text    = new StringFBuilder();
  }
  
  @Override
  public StringFBuilder getRootNode() {
    return text;
  }
  
  @Override
  public StringFBuilder getChild( String parentPath, StringFBuilder parent, String name, String nodeType, boolean fail ) {
    if( "/".equals( parentPath ) ) {
      parentPath = "";
    } else if( parentPath.endsWith("/") ) {
      parentPath = parentPath.substring( 0, parentPath.length() - 1 );
    }
    parent.appendF( "(new) %s/%s\n", parentPath, name );
    return parent;
  }

  @Override
  public void setBasicProperty( StringFBuilder node, String key, Object value ) {
    node.appendF( "@%s = '%s'\n", key, value );
  }

  @Override
  public void setErrorHandler( Function<Exception, IllegalStateException> errorHandler ) {
    handler = errorHandler;
    if( handler == null ) {
      handler = $ -> new IllegalStateException($);
    }
  }
  
  @Override
  public String toString() {
    return text.toString();
  }

} /* ENDCLASS */