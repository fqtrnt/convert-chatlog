/**
 *
 */
package com.chatlogs.core

/**
 * @author fqtrnt [2012/01/19]
 * @version 1.0.0
 *
 */
trait MessageWriter {
  def write(sessions: List[Session], folder: String, target: String)
}