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
  
  public InstallTemplateAvailability( @Nonnull String site ) {
    declarations = new ArrayList<>();
    siteName     = site;
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
    result.sContentNode( "modules/site/config" );
    result.sContentNode( siteName );
    result.sContentNode( "templates/availability/templates" );
    declarations.forEach( $ -> create( result, $ ) );
    result.sEnd();
    result.sEnd();
    result.sEnd();
    return result;
  }
  
  private void create( TreeBuilder result, TemplateDeclaration decl ) {
    result
      .sContentNode( decl.getName() )
        .property( "id", decl.getId() )
      .sEnd();
  }

} /* ENDCLASS */