package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRestServiceTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallRestService service = new InstallRestService( String.class, List.class );
    
    String expected = ""
      + "(new) /modules[mgnl:folder]\n"
      + "(new) /modules/rest-services[mgnl:folder]\n"
      + "(new) /modules/rest-services/rest-endpoints[mgnl:folder]\n"
      + "(new) /modules/rest-services/rest-endpoints/list[mgnl:contentNode]\n"
      + "@implementationClass = 'java.util.List'\n"
      + "@class = 'java.lang.String'\n"
      ;
    
    String desc = buildDescription( service );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void named() {
    
    InstallRestService service = new InstallRestService( String.class, List.class )
      .serviceName( "bibo" );
    
    String expected = ""
      + "(new) /modules[mgnl:folder]\n"
      + "(new) /modules/rest-services[mgnl:folder]\n"
      + "(new) /modules/rest-services/rest-endpoints[mgnl:folder]\n"
      + "(new) /modules/rest-services/rest-endpoints/bibo[mgnl:contentNode]\n"
      + "@implementationClass = 'java.util.List'\n"
      + "@class = 'java.lang.String'\n"
      ;
    
    String desc = buildDescription( service );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallRestService service = new InstallRestService( String.class, List.class );
    assertNotNull( StringFunctions.cleanup( service.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallRestService service = new InstallRestService( String.class, List.class );
    assertNotNull( StringFunctions.cleanup( service.getDescription() ) );
  }
  
} /* ENDCLASS */
