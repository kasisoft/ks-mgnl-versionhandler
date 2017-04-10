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
public class InstallTheme implements TreeBuilderProvider {

  String    themeName;
  String    moduleName;
  
  public InstallTheme( @Nonnull String module, @Nonnull String theme ) {
    moduleName  = module;
    themeName   = theme;
  }

  @Override
  public String getDescription() {
    return desc_install_theme.format( themeName, moduleName );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .substitution( "theme", themeName )
      .substitution( "module", moduleName )
      .sContentNode( "modules/${module}/virtualURIMapping/cssFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .property( "fromURI" , "^/css/(.+)$" )
        .property( "toURI"   , "forward:/.resources/${theme}/css/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/fontFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .property( "fromURI" , "^/fonts/(.+)$" )
        .property( "toURI"   , "forward:/.resources/${theme}/fonts/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/iconFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .property( "fromURI" , "^/icons/(.+)$" )
        .property( "toURI"   , "forward:/.resources/${theme}/icons/$1" )
      .sEnd()
      .sContentNode( "modules/${module}/virtualURIMapping/jsFiles" )
        .clazz( RegexpVirtualURIMapping.class )
        .property( "fromURI" , "^/js/(.+)$" )
        .property( "toURI"   , "forward:/.resources/${theme}/js/$1" )
      .sEnd()
      ;
  }

} /* ENDCLASS */
