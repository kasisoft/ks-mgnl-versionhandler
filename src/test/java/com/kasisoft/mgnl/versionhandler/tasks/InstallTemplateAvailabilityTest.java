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
      + "(new) /modules/site/config/bobo[mgnl:contentNode]\n"
      + "(new) /modules/site/config/bobo/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/bobo/templates/availability[mgnl:contentNode]\n"
      + "(new) /modules/site/config/bobo/templates/availability/templates[mgnl:contentNode]\n"
      + "(new) /modules/site/config/bobo/templates/availability/templates/mytemplate1[mgnl:contentNode]\n"
      + "@id = 'id:template1'\n"
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

} /* ENDCLASS */
