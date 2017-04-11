package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.module.delta.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.servlet.http.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.filters.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallServlet implements TreeBuilderProvider {

  Class<? extends HttpServlet>    servletClass;
  String                          name;
  Map<String, String>             mappings;
  Map<String, String>             parameters;
  String                          afterNode;
  
  public InstallServlet( @Nonnull Class<? extends HttpServlet> servlet ) {
    servletClass  = servlet;
    name          = null;
    mappings      = new HashMap<>();
    parameters    = new HashMap<>();
    afterNode     = null;
  }
  
  public InstallServlet name( @Nonnull String newName ) {
    name = newName;
    return this;
  }

  public InstallServlet mappings( @Nonnull Map<String, String> newMappings ) {
    mappings.putAll( newMappings );
    return this;
  }

  public InstallServlet mapping( @Nonnull String key, @Nonnull String value ) {
    mappings.put( key, value );
    return this;
  }

  public InstallServlet parameters( @Nonnull Map<String, String> newParameters ) {
    parameters.putAll( newParameters );
    return this;
  }

  public InstallServlet parameter( @Nonnull String name, @Nonnull String value ) {
    parameters.put( name, value );
    return this;
  }

  public InstallServlet positionAfter( @Nonnull String after ) {
    afterNode = after;
    return this;
  }

  @Override
  public String getDescription() {
    return desc_install_servlet.format( servletClass.getName() );
  }

  @Override
  public TreeBuilder create() {
    
    TreeBuilder result = new TreeBuilder()
      .sFolder( "server/filters/servlets" )
        .sContentNode( getName() )
          .clazz( ServletDispatchingFilter.class )
          .property( "enabled", "true" )
          .property( "servletClass", servletClass.getName() )
          .property( "servletName", getName() );
    
    result.sContentNode( "mappings" );
    mappings.forEach( ($k, $v) -> createMapping( result, $k, $v ) );
    result.sEnd();
    
    result.sContentNode( "parameters" );
    parameters.forEach( result::property );
    result.sEnd();
          
    result.sEnd().sEnd();
    
    return result;
    
  }
  
  private String getName() {
    String result = name;
    if( result == null ) {
      result = StringFunctions.firstDown( servletClass.getSimpleName() );
    }
    return result;
  }
  
  private void createMapping( TreeBuilder result, String mapping, String pattern ) {
    result
      .sContentNode( mapping )
        .property( "pattern", pattern )
      .sEnd();
  }

  @Override
  public List<Task> postExecute() {
    List<Task> result = Collections.emptyList();
    if( afterNode != null ) {
      result = Arrays.asList( 
        new OrderNodeAfterTask( task_positioning_filter_servlet.format( getName(), afterNode ), String.format( "/server/filters/servlets/%s", getName() ), afterNode )
      );
    }
    return result;
  }

} /* ENDCLASS */
