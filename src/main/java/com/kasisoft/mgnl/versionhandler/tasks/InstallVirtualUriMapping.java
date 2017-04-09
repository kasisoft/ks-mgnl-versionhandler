package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.beans.config.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallVirtualUriMapping implements TreeBuilderProvider {

  String                                module;
  Class<? extends VirtualURIMapping>    uriMapper;
  String                                mapperName;
  
  public InstallVirtualUriMapping( @Nonnull String moduleName, @Nonnull Class<? extends VirtualURIMapping> clazz ) {
    module    = moduleName;
    uriMapper = clazz;
  }
  
  @Override
  public String getDescription() {
    return desc_install_virtal_uri_mapping.format( uriMapper.getName() );
  }
  
  public InstallVirtualUriMapping withMapperName( @Nonnull String newMapperName ) {
    mapperName = newMapperName;
    return this;
  }
  
  @Override
  public TreeBuilder create() {
    String mapper = mapperName != null ? mapperName : StringFunctions.firstDown( uriMapper.getSimpleName() );
    return new TreeBuilder()
      .substitution( "module", module )
      .sContentNode( "modules/${module}/virtualURIMapping" )
        .sContentNode( mapper )
          .clazz( uriMapper )
        .sEnd()
      .sEnd()
    ;
  }

} /* ENDCLASS */
