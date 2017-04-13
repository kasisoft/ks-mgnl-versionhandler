package com.kasisoft.mgnl.versionhandler;

import static org.mockito.Mockito.*;

import info.magnolia.test.mock.jcr.*;
import info.magnolia.test.mock.jcr.MockWorkspace;

import info.magnolia.test.mock.*;

import info.magnolia.test.*;

import info.magnolia.module.*;

import info.magnolia.context.*;

import info.magnolia.cms.core.*;

import info.magnolia.jcr.util.*;

import info.magnolia.jcr.*;

import org.mockito.*;

import javax.servlet.http.*;

import javax.annotation.*;
import javax.jcr.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.security.*;
import info.magnolia.init.*;
import info.magnolia.objectfactory.*;

/**
 * This extension provides some helpful defaults in order to easily setup a test environment.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ExtendedMockWebContext extends MockWebContext {

  private static final String DEFAULT_APP_PATH = "/test-path";

  @Getter @Setter(AccessLevel.PRIVATE)
  MagnoliaConfigurationProperties   mgnlProps;
  
  @Getter @Setter(AccessLevel.PRIVATE)
  SecuritySupport                   secSupport;
  
  @Getter
  MgnlRoleManager                   roleManager;

  public ExtendedMockWebContext() {
    setAggregationState   ( Mockito.spy( new MockAggregationState            () ) );
    setRepositoryStrategy ( Mockito.spy( new MockRepositoryAcquiringStrategy () ) );
    setRequest            ( Mockito.mock( HttpServletRequest  . class ) );
    setResponse           ( Mockito.mock( HttpServletResponse . class ) );
    setParameters         ( new HashMap<String, String>() );
    setLocale             ( Locale.GERMAN );
    setContextPath        ( DEFAULT_APP_PATH );
  }
  
  @Nonnull
  public <R extends AggregationState> R getMockAggregationState() {
    return (R) getAggregationState();
  }

  @Nonnull
  public <R extends HttpServletRequest> R getMockRequest() {
    return (R) getRequest();
  }

  @Nonnull
  public <R extends HttpServletRequest> R getMockResponse() {
    return (R) getResponse();
  }

  @Override
  public void setAggregationState( @Nonnull AggregationState aggregationState ) {
    Node mainContentNode    = getAggregationState().getMainContentNode();
    Node currentContentNode = getAggregationState().getCurrentContentNode();
    super.setAggregationState( aggregationState );
    if( mainContentNode != null ) {
      aggregationState.setMainContentNode( mainContentNode );
    }
    if( currentContentNode != null ) {
      aggregationState.setCurrentContentNode( currentContentNode );
    }
  }
  
  @Nonnull
  public MockRepositoryAcquiringStrategy getMockRepositoryStrategy() {
    return (MockRepositoryAcquiringStrategy) getRepositoryStrategy();
  }
  
  @Nonnull
  public MockSession getMockSession( @Nonnull String name ) {
    MockSession result = null;
    if( hasSession( name ) ) {
      try {
        result = (MockSession) getJCRSession( name );
      } catch( RepositoryException ex ) {
        throw new RuntimeRepositoryException(ex);
      }
    } else {
      result = Mockito.spy( new MockSession( name ) );
      getMockRepositoryStrategy().addSession( name, result );
    }
    return result;
  }

  public MockWorkspace getMockWorkspace( @Nonnull String wsname ) {
    MockSession session = getMockSession( wsname );
    if( session != null ) {
      return (MockWorkspace) session.getWorkspace();
    }
    return null;
  }

  private boolean hasSession( String name ) {
    MockSession result = null;
    try {
      result = (MockSession) getJCRSession( name );
    } catch( Exception ex ) {
      // indicates the session doesn't exist
    }
    return result != null;
  }
  
  private void defaults() {
    if( mgnlProps == null ) {
      mgnlProps = mock( MagnoliaConfigurationProperties.class );
    }
    if( secSupport == null ) {
      secSupport = mock( SecuritySupport.class );
    }
  }
  
  /**
   * Installs this context into the current test environment.
   */
  public void install() {
    
    defaults();
    
    NodeNameHelper nodeNameHelper = new NodeNameHelper( mgnlProps );
    roleManager                   = new MgnlRoleManager( nodeNameHelper );
    when( secSupport.getRoleManager() ).thenReturn( roleManager );
    
    ComponentsTestUtil.clear();
    Components.setComponentProvider( new MockComponentProvider() );
    MgnlContext.setInstance( this );
    ComponentsTestUtil.setInstance( MagnoliaConfigurationProperties.class, mgnlProps );
    ComponentsTestUtil.setInstance( SecuritySupport.class, secSupport );
    ComponentsTestUtil.setInstance( SystemContext.class, this );
    ComponentsTestUtil.setInstance( ModuleRegistry.class, new ModuleRegistryImpl() );
    
  }

  public static ExtendedMockWebContextBuilder builder() {
    return new ExtendedMockWebContextBuilder();
  }
  
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static final class ExtendedMockWebContextBuilder {

    ExtendedMockWebContext   instance;

    private ExtendedMockWebContextBuilder() {
      instance = new ExtendedMockWebContext();
    }

    public ExtendedMockWebContextBuilder parameter( @Nonnull String key, String value ) {
      instance.getParameters().put( key, value );
      return this;
    }
    
    public <A extends AggregationState> ExtendedMockWebContextBuilder aggregationState( A aggregationState ) {
      instance.setAggregationState( aggregationState );
      return this;
    }
    
    public ExtendedMockWebContextBuilder mgnlProperties( @Nonnull MagnoliaConfigurationProperties props ) {
      instance.setMgnlProps( props );
      return this;
    }

    public ExtendedMockWebContextBuilder securitySupport( @Nonnull SecuritySupport secSupport ) {
      instance.setSecSupport( secSupport );
      return this;
    }

    public ExtendedMockWebContextBuilder workspace( @Nonnull String wsname ) {
      return workspace( wsname, null );
    }

    public ExtendedMockWebContextBuilder workspace( @Nonnull String wsname, TreeBuilder builder ) {
      MockSession session = instance.getMockSession( wsname );
      if( builder != null ) {
        builder.build( new MockNodeProducer( session ) );
      }
      return this;
    }

    public ExtendedMockWebContextBuilder contextPath( @Nonnull String path ) {
      instance.setContextPath( path );
      return this;
    }

    public ExtendedMockWebContextBuilder request( @Nonnull HttpServletRequest request ) {
      instance.setRequest( request );
      return this;
    }

    public ExtendedMockWebContextBuilder response( @Nonnull HttpServletResponse response ) {
      instance.setResponse( response );
      return this;
    }

    public ExtendedMockWebContextBuilder mainContentNode( @Nonnull Node newNode ) {
      instance.getAggregationState().setMainContentNode( newNode );
      return this;
    }

    public ExtendedMockWebContextBuilder locale( @Nonnull Locale locale ) {
      instance.setLocale( locale );
      return this;
    }

    public ExtendedMockWebContext build() {
      return instance;
    }

  } /* ENDCLASS */

} /* ENDCLASS */
