package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import com.kasisoft.mgnl.util.model.*;
import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallTemplateAvailability implements TreeBuilderProvider {
  
  List<TemplateDeclaration>   declarations;
  String                      siteName;
  Class<?>                    templateAvailability;
  String                      prototypeId;
  
  public InstallTemplateAvailability( @Nonnull String site ) {
    declarations = new ArrayList<>();
    siteName     = site;
  }

  public InstallTemplateAvailability prototypeId( @Nonnull String prototype ) {
    prototypeId = prototype;
    return this;
  }
  
  public InstallTemplateAvailability templateAvailability( @Nonnull Class<?> availabilityClass ) {
    templateAvailability = availabilityClass;
    return this;
  }
  
  public InstallTemplateAvailability templateDeclarations( @Nonnull TemplateDeclaration... templateDeclarations ) {
    declarations.addAll( Arrays.asList( templateDeclarations ) );
    return this;
  }

  @Override
  public String getDescription() {
    return desc_install_template_declarations.format( siteName );
  }

  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder();
    result.sContentNode( "modules/site/config/site/templates" );
    
    if( prototypeId != null ) {
      result.property( "class", "info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings" );
      result.property( "prototypeId", prototypeId );
    }
    
    result.sContentNode( "availability" );
    if( templateAvailability != null ) {
      result.clazz( templateAvailability );
    }
    
    Set<String> done = new HashSet<>();
    result.sContentNode( "templates" );
    declarations.forEach( $ -> create( result, $, done ) );
    result.sEnd();
    
    result.sEnd();
    
    result.sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, TemplateDeclaration decl, Set<String> done ) {
    if( ! done.contains( decl.getId() ) ) {
      done.add( decl.getId() );
      result
        .sContentNode( decl.getName() )
          .property( "id", decl.getId() )
        .sEnd();
    }
  }

} /* ENDCLASS */
