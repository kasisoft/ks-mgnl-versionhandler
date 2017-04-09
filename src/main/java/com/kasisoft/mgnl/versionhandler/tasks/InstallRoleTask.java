package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.security.*;
import info.magnolia.objectfactory.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRoleTask extends AbstractTask {

  String           role;
  List<Object[]>   permissions;
  
  public InstallRoleTask( @Nonnull String newRole, @Nonnull String workspace, @Nonnull String path, boolean subnodes, long permission ) {
    super( "InstallRole", task_installing_role.format( newRole ) );
    role        = newRole;
    permissions = new ArrayList<>();
    permission( workspace, path, subnodes, permission );
  }
  
  public InstallRoleTask permission( @Nonnull String workspace, @Nonnull String path, boolean subnodes, long permission ) {
    permissions.add( new Object[] { workspace, path, subnodes, permission } );
    return this;
  }

  @Override
  public void execute( @Nonnull InstallContext ctx ) throws TaskExecutionException {
    try {
      executeImpl();
    } catch( Exception ex ) {
      throw new TaskExecutionException( ex.getLocalizedMessage(), ex );
    }
  }
  
  private void executeImpl() throws Exception {
    SecuritySupport securitySupport = Components.getComponent( SecuritySupport.class );
    RoleManager     roleManager     = securitySupport.getRoleManager();
    Role            roleObj         = roleManager.getRole( role );
    if( roleObj == null ) {
      roleObj = roleManager.createRole( role );
    }
    for( Object[] permission : permissions ) {
      String ws         = (String) permission[0];
      String path       = (String) permission[1];
      boolean subnodes  = (Boolean) permission[2];
      long    perm      = (Long) permission[3];
      roleManager.addPermission( roleObj, ws, path, perm );
      if( subnodes ) {
        String incSubnodes = "/*";
        if( ! "/".equals( path ) ) {
          incSubnodes = path + incSubnodes;
        }
        roleManager.addPermission( roleObj, ws, incSubnodes, perm );
      }
    }
  }
  
} /* ENDCLASS */
