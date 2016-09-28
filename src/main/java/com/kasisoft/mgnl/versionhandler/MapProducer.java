package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import java.util.function.*;

import java.util.*;

import lombok.extern.slf4j.*;

import lombok.experimental.*;

import lombok.*;

/**
 * This {@link Producer} implementation generates a Map based tree structure. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapProducer implements Producer<Map<String, Object>> {

  static final String PN_NODETYPE = "nodetype";
  
  Function<Exception, IllegalStateException>   handler = $ -> new IllegalStateException($);
  
  @Override
  public Map<String, Object> getRootNode() {
    return new HashMap<>();
  }
  
  @Override
  public Map<String, Object> getChild( Map<String, Object> parent, String name, String nodeType, boolean fail ) {
    Map<String, Object> result = null;
    if( parent.containsKey( name ) ) {
      result = (Map<String, Object>) parent.get( name );
      if( result.containsKey( PN_NODETYPE ) ) {
        if( ! result.get( PN_NODETYPE ).equals( nodeType ) ) {
          String invalidNodetype = error_invalid_nodetype.format( result.get( PN_NODETYPE ), nodeType );
          if( fail ) {
            handler.apply( new IllegalStateException( invalidNodetype ) );
          } else {
            log.warn( invalidNodetype );
          }
        }
      } else {
        result.put( PN_NODETYPE, nodeType );
      }
    } else {
      result = new HashMap<>();
      result.put( PN_NODETYPE, nodeType );
      parent.put( name, result );
    }
    return result;
  }
  
  @Override
  public void setBasicProperty( Map<String, Object> node, String key, Object value ) {
    node.put( key, value );
  }

  @Override
  public void setErrorHandler( Function<Exception, IllegalStateException> errorHandler ) {
    handler = errorHandler;
    if( handler == null ) {
      handler = $ -> new IllegalStateException($);
    }
  }

} /* ENDCLASS */
