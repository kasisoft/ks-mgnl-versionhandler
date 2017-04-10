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
public class InstallMimeTypeTest extends AbstractTreeBuilderProvider {

  @Test
  public void single() {
    
    InstallMimeType tb = new InstallMimeType( ".iff", "image/iff" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/imageIff[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = null\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void singleWithName() {
    
    InstallMimeType tb = new InstallMimeType( "bigBird", ".iff", "image/iff" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/bigBird[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = null\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void singleWithIcon() {
    
    InstallMimeType tb = new InstallMimeType( null, ".iff", "image/iff", "icon-trashcan" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/imageIff[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = 'icon-trashcan'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void singleWithNameAndIcon() {
    
    InstallMimeType tb = new InstallMimeType( "bigBird", ".iff", "image/iff", "icon-trashcan" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/bigBird[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = 'icon-trashcan'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void many() {
    
    InstallMimeType tb = new InstallMimeType( ".iff", "image/iff" )
      .mimeType( "oscar", ".png", "image/png", "icon-goofy" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/imageIff[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = null\n"
      + "(new) /server/MIMEMapping/oscar[mgnl:contentNode]\n"
      + "@extension = '.png'\n"
      + "@mime-type = 'image/png'\n"
      + "@icon = 'icon-goofy'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void manyWithName() {
    
    InstallMimeType tb = new InstallMimeType( "bigBird", ".iff", "image/iff" )
      .mimeType( "oscar", ".png", "image/png", "icon-goofy" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/bigBird[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = null\n"
      + "(new) /server/MIMEMapping/oscar[mgnl:contentNode]\n"
      + "@extension = '.png'\n"
      + "@mime-type = 'image/png'\n"
      + "@icon = 'icon-goofy'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void manyWithIcon() {
    
    InstallMimeType tb = new InstallMimeType( null, ".iff", "image/iff", "icon-trashcan" )
      .mimeType( "oscar", ".png", "image/png", "icon-goofy" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/imageIff[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = 'icon-trashcan'\n"
      + "(new) /server/MIMEMapping/oscar[mgnl:contentNode]\n"
      + "@extension = '.png'\n"
      + "@mime-type = 'image/png'\n"
      + "@icon = 'icon-goofy'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void manyWithNameAndIcon() {
    
    InstallMimeType tb = new InstallMimeType( "bigBird", ".iff", "image/iff", "icon-trashcan" )
      .mimeType( "oscar", ".png", "image/png", "icon-goofy" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping[mgnl:contentNode]\n"
      + "(new) /server/MIMEMapping/bigBird[mgnl:contentNode]\n"
      + "@extension = '.iff'\n"
      + "@mime-type = 'image/iff'\n"
      + "@icon = 'icon-trashcan'\n"
      + "(new) /server/MIMEMapping/oscar[mgnl:contentNode]\n"
      + "@extension = '.png'\n"
      + "@mime-type = 'image/png'\n"
      + "@icon = 'icon-goofy'\n"
      ;

    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }
  
  @Test
  public void hasTitle() {
    InstallMimeType tb = new InstallMimeType( ".iff", "image/iff" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallMimeType tb = new InstallMimeType( ".iff", "image/iff" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

} /* ENDCLASS */
