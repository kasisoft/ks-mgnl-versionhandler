package com.kasisoft.mgnl.versionhandler;

import static com.kasisoft.mgnl.versionhandler.internal.Messages.*;

import org.slf4j.*;

import javax.annotation.*;
import javax.jcr.*;
import javax.jcr.query.*;

import java.util.function.*;

import java.util.stream.*;

import java.util.*;

import info.magnolia.context.*;
import info.magnolia.jcr.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@SuppressWarnings("deprecation")
public enum QueryFunctions {
    
  jqom  ( Query.JCR_JQOM ),
  sql   ( Query.SQL      ), 
  sql2  ( Query.JCR_SQL2 ), 
  xpath ( Query.XPATH    );

  private static final Logger log = LoggerFactory.getLogger( QueryFunctions.class );

  private String   language;

  QueryFunctions( String lang ) {
    language = lang;
  }

  public String getLanguage() {
    return language;
  }

  public static void loggingErrorHandler( RepositoryException ex ) {
    log.error( ex.getLocalizedMessage(), ex );
  }

  public static void runtimeExErrorHandler( RepositoryException ex ) {
    throw new RuntimeRepositoryException( ex );
  }

  @Nullable
  public Query createQuery( @Nonnull String workspace, @Nonnull String fmt, Object ... args ) {
    return createQuery( workspace, fmt, null, args );
  }
  
  @Nullable
  public Query createQuery( @Nonnull String workspace, @Nonnull String fmt, @Nullable Consumer<RepositoryException> handler, Object ... args ) {
    if( handler == null ) {
      handler = QueryFunctions::loggingErrorHandler;
    }
    if( (args != null) && (args.length > 0) ) {
      fmt = String.format( fmt, args );
    }
    log.trace( executing_query.format( name(), fmt ) );
    try {
      Session      session = MgnlContext.getJCRSession( workspace );
      QueryManager qm      = session.getWorkspace().getQueryManager();
      return qm.createQuery( fmt, language );
    } catch( RepositoryException ex ) {
      handler.accept( ex );
      return null;
    }
  }
  
  @Nullable
  public Node find( @Nonnull String workspace, @Nonnull String fmt, Object... args ) {
    return find( workspace, fmt, null, args );
  }
  
  @Nullable
  public Node find( @Nonnull String workspace, @Nonnull String fmt, @Nullable Consumer<RepositoryException> handler, Object... args ) {
    Node result = null;
    try {
      List<Node> nodes = list( workspace, fmt, handler, args );
      if( (nodes != null) && (nodes.size() > 0) ) {
        result = nodes.get(0);
      }
    } catch( RuntimeRepositoryException ex ) {
      throw ex;
    } catch( Exception ex ) {
      log.error( ex.getLocalizedMessage(), ex );
    }
    return result;
  }
  
  @Nonnull
  public List<Node> list( @Nonnull String workspace, @Nonnull String fmt, @Nullable Consumer<RepositoryException> handler, Object... args ) {
    try {
      return loadRows( createQuery( workspace, fmt, handler, args ).execute(), handler );
    } catch( RuntimeRepositoryException ex ) {
      throw ex;
    } catch( Exception ex ) {
      log.error( ex.getLocalizedMessage(), ex );
      return Collections.emptyList();
    }
  }

  private List<Node> loadRows( QueryResult queryResult, Consumer<RepositoryException> handler ) {
    List<Node> result = null;
    try {
      if( queryResult != null ) {
        result = new ArrayList<>();
        RowIterator iterator = queryResult.getRows();
        while( iterator.hasNext() ) {
          result.add( iterator.nextRow().getNode() );
        }
      } else {
        result = Collections.emptyList();
      }
    } catch( RepositoryException ex ) {
      handler.accept( ex );
    }
    return result;
  }

  @Nonnull
  public <R> List<R> list( @Nonnull String workspace, Function<Node, R> transform, @Nonnull String fmt, @Nullable Consumer<RepositoryException> handler, Object... args ) {
    try {
      return list( workspace, fmt, handler, args ).stream()
              .map( transform )
              .collect( Collectors.toList() );
    } catch( RuntimeRepositoryException ex ) {
      throw ex;
    } catch( Exception ex ) {
      log.error( ex.getLocalizedMessage(), ex );
      return Collections.emptyList();
    }
  }
  
} /* ENDCLASS */
