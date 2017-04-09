package com.kasisoft.mgnl.versionhandler.tasks;

import static org.testng.Assert.*;

import com.kasisoft.mgnl.versionhandler.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
abstract class AbstractTreeBuilderProvider {

  DescriptiveProducer   producer = new DescriptiveProducer();

  protected String buildDescription( TreeBuilderProvider provider ) {
    assertNotNull( provider );
    TreeBuilder builder = provider.create();
    assertNotNull( builder );
    return builder.build( producer ).toString();
  }
  

} /* ENDCLASS */
