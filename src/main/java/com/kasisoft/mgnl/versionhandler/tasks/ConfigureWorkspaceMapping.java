package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigureWorkspaceMapping<R extends ConfigureWorkspaceMapping> implements TreeBuilderProvider {

  String   workspace;
  String   fromUri;
  String   toUri;
  String   path;

  public ConfigureWorkspaceMapping( @Nonnull String ws, @Nonnull String wsPath ) {
    workspace = ws;
    path      = wsPath;
    fromUri   = "/";
    toUri     = "/";
  }

  public R fromUri( @Nonnull String newFromUri ) {
    fromUri = newFromUri;
    return (R) this;
  }
  
  public R toUri( @Nonnull String newToUri ) {
    toUri = newToUri;
    return (R) this;
  }
  
  @Override
  public String getDescription() {
    return desc_configure_workspace_mapping.format( path, workspace, fromUri, toUri );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sContentNode( path )
        .property( "fromURI"    , fromUri   )
        .property( "toURI"      , toUri     )
        .property( "repository" , workspace )
      .sEnd()
      ;
  }

} /* ENDCLASS */
