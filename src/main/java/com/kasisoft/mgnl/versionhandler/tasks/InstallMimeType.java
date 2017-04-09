package com.kasisoft.mgnl.versionhandler.tasks;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

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
public class InstallMimeType implements TreeBuilderProvider {

  List<String[]>   mimeTypes = new ArrayList<>();

  public InstallMimeType( @Nonnull String suffix, @Nonnull String mimeType ) {
    mimeType( null, suffix, mimeType, null );
  }

  public InstallMimeType( @Nullable String name, @Nonnull String suffix, @Nonnull String mimeType ) {
    mimeType( name, suffix, mimeType, null );
  }
  
  public InstallMimeType( @Nullable String name, @Nonnull String suffix, @Nonnull String mimeType, @Nullable String icon ) {
    mimeType( name, suffix, mimeType, icon );
  }
  
  public InstallMimeType mimeType( @Nonnull String suffix, @Nonnull String mimeType ) {
    return mimeType( null, suffix, mimeType, null );
  }
  
  public InstallMimeType mimeType( @Nullable String name, @Nonnull String suffix, @Nonnull String mimeType ) {
    return mimeType( name, suffix, mimeType, null );
  }
  
  public InstallMimeType mimeType( @Nullable String name, @Nonnull String suffix, @Nonnull String mimeType, @Nullable String icon ) {
    mimeTypes.add( new String[] { toName( name, mimeType ), suffix, mimeType, icon } );
    return this;
  }
  
  private String toName( String name, String mimeType ) {
    if( name != null ) {
      return name;
    } else {
      return StringFunctions.firstDown( StringFunctions.camelCase( mimeType ) );
    }
  }
  
  @Override
  public String getTitle() {
    return getClass().getSimpleName();
  }

  @Override
  public String getDescription() {
    if( mimeTypes.size() == 1 ) {
      return desc_install_mime_type.format( mimeTypes.get(0)[0] );
    } else {
      return desc_install_mime_types.format( mimeTypes.stream().map( $ -> $[0] ).reduce( "", ($a, $b) -> $a + ", " + $b ) );
    }
  }

  @Override
  public TreeBuilder create() {
    TreeBuilder result = new TreeBuilder();
    result.sContentNode( "server/MIMEMapping" );
    mimeTypes.forEach( $ -> create( result, $ ) );
    result.sEnd();
    return result;
  }
  
  private void create( TreeBuilder tb, String[] mimeType ) {
    tb.sContentNode( mimeType[0] )
      .property( "extension" , mimeType[1] )
      .property( "mime-type" , mimeType[2] )
      .property( "icon"      , mimeType[3] )
    .sEnd();
  }

} /* ENDCLASS */
