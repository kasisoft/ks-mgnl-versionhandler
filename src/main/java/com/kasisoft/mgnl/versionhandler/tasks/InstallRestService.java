package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallRestService implements TreeBuilderProvider {

  String      name;
  Class<?>    endpointDef;
  Class<?>    implementation;
  
  public InstallRestService( @Nonnull Class<?> def, @Nonnull Class<?> impl ) {
    name            = null;
    endpointDef     = def;
    implementation  = impl;
  }
  
  public InstallRestService serviceName( @Nonnull String serviceName ) {
    name = serviceName;
    return this;
  }
  
  private String getServiceName() {
    return name != null ? name : StringFunctions.firstDown( implementation.getSimpleName() );
  }
  
  @Override
  public String getDescription() {
    return desc_install_rest_service.format( getServiceName(), implementation.getName() );
  }

  @Override
  public TreeBuilder create() {
    return new TreeBuilder()
      .sFolder( "modules/rest-services/rest-endpoints" )
        .sContentNode( getServiceName() )
          .clazz( endpointDef )
          .implementationClass( implementation )
        .sEnd()
      .sEnd()
      ;
  }
  
} /* ENDCLASS */
