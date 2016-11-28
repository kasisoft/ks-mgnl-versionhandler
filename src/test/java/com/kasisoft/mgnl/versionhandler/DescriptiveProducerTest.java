package com.kasisoft.mgnl.versionhandler;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import com.kasisoft.libs.common.text.*;

import org.testng.annotations.*;

import info.magnolia.test.mock.jcr.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class DescriptiveProducerTest extends AbstractProducerTest<StringFBuilder> {

  @Override
  protected Producer<StringFBuilder> newProducer() {
    return new DescriptiveProducer();
  }
  
  protected MockNode assertNode( MockNode parent, String childName, String type ) throws Exception {
    assertTrue( parent.hasNode( childName ) );
    MockNode result = (MockNode) parent.getNode( childName );
    assertThat( result.getPrimaryNodeType().getName(), is( type ) );
    return result;
  }
  
  @Test
  public void simple() throws Exception {
    
    StringFBuilder node = simpleTree();
    assertNotNull( node );

    String expected = ""
        + "(new) /root\n"
        + "(new) /root/base\n"
        + "(new) /root/base/simple\n"
        + "@name = 'dodo'\n";

    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void substitution() throws Exception {
    
    StringFBuilder node = substitutionTree();
    assertNotNull( node );

    String expected = ""
        + "(new) /root\n"
        + "(new) /root/base\n"
        + "(new) /root/base/simple\n"
        + "@name = 'world'\n";
    
    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void complex() throws Exception {
    
    StringFBuilder node = complexTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/oopsi\n"
      + "@word = 'boo'\n"
      + "@second = 'third'\n";

    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void complexSimplified() throws Exception {
    
    StringFBuilder node = complexSimplifiedTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/wombat\n"
      + "(new) /root/base/wombat/what\n"
      + "(new) /root/base/wombat/what/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/wombat/what/oopsi\n"
      + "@word = 'boo'\n"
      + "@second = 'third'\n";
    
    assertThat( node.toString(), is( expected ) );

  }

  @Test
  public void supplier() throws Exception {
    
    StringFBuilder node = supplierTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/oopsi\n"
      + "@word = 'boo'\n"
      + "@second = 'simpleSupplier'\n";

    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void mapValue() throws Exception {
    
    StringFBuilder node = mapValueTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/oopsi\n"
      + "@word = 'list'\n"
      + "(new) /root/base/oopsi/second\n"
      + "@a = 'b'\n";
      
    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void yaml() throws Exception {
    
    StringFBuilder node = yamlTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/oopsi\n"
      + "@templateScript = 'overridden'\n"
      + "(new) /root/base/oopsi/areas\n"
      + "(new) /root/base/oopsi/content-sections\n"
      + "@renderType = 'freemarker'\n"
      + "(new) /root/base/oopsi/availableComponents\n"
      + "(new) /root/base/oopsi/content-items-list\n"
      + "@id = 'items1'\n"
      + "(new) /root/base/oopsi/textImage\n"
      + "@id = 'items2'\n"
      + "@renderType = 'freemarker'\n"
      + "@title = 'title'\n"
      + "@word = 'list'\n";
        
    assertThat( node.toString(), is( expected ) );
    
  }

  @Test
  public void json() throws Exception {
    
    StringFBuilder node = jsonTree();
    assertNotNull( node );
    
    String expected = ""
      + "(new) /root\n"
      + "(new) /root/base\n"
      + "(new) /root/base/simple\n"
      + "@name = 'dodo'\n"
      + "(new) /root/base/oopsi\n"
      + "@templateScript = 'overridden'\n"
      + "(new) /root/base/oopsi/areas\n"
      + "(new) /root/base/oopsi/content-sections\n"
      + "@renderType = 'freemarker'\n"
      + "(new) /root/base/oopsi/availableComponents\n"
      + "(new) /root/base/oopsi/content-items-list\n"
      + "@id = 'items1'\n"
      + "(new) /root/base/oopsi/textImage\n"
      + "@id = 'items2'\n"
      + "@renderType = 'freemarker'\n"
      + "@title = 'title'\n"
      + "@word = 'list'\n";

    assertThat( node.toString(), is( expected ) );
    
  }

} /* ENDCLASS */
