package com.kasisoft.mgnl.versionhandler.internal;

import com.kasisoft.libs.common.i18n.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class Messages {

  @I18N("configuring workspace mapping at '%s' for workspace '%s' ('%s' -> '%s')")
  public static I18NFormatter               desc_configure_workspace_mapping;
  
  @I18N("installing filter '%s' (%s)")
  public static I18NFormatter               desc_install_filter;
  
  @I18N("positioning filter '%s' after '%s'")
  public static I18NFormatter               desc_positioning_filter;

  @I18N("installing image variations for theme '%s'")
  public static I18NFormatter               desc_install_image_variations;

  @I18N("installing flush all for workspace '%s'")
  public static I18NFormatter               desc_install_flush_all;

  @I18N("installing rest service '%s' (implementation: %s)")
  public static I18NFormatter               desc_install_rest_service;
  
  @I18N("installing virtual uri mapping for '%s'")
  public static I18NFormatter               desc_install_virtal_uri_mapping;

  @I18N("installing theme '%s' for site '%s'")
  public static I18NFormatter               desc_installing_site_config;

  @I18N("installing theme '%s' into module '%s'")
  public static I18NFormatter               desc_installing_module;

  @I18N("installing servlet '%s'")
  public static I18NFormatter               desc_install_servlet;
  
  @I18N("installing regex virtual uri mapping '%s' for '%s' -> '%s'")
  public static I18NFormatter               desc_install_virtual_uri_regex_mapping;
  
  @I18N("installing regex virtual uri mapping '%s': %s")
  public static I18NFormatter               desc_install_virtual_uri_regex_mappings;

  @I18N("installing mime type '%s'")
  public static I18NFormatter               desc_install_mime_type;
  
  @I18N("installing mime types: %s")
  public static I18NFormatter               desc_install_mime_types;

  @I18N("configuring template declarations for site '%s'")
  public static I18NFormatter               desc_install_template_declarations;
  
  @I18N("installing theme '%s' for module '%s'")
  public static I18NFormatter               desc_install_theme;
  
  @I18N("registering app launcher group '%s'")
  public static I18NFormatter               desc_register_app_launcher_group;

  @I18N("registering app '%s' in launcher group '%s'")
  public static I18NFormatter               desc_register_app_in_group;

  @I18N("registering subscription for workspace '%s' ('%s' -> '%s')")
  public static I18NFormatter               desc_register_workspace_subscription;

  @I18N("cannot determine name of record '%s'")
  public static I18NFormatter               error_cannot_determine_name;

  @I18N("failed to update module '%s' from version %s. cause: %s")
  public static I18NFormatter               error_failed_to_update;

  @I18N("failed to access module '%s'")
  public static I18NFormatter               error_failed_to_access_module;
  
  @I18N("failed to access node '%s' in workspace '%s'")
  public static I18NFormatter               error_failed_to_access_node;
  
  @I18N("the property path '%s' must contain a '@' to separate the property name")
  public static I18NFormatter               error_invalid_property_path;
  
  @I18N("invalid node type at (%s/%s). got '%s' instead of '%s'")
  public static I18NFormatter               error_invalid_nodetype;
  
  @I18N("the resource '%s' could not be loaded. cause: %s")
  public static I18NFormatter               error_loading;
  
  @I18N("the resource '%s' is not on the classpath.")
  public static I18NFormatter               error_missing_resource;

  @I18N("there's no role with the name '%s'.")
  public static I18NFormatter               error_missing_role;

  @I18N("there's no account with the user name '%s'.")
  public static I18NFormatter               error_missing_user;

  @I18N("failed to determine version of module '%s'. cause: %s")
  public static I18NFormatter               error_missing_version;
  
  @I18N("cannot register running key %d for discriminator '%s' as it had already been registered")
  public static I18NFormatter               error_registration_failure;

  @I18N("[%d/%d] %s: configuring on workspace '%s'")
  public static I18NFormatter               msg_configuring;

  @I18N("[%d/%d] %s: not configuring on workspace '%s' (authorOnly=%s)")
  public static I18NFormatter               msg_not_configuring;

  @I18N("[%d/%d] executing task '%s'")
  public static I18NFormatter               msg_executing_task;

  @I18N("installing to %s")
  public static I18NFormatter               msg_installing;
  
  @I18N("found module '%s' but without update property '%s'. assuming installation.")
  public static I18NFormatter               msg_missing_running;
  
  @I18N("got %d tree configurations to setup")
  public static I18NFormatter               msg_n_configurations;
  
  @I18N("running task '%s' (discriminator=%s, update num=%d)")
  public static I18NFormatter               msg_running_task;
  
  @I18N("testing for the module version '%s'")
  public static I18NFormatter               msg_testing_version;

  @I18N("updating to %s")
  public static I18NFormatter               msg_updating;
  
  @I18N("updating %s / [%s:%d]")
  public static I18NFormatter               msg_updating_task;

  @I18N("")
  public static I18NFormatter               task_adding_role;
  
  @I18N("granting module configuration for module '%s'")
  public static I18NFormatter               task_grant_module_desc;

  @I18N("grant module")
  public static String                      task_grant_module_name;
  
  @I18N("positioning filter servlet '%s' after '%s'")
  public static I18NFormatter               task_positioning_filter_servlet;
  
  @I18N("installing role '%s'")
  public static I18NFormatter               task_installing_role;
  
  @I18N("setting property '%s' to '%s'")
  public static I18NFormatter               task_set_property_desc;
  
  @I18N("setting property")
  public static String                      task_set_property_name;
  
  static {
    I18NSupport.initialize( Messages.class );
  }

} /* ENDCLASS */
