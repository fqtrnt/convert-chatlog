/**
 *
 */
package com.chatlogs.msn
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

import com.chatlogs.core.MessageReader
import com.chatlogs.core.Session

/**
 * @author : fqtrnt [2012/01/16]
 * @version: 1.0.0
 */
class MSNMessageReaderTest extends Spec with ShouldMatchers {

  describe("MSN Log") {
    val filename = "test2517631239.xml"
    val folder = "./src/test/resources/msn"
    val messageReader: MessageReader = new MSNMessageReader
    val sessions: List[Session] = messageReader.read(folder, filename)
    it("should be have one session and two messages in the session") {
      sessions should not be null
      sessions should not be 'empty
      sessions should have length 1
      sessions(0).messages should have length 2
    }
  }

}