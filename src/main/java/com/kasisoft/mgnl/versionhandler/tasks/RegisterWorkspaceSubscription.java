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
public class RegisterWorkspaceSubscription implements TreeBuilderProvider {

  String    workspace;
  String    fromUri;
  String    toUri;
  String    subscriber;
  
  public RegisterWorkspaceSubscription( @Nonnull String workspace ) {
    this( workspace, null, null );
  }
  
  public RegisterWorkspaceSubscription( @Nonnull String ws, @Nullable String from, @Nullable String to ) {
    workspace   = ws;
    fromUri     = from != null ? from : "/";
    toUri       = to   != null ? to   : "/";
    subscriber  = "magnoliaPublic8080";
  }
  
  public RegisterWorkspaceSubscription subscriber( @Nonnull String newsubscriber ) {
    subscriber = newsubscriber;
    return this;
  }

  @Override
  public String getDescription() {
    return desc_register_workspace_subscription.format( workspace, fromUri, toUri );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sContentNode( "server/activation/subscribers" )
        .sContentNode( subscriber )
          .sContentNode( "subscriptions" )
            .sContentNode( workspace )
              .property( "repository", workspace )
              .property( "fromURI", fromUri )
              .property( "toURI", toUri )
            .sEnd()
          .sEnd()
        .sEnd()
      .sEnd();
  }

} /* ENDCLASS */
