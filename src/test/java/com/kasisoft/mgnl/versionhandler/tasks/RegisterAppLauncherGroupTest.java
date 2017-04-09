package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import java.awt.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAppLauncherGroupTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    RegisterAppLauncherGroup registerAppLauncherGroup = new RegisterAppLauncherGroup( "newgroup", Color.yellow );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups[mgnl:contentNode]\n"
      + "(new) /modules/ui-admincentral/config/appLauncherLayout/groups/newgroup[mgnl:contentNode]\n"
      + "@color = '#ffff00'\n"
      ;
    
    String desc = buildDescription( registerAppLauncherGroup );
    assertThat( desc, is( expected ) );
    
  }
  
  @Test
  public void hasTitle() {
    RegisterAppLauncherGroup registerAppLauncherGroup = new RegisterAppLauncherGroup( "newgroup", Color.yellow );
    assertNotNull( StringFunctions.cleanup( registerAppLauncherGroup.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    RegisterAppLauncherGroup registerAppLauncherGroup = new RegisterAppLauncherGroup( "newgroup", Color.yellow );
    assertNotNull( StringFunctions.cleanup( registerAppLauncherGroup.getDescription() ) );
  }

} /* ENDCLASS */
