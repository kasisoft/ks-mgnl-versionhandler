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
public class RegisterWorkspaceSubscriptionTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    RegisterWorkspaceSubscription tb = new RegisterWorkspaceSubscription( "myws" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080/subscriptions/myws[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/'\n"
      + "@repository = 'myws'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void mapped() {
    
    RegisterWorkspaceSubscription tb = new RegisterWorkspaceSubscription( "myws", "/hello", "/kitty" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/magnoliaPublic8080/subscriptions/myws[mgnl:contentNode]\n"
      + "@fromURI = '/hello'\n"
      + "@toURI = '/kitty'\n"
      + "@repository = 'myws'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void subscriber() {
    
    RegisterWorkspaceSubscription tb = new RegisterWorkspaceSubscription( "myws" )
      .subscriber( "remote" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/remote[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/remote/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/remote/subscriptions/myws[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/'\n"
      + "@repository = 'myws'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    RegisterWorkspaceSubscription tb = new RegisterWorkspaceSubscription( "myws" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    RegisterWorkspaceSubscription tb = new RegisterWorkspaceSubscription( "myws" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

} /* ENDCLASS */
