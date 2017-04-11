package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.repository.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.jcr.util.*;

import javax.annotation.*;
import javax.jcr.*;

import lombok.experimental.*;

import lombok.*;

/**
 * In contrast to other SetProperty tasks this one will fail if the node doesn't exist. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KsSetPropertyTask extends AbstractTask {
  
  String   workspace;
  String   propertyPath;
  String   value;
  
  public KsSetPropertyTask( @Nonnull String path, @Nonnull String propValue ) {
    super( "KsSetPropertyTask", task_set_property_desc.format( path, propValue ) );
    propertyPath = path;
    value        = propValue;
  }
  
  public KsSetPropertyTask workspace( @Nonnull String newWorkspace ) {
    workspace = newWorkspace;
    return this;
  }

  @Override
  public void execute( @Nonnull InstallContext ctx ) throws TaskExecutionException {
    try {
      executeImpl( ctx );
    } catch( Exception ex ) {
      throw new TaskExecutionException( ex.getLocalizedMessage(), ex );
    }
  }
  
  private void executeImpl( InstallContext ctx ) throws Exception {
    if( workspace == null ) {
      workspace = RepositoryConstants.CONFIG;
    }
    int  idx    = propertyPath.indexOf('@');
    if( (idx == -1) || (idx == propertyPath.length() - 1) ) {
      throw new TaskExecutionException( error_invalid_property_path.format( propertyPath ) );
    }
    String path   = propertyPath.substring( 0, idx );
    Node   node   = SessionUtil.getNode( ctx.getJCRSession( workspace ), path );
    if( node == null ) {
      throw new TaskExecutionException( error_failed_to_access_node.format( path, workspace ) );
    }
    PropertyUtil.setProperty( node, propertyPath.substring( idx + 1 ), value );
  }

} /* ENDCLASS */