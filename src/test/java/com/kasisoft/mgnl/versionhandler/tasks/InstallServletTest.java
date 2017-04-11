package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import javax.servlet.http.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallServletTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    InstallServlet tb = new InstallServlet( DummyServlet.class );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/dummyServlet[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'dummyServlet'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/dummyServlet/parameters[mgnl:contentNode]\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );

    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );

  }

  @Test
  public void named() {
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .name( "bigbird" );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/bigbird[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'bigbird'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/bigbird/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/bigbird/parameters[mgnl:contentNode]\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );

    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );

  }

  @Test
  public void mappings() {
    
    Map<String, String> mappings = new HashMap<>();
    mappings.put( "a", "b" );
    mappings.put( "c", "d" );
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .mappings( mappings );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/dummyServlet[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'dummyServlet'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings/a[mgnl:contentNode]\n"
      + "@pattern = 'b'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings/c[mgnl:contentNode]\n"
      + "@pattern = 'd'\n"
      + "(new) /server/filters/servlets/dummyServlet/parameters[mgnl:contentNode]\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );
    
    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );
    
  }

  @Test
  public void mapping() {
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .mapping( "a", "b" )
      .mapping( "c", "d" );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/dummyServlet[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'dummyServlet'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings/a[mgnl:contentNode]\n"
      + "@pattern = 'b'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings/c[mgnl:contentNode]\n"
      + "@pattern = 'd'\n"
      + "(new) /server/filters/servlets/dummyServlet/parameters[mgnl:contentNode]\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );

    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );

  }

  @Test
  public void parameters() {
    
    Map<String, String> parameters = new HashMap<>();
    parameters.put( "a", "b" );
    parameters.put( "c", "d" );
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .parameters( parameters );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/dummyServlet[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'dummyServlet'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/dummyServlet/parameters[mgnl:contentNode]\n"
      + "@a = 'b'\n"
      + "@c = 'd'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );

    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );

  }

  @Test
  public void parameter() {
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .parameter( "a", "b" )
      .parameter( "c", "d" );
    
    String expected = ""
      + "(new) /server[mgnl:folder]\n"
      + "(new) /server/filters[mgnl:folder]\n"
      + "(new) /server/filters/servlets[mgnl:folder]\n"
      + "(new) /server/filters/servlets/dummyServlet[mgnl:contentNode]\n"
      + "@servletClass = 'com.kasisoft.mgnl.versionhandler.tasks.InstallServletTest$DummyServlet'\n"
      + "@servletName = 'dummyServlet'\n"
      + "@class = 'info.magnolia.cms.filters.ServletDispatchingFilter'\n"
      + "@enabled = 'true'\n"
      + "(new) /server/filters/servlets/dummyServlet/mappings[mgnl:contentNode]\n"
      + "(new) /server/filters/servlets/dummyServlet/parameters[mgnl:contentNode]\n"
      + "@a = 'b'\n"
      + "@c = 'd'\n"
      ;
    
    String desc = buildDescription( tb );
    assertThat( desc, is( expected ) );

    assertNotNull( tb.postExecute() );
    assertTrue( tb.postExecute().isEmpty() );

  }

  @Test
  public void afterNode() {
    
    InstallServlet tb = new InstallServlet( DummyServlet.class )
      .positionAfter( "tokli" );

    assertNotNull( tb.postExecute() );
    assertThat( tb.postExecute().size(), is(1) );
    
  }

  @Test
  public void hasTitle() {
    InstallServlet tb = new InstallServlet( DummyServlet.class );
    assertNotNull( StringFunctions.cleanup( tb.getTitle() ) );
  }

  @Test
  public void hasDescription() {
    InstallServlet tb = new InstallServlet( DummyServlet.class );
    assertNotNull( StringFunctions.cleanup( tb.getDescription() ) );
  }

  public static class DummyServlet extends HttpServlet {
    
  } /* ENDCLASS */
  
} /* ENDCLASS */
