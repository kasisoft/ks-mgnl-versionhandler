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
    
    InstallTheme tb = new InstallTheme( "dodod-module", "dodo-theme" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping/cssFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/css/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/css/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/fontFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/fonts/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/fonts/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/iconFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/icons/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/icons/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/jsFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/js/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/js/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallTheme tb = new InstallTheme( "dodod-module", "dodo-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallTheme tb = new InstallTheme( "dodod-module", "dodo-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }
  
} /* ENDCLASS */
