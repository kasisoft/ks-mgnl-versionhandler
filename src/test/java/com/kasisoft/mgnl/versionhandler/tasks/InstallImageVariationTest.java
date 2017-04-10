package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.util.model.*;

import org.testng.annotations.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallImageVariationTest extends AbstractTreeBuilderProvider {

  @BeforeClass
  public void setup() {
    BreakpointDeclaration.loadDefaultBreakpoints();
  }
  
  @Test
  public void breakpoint() {
    
    InstallImageVariation tb = new InstallImageVariation( "my-theme" )
      .variation( DummyVariation.class, BreakpointDeclaration.DEFAULT );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme/imaging[mgnl:contentNode]\n"
      + "@class = 'info.magnolia.templating.imaging.VariationAwareImagingSupport'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/themes/my-theme/imaging/variations[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme/imaging/variations/ls[mgnl:contentNode]\n"
      + "@width = '1200'\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallImageVariationTest$DummyVariation'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void named() {
    
    InstallImageVariation tb = new InstallImageVariation( "my-theme" )
      .variation( DummyVariation.class, "Gollum", 500, 300 );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme/imaging[mgnl:contentNode]\n"
      + "@class = 'info.magnolia.templating.imaging.VariationAwareImagingSupport'\n"
      + "@enabled = 'true'\n"
      + "(new) /modules/site/config/themes/my-theme/imaging/variations[mgnl:contentNode]\n"
      + "(new) /modules/site/config/themes/my-theme/imaging/variations/Gollum[mgnl:contentNode]\n"
      + "@width = '500'\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallImageVariationTest$DummyVariation'\n"
      + "@height = '300'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallImageVariation tb = new InstallImageVariation( "my-theme" )
      .variation( DummyVariation.class, "Gollum", 500, 300 );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallImageVariation tb = new InstallImageVariation( "my-theme" )
      .variation( DummyVariation.class, "Gollum", 500, 300 );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

  public static class DummyVariation {
  } /* ENDCLASS */
  
} /* ENDCLASS */
