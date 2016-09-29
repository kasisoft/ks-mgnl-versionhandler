package com.kasisoft.mgnl.versionhandler.internal;

import com.kasisoft.libs.common.i18n.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Messages {

  @I18N("cannot determine name of record '%s'")
  static I18NFormatter              error_cannot_determine_name;
  
  @I18N("failed to update module '%s' from version %s. cause: %s")
  static I18NFormatter              error_failed_to_update;

  @I18N("invalid node type. got '%s' instead of '%s'")
  static I18NFormatter              error_invalid_nodetype;
  
  @I18N("the resource '%s' could not be loaded. cause: %s")
  static I18NFormatter              error_loading;
  
  @I18N("the resource '%s' is not on the classpath.")
  static I18NFormatter              error_missing_resource;
  
  @I18N("failed to determine version of module '%s'. cause: %s")
  static I18NFormatter              error_missing_version;
  
  @I18N("cannot register running key %d for discriminator '%s' as it had already been registered")
  static I18NFormatter              error_registration_failure;
  
  
  @I18N("configuring %d/%d on workspace '%s'")
  static I18NFormatter              msg_configuring;
  
  @I18N("installing to %s")
  static I18NFormatter              msg_installing;
  
  @I18N("found module '%s' but without update property '%s'. assuming installation.")
  static I18NFormatter              msg_missing_running;
  
  @I18N("got %d tree configurations to setup")
  static I18NFormatter              msg_n_configurations;
  
  @I18N("testing for the module version '%s'")
  static I18NFormatter              msg_testing_version;

  @I18N("updating to %s")
  static I18NFormatter              msg_updating;
  
  @I18N("updating %s / [%s:%d]")
  static I18NFormatter              msg_updating_task;
  

  @I18N("Granting module configuration for module '%s'")
  static I18NFormatter              task_grant_module_desc;

  @I18N("Grant module")
  static String                     task_grant_module_name;
  
  @I18N("Setting property %s@%s to '%s'")
  static I18NFormatter              task_set_property_desc;
  
  @I18N("Setting property")
  static String                     task_set_property_name;

  static {
    I18NSupport.initialize( Messages.class );
  }

} /* ENDCLASS */
