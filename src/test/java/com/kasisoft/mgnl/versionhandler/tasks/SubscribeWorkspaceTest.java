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
public class SubscribeWorkspaceTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions/toto[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void fromUri() {
    
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" )
      .fromUri( "/start" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions/toto[mgnl:contentNode]\n"
      + "@fromURI = '/start'\n"
      + "@toURI = '/'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void toUri() {
    
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" )
      .toUri( "/end" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions/toto[mgnl:contentNode]\n"
      + "@fromURI = '/'\n"
      + "@toURI = '/end'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void fromAndToUri() {
    
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" )
      .fromUri( "/start" )
      .toUri( "/end" );
    
    String expected = ""
      + "(new) /server[mgnl:contentNode]\n"
      + "(new) /server/activation[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions[mgnl:contentNode]\n"
      + "(new) /server/activation/subscribers/ginger/subscriptions/toto[mgnl:contentNode]\n"
      + "@fromURI = '/start'\n"
      + "@toURI = '/end'\n"
      + "@repository = 'toto'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    SubscribeWorkspace tb = new SubscribeWorkspace( "toto", "ginger" );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

} /* ENDCLASS */
