package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.repository.*;

import info.magnolia.module.delta.*;

import info.magnolia.module.*;

import info.magnolia.jcr.util.*;

import com.kasisoft.mgnl.util.*;

import javax.annotation.*;
import javax.jcr.*;

import java.util.function.*;

import lombok.experimental.*;

import lombok.*;

/**
 * In contrast to other SetProperty tasks this one will fail if the node doesn't exist. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KsSetPropertyTask extends AbstractTask {
  
  String              workspace;
  String              propertyPath;
  String              value;
  Supplier<String>    valueSupplier;
  boolean             createPath;

  public KsSetPropertyTask( @Nullable String moduleName, @Nonnull String path, @Nonnull String propValue ) {
    super( KsSetPropertyTask.class.getSimpleName(), task_set_property_desc.format( toFullPath( moduleName, path ), propValue ) );
    propertyPath  = toFullPath( moduleName, path );
    value         = propValue;
    valueSupplier = null;
    createPath    = false;
  }

  public KsSetPropertyTask( @Nullable String moduleName, @Nonnull String path, @Nonnull Supplier<String> propSupplier ) {
    super( KsSetPropertyTask.class.getSimpleName(), task_set_property_desc.format( toFullPath( moduleName, path ), "[property suppplier]" ) );
    propertyPath  = toFullPath( moduleName, path );
    value         = null;
    valueSupplier = propSupplier;
  }
  
  public KsSetPropertyTask( @Nonnull String path, @Nonnull String propValue ) {
    this( null, path, propValue );
  }

  public KsSetPropertyTask( @Nonnull String path, @Nonnull Supplier<String> propSupplier ) {
    this( null, path, propSupplier );
  }

  private static String toFullPath( String moduleName, String path ) {
    String result = path;
    if( moduleName != null ) {
      result = "/modules/" + moduleName;
      if( ! result.endsWith("/") ) {
        result += "/";
      }
      result += path;
    }
    return result;
  }
  
  public KsSetPropertyTask createPath() {
    createPath = true;
    return this;
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
    if( propertyPath.charAt(0) != '/' ) {
      throw new TaskExecutionException( error_invalid_property_path.format( propertyPath ) );
    }
    int  idx    = propertyPath.indexOf('@');
    if( (idx == -1) || (idx == propertyPath.length() - 1) ) {
      throw new TaskExecutionException( error_invalid_property_path.format( propertyPath ) );
    }
    String path   = propertyPath.substring( 0, idx );
    Node   node   = null;
    if( createPath ) {
      node = NodeFunctions.getOrCreateNode( ctx.getJCRSession( workspace ).getRootNode(), path.substring(1) );
    } else {
      node = SessionUtil.getNode( ctx.getJCRSession( workspace ), path );
    }
    if( node == null ) {
      throw new TaskExecutionException( error_failed_to_access_node.format( path, workspace ) );
    }
    if( valueSupplier != null ) {
      value = valueSupplier.get();
    }
    PropertyUtil.setProperty( node, propertyPath.substring( idx + 1 ), value );
  }
  
} /* ENDCLASS */
