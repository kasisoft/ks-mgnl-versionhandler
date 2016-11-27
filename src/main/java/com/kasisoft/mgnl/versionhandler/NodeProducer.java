package com.kasisoft.mgnl.versionhandler;

import javax.jcr.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeProducer extends AbstractNodeProducer<Node> {

  public NodeProducer( Session jcrSession ) {
    super( jcrSession );
  }
  
} /* ENDCLASS */
