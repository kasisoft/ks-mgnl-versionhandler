package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.stream.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.beans.config.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRegexpVirtualUriMapping implements TreeBuilderProvider {
  
  String            module;
  List<String[]>    mappings;
  
  public InstallRegexpVirtualUriMapping( @Nonnull String moduleName, @Nonnull String mappingName, @Nonnull String fromUri, @Nonnull String toUri ) {
    module   = moduleName;
    mappings = new ArrayList<>();
    mapping( mappingName, fromUri, toUri );
  }
  
  public InstallRegexpVirtualUriMapping mapping( @Nonnull String mappingName, @Nonnull String fromUri, @Nonnull String toUri ) {
    mappings.add( new String[] { mappingName, fromUri, toUri } );
    return this;
  }
  
  @Override
  public String getDescription() {
    if( mappings.size() == 1 ) {
      return desc_install_virtual_uri_regex_mapping.format( mappings.get(0)[0], mappings.get(0)[1], mappings.get(0)[2] );
    } else {
      return desc_install_virtual_uri_regex_mappings.format( StringFunctions.concatenate( ", ", mappings.stream().map( $ -> $[0] ).collect( Collectors.toList() ) ) );
    }
  }
  
  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder()
      .substitution( "module", module )
      .sContentNode( "modules/${module}/virtualURIMapping" );
    
    mappings.forEach( $ -> create( result, $[0], $[1], $[2] ) );
    
    result.sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, String mapperName, String fromUri, String toUri ) {
    result
      .sContentNode( mapperName )
        .clazz( RegexpVirtualURIMapping.class )
        .property( "fromURI", fromUri )
        .property( "toURI", toUri )
      .sEnd();
  }

} /* ENDCLASS */
