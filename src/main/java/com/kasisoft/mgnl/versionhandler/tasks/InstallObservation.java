package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.repository.*;
import javax.jcr.observation.EventListener;

import com.kasisoft.libs.common.text.*;
import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;
import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallObservation implements TreeBuilderProvider {

  Class<? extends EventListener>    listenerClass;
  int                               delay;
  int                               maxDelay;
  String                            description;
  List<String>                      eventTypes;
  String                            name;
  String                            path;
  String                            workspace;
  String                            nodeType;
  boolean                           includeSubNodes;
  
  public InstallObservation( @Nonnull Class<? extends EventListener> listener, @Nonnull String nt ) {
    listenerClass   = listener;
    delay           = 30;
    maxDelay        = 60;
    description     = "";
    eventTypes      = new ArrayList<>(6);
    name            = StringFunctions.firstDown( listener.getSimpleName() );
    path            = "/";
    workspace       = RepositoryConstants.WEBSITE;
    nodeType        = nt;
    includeSubNodes = true;
  }
  
  public InstallObservation dontIncludeSubnodes() {
    includeSubNodes = false;
    return this;
  }
  
  public InstallObservation workspace( @Nonnull String newWorkspace ) {
    workspace = newWorkspace;
    return this;
  }
  
  public InstallObservation path( @Nonnull String newPath ) {
    path = newPath;
    return this;
  }
  
  public InstallObservation name( @Nonnull String newName ) {
    name = newName;
    return this;
  }
  
  public InstallObservation nodeAdded() {
    eventTypes.add( "NODE_ADDED" );
    return this;
  }

  public InstallObservation nodeMoved() {
    eventTypes.add( "NODE_MOVED" );
    return this;
  }

  public InstallObservation nodeRemoved() {
    eventTypes.add( "NODE_REMOVED" );
    return this;
  }

  public InstallObservation propertyAdded() {
    eventTypes.add( "PROPERTY_ADDED" );
    return this;
  }

  public InstallObservation propertyChanged() {
    eventTypes.add( "PROPERTY_CHANGED" );
    return this;
  }

  public InstallObservation propertyRemoved() {
    eventTypes.add( "PROPERTY_REMOVED" );
    return this;
  }

  public InstallObservation persist() {
    eventTypes.add( "PERSIST" );
    return this;
  }

  public InstallObservation description( @Nonnull String newDescription ) {
    description = newDescription;
    return this;
  }
  
  public InstallObservation maxDelay( int newMaxDelay ) {
    maxDelay = newMaxDelay;
    return this;
  }
  
  public InstallObservation delay( int newdelay ) {
    delay = newdelay;
    return this;
  }
  
  @Override
  public String getDescription() {
    return desc_install_filter.format( "---", listenerClass.getName() );
  }
  
  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder().sContent( "modules/observation/config/listenerConfigurations" );
    
    result.sContentNode( name )
      .property( "active"           , true  )
      .property( "delay"            , delay )
      .property( "description"      , description )
      .property( "eventTypes"       , StringFunctions.concatenate( ",", eventTypes ) )
      .property( "includeSubNodes"  , includeSubNodes )
      .property( "maxDelay"         , maxDelay )
      .property( "path"             , path )
      .property( "repository"       , workspace )
      .sContentNode( "listener" )
        .clazz( listenerClass )
        .property( "nodeType", nodeType )
      .sEnd()
    .sEnd();
    
    return result.sEnd();
  }
  
} /* ENDCLASS */
