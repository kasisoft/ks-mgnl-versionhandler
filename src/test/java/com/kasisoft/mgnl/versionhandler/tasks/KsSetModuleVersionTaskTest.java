package com.kasisoft.mgnl.versionhandler.tasks;

import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

import info.magnolia.repository.*;

import info.magnolia.module.model.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.context.*;

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
public class KsSetModuleVersionTaskTest {

  @Mock
  InstallContext      installContext;
  
  @Mock
  ModuleDefinition    moduleDefinition;
  
  @BeforeClass
  public void setup() throws Exception {
    
    initMocks( this );
    
    when( moduleDefinition.getName() ).thenReturn( "fluffy" );
    when( installContext.getCurrentModuleDefinition() ).thenReturn( moduleDefinition );
    
    ExtendedMockWebContext.builder()
      .workspace( RepositoryConstants.CONFIG )
      .build()
      .install();
    
    Session config = MgnlContext.getJCRSession( RepositoryConstants.CONFIG );
    when( installContext.getJCRSession( RepositoryConstants.CONFIG  ) ).thenReturn( config );
    
  }
  
  @Test(expectedExceptions = TaskExecutionException.class)
  public void error() throws Exception {
    KsSetModuleVersionTask task = new KsSetModuleVersionTask( "fluffy", "key", 1 );
    task.execute( installContext );
  }

  @Test(dependsOnMethods = "error")
  public void basic() throws Exception {
    
    Session session = MgnlContext.getJCRSession( RepositoryConstants.CONFIG );
    Node    modules = session.getRootNode().addNode( "modules" );
    modules.addNode( "fluffy" );
    
    KsSetModuleVersionTask task = new KsSetModuleVersionTask( "fluffy", "key", 1 );
    task.execute( installContext );
    
  }
  
} /* ENDCLASS */
