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
public class InstallResponsiveVariation implements TreeBuilderProvider {
  
  String                  themeName;
  Map<String, Integer>    maxWidths;
  List<VariationRecord>   variationRecords;
  
  public InstallResponsiveVariation( @Nonnull String theme, @Nonnull Map<String, Integer> widths ) {
    themeName         = theme;
    maxWidths         = widths;
    variationRecords  = new ArrayList<>(5);
  }

  public InstallResponsiveVariation variation( @Nonnull String name, boolean expand, int ratioWidth, int ratioHeight, int maxColumns ) {
    variationRecords.add( new VariationRecord( name, expand, ratioWidth, ratioHeight, maxColumns ) );
    return this;
  }

  public InstallResponsiveVariation variation( @Nonnull String name, int ratioWidth, int ratioHeight, int maxColumns ) {
    variationRecords.add( new VariationRecord( name, true, ratioWidth, ratioHeight, maxColumns ) );
    return this;
  }

  public InstallResponsiveVariation variation( @Nonnull String name, int maxColumns ) {
    variationRecords.add( new VariationRecord( name, true, null, null, maxColumns ) );
    return this;
  }

  public InstallResponsiveVariation variation( @Nonnull String name,  boolean expand, int maxColumns ) {
    variationRecords.add( new VariationRecord( name, expand, null, null, maxColumns ) );
    return this;
  }

  public InstallResponsiveVariation variation( @Nonnull String name, boolean expand ) {
    variationRecords.add( new VariationRecord( name, expand, null, null, null ) );
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
    variationRecords.forEach( $ -> create( result, $ ) );
    result.sEnd().sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, VariationRecord record ) {
    result.sContentNode( record.getName() )
      .property( "class"    , "com.kasisoft.mgnl.imaging.ResponsiveVariation" )
      .property( "expand"   , record.isExpand() )
      ;
    if( record.getMaxColumns() != null ) {
      result.property( "maxColumns", record.getMaxColumns() );
    }
    if( record.getRatioWidth() != null ) {
      result.property( "ratioWidth", record.getRatioWidth() );
    }
    if( record.getRatioHeight() != null ) {
      result.property( "ratioHeight", record.getRatioHeight() );
    }
    result.sContentNode( "maxWidths" );
    maxWidths.forEach( result::property );
    result.sEnd();
    result.sEnd();
  }

  @Getter @Setter @AllArgsConstructor
  private static class VariationRecord {
    
    String                name;
    boolean               expand;
    Integer               ratioWidth;
    Integer               ratioHeight;
    Integer               maxColumns;

  } /* ENDCLASS */
  
} /* ENDCLASS */
