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
public class ConfigureWorkspaceMappingTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/gollum[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void fromUri() {
    
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" )
      .fromUri( "/start" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/gollum[mgnl:contentNode]\n"
      + "@fromURI = '/start'\n"
      + "@toURI = '/'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void toUri() {
    
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" )
      .toUri( "/end" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/gollum[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/end'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void fromAndToUri() {
    
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" )
      .fromUri( "/start" )
      .toUri( "/end" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/gollum[mgnl:contentNode]\n"
      + "@fromURI = '/start'\n"
      + "@toURI = '/end'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    ConfigureWorkspaceMapping tb = new ConfigureWorkspaceMapping( "toto", "/modules/gollum" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

} /* ENDCLASS */
