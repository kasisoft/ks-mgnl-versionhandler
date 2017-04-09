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
public class RegisterAppLauncher implements TreeBuilderProvider {

  String    group;
  String    app;
  
  public RegisterAppLauncher( @Nonnull String launcherGroup, @Nonnull String launcherApp ) {
    group   = launcherGroup;
    app     = launcherApp;
  }

  @Override
  public String getDescription() {
    return desc_register_app_in_group.format( app, group );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sContentNode( "modules/ui-admincentral/config/appLauncherLayout/groups" )
        .sContentNode( group )
          .sContentNode( "apps" )
            .sContentNode( app )
            .sEnd()
          .sEnd()
        .sEnd()
      .sEnd();
  }

} /* ENDCLASS */
