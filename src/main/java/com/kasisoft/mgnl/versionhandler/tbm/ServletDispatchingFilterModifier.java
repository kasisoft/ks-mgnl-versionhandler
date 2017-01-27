package com.kasisoft.mgnl.versionhandler.tbm;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.servlet.http.*;

import javax.annotation.*;

import java.util.*;

import info.magnolia.cms.filters.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ServletDispatchingFilterModifier implements TreeBuilderModifier {

  private TreeBuilder   treebuilder;

  private ServletDispatchingFilterModifier( @Nonnull TreeBuilder tb ) {
    treebuilder = tb;
    treebuilder.sFolder( "server/filters/servlets" );
  }
  
  public ServletDispatchingFilterModifier sServlet( @Nonnull Class<? extends HttpServlet> servletClass ) {
    return sServlet( null, servletClass );
  }
  
  public ServletDispatchingFilterModifier sServlet( @Nullable String name, @Nonnull Class<? extends HttpServlet> servletClass ) {
    name = toName( name, servletClass );
    treebuilder.sContentNode( name )
      .sContentNode( "mappings" )
      .sEnd()
      .sContentNode( "parameters" )
      .sEnd()
      .clazz( ServletDispatchingFilter.class )
      .property( "servletClass", servletClass.getName() )
      .property( "servletName", name );
    return this;
  }

  public ServletDispatchingFilterModifier servletName( String comment ) {
    treebuilder.property( "servletName", comment );
    return this;
  }
  
  public ServletDispatchingFilterModifier comment( String comment ) {
    treebuilder.property( "comment", comment );
    return this;
  }
  
  public ServletDispatchingFilterModifier enabled( boolean enabled ) {
    treebuilder.property( "enabled", String.valueOf( enabled ) );
    return this;
  }

  public ServletDispatchingFilterModifier mapping( @Nonnull String name, @Nonnull String pattern ) {
    treebuilder.sContentNode( "mappings" ).sContentNode( name ).property( "pattern", pattern ).sEnd().sEnd();
    return this;
  }

  public ServletDispatchingFilterModifier mapping( @Nonnull Map<String, String> mappings ) {
    mappings.forEach( this::mapping );
    return this;
  }

  public ServletDispatchingFilterModifier parameter( @Nonnull String name, @Nonnull String value ) {
    treebuilder.sContentNode( "parameters" ).property( name, value ).sEnd();
    return this;
  }

  public ServletDispatchingFilterModifier sEnd() {
    treebuilder.sEnd();
    return this;
  }

  private String toName( String name, @Nonnull Class<? extends HttpServlet> servletClass ) {
    if( name == null ) {
      name = StringFunctions.firstDown( servletClass.getSimpleName() );
    }
    return name;
  }
  
  @Override
  public TreeBuilder sEndModifier() {
    treebuilder.sEnd();
    return treebuilder;
  }
  
  public static ServletDispatchingFilterModifier create( @Nonnull TreeBuilder tb ) {
    return new ServletDispatchingFilterModifier( tb );
  }
  
} /* ENDCLASS */
