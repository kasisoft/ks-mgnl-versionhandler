package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.jcr.util.*;

import javax.jcr.*;

import java.util.function.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeProducer implements Producer<Node> {

  Session                                      session;
  Function<Exception, IllegalStateException>   handler;
  
  public NodeProducer( Session jcrSession ) {
    session = jcrSession;
    handler = $ -> new IllegalStateException($);
  }
  
  @Override
  public Node getRootNode() {
    try {
      return session.getRootNode();
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
    }
  }
  
  @Override
  public Node getChild( Node parent, String name, String nodeType, boolean fail ) {
    try {
      return getChildImpl( parent, name, nodeType, fail );
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
    }
  }

  private Node getChildImpl( Node parent, String name, String nodeType, boolean fail ) throws RepositoryException {
    Node result = null;
    if( parent.hasNode( name ) ) {
      result = parent.getNode( name );
      if( ! result.isNodeType( nodeType ) ) {
        String invalidNodetype = error_invalid_nodetype.format( parent.getPath(), name, result.getPrimaryNodeType().getName(), nodeType );
        if( fail ) {
          handler.apply( new IllegalStateException( invalidNodetype ) );
        } else {
          log.warn( invalidNodetype );
        }
      }
    } else {
      result = parent.addNode( name, nodeType );
    }
    return result;
  }
  
  @Override
  public void setBasicProperty( Node node, String key, Object value ) {
    try {
      PropertyUtil.setProperty( node, key, value );
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
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
