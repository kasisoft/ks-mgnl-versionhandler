package com.kasisoft.mgnl.versionhandler.tasks;

import info.magnolia.module.model.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KsSetModuleVersionTask extends KsSetPropertyTask {
  
  private static final String FMT_PROPERTY = "/modules/%s@%s";
  
  public KsSetModuleVersionTask( @Nonnull String module, @Nonnull String prop, @Nonnull Version toVersion ) {
    super( String.format( FMT_PROPERTY, module, prop ), toVersion.toString() );
  }

  public KsSetModuleVersionTask( @Nonnull String module, @Nonnull String prop, int run ) {
    super( String.format( FMT_PROPERTY, module, prop ), String.valueOf( run ) );
  }
  
} /* ENDCLASS */
