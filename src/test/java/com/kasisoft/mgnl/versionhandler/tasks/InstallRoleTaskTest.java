package com.kasisoft.mgnl.versionhandler.tasks;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.testng.Assert.*;

import info.magnolia.repository.*;

import info.magnolia.test.*;

import com.kasisoft.mgnl.versionhandler.*;

import org.testng.annotations.*;

import org.mockito.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.security.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRoleTaskTest {

  @Mock
  SecuritySupport   secSupport;
  
  RoleManager       roleManager;
  
  @BeforeClass
  public void setup() {
    
    initMocks( this );
    
    roleManager = new MgnlRoleManager();
    when( secSupport.getRoleManager() ).thenReturn( roleManager );
    
    ExtendedMockWebContext.builder()
      .workspace( "userroles" )
      .build()
      .install();
    
    ComponentsTestUtil.setInstance( SecuritySupport.class, secSupport );
    
  }

  @Test
  public void basic() throws Exception {

    assertNull( roleManager.getRole( "bibo" ) );
    
    InstallRoleTask roleTask = new InstallRoleTask( "bibo", RepositoryConstants.WEBSITE, "/", false, Permission.ADD );
    roleTask.execute( null );

    Role role = roleManager.getRole( "bibo" );
    assertNotNull( role );
    
    /** @todo [10-Apr-2017:KASI]   write more usable tests */
    
  }
  
} /* ENDCLASS */
