package com.kasisoft.mgnl.versionhandler;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import org.testng.annotations.*;

import java.util.function.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ProducerTest {

  private Map<String, Object> newMap( Object ... params ) {
    Map<String, Object> result = new HashMap<>();
    for( int i = 0; i < params.length; i += 2 ) {
      result.put( (String) params[i], params[ i + 1 ] );
    }
    return result;
  }
  
  @Test
  public void simple() {
    
    TreeBuilder tb = new TreeBuilder()
      .sSubnode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
        .sEnd()
      ;
    
    Map<String, Object> map = tb.build( new MapProducer() );
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:contentNode",
        "base", newMap(
          "nodetype", "mgnl:content",
          "simple", newMap(
            "nodetype", "mgnl:content",
            "name", "dodo"
          )
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }

  @Test
  public void complex() {
    
    TreeBuilder tb = new TreeBuilder()
      .sSubnode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sSubnode( "oopsi" )
            .property( "word", "list" )
            .property( "word", "boo" )
            .property( "second", "third" )
          .sEnd()
        .sEnd()
      ;
    
    Map<String, Object> map = tb.build( new MapProducer() );
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:contentNode",
        "base", newMap(
          "nodetype", "mgnl:content",
          "simple", newMap(
            "nodetype", "mgnl:content",
            "name", "dodo"
          ),
          "oopsi", newMap(
            "nodetype", "mgnl:contentNode",
            "word", "boo",
            "second", "third"
          )
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }
  
  private String simpleSupplier() {
    return "simpleSupplier";
  }

  @Test
  public void supplier() {
    
    TreeBuilder tb = new TreeBuilder()
      .sSubnode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sSubnode( "oopsi" )
            .property( "word", "list" )
            .property( "word", "boo" )
            .property( "second", (Supplier) this::simpleSupplier )
          .sEnd()
        .sEnd()
      ;
    
    Map<String, Object> map = tb.build( new MapProducer() );
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:contentNode",
        "base", newMap(
          "nodetype", "mgnl:content",
          "simple", newMap(
            "nodetype", "mgnl:content",
            "name", "dodo"
          ),
          "oopsi", newMap(
            "nodetype", "mgnl:contentNode",
            "word", "boo",
            "second", "simpleSupplier"
          )
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }

  @Test
  public void mapValue() {
    
    TreeBuilder tb = new TreeBuilder()
      .sSubnode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sSubnode( "oopsi" )
            .property( "word", "list" )
            .property( "second", newMap( "a", "b" ) )
          .sEnd()
        .sEnd()
      ;
    
    Map<String, Object> map = tb.build( new MapProducer() );
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:contentNode",
        "base", newMap(
          "nodetype", "mgnl:content",
          "simple", newMap(
            "nodetype", "mgnl:content",
            "name", "dodo"
          ),
          "oopsi", newMap(
            "nodetype", "mgnl:contentNode",
            "word", "list",
            "second", newMap(
              "nodetype", "mgnl:contentNode",
              "a", "b"
            )
          )
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }

  @Test
  public void yaml() {
    
    TreeBuilder tb = new TreeBuilder()
      .sSubnode( "root" )
        .sFolder( "base" )
          .sFolder( "simple" )
            .property( "name", "dodo" )
          .sEnd()
          .sSubnode( "oopsi" )
            .property( "word", "list" )
            .property( "templateScript", "overridden" )
            .yaml( "example.yaml" )
          .sEnd()
        .sEnd()
      ;
    
    Map<String, Object> map = tb.build( new MapProducer() );
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:contentNode",
        "base", newMap(
          "nodetype", "mgnl:content",
          "simple", newMap(
            "nodetype", "mgnl:content",
            "name", "dodo"
          ),
          "oopsi", newMap(
            "nodetype", "mgnl:contentNode",
            "word", "list",
            "templateScript", "overridden",
            "renderType", "freemarker",
            "title", "title",
            "areas", newMap(
              "nodetype", "mgnl:contentNode",
              "content-sections", newMap(
                "nodetype", "mgnl:contentNode",
                "renderType", "freemarker",
                "availableComponents", newMap(
                  "nodetype", "mgnl:contentNode",
                  "content-items-list", newMap(
                    "nodetype", "mgnl:contentNode",
                    "id", "items1"
                  ),
                  "textImage", newMap(
                    "nodetype", "mgnl:contentNode",
                    "id", "items2"
                  )
                )
              )
            )
          )
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }

} /* ENDCLASS */
