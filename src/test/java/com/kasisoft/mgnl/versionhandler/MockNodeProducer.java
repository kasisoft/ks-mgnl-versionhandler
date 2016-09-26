package com.kasisoft.mgnl.versionhandler;

import javax.jcr.*;

import java.util.function.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.jcr.util.*;
import info.magnolia.test.mock.jcr.*;

/**
 * Like {@link NodeProducer} but with the difference that this produces structures for testing.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockNodeProducer implements Producer<MockNode> {

  static final String MSG_INVALID_NODETYPE = "invalid node type. got '%s' instead of '%s'";
  
  MockSession                                  session;
  Function<Exception, IllegalStateException>   handler;
  
  public MockNodeProducer( MockSession jcrSession ) {
    session = jcrSession;
    handler = $ -> new IllegalStateException($);
  }
  
  @Override
  public MockNode getRootNode() {
    return (MockNode) session.getRootNode();
  }
  
  @Override
  public MockNode getChild( MockNode parent, String name, String nodeType, boolean fail ) {
    MockNode result = null;
    try {
      if( parent.hasNode( name ) ) {
        result = (MockNode) parent.getNode( name );
        if( ! result.isNodeType( nodeType ) ) {
          String invalidNodetype = String.format( MSG_INVALID_NODETYPE, result.getPrimaryNodeType().getName(), nodeType );
          if( fail ) {
            handler.apply( new IllegalStateException( invalidNodetype ) );
          } else {
            log.warn( invalidNodetype );
          }
        }
      } else {
        result = (MockNode) parent.addNode( name, nodeType );
      }
    } catch( RepositoryException ex ) {
      throw handler.apply( ex );
    }
    return result;
  }
  
  @Override
  public void setBasicProperty( MockNode node, String key, Object value ) {
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
