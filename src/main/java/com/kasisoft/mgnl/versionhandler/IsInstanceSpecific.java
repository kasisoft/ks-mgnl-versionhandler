package com.kasisoft.mgnl.versionhandler;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface IsInstanceSpecific {

  /**
   * Returns <code>null</code> if this provider shall be used on author and public. <code>true</code> means
   * that it's only supposed to be used on the author instance. <code>false</code> means that it's only
   * supposed to be used on the public instance.
   * 
   * @return   <code>null</code>  <=> Install on both instances.
   *           <code>true</code>  <=> Install on author instance only.
   *           <code>false</code> <=> Install on public instance only.
   */
  default Boolean isAuthorOnly() {
    return null;
  }
  
} /* ENDINTERFACE */
