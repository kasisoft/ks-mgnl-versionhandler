package com.kasisoft.mgnl.versionhandler;

import info.magnolia.test.mock.jcr.*;

/**
 * Like {@link NodeProducer} but with the difference that this produces structures for testing.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class MockNodeProducer extends AbstractNodeProducer<MockNode> {
  
  public MockNodeProducer( MockSession jcrSession ) {
    super( jcrSession );
  }
  
} /* ENDCLASS */
