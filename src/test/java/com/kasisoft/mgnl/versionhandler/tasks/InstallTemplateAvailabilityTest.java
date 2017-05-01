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
public class InstallTemplateAvailabilityTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallTemplateAvailability tb = new InstallTemplateAvailability( "bobo" )
      .templateDeclarations( new TemplateDeclaration( "mytemplate1", "id:template1", false ) );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability/templates/mytemplate1[mgnl:contentNode]\n"
      + "@id = 'id:template1'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void templateAvailability() {
    
    InstallTemplateAvailability tb = new InstallTemplateAvailability( "bobo" )
      .templateAvailability( DummyTemplateAvailability.class )
      .templateDeclarations( new TemplateDeclaration( "mytemplate4", "id:template4", false ) );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability[mgnl:contentNode]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallTemplateAvailabilityTest$DummyTemplateAvailability'\n"
      + "(new) /modules/site/config/site/templates/availability/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability/templates/mytemplate4[mgnl:contentNode]\n"
      + "@id = 'id:template4'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void prototype() {
    
    InstallTemplateAvailability tb = new InstallTemplateAvailability( "bobo" )
      .prototypeId( "kasisoft:bibo" )
      .templateDeclarations( new TemplateDeclaration( "mytemplate5", "id:template5", false ) );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates[mgnl:contentNode]\n"
      + "@prototypeId = 'kasisoft:bibo'\n"
      + "@class = 'info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings'\n"
      + "(new) /modules/site/config/site/templates/availability[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/site/templates/availability/templates/mytemplate5[mgnl:contentNode]\n"
      + "@id = 'id:template5'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallTemplateAvailability tb = new InstallTemplateAvailability( "bobo" )
      .templateDeclarations( new TemplateDeclaration( "mytemplate2", "id:template2", false ) );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallTemplateAvailability tb = new InstallTemplateAvailability( "bobo" )
      .templateDeclarations( new TemplateDeclaration( "mytemplate3", "id:template3", false ) );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

  private static class DummyTemplateAvailability {
  } /* ENDCLASS */
  
} /* ENDCLASS */
