package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.i18n.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallSite implements TreeBuilderProvider {

  String            siteName;
  String            themeName;
  
  List<String[]>    resources;
  Class<?>          availability;
  String            prototypeId;
  
  public InstallSite( @Nonnull String site, @Nonnull String theme ) {
    siteName      = site;
    themeName     = theme;
    resources     = new ArrayList<>();
    availability  = null;
  }
  
  public InstallSite prototypeId( @Nonnull String prototype ) {
    prototypeId = prototype;
    return this;
  }
  
  public InstallSite templateAvailability( @Nonnull Class<?> availabilityClass ) {
    availability = availabilityClass;
    return this;
  }
  
  public InstallSite resourceFile( @Nonnull String name, @Nonnull String link, @Nullable String media ) {
    resources.add( new String[] { name, link, media } );
    return this;
  }

  @Override
  public String getDescription() {
    return desc_installing_site_config.format( themeName, siteName );
  }

  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder()
      .substitution( "site", siteName )
      .substitution( "theme", themeName )
      .sContentNode( "modules/site/config" );
    
    result.sContentNode( "themes/${theme}" );
    resources.forEach( $ -> create( result, $ ) );
    result.sEnd();
    
    result
      .sContentNode( "${site}" )
        .property("label", "${site}")
        
        .sContentNode( "theme" )
          .property( "class" , "info.magnolia.module.site.theme.ThemeReference" )
          .property( "name"  , "${theme}" )
        .sEnd()
        
        .sContentNode( "i18n" )
          .sContentNode( "locales" )
            .sContentNode( "en" ) 
              .property( "country", "" )
              .property( "enabled", "true" )
              .property( "language", "en" )
            .sEnd()
            .sContentNode( "de" )
              .property( "country", "" )
              .property( "enabled", "true" )
              .property( "language", "de" )
            .sEnd()
          .sEnd()
          .clazz( DefaultI18nContentSupport.class )
          .property( "defaultLocale", "de" )
          .property( "enabled", "true" )
          .property( "fallbackLocale", "de" )
        .sEnd()
        
        .sContentNode( "mappings" )
          .sContentNode( "website" )
            .property( "URIPrefix", "/" )
            .property( "handlePrefix", "/" )
            .property( "repository", "website" )
          .sEnd()
        .sEnd()
        
      .sEnd();
    
    if( prototypeId != null ) {
      result
        .sContentNode( "${site}/templates" )
          .property( "class", "info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings" )
          .property( "prototypeId", prototypeId )
        .sEnd();
    }
    
    if( availability != null ) {
      result
        .sContentNode( "${site}/templates/availability" )
          .property( "class", availability.getName() )
        .sEnd();
    }
    
    result.sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, String[] args ) {
    if( args[1].endsWith( ".css" ) ) {
      createCss( result, args[0], args[1], args[2] );
    } else if( args[1].endsWith( ".js" ) ) {
      createJs( result, args[0], args[1], args[2] );
    }
  }
  
  private void createCss( TreeBuilder result, String name, String link, String media ) {
    if( media == null ) {
      media = "all";
    }
    result
      .sContentNode( "cssFiles" )
        .sContentNode( name )
          .property( "link"  , link )
          .property( "media" , media )
        .sEnd()
      .sEnd();
  }

  private void createJs( TreeBuilder result, String name, String link, String media ) {
    if( media == null ) {
      media = "all";
    }
    result
    .sContentNode( "jsFiles" )
      .sContentNode( name )
        .property( "link"  , link )
        .property( "media" , media )
      .sEnd()
    .sEnd();
  }

} /* ENDCLASS */
