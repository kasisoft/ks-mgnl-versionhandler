package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.beans.config.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallThemeResourceMapping implements TreeBuilderProvider {

  String    themeName;
  String    moduleName;
  boolean   includeTheme;
  
  public InstallThemeResourceMapping( @Nonnull String module, @Nonnull String theme ) {
    moduleName    = module;
    themeName     = theme;
    includeTheme  = false;
  }

  public InstallThemeResourceMapping includeThemeName() {
    includeTheme = true;
    return this;
  }
  
  @Override
  public String getDescription() {
    return desc_install_theme.format( themeName, moduleName );
  }

  @Override
  public TreeBuilder create() {
    String prefix = includeTheme ? "/" + themeName : "";
    return new TreeBuilder()
      .substitution( "theme", themeName )
      .substitution( "module", moduleName )
      .sContentNode( "modules/${module}/virtualURIMapping/cssFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/css/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/css/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/fontFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/fonts/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/fonts/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/iconFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/icons/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/icons/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/jsFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/js/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/js/$1" )
      .sEnd()
      ;
  }

} /* ENDCLASS */
