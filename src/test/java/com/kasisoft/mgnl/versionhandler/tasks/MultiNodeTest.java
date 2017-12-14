package com.kasisoft.mgnl.versionhandler.tasks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import info.magnolia.jcr.util.*;

import com.kasisoft.mgnl.versionhandler.*;

import org.testng.annotations.*;

import javax.annotation.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MultiNodeTest extends AbstractTreeBuilderProvider {

  @Test
  public void basic() {
    
    TreeBuilderProvider provider = new TreeBuilderProvider() {

      @Override
      @Nonnull
      public String getDescription() {
        return "desc";
      }

      @Override
      @Nonnull
      public TreeBuilder create() {
        return new TreeBuilder()
          .sNode( "first/second{mgnl:alpha}/third{mgnl:beta}/fourth", NodeTypes.Content.NAME )
            .property( "a", "b" )
          .sEnd()
          ;
      }
      
    };
    
    String expected = ""
      + "(new) /first[mgnl:content]\n"
      + "(new) /first/second[mgnl:alpha]\n"
      + "(new) /first/second/third[mgnl:beta]\n"
      + "(new) /first/second/third/fourth[mgnl:content]\n"
      + "@a = 'b'\n"
      ;
    
    String desc = buildDescription( provider );
    assertThat( desc, is( expected ) );
    
  }
} /* ENDCLASS */
