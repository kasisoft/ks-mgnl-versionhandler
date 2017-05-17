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
public class InstallThemeResourceMappingTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" );
    
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
  public void includeTheme() {
    
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" )
      .includeThemeName();
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping/cssFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/dodo-theme/css/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/css/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/fontFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/dodo-theme/fonts/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/fonts/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/iconFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/dodo-theme/icons/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/icons/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/jsFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/dodo-theme/js/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/js/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void cssFolderName() {
    
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" )
      .cssFolderName( "bibo-css" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping/cssFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/bibo-css/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/bibo-css/$1'\n"
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
  public void jsFolderName() {
    
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" )
      .javascriptFolderName( "bibo-js" );
    
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
      + "@fromURI = '^/bibo-js/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/bibo-js/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void fontsFolderName() {
    
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" )
      .fontsFolderName( "bibo-fonts" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodod-module/virtualURIMapping/cssFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/css/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/css/$1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodod-module/virtualURIMapping/fontFiles[mgnl:contentNode]\n"
      + "@fromURI = '^/bibo-fonts/(.+)$'\n"
      + "@toURI = 'forward:/.resources/dodo-theme/bibo-fonts/$1'\n"
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
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallThemeResourceMapping tb = new InstallThemeResourceMapping( "dodod-module", "dodo-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }
  
} /* ENDCLASS */
