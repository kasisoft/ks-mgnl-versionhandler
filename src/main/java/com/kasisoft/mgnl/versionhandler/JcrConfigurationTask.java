package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;
import static com.kasisoft.mgnl.util.JcrProperties.*;

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

  // either Task or Triple<String, TreeBuilder, Boolean>
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
    register( tbProvider.getWorkspace(), tbProvider.create(), tbProvider.authorOnly() );
    tbProvider.postExecute().stream()
      .map( $ -> protect( tbProvider.getTitle(), tbProvider.authorOnly(), $ ) )
      .forEach( this::register );
  }
  
  private Task protect( String title, Boolean authorOnly, Task task ) {
    // make sure that the post execution task is only executed when required 
    Task result = task;
    if( authorOnly != null ) {
      if( authorOnly.booleanValue() ) {
        result = new IsAuthorInstanceDelegateTask( title, result );
      } else {
        result = new IsAuthorInstanceDelegateTask( title, (Task) null, result );
      }
    }
    return result;
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
    return register( workspace, builder, null );
  }

  /**
   * Registers the supplied {@link TreeBuilder} instance to be executed on the specified <param>workspace</param>.
   *
   * @param workspace   The workspace to be used.
   * @param builder     The builder used to produce the structure.
   * @param authorOnly  <code>null</code> <=> Install on author and public.
   *                    <code>true</code> <=> Install on author only.
   *                    <code>true</code> <=> Install on public only.
   * 
   * @return   this
   */
  @Nonnull
  protected <R extends JcrConfigurationTask> R register( @Nonnull String workspace, @Nonnull TreeBuilder builder, @Nullable Boolean authorOnly ) {
    builders.add( new Triple<String, TreeBuilder, Boolean>( workspace, builder, authorOnly ) );
    return (R) this;
  }

  @Override
  protected void doExecute( @Nonnull InstallContext ctx ) throws RepositoryException, TaskExecutionException {
    log.debug( msg_n_configurations.format( builders.size() ) );
    int i = 1;
    boolean isAuthor = Admin.getValue().booleanValue();
    boolean isPublic = !isAuthor;
    for( Object obj : builders ) {
      if( obj instanceof Task ) {
        ((Task) obj).execute( ctx );
      } else {
        Triple<String, TreeBuilder, Boolean>  triple      = (Triple<String, TreeBuilder, Boolean>) obj;
        String                                workspace   = triple.getValue1();
        TreeBuilder                           ntBuilder   = triple.getValue2();
        Boolean                               authorOnly  = triple.getValue3();
        boolean                               execute     = true;
        if( authorOnly != null ) {
          if( authorOnly.booleanValue() ) {
            execute = isAuthor;
          } else {
            execute = isPublic;
          }
        }
        if( execute ) {
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
  }

} /* ENDCLASS */
