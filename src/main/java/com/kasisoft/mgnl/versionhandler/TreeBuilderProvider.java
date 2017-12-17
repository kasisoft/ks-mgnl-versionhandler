package com.kasisoft.mgnl.versionhandler;

import info.magnolia.repository.*;

import info.magnolia.module.delta.*;

import javax.annotation.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface TreeBuilderProvider extends IsInstanceSpecific {

  /**
   * Returns the title to be used while generating the tree structure.
   * 
   * @return   The title to be used while generating the tree structure.
   */
  @Nonnull
  default String getTitle() {
    return getClass().getSimpleName();
  }
  
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

  /**
   * Returns the workspace name the TreeBuilder from {@link #create()} should operate on.
   * 
   * @return   The workspace name. Default is {@link RepositoryConstants#CONFIG}.
   */
  @Nonnull
  default String getWorkspace() {
    return RepositoryConstants.CONFIG;
  }
  
  /**
   * Returns a list of tasks that will be executed after the jcr structure had been configured (mostly for
   * ordering purposes).
   * 
   * @return   A list of tasks that will be executed after the jcr structure had been configured.
   */
  @Nonnull
  default List<Task> postExecute() {
    return Collections.emptyList();
  }
  
} /* ENDINTERFACE */
