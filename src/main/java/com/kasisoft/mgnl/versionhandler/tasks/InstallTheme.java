package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallTheme implements TreeBuilderProvider {

  String            moduleName;
  String            themeName;
  List<String[]>    resources;
  
  public InstallTheme( @Nonnull String module, @Nonnull String theme ) {
    moduleName    = module;
    themeName     = theme;
    resources     = new ArrayList<>();
  }
  
  public InstallTheme resourceFile( @Nonnull String name, @Nonnull String link, @Nullable String media ) {
    resources.add( new String[] { name, link, media } );
    return this;
  }

  @Override
  public String getDescription() {
    return desc_installing_module.format( themeName, moduleName );
  }

  // https://documentation.magnolia-cms.com/display/DOCS/Theme
  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder()
      .substitution( "module", moduleName  )
      .substitution( "theme", themeName )
      .sContentNode( "modules/${module}/themes/${theme}" );
    
    resources.forEach( $ -> create( result, $ ) );
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
