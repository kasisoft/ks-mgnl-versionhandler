package com.kasisoft.mgnl.versionhandler;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.test.mock.jcr.*;

/**
 * Like {@link NodeProducer} but with the difference that this produces structures for testing.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockNodeProducer extends AbstractNodeProducer<MockNode> {
  
  public MockNodeProducer( MockSession jcrSession ) {
    super( jcrSession );
  }
  
} /* ENDCLASS */
