package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.security.*;
import info.magnolia.objectfactory.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddRoleTask extends AbstractTask implements IsInstanceSpecific {

  String           role;
  String           user;
  Boolean          authorOnly;

  public AddRoleTask( @Nonnull String roleToAdd ) {
    this( null, roleToAdd );
  }
  
  public AddRoleTask( String userToConfigure, @Nonnull String roleToAdd ) {
    super( "AddRole", task_adding_role.format( userToConfigure, roleToAdd ) );
    user  = userToConfigure;
    role  = roleToAdd;
  }
  
  public AddRoleTask authorOnly() {
    authorOnly = Boolean.TRUE;
    return this;
  }

  public AddRoleTask publicOnly() {
    authorOnly = Boolean.FALSE;
    return this;
  }

  @Override
  public Boolean isAuthorOnly() {
    return authorOnly;
  }
  
  @Override
  public void execute( @Nonnull InstallContext ctx ) throws TaskExecutionException {
    try {
      executeImpl();
    } catch( TaskExecutionException ex ) {
      throw ex;
    } catch( Exception ex ) {
      throw new TaskExecutionException( ex.getLocalizedMessage(), ex );
    }
  }
  
  private void executeImpl() throws Exception {
    SecuritySupport securitySupport = Components.getComponent( SecuritySupport.class );
    UserManager     userManager     = securitySupport.getUserManager();
    User            mgnlUser        = null;
    if( user != null ) {
      mgnlUser                      = userManager.getUser( user );
    } else {
      mgnlUser                      = userManager.getAnonymousUser();
    }
    if( mgnlUser == null ) {
      throw new TaskExecutionException( error_missing_user.format( user != null ? user : "anonymous"  ) );
    }
    RoleManager     roleManager     = securitySupport.getRoleManager();
    if( roleManager.getRole( role ) == null ) {
      throw new TaskExecutionException( error_missing_role.format( role ) );
    }
    userManager.addRole( mgnlUser, role );
  }
  
} /* ENDCLASS */
