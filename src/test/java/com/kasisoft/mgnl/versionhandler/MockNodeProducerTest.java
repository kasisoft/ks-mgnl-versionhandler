package com.kasisoft.mgnl.versionhandler;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import info.magnolia.repository.*;

import info.magnolia.jcr.util.*;

import org.testng.annotations.*;

import info.magnolia.test.mock.jcr.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class MockNodeProducerTest extends AbstractProducerTest<MockNode> {

  @Override
  protected Producer<MockNode> newProducer() {
    return new MockNodeProducer( new MockSession( RepositoryConstants.CONFIG ) );
  }
  
  protected MockNode assertNode( MockNode parent, String childName, String type ) throws Exception {
    assertTrue( parent.hasNode( childName ) );
    MockNode result = (MockNode) parent.getNode( childName );
    assertThat( result.getPrimaryNodeType().getName(), is( type ) );
    return result;
  }
  
  @Test
  public void simple() throws Exception {
    
    MockNode node = simpleTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.ContentNode.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );

    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
  }

  @Test
  public void complex() throws Exception {
    
    MockNode node = complexTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.Content.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );

    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );

    MockNode oopsiNode = assertNode( baseNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "boo" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "second" ), is( "third" ) );
    
  }

  @Test
  public void complexSimplified() throws Exception {
    
    MockNode node = complexSimplifiedTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.Content.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );
    
    MockNode wombatNode = assertNode( baseNode, "wombat", NodeTypes.Content.NAME );
    
    MockNode whatNode = assertNode( wombatNode, "what", NodeTypes.Content.NAME );
    
    MockNode simpleNode = assertNode( whatNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
    MockNode oopsiNode = assertNode( whatNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "boo" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "second" ), is( "third" ) );
    
  }

  @Test
  public void supplier() throws Exception {
    
    MockNode node = supplierTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.ContentNode.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );
    
    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
    MockNode oopsiNode = assertNode( baseNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "boo" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "second" ), is( "simpleSupplier" ) );
    
  }

  @Test
  public void mapValue() throws Exception {
    
    MockNode node = mapValueTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.ContentNode.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );
    
    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
    MockNode oopsiNode = assertNode( baseNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "list" ) );
    
    MockNode secondNode = assertNode( oopsiNode, "second", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( secondNode, "a" ), is( "b" ) );
    
  }

  @Test
  public void yaml() throws Exception {
    
    MockNode node = yamlTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.ContentNode.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );
    
    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
    MockNode oopsiNode = assertNode( baseNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "list" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "templateScript" ), is( "overridden" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "renderType" ), is( "freemarker" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "title" ), is( "title" ) );
    
    MockNode areasNode = assertNode( oopsiNode, "areas", NodeTypes.ContentNode.NAME );
    
    MockNode contentSectionsNode = assertNode( areasNode, "content-sections", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( contentSectionsNode, "renderType" ), is( "freemarker" ) );
    
    MockNode availableComponentsNode = assertNode( contentSectionsNode, "availableComponents", NodeTypes.ContentNode.NAME );
    
    MockNode contentItemsListNode = assertNode( availableComponentsNode, "content-items-list", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( contentItemsListNode, "id" ), is( "items1" ) );

    MockNode textImageNode = assertNode( availableComponentsNode, "textImage", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( textImageNode, "id" ), is( "items2" ) );
    
  }

  @Test
  public void json() throws Exception {
    
    MockNode node = jsonTree();
    assertNotNull( node );
    
    MockNode rootNode = assertNode( node, "root", NodeTypes.ContentNode.NAME );
    
    MockNode baseNode = assertNode( rootNode, "base", NodeTypes.Content.NAME );
    
    MockNode simpleNode = assertNode( baseNode, "simple", NodeTypes.Content.NAME );
    assertThat( PropertyUtil.getString( simpleNode, "name" ), is( "dodo" ) );
    
    MockNode oopsiNode = assertNode( baseNode, "oopsi", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( oopsiNode, "word" ), is( "list" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "templateScript" ), is( "overridden" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "renderType" ), is( "freemarker" ) );
    assertThat( PropertyUtil.getString( oopsiNode, "title" ), is( "title" ) );
    
    MockNode areasNode = assertNode( oopsiNode, "areas", NodeTypes.ContentNode.NAME );
    
    MockNode contentSectionsNode = assertNode( areasNode, "content-sections", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( contentSectionsNode, "renderType" ), is( "freemarker" ) );
    
    MockNode availableComponentsNode = assertNode( contentSectionsNode, "availableComponents", NodeTypes.ContentNode.NAME );
    
    MockNode contentItemsListNode = assertNode( availableComponentsNode, "content-items-list", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( contentItemsListNode, "id" ), is( "items1" ) );

    MockNode textImageNode = assertNode( availableComponentsNode, "textImage", NodeTypes.ContentNode.NAME );
    assertThat( PropertyUtil.getString( textImageNode, "id" ), is( "items2" ) );
    
  }

} /* ENDCLASS */
