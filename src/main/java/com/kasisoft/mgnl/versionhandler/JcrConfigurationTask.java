package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.repository.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import com.kasisoft.libs.common.model.*;

import javax.annotation.*;
import javax.jcr.*;

import java.util.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This basic task allows to execute the tree based creation on different workspaces. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class JcrConfigurationTask extends AbstractRepositoryTask {

  // either Task or Pair<String, TreeBuilder>
  List<Object>   builders = new ArrayList<>();
  
  /**
   * Initializes this task with a certain name and a description.
   * 
   * @param name          The name to be used while executing this task.
   * @param description   Some description for this task.
   */
  public JcrConfigurationTask( @Nonnull String name, @Nonnull String description ) {
    super( name, description );
  }

  /**
   * Initializes this task for a certain {@link TreeBuilderProvider} instance.
   * 
   * @param tbProvider   The {@link TreeBuilderProvider} containing all necessary infos.
   */
  public JcrConfigurationTask( @Nonnull TreeBuilderProvider tbProvider ) {
    super( tbProvider.getTitle(), tbProvider.getDescription() );
    register( tbProvider.create() );
  }

  /**
   * Registers the supplied {@link Task} instance to be executed as part of this task.
   * 
   * @param task   The task that will be executed as part of this task.
   * 
   * @return   this
   */
  @Nonnull
  protected <R extends JcrConfigurationTask> R register( @Nonnull Task task ) {
    builders.add( task );
    return (R) this;
  }

  /**
   * Registers the supplied {@link TreeBuilder} instance to be executed on the {@link RepositoryConstants#CONFIG} workspace.
   * 
   * @param builder   The builder used to produce the structure.
   * 
   * @return   this
   */
  @Nonnull
  protected <R extends JcrConfigurationTask> R register( @Nonnull TreeBuilder builder ) {
    return register( RepositoryConstants.CONFIG, builder );
  }
  
  /**
   * Registers the supplied {@link TreeBuilder} instance to be executed on the specified <param>workspace</param>.
   *
   * @param workspace   The workspace to be used.
   * @param builder     The builder used to produce the structure.
   * 
   * @return   this
   */
  @Nonnull
  protected <R extends JcrConfigurationTask> R register( @Nonnull String workspace, @Nonnull TreeBuilder builder ) {
    builders.add( new Pair<String, TreeBuilder>( workspace, builder ) );
    return (R) this;
  }
  
  @Override
  protected void doExecute( @Nonnull InstallContext ctx ) throws RepositoryException, TaskExecutionException {
    log.debug( msg_n_configurations.format( builders.size() ) );
    int i = 1;
    for( Object obj : builders ) {
      if( obj instanceof Task ) {
        ((Task) obj).execute( ctx );
      } else {
        Pair<String, TreeBuilder> pair = (Pair<String, TreeBuilder>) obj;
        String      workspace   = pair.getKey();
        TreeBuilder ntBuilder   = pair.getValue();
        log.debug( msg_configuring.format( i, builders.size(), workspace ) );
        if( log.isDebugEnabled() ) {
          DescriptiveProducer prod = new DescriptiveProducer();
          ntBuilder.build( prod );
          log.debug( prod.toString() );
        }      
        Session jcrSession  = ctx.getJCRSession( workspace );
        ntBuilder.build( new NodeProducer( jcrSession ) );
        jcrSession.save();
        i++;
      }
    }
  }

} /* ENDCLASS */
