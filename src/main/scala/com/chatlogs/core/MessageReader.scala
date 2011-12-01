package com.chatlogs.core


/**
 *
 * User: fqtrnt [2011/11/23]
 * Version: 1.0.0
 */

trait MessageReader {
  def sessions(): List[Session]
}