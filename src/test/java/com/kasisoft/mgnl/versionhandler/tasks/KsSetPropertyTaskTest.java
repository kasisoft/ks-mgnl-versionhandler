package com.kasisoft.mgnl.versionhandler.tasks;

import static org.mockito.MockitoAnnotations.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import info.magnolia.repository.*;

import info.magnolia.module.model.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.context.*;

import info.magnolia.jcr.util.*;

import com.kasisoft.mgnl.versionhandler.*;

import org.testng.annotations.*;

import org.mockito.*;

import javax.jcr.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KsSetPropertyTaskTest {

  @Mock
  InstallContext      installContext;
  
  @Mock
  ModuleDefinition    moduleDefinition;
  
  @BeforeMethod
  public void setup() throws Exception {
    
    initMocks( this );
    
    when( moduleDefinition.getName() ).thenReturn( "fluffy" );
    when( installContext.getCurrentModuleDefinition() ).thenReturn( moduleDefinition );
    
    ExtendedMockWebContext.builder()
      .workspace( RepositoryConstants.CONFIG )
      .workspace( RepositoryConstants.WEBSITE )
      .build()
      .install();
    
    Session config  = MgnlContext.getJCRSession( RepositoryConstants.CONFIG  );
    Session website = MgnlContext.getJCRSession( RepositoryConstants.WEBSITE );
    when( installContext.getJCRSession( RepositoryConstants.CONFIG  ) ).thenReturn( config );
    when( installContext.getJCRSession( RepositoryConstants.WEBSITE ) ).thenReturn( website );
    
  }
  
  @Test(expectedExceptions = TaskExecutionException.class)
  public void error() throws Exception {
    KsSetPropertyTask task = new KsSetPropertyTask( "/modules/fluffy@key", "my-value" );
    task.execute( installContext );
  }

  @Test(dependsOnMethods = "error")
  public void basic() throws Exception {
    
    Session session = MgnlContext.getJCRSession( RepositoryConstants.CONFIG );
    Node    modules = session.getRootNode().addNode( "modules" );
    Node    node    = modules.addNode( "fluffy" );
    
    KsSetPropertyTask task = new KsSetPropertyTask( "/modules/fluffy@key", "my-value" );
    task.execute( installContext );
    
    assertThat( PropertyUtil.getString( node, "key" ), is( "my-value" ) );
    
  }

  @Test(dependsOnMethods = "error")
  public void workspace() throws Exception {
    
    Session session = MgnlContext.getJCRSession( RepositoryConstants.WEBSITE );
    Node    modules = session.getRootNode().addNode( "page" );
    Node    node    = modules.addNode( "fluffy" );
    
    KsSetPropertyTask task = new KsSetPropertyTask( "/page/fluffy@key", "my-value" )
      .workspace( RepositoryConstants.WEBSITE );
    task.execute( installContext );
    
    assertThat( PropertyUtil.getString( node, "key" ), is( "my-value" ) );
    
  }

} /* ENDCLASS */
