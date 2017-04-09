package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.filters.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallFilterTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class );
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void named() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .filterName( "dodo" );
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dodo[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void disabled() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .disable();
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'false'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void bypassInternal() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .bypassInternal();
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/dummyFilter/bypasses[mgnl:contentNode]\n"
      + "(new) /server/filters/dummyFilter/bypasses/BypassUsingPrefix[mgnl:contentNode]\n"
      + "@pattern = '/.'\n"
      + "@class = 'info.magnolia.voting.voters.URIStartsWithVoter'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void bypassUsingPrefix() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .bypassPrefixed( "/dodo-" );
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/dummyFilter/bypasses[mgnl:contentNode]\n"
      + "(new) /server/filters/dummyFilter/bypasses/BypassUsingPrefix[mgnl:contentNode]\n"
      + "@pattern = '/dodo-'\n"
      + "@class = 'info.magnolia.voting.voters.URIStartsWithVoter'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void bypassDefaultResources() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .bypassResource();
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/dummyFilter/bypasses[mgnl:contentNode]\n"
      + "(new) /server/filters/dummyFilter/bypasses/BypassResourceRequest[mgnl:contentNode]\n"
      + "@pattern = '^*.(avi|bmp|css|flv|gif|ico|jpeg|jpg|js|mpeg|mpg|png|svg|ttf|txt|woff|woff2)$'\n"
      + "@class = 'info.magnolia.voting.voters.URIPatternVoter'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }

  @Test
  public void bypassSpecificResources() {
    
    InstallFilter filter = new InstallFilter( DummyFilter.class )
      .bypassResource( "css", "js" );
    
    String expected = ""
      + "(new) /server[mgnl:content]\n"
      + "(new) /server/filters[mgnl:content]\n"
      + "(new) /server/filters/dummyFilter[mgnl:content]\n"
      + "@class = 'com.kasisoft.mgnl.versionhandler.tasks.InstallFilterTest$DummyFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/dummyFilter/bypasses[mgnl:contentNode]\n"
      + "(new) /server/filters/dummyFilter/bypasses/BypassResourceRequest[mgnl:contentNode]\n"
      + "@pattern = '^*.(css|js)$'\n"
      + "@class = 'info.magnolia.voting.voters.URIPatternVoter'\n"
      ;
    
    assertThat( buildDescription( filter ), is( expected ) );
    
  }
  
  @Test
  public void hasTitle() {
    InstallFilter filter = new InstallFilter( DummyFilter.class );
    assertNotNull( StringFunctions.cleanup( filter.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallFilter filter = new InstallFilter( DummyFilter.class );
    assertNotNull( StringFunctions.cleanup( filter.getDescription() ) );
  }

  @SuppressWarnings("deprecation")
  public static class DummyFilter extends ContentTypeFilter {
    
  } /* ENDCLASS */
  
} /* ENDCLASS */
