package com.kasisoft.mgnl.versionhandler;

import javax.annotation.*;

import java.util.function.*;

/**
 * Each producer is supposed to transform the hierarchical structure into a desired return type. 
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface Producer<R> {
  
  /**
   * Allows to setup an error handler just in case the code under question uses checked exceptions.
   * 
   * @param handler   The new handler. 
   */
  void setErrorHandler( @Nullable Function<Exception, IllegalStateException> handler );
  
  /**
   * Returns the root node which serves as an entry point for the construction of the tree.
   * 
   * @return   The root node which serves as an entry point for the construction of the tree.
   */
  @Nonnull
  R getRootNode();
  
  /**
   * Returns the child node associated with a parental node. If the child doesn't exist it's supposed to be
   * created.
   * 
   * @param path       The current path of the parental element (useful if not available within <code>parent</code>).
   * @param parent     The parental node.
   * @param name       The name of the child node.
   * @param nodeType   The desired nodetype.
   * @param fail       <code>true</code> <=> Cause an error if the nodetype doesn't match.
   * 
   * @return   The child node.
   */
  @Nonnull
  R getChild( @Nonnull String path, @Nonnull R parent, @Nonnull String name, @Nonnull String nodeType, boolean fail );
  
  /**
   * Applies a simple property to the supplied node.
   * 
   * @param node    The node which will be altered.
   * @param key     The name of the property.
   * @param value   The value of the property.
   */
  void setBasicProperty( @Nonnull R node, @Nonnull String key, @Nullable Object value );

} /* ENDINTERFACE */

