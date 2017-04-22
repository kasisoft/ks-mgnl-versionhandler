package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

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
  
  Class<?>          availability;
  String            prototypeId;
  
  public InstallSite( @Nonnull String site, @Nonnull String theme ) {
    siteName      = site;
    themeName     = theme;
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
  
  @Override
  public String getDescription() {
    return desc_installing_site_config.format( themeName, siteName );
  }

  // https://documentation.magnolia-cms.com/display/DOCS/Site+definition
  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder()
      .substitution( "site", siteName )
      .substitution( "theme", themeName )
      .sContentNode( "modules/site/config/site" )
        .property("label"    , "${site}" )
       ;
    
    result
      .sContentNode( "theme" )
        .property( "class" , "info.magnolia.module.site.theme.ThemeReference" )
        .property( "name"  , "${theme}" )
      .sEnd()
      ;
    
    result
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
        .property( "defaultLocale"  , "de"   )
        .property( "fallbackLocale" , "de"   )
        .property( "enabled"        , "true" )
      .sEnd()
      ;
  
    result
      .sContentNode( "mappings" )
        .sContentNode( "website" )
          .property( "URIPrefix", "/" )
          .property( "handlePrefix", "/" )
          .property( "repository", "website" )
        .sEnd()
      .sEnd()
      ;
  
        
    if( prototypeId != null ) {
      result
        .sContentNode( "templates" )
          .property( "class", "info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings" )
          .property( "prototypeId", prototypeId )
        .sEnd();
    }
    
    if( availability != null ) {
      result
        .sContentNode( "templates/availability" )
          .property( "class", availability.getName() )
        .sEnd();
    }
    
    result.sEnd();
    return result;
  }

} /* ENDCLASS */
