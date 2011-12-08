package com.chatlogs.core

/**
 * The trait of reader of message.<br>
 * Read the message log files and convert them to {@link Session}
 * User: fqtrnt [2011/11/23]
 * Version: 1.0.0
 */

trait MessageReader {
  def sessions(): List[Session]
}