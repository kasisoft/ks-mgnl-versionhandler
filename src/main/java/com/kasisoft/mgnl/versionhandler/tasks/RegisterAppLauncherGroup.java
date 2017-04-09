package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.awt.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAppLauncherGroup implements TreeBuilderProvider  {

  String    group;
  Color     color;
  
  public RegisterAppLauncherGroup( @Nonnull String launcherGroup, @Nonnull Color launcherColor ) {
    group = launcherGroup;
    color = launcherColor;
  }
  
  @Override
  public String getDescription() {
    return desc_register_app_launcher_group.format( group );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sContentNode( "modules/ui-admincentral/config/appLauncherLayout/groups" )
        .sContentNode( group )
          .propertyF( "color", "#%06x", color.getRGB() & 0x00FFFFFF )
        .sEnd()
      .sEnd()
    ;
  }

} /* ENDCLASS */
