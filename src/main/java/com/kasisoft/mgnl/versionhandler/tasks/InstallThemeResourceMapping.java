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
  String    jsFolderName;
  String    cssFolderName;
  String    fontsFolderName;
  
  public InstallThemeResourceMapping( @Nonnull String module, @Nonnull String theme ) {
    moduleName      = module;
    themeName       = theme;
    includeTheme    = false;
    jsFolderName    = "js";
    cssFolderName   = "css";
    fontsFolderName = "fonts";
  }

  public InstallThemeResourceMapping includeThemeName() {
    includeTheme = true;
    return this;
  }

  public InstallThemeResourceMapping javascriptFolderName( @Nonnull String name ) {
    jsFolderName = name;
    return this;
  }

  public InstallThemeResourceMapping cssFolderName( @Nonnull String name ) {
    cssFolderName = name;
    return this;
  }

  public InstallThemeResourceMapping fontsFolderName( @Nonnull String name ) {
    fontsFolderName = name;
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
      
      .substitution( "theme"       , themeName       )
      .substitution( "module"      , moduleName      )
      .substitution( "jsFolder"    , jsFolderName    )
      .substitution( "cssFolder"   , cssFolderName   )
      .substitution( "fontsFolder" , fontsFolderName )
      
      .sContentNode( "modules/${module}/virtualURIMapping/cssFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/${cssFolder}/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/${cssFolder}/$1" )
      .sEnd()
      
      .sContentNode( "modules/${module}/virtualURIMapping/fontFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/${fontsFolder}/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/${fontsFolder}/$1" )
      .sEnd()
      
      .sContentNode( "modules/${module}/virtualURIMapping/iconFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/icons/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/icons/$1" )
      .sEnd()
      
      .sContentNode( "modules/${module}/virtualURIMapping/jsFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .propertyF( "fromURI" , "^%s/${jsFolder}/(.+)$", prefix )
        .property( "toURI"   , "forward:/.resources/${theme}/${jsFolder}/$1" )
      .sEnd()
      ;
  }

} /* ENDCLASS */
