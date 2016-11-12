package com.kasisoft.mgnl.versionhandler;

import java.util.function.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public abstract class AbstractProducerTest<T> {

  protected abstract Producer<T> newProducer();
  
  protected Map<String, Object> newMap( Object ... params ) {
    Map<String, Object> result = new HashMap<>();
    for( int i = 0; i < params.length; i += 2 ) {
      result.put( (String) params[i], params[ i + 1 ] );
    }
    return result;
  }
  
  protected T simpleTree() {
    TreeBuilder<?> tb = new TreeBuilder<>()
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T substitutionTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .substitution( "hello", "world" )
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "${hello}" )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T complexTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sFolder( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sNode( "oopsi" )
            .property( "word", "list" )
            .property( "word", "boo" )
            .property( "second", "third" )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T complexSimplifiedTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sFolder( "root/base/wombat/what" )
        .sFolder( "simple" )
          .property( "name", "dodo" )
        .sEnd()
        .sNode( "oopsi" )
          .property( "word", "list" )
          .property( "word", "boo" )
          .property( "second", "third" )
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  private String simpleSupplier() {
    return "simpleSupplier";
  }
  
  protected T supplierTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sNode( "oopsi" )
            .property( "word", "list" )
            .property( "word", "boo" )
            .property( "second", (Supplier) this::simpleSupplier )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T mapValueTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sNode( "oopsi" )
            .property( "word", "list" )
            .property( "second", newMap( "a", "b" ) )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T yamlTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sNode( "oopsi" )
            .property( "word", "list" )
            .property( "templateScript", "overridden" )
            .yaml( "example.yaml" )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

  protected T jsonTree() {
    TreeBuilder<?> tb = new TreeBuilder()
      .sNode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sNode( "oopsi" )
            .property( "word", "list" )
            .property( "templateScript", "overridden" )
            .json( "example.json" )
          .sEnd()
        .sEnd()
      ;
    return tb.build( newProducer() );
  }

} /* ENDCLASS */
