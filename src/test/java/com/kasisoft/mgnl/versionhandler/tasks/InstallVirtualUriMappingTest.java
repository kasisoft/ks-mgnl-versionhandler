package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.beans.config.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallVirtualUriMappingTest extends AbstractTreeBuilderProvider {

  @Test
  public void defaultUsage() {
    
    InstallVirtualUriMapping mapping = new InstallVirtualUriMapping( "dodo", DummyMapping.class );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodo[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping/dummyMapping[mgnl:contentNode]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallVirtualUriMappingTest$DummyMapping'\n"
      ;

    assertThat( buildDescription( mapping ), is( expected ) );
    
  }

  @Test
  public void withMapperName() {
    
    InstallVirtualUriMapping mapping = new InstallVirtualUriMapping( "dodo", DummyMapping.class )
      .withMapperName( "bibo" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodo[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping/bibo[mgnl:contentNode]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallVirtualUriMappingTest$DummyMapping'\n"
      ;

    assertThat( buildDescription( mapping ), is( expected ) );
    
  }

  public static class DummyMapping extends DefaultVirtualURIMapping {
    
  } /* ENDCLASS */
  
} /* ENDCLASS */