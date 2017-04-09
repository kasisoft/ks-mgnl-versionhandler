package com.kasisoft.mgnl.versionhandler;

import javax.annotation.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface TreeBuilderProvider {

  /**
   * Returns the title to be used while generating the tree structure.
   * 
   * @return   The title to be used while generating the tree structure.
   */
  @Nonnull
  String getTitle();
  
  /**
   * Returns the description to be used while generating the tree structure.
   * 
   * @return   The description to be used while generating the tree structure.
   */
  @Nonnull
  String getDescription();
  
  /**
   * Returns the builder to generate the tree structure. Each implementor is supposed to raise an exception in
   * case of an issue (f.e. misconfiguration).
   * 
   * @return   The builder to generate the tree structure.
   */
  @Nonnull
  TreeBuilder create();
  
} /* ENDINTERFACE */
