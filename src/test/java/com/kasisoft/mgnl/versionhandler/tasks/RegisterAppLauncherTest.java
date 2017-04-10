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
public class RegisterAppLauncherTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    RegisterAppLauncher tb = new RegisterAppLauncher( "newgroup", "myapp" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups/newgroup[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups/newgroup/apps[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups/newgroup/apps/myapp[mgnl:contentNode]\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }
  
  @Test
  public void hasTitle() {
    RegisterAppLauncher tb = new RegisterAppLauncher( "newgroup", "myapp" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    RegisterAppLauncher tb = new RegisterAppLauncher( "newgroup", "myapp" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

} /* ENDCLASS */
