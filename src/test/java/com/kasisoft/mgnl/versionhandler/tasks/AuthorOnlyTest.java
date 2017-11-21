package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.util.JcrProperties.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.testng.Assert.*;

import info.magnolia.repository.*;

import info.magnolia.test.mock.jcr.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.jcr.util.*;

import com.kasisoft.mgnl.versionhandler.*;

import org.testng.annotations.*;

import org.mockito.*;

import javax.jcr.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorOnlyTest {

  @Mock
  InstallContext           installContext;
  
  ExtendedMockWebContext   context;
  MockSession              mockSession;
  
  @SuppressWarnings("deprecation")
  @BeforeClass
  public void setup() throws Exception {
    
    initMocks( this );
    
    context = ExtendedMockWebContext.builder()
      .workspace( RepositoryConstants.CONFIG )
      .build();
    
    context.install();
    
    TreeBuilder builder = new TreeBuilder()
      .sContentNode( "server" )
      .sEnd();
    
    mockSession = (MockSession) context.getJCRSession( RepositoryConstants.CONFIG );
    builder.build( new MockNodeProducer( mockSession ) );
    
    when( installContext.getJCRSession( RepositoryConstants.CONFIG ) ).thenReturn( mockSession );
    
  }

  private TreeBuilderProvider newTreeBuilderProvider() {
    return new RegisterAppLauncher( "newgroup", "myapp" ) {

      @Override
      public Boolean authorOnly() {
        return Boolean.TRUE;
      }

      @Override
      public List<Task> postExecute() {
        return Arrays.asList(
          new KsSetPropertyTask( "/server@dodo", "hotte" )
        );
      }
      
    };
  }
  
  @Test(expectedExceptions = RepositoryException.class)
  public void notAnAuthorInstance() throws Exception {

    Admin.setValue( false );
    
    JcrConfigurationTask task = new JcrConfigurationTask( newTreeBuilderProvider() );
    task.execute( installContext );
    
    Session session = context.getJCRSession( RepositoryConstants.CONFIG );
    Node    server  = session.getNode( "server" );
    assertNotNull( server );
    
    String postExecutePropertyValue = PropertyUtil.getString( server, "dodo" );
    assertNull( postExecutePropertyValue );
    
    session.getNode( "/modules/ui-admincentral/config/appLauncherLayout/groups/newgroup/apps/myapp" );
    fail( "node lookup should fail as it had not been executed" );
    
  }
  
  @Test(dependsOnMethods = "notAnAuthorInstance")
  public void onAuthorInstance() throws Exception {
    
    Admin.setValue( true );

    JcrConfigurationTask task = new JcrConfigurationTask( newTreeBuilderProvider() );
    task.execute( installContext );
    
    Session session = context.getJCRSession( RepositoryConstants.CONFIG );
    Node    server  = session.getNode( "server" );
    String postExecutePropertyValue = PropertyUtil.getString( server, "dodo" );
    assertThat( postExecutePropertyValue, is( "hotte" ) );
    
    Node    node    = session.getNode( "/modules/ui-admincentral/config/appLauncherLayout/groups/newgroup/apps/myapp" );
    assertNotNull( node );
    
  }
  
} /* ENDCLASS */
