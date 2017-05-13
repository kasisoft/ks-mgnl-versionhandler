package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author noreply@aperto.com
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlushAllForWorkspace implements TreeBuilderProvider {

  String   workspace;
  String   config;
  
  public FlushAllForWorkspace( @Nonnull String ws ) {
    workspace = ws;
    config    = "default";
  }
  
  public FlushAllForWorkspace configuration( @Nonnull String name ) {
    config = name;
    return this;
  }
  
  @Override
  public String getDescription() {
    return desc_install_flush_all.format( workspace );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sContentNode( "/modules/cache/config/configurations" )
        .sContentNode( config )
          .sContentNode( "flushPolicy/policies/flushAll/repositories" )
            .property( workspace, workspace )
          .sEnd()
        .sEnd()
      .sEnd()
      ;
  }

} /* ENDCLASS */
