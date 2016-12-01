package com.kasisoft.mgnl.versionhandler;

import javax.jcr.*;

/**
 * This producer generates a JCR based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class NodeProducer extends AbstractNodeProducer<Node> {

  public NodeProducer( Session jcrSession ) {
    super( jcrSession );
  }
  
} /* ENDCLASS */
