package com.kasisoft.mgnl.versionhandler;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import org.testng.annotations.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class MapProducerTest extends AbstractProducerTest<Map<String, Object>> {

  @Override
  protected Producer<Map<String, Object>> newProducer() {
    return new MapProducer();
  }
  
  @Test
  public void simple() {
    
    Map<String, Object> map = simpleTree();
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
    
    Map<String, Object> map = complexTree();
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:content",
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

  @Test
  public void complexSimplified() {
    
    Map<String, Object> map = complexSimplifiedTree();
    assertNotNull( map );
    
    Map<String, Object> expected = newMap(
      "root", newMap(
        "nodetype", "mgnl:content",
        "base", newMap(
          "nodetype", "mgnl:content",
          "wombat", newMap(
            "nodetype", "mgnl:content",
            "what", newMap(
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
        )
      )
    );
    
    assertThat( map, is( expected ) );
    
  }

  @Test
  public void supplier() {
    
    Map<String, Object> map = supplierTree();
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
    
    Map<String, Object> map = mapValueTree();
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
    
    Map<String, Object> map = yamlTree();
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

  @Test
  public void json() {
    
    Map<String, Object> map = jsonTree();
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
