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
public class InstallThemeTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallTheme tb = new InstallTheme( "my-module", "my-theme" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/my-module[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes/my-theme[mgnl:contentNode]\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void resources() {
    
    InstallTheme tb = new InstallTheme( "my-module", "my-theme" )
      .resourceFile( "main", "/css/main.css", null )
      .resourceFile( "print", "/css/print.css", "print" )
      .resourceFile( "main", "/js/main.js", null );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/my-module[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes/my-theme[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes/my-theme/cssFiles[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes/my-theme/cssFiles/main[mgnl:contentNode]\n"
      + "@link = '/css/main.css'\n"
      + "@media = 'all'\n"
      + "(new) /modules/my-module/themes/my-theme/cssFiles/print[mgnl:contentNode]\n"
      + "@link = '/css/print.css'\n"
      + "@media = 'print'\n"
      + "(new) /modules/my-module/themes/my-theme/jsFiles[mgnl:contentNode]\n"
      + "(new) /modules/my-module/themes/my-theme/jsFiles/main[mgnl:contentNode]\n"
      + "@link = '/js/main.js'\n"
      + "@media = 'all'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallSite tb = new InstallSite( "my-module", "my-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallSite tb = new InstallSite( "my-module", "my-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

  public static class DummyAvailability {
  } /* ENDCLASS */
  
} /* ENDCLASS */
