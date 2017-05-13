package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlushAllForWorkspaceTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    FlushAllForWorkspace tb = new FlushAllForWorkspace( "bibo" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/cache[mgnl:contentNode]\n"
      + "(new) /modules/cache/config[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/default[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/default/flushPolicy[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/default/flushPolicy/policies[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/default/flushPolicy/policies/flushAll[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/default/flushPolicy/policies/flushAll/repositories[mgnl:contentNode]\n"
      + "@bibo = 'bibo'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void configuration() {
    
    FlushAllForWorkspace tb = new FlushAllForWorkspace( "bibo" )
      .configuration( "bronco" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/cache[mgnl:contentNode]\n"
      + "(new) /modules/cache/config[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/bronco[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/bronco/flushPolicy[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/bronco/flushPolicy/policies[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/bronco/flushPolicy/policies/flushAll[mgnl:contentNode]\n"
      + "(new) /modules/cache/config/configurations/bronco/flushPolicy/policies/flushAll/repositories[mgnl:contentNode]\n"
      + "@bibo = 'bibo'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    FlushAllForWorkspace tb = new FlushAllForWorkspace( "bibo" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    FlushAllForWorkspace tb = new FlushAllForWorkspace( "bibo" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }
  
} /* ENDCLASS */
