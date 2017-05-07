package com.kasisoft.mgnl.versionhandler.tasks;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeWorkspace extends  ConfigureWorkspaceMapping<SubscribeWorkspace> {

  public SubscribeWorkspace( @Nonnull String ws, @Nonnull String subscriber ) {
    super( ws, String.format( "/server/activation/subscribers/%s/subscriptions/%s", subscriber, ws ) );
  }

  @Override
  public Boolean authorOnly() {
    return Boolean.TRUE;
  }

} /* ENDCLASS */
