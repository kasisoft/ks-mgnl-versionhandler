package com.kasisoft.mgnl.versionhandler;

import info.magnolia.module.model.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.jcr.util.*;

import org.apache.commons.lang3.*;

import javax.annotation.*;
import javax.jcr.*;

import java.util.stream.*;

import java.util.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This {@link ModuleVersionHandler} is pretty simple as the task execution is tracked through a running number.
 * It's possible to associate this number with a discriminator in order to distinguish various numbers. Since
 * this running number does <b>not</b> depend on the actual version it's possible to add different tasks to
 * the same version. In case of an error the repeated execution of this handler will attempt to execute
 * outstanding tasks.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KsModuleVersionHandler implements ModuleVersionHandler {
  
  static final String MSG_FAILED_TO_UPDATE        = "failed to update module '{}' from version {}. cause: {}";
  static final String MSG_MISSING_RUNNING_NUMBER  = "found module '{}' but without update property '{}'. assuming installation.";
  static final String MSG_MISSING_VERSION         = "failed to determine version of module [{}]. cause: {}";
  static final String MSG_TESTING_VERSION         = "testing for the module version [{}]";

  static final String FMT_FAILED_TO_REGISTER      = "cannot register running key %d for discriminator '%s' as it had already been registered";
  static final String FMT_INSTALLING              = "installing to %s";
  static final String FMT_UPATING                 = "updating to %s";
  static final String FMT_UPDATING_TASK           = "updating %s / [%s:%d]";

  static final String PN_VERSION                  = "version";
  
  static final String FMT_UPDATESET               = "update_%s";
  static final String FMT_MODULES                 = "/modules/%s";

  static final String DEFAULT_DISCRIMINATOR       = "default";
  
  Map<String, Map<Integer, Task>>   tasks = new HashMap<>();
  
  /**
   * Registers the supplied task with a running number.
   * 
   * @param running   The running number.
   * @param task      The task that will be executed.
   */
  protected void register( int running, @Nonnull Task task ) {
    register( running, null, task );
  }
  
  /**
   * Registers the supplied task with a running number and an optional discrimator.
   * 
   * @param running         The running number.
   * @param discriminator   A distinction literal.
   * @param task            The task that will be executed.
   */
  protected void register( int running, @Nullable String discriminator, @Nonnull Task task ) {
    discriminator = StringUtils.defaultIfBlank( discriminator, DEFAULT_DISCRIMINATOR );
    Map<Integer, Task> map = tasks.get( discriminator );
    if( map == null ) {
      map = new HashMap<>();
      tasks.put( discriminator, map );
    }
    Integer key = Integer.valueOf( running );
    if( map.containsKey( key ) ) {
      String msg = String.format( FMT_FAILED_TO_REGISTER, key, discriminator );
      log.error( msg );
      throw new IllegalStateException( msg );
    }
    map.put( key, task );
  }
  
  /**
   * Returns a list of tasks that will always be executed as part of the update process. The returned list
   * is mutable so it's not necessary to create new list instances.
   * 
   * @return   A list of tasks that will always be executed as part of the update process.
   */
  @Nonnull
  protected List<Task> getMaintenanceTasks() {
    return new ArrayList<>();
  }

  @Override 
  @Nonnull
  public final List<Delta> getDeltas( @Nonnull InstallContext ctx, @Nonnull Version from ) {
    try { 
      return getDeltasImpl( ctx, from );
    } catch( IllegalStateException ex ) {
      log.error( MSG_FAILED_TO_UPDATE, ctx.getCurrentModuleDefinition().getName(), from, ex );
      throw ex;
    } catch( Exception ex ) {
      log.error( MSG_FAILED_TO_UPDATE, ctx.getCurrentModuleDefinition().getName(), from, ex );
      throw new IllegalStateException(ex);
    }
  }

  private List<Integer> getUpdatesToBeDone( Set<Integer> available, int current ) {
    List<Integer> list = new ArrayList<>( available );
    Collections.sort( list );
    return list.stream().filter( $ -> $.intValue() > current ).collect( Collectors.toList() );
  }
  
  private List<Delta> getDeltasImpl( InstallContext ctx, Version from ) throws RepositoryException {
    
    Version     version       = ctx.getCurrentModuleDefinition().getVersion();
    List<Delta> result        = new ArrayList<>(3);
    String      moduleName    = ctx.getCurrentModuleDefinition().getName();
    boolean     installation  = from == Version.UNDEFINED_FROM;
    if( installation ) {
      // in case of an installation we might need to setup the basic module structure
      result.add( db( from, version ).addTask( new GrantModuleTask( moduleName ) ) );
    }
    
    // process each tasks per discriminator
    for( Map.Entry<String, Map<Integer, Task>> perDiscriminator : tasks.entrySet() ) {
      String             discriminator = perDiscriminator.getKey();
      // get all registered tasks
      Map<Integer, Task> updates       = perDiscriminator.getValue();
      // get the current running number (the tasks that already passed)
      int                current       = getDiscriminatorValue( ctx, discriminator, installation );
      // filter only remaining tasks and add them
      List<Integer>      matches       = getUpdatesToBeDone( updates.keySet(), current );
      matches.forEach( $ -> add( result, moduleName, discriminator, version, $, updates.get($) ) );
    }
    
    // register maintenance tasks if there are some
    List<Task> maintenanceTasks = getMaintenanceTasks();
    if( ! maintenanceTasks.isEmpty() ) {
      result.add( db( from, version ).addTasks( maintenanceTasks ) );
    }
    
    // update the module version
    result.add( db( from, version ).addTask( new SetPropertyTask( moduleName, PN_VERSION, version ) ) );
    
    return result;
    
  }
  
  private DeltaBuilder db( Version current, Version toVersion ) {
    if( current == Version.UNDEFINED_FROM ) {
      return DeltaBuilder.install( toVersion, String.format( FMT_INSTALLING, toVersion ) );
    } else {
      return DeltaBuilder.update( toVersion, String.format( FMT_UPATING, toVersion ) );
    }
  }
  
  private void add( List<Delta> deltas, String modulename, String discriminator, Version toVersion, Integer running, Task task ) {
    deltas.add( 
      DeltaBuilder.update( toVersion, String.format( FMT_UPDATING_TASK, toVersion, discriminator, running ) )
        // execute the task itself
        .addTask( task )
        // update the running number
        .addTask( new SetPropertyTask( modulename, String.format( FMT_UPDATESET, discriminator ), running.intValue() ) )
    );
  }

  private int getDiscriminatorValue( InstallContext ctx, String discriminator, boolean installation ) throws RepositoryException {
    int result = -1;
    if( ! installation ) {
      String propertyName = String.format( FMT_UPDATESET, discriminator );
      Node   module       = SessionUtil.getNode( ctx.getConfigJCRSession(), getModulePath( ctx ) );
      try {
        result = Integer.parseInt( PropertyUtil.getString( module, propertyName ) );
      } catch( NumberFormatException ex ) {
        log.warn( MSG_MISSING_RUNNING_NUMBER, ctx.getCurrentModuleDefinition().getName(), propertyName );
      }
    }
    return result;
  }

  @SuppressWarnings("deprecation")
  @Override 
  @Nonnull 
  public final Version getCurrentlyInstalled( @Nonnull InstallContext ctx ) {
    
    Version result = Version.UNDEFINED_FROM;
    
    try {
      
      log.debug( MSG_TESTING_VERSION, ctx.getCurrentModuleDefinition() );

      Node module = SessionUtil.getNode( ctx.getConfigJCRSession(), getModulePath( ctx ) );
      if( module != null ) {

        String version = StringUtils.trimToNull( PropertyUtil.getString( module, PN_VERSION ) );
        if( version != null ) {
          result = Version.parseVersion( version );
          
        }
        
      }
      
    } catch( Exception ex ) {
      log.error( MSG_MISSING_VERSION, ctx.getCurrentModuleDefinition(), ex.getLocalizedMessage(), ex );
      throw new IllegalStateException(ex);
    }
    
    return result;
    
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nonnull 
  public final Delta getStartupDelta( @Nonnull InstallContext ctx ) {
    return DeltaBuilder.startup( ctx.getCurrentModuleDefinition(), Collections.emptyList() );
  }

  private static String getModulePath( InstallContext ctx ) {
    return String.format( FMT_MODULES, ctx.getCurrentModuleDefinition().getName() );
  }
  
  private static class SetPropertyTask extends AbstractRepositoryTask {
    
    static final String NAME        = "Setting property";
    static final String DESCRIPTION = "Setting property %s@%s to '%s'";
    
    String   value;
    String   property;
    
    protected SetPropertyTask( String module, String prop, Version toVersion ) {
      super( NAME, String.format( DESCRIPTION, module, prop, toVersion ) );
      value    = toVersion.toString();
      property = prop;
    }

    protected SetPropertyTask( String module, String prop, int run ) {
      super( NAME, String.format( DESCRIPTION, module, prop, run ) );
      value    = String.valueOf( run );
      property = prop;
    }
    
    @Override
    protected void doExecute( InstallContext ctx ) throws RepositoryException, TaskExecutionException {
      Node module = SessionUtil.getNode( ctx.getConfigJCRSession(), getModulePath( ctx ) );
      PropertyUtil.setProperty( module, property, value );
    }

  } /* ENDCLASS */

  private static class GrantModuleTask extends AbstractRepositoryTask {
    
    static final String DESCRIPTION = "Granting module configuration for module '%s'";
    static final String NAME        = "Grant module";

    protected GrantModuleTask( String moduleName ) {
      super( NAME, String.format( DESCRIPTION, moduleName ) );
    }

    @Override
    protected void doExecute( InstallContext ctx ) throws RepositoryException, TaskExecutionException {
      Node module = SessionUtil.getNode( ctx.getConfigJCRSession(), getModulePath( ctx ) );
      if( module == null ) {
        module = NodeUtil.createPath( ctx.getConfigJCRSession().getRootNode(), getModulePath( ctx ), NodeTypes.Content.NAME );
      }
    }

  } /* ENDCLASS */

} /* ENDCLASS */
