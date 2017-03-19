package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.jcr.util.*;

import org.slf4j.*;

import javax.jcr.*;

import java.util.function.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class AbstractNodeProducer<R extends Node> implements Producer<R> {

  private static final Logger log = LoggerFactory.getLogger( AbstractNodeProducer.class );
  
  private Session                                      session;
  private Function<Exception, IllegalStateException>   handler;
  
  public AbstractNodeProducer( Session jcrSession ) {
    session = jcrSession;
    handler = $ -> new IllegalStateException($);
  }
  
  @Override
  public R getRootNode() {
    try {
      return (R) session.getRootNode();
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
    }
  }
  
  @Override
  public R getChild( String parentPath, R parent, String name, String nodeType, boolean fail ) {
    try {
      return getChildImpl( parentPath, parent, name, nodeType, fail );
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
    }
  }

  private R getChildImpl( String parentPath, R parent, String name, String nodeType, boolean fail ) throws RepositoryException {
    R result = null;
    if( parent.hasNode( name ) ) {
      result = (R) parent.getNode( name );
      if( ! result.isNodeType( nodeType ) ) {
        if( parentPath.endsWith("/") ) {
          if( parentPath.length() == 1 ) {
            parentPath = "";
          } else {
            parentPath = parentPath.substring( 0, parentPath.length() - 1 );
          }
        }
        String invalidNodetype = error_invalid_nodetype.format( parentPath, name, result.getPrimaryNodeType().getName(), nodeType );
        if( fail ) {
          handler.apply( new IllegalStateException( invalidNodetype ) );
        } else {
          log.warn( invalidNodetype );
        }
      }
    } else {
      result = (R) parent.addNode( name, nodeType );
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
