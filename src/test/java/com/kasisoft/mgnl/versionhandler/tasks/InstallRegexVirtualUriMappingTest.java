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
public class InstallRegexVirtualUriMappingTest extends AbstractTreeBuilderProvider {

  @Test
  public void defaultUsage() {
    
    InstallRegexpVirtualUriMapping mapping = new InstallRegexpVirtualUriMapping( "dodo", "mapping1", "fromUri1", "toUri1" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodo[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping/mapping1[mgnl:contentNode]\n"
      + "@fromURI = 'fromUri1'\n"
      + "@toURI = 'toUri1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      ;
    
    String desc = buildDescription( mapping );
    System.err.println( desc );
    
    assertThat( desc, is( expected ) );
    
  }

  @Test
  public void manyMappings() {
    
    InstallRegexpVirtualUriMapping mapping = new InstallRegexpVirtualUriMapping( "dodo", "mapping1", "fromUri1", "toUri1" )
      .mapping( "mapping2", "fromUri2", "toUri2" );
    
    String expected = ""
      + "(new) /modules[mgnl:contentNode]\n"
      + "(new) /modules/dodo[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping[mgnl:contentNode]\n"
      + "(new) /modules/dodo/virtualURIMapping/mapping1[mgnl:contentNode]\n"
      + "@fromURI = 'fromUri1'\n"
      + "@toURI = 'toUri1'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      + "(new) /modules/dodo/virtualURIMapping/mapping2[mgnl:contentNode]\n"
      + "@fromURI = 'fromUri2'\n"
      + "@toURI = 'toUri2'\n"
      + "@class = 'info.magnolia.cms.beans.config.RegexpVirtualURIMapping'\n"
      ;
    
    assertThat( buildDescription( mapping ), is( expected ) );
    
  }

  @Test
  public void hasTitle() {
    InstallRegexpVirtualUriMapping mapping = new InstallRegexpVirtualUriMapping( "dodo", "mapping1", "fromUri1", "toUri1" );
    assertNotNull( StringFunctions.cleanup( mapping.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallRegexpVirtualUriMapping mapping = new InstallRegexpVirtualUriMapping( "dodo", "mapping1", "fromUri1", "toUri1" );
    assertNotNull( StringFunctions.cleanup( mapping.getDescription() ) );
  }

} /* ENDCLASS */
