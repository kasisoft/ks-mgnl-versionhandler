package com.kasisoft.mgnl.versionhandler.tasks;

import static org.mockito.MockitoAnnotations.*;
import static org.testng.Assert.*;

import info.magnolia.repository.*;

import com.kasisoft.mgnl.versionhandler.*;

import org.testng.annotations.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.security.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRoleTaskTest {

  ExtendedMockWebContext   context;
  
  @BeforeClass
  public void setup() {
    
    initMocks( this );
    
    context = ExtendedMockWebContext.builder()
      .workspace( "userroles" )
      .build();
    
    context.install();
        
  }

  @Test
  public void basic() throws Exception {

    assertNull( context.getRoleManager().getRole( "bibo" ) );
    
    InstallRoleTask roleTask = new InstallRoleTask( "bibo", RepositoryConstants.WEBSITE, "/", false, Permission.ADD );
    roleTask.execute( null );

    Role role = context.getRoleManager().getRole( "bibo" );
    assertNotNull( role );
    
    /** @todo [10-Apr-2017:KASI]   write more usable tests */
    
  }
  
} /* ENDCLASS */
