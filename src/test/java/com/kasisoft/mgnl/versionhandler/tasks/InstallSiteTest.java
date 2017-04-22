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
public class InstallSiteTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallSite tb = new InstallSite( "my-site", "my-theme" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "@label = 'my-site'\n"
      + "(new) /modules/site/config/site/theme[mgnl:contentNode]\n"
      + "@name = 'my-theme'\n"
      + "@class = 'info.magnolia.module.site.theme.ThemeReference'\n"
      + "(new) /modules/site/config/site/i18n[mgnl:contentNode]\n"
      + "@defaultLocale = 'de'\n"
      + "@fallbackLocale = 'de'\n"
      + "@class = 'info.magnolia.cms.i18n.DefaultI18nContentSupport'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/i18n/locales/en[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'en'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales/de[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'de'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/mappings[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/mappings/website[mgnl:contentNode]\n"
      + "@handlePrefix = '/'\n"
      + "@URIPrefix = '/'\n"
      + "@repository = 'website'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void prototypeId() {
    
    InstallSite tb = new InstallSite( "my-site", "my-theme" )
      .prototypeId( "gollum:prototype" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "@label = 'my-site'\n"
      + "(new) /modules/site/config/site/theme[mgnl:contentNode]\n"
      + "@name = 'my-theme'\n"
      + "@class = 'info.magnolia.module.site.theme.ThemeReference'\n"
      + "(new) /modules/site/config/site/i18n[mgnl:contentNode]\n"
      + "@defaultLocale = 'de'\n"
      + "@fallbackLocale = 'de'\n"
      + "@class = 'info.magnolia.cms.i18n.DefaultI18nContentSupport'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/i18n/locales/en[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'en'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales/de[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'de'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/mappings[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/mappings/website[mgnl:contentNode]\n"
      + "@handlePrefix = '/'\n"
      + "@URIPrefix = '/'\n"
      + "@repository = 'website'\n"
      + "(new) /modules/site/config/site/templates[mgnl:contentNode]\n"
      + "@prototypeId = 'gollum:prototype'\n"
      + "@class = 'info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void availability() {
    
    InstallSite tb = new InstallSite( "my-site", "my-theme" )
      .templateAvailability( DummyAvailability.class );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "@label = 'my-site'\n"
      + "(new) /modules/site/config/site/theme[mgnl:contentNode]\n"
      + "@name = 'my-theme'\n"
      + "@class = 'info.magnolia.module.site.theme.ThemeReference'\n"
      + "(new) /modules/site/config/site/i18n[mgnl:contentNode]\n"
      + "@defaultLocale = 'de'\n"
      + "@fallbackLocale = 'de'\n"
      + "@class = 'info.magnolia.cms.i18n.DefaultI18nContentSupport'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/i18n/locales/en[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'en'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/i18n/locales/de[mgnl:contentNode]\n"
      + "@country = ''\n"
      + "@language = 'de'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/site/mappings[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/mappings/website[mgnl:contentNode]\n"
      + "@handlePrefix = '/'\n"
      + "@URIPrefix = '/'\n"
      + "@repository = 'website'\n"
      + "(new) /modules/site/config/site/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability[mgnl:contentNode]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallSiteTest$DummyAvailability'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallSite tb = new InstallSite( "my-site", "my-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallSite tb = new InstallSite( "my-site", "my-theme" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

  public static class DummyAvailability {
  } /* ENDCLASS */
  
} /* ENDCLASS */
