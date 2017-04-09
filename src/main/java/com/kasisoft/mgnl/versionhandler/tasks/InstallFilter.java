package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import info.magnolia.module.delta.*;

import com.kasisoft.libs.common.text.*;

import com.kasisoft.mgnl.versionhandler.*;

import javax.annotation.*;

import java.util.*;

import lombok.experimental.*;

import lombok.*;

import info.magnolia.cms.filters.*;
import info.magnolia.voting.voters.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstallFilter implements TreeBuilderProvider {

  public static final String[] DEFAULT_BYPASSES = new String[] {
    "js", "ico", "png", "bmp", "jpg", "gif", "svg", "txt", "jpeg", "avi", "ttf", "mpg", "mpeg", "flv", "css", "woff", "woff2" 
  };
  
  Class<? extends MgnlFilter>   filterClass;
  String                        filterName;
  Set<String>                   bypasses;
  String                        bypassPrefix;
  boolean                       active;
  String                        afterNode;
  
  public InstallFilter( @Nonnull Class<? extends MgnlFilter> filter ) {
    filterClass = filter;
    filterName  = null;
    active      = true;
    bypasses    = new HashSet<>();
    afterNode   = null;
  }
  
  public InstallFilter afterNode( @Nonnull String name ) {
    afterNode = name;
    return this;
  }
  
  public InstallFilter filterName( @Nonnull String name ) {
    filterName = name;
    return this;
  }

  public InstallFilter disable() {
    active = false;
    return this;
  }
  
  public InstallFilter bypassResource( String ... suffices ) {
    if( (suffices == null) || (suffices.length == 0) ) {
      bypasses.addAll( Arrays.asList( DEFAULT_BYPASSES ) );
    } else {
      bypasses.addAll( Arrays.asList( suffices ) );
    }
    return this;
  }
  
  public InstallFilter bypassInternal() {
    return bypassPrefixed( "/." );
  }
  
  public InstallFilter bypassPrefixed( @Nonnull String prefix ) {
    bypassPrefix = prefix;
    return this;
  }

  @Override
  public String getDescription() {
    return desc_install_filter.format( getFilterName(), filterClass.getName() );
  }

  private String getFilterName() {
    return filterName != null ? filterName : StringFunctions.firstDown( filterClass.getSimpleName() );
  }
  
  @Override
  public TreeBuilder create() {
    String      name   = getFilterName();
    TreeBuilder result = new TreeBuilder()
      .sContent( "server/filters" )
        .sContent( name )
          .clazz( filterClass )
          .property( "enabled", String.valueOf( active ) )
        .sEnd()
      .sEnd()
      ;
    if( bypassPrefix != null ) {
      result
        .sContent( "server/filters" )
          .sContent( name )
            .sContentNode( "bypasses" )
              .sContentNode( "BypassUsingPrefix" )
                .clazz( URIStartsWithVoter.class )
                .property( "pattern", bypassPrefix )
              .sEnd()
            .sEnd()
          .sEnd()
        .sEnd();
    }
    if( ! bypasses.isEmpty() ) {
      List<String> suffices = new ArrayList<>( bypasses );
      Collections.sort( suffices );
      result
        .sContent( "server/filters" )
          .sContent( name )
            .sContentNode( "bypasses" )
              .sContentNode( "BypassResourceRequest" )
                .clazz( URIPatternVoter.class )
                .propertyF( "pattern", "^*.(%s)$", StringFunctions.concatenate( "|", suffices ) )
              .sEnd()
            .sEnd()
          .sEnd()
        .sEnd();
    }
    return result;
  }
  
  @Override
  public List<Task> postExecute() {
    if( afterNode != null ) {
      String name = getFilterName();
      return Arrays.asList(
        new OrderNodeAfterTask( desc_positioning_filter.format( name, afterNode ), String.format( "/server/filters/%s", name ), afterNode )
      );
    }
    return Collections.emptyList();
  }
  
} /* ENDCLASS */
