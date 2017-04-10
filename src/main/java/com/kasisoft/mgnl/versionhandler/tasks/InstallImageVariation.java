package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.util.model.*;
import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;
import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallImageVariation implements TreeBuilderProvider {
  
  String           themeName;
  List<Object[]>   variations;
  
  public InstallImageVariation( @Nonnull String theme ) {
    variations = new ArrayList<>();
    themeName  = theme;
  }

  public InstallImageVariation variation( @Nonnull String key, int width, int height ) {
    return variation( null, key, width, height );
  }

  public InstallImageVariation variation( @Nullable Class<?> variation, @Nonnull String key, int width, int height ) {
    variations.add( new Object[] { variation, key, width, height } );
    return this;
  }
    
  public InstallImageVariation variation( Class<?> variation, BreakpointDeclaration bp ) {
    variations.add( new Object[] { variation, bp } );
    return this;
  }
  
  @Override
  public String getDescription() {
    return desc_install_image_variations.format( themeName );
  }

  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder()
      .substitution( "theme" , themeName )
      .sContentNode( "modules/site/config/themes/${theme}/imaging" )
        .property( "class", "info.magnolia.templating.imaging.VariationAwareImagingSupport" )
        .property( "enabled", "true" )
        .sContentNode( "variations" )
      ;
    variations.forEach( $ -> create( result, $ ) );
    result.sEnd().sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, Object[] variation ) {
    if( variation.length == 2 ) {
      create( result, (Class) variation[0], (BreakpointDeclaration) variation[1] );
    } else {
      create( result, (Class) variation[0], (String) variation[1], (int) variation[2], (int) variation[3] );
    }
  }

  private void create( TreeBuilder result, Class<?> variation, BreakpointDeclaration bp ) {
    result.sContentNode( bp.getKey().toLowerCase() )
      .property( "class", toVariationClass( variation ) )
      .property( "width", bp.getMaxWidth() )
    .sEnd();
  }

  private void create( TreeBuilder result, Class<?> variation, String name, int width, int height ) {
    result.sContentNode( name )
      .property( "class", toVariationClass( variation ) )
      .property( "width", width )
      .property( "height", height )
    .sEnd();
  }
  
  private String toVariationClass( Class<?> variation ) {
    return variation != null ? variation.getName() : "info.magnolia.templating.imaging.variation.SimpleResizeVariation";
  }

} /* ENDCLASS */
