package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.repository.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JcrConfigurationTask extends AbstractRepositoryTask {

  List<Object[]>   builders = new ArrayList<>();
  
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
   * Registers the supplied {@link TreeBuilder} instance to be executed on the {@link RepositoryConstants#CONFIG} workspace.
   * 
   * @param builder   The builder used to produce the structure.
   */
  protected void register( @Nonnull TreeBuilder builder ) {
    register( RepositoryConstants.CONFIG, builder );
  }
  
  /**
   * Registers the supplied {@link TreeBuilder} instance to be executed on the specified <param>workspace</param>.
   *
   * @param workspace   The workspace to be used.
   * @param builder     The builder used to produce the structure.
   */
  protected void register( @Nonnull String workspace, @Nonnull TreeBuilder builder ) {
    builders.add( new Object[] { workspace, builder } );
  }
  
  @Override
  protected void doExecute( @Nonnull InstallContext ctx ) throws RepositoryException, TaskExecutionException {
    log.debug( msg_n_configurations.format( builders.size() ) );
    int i = 1;
    for( Object[] builder : builders ) {
      String      workspace   = (String) builder[0];
      log.debug( msg_configuring.format( i, builders.size(), workspace ) );
      TreeBuilder ntBuilder   = (TreeBuilder) builder[1];
      Session     jcrSession  = ctx.getJCRSession( workspace );
      ntBuilder.build( new NodeProducer( jcrSession ) );
      jcrSession.save();
      i++;
    }
  }

} /* ENDCLASS */
