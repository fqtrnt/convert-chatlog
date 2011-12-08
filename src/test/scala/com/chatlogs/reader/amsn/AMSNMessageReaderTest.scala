package com.chatlogs.reader.amsn

import org.scalatest.Spec
import com.chatlogs.core.MessageReader
import org.scalatest.matchers.ShouldMatchers
import com.chatlogs.core.Session

/**
 *
 * User: fqtrnt [2011/11/23]
 * Version: 1.0.0
 */

class AMSNMessageReaderTest extends Spec with ShouldMatchers {

  describe("aMSN log") {
    val fileName = "./src/test/resources/amsn/aaa632@hotmail.com.log"
    it("should only have 3 sessions in [" + fileName + "] and spokesmans are allright") {
      val messageReader: MessageReader = new AMSNMessageReader(fileName)
      val sessions: List[Session] = messageReader.sessions()
      sessions should not be null
      sessions should not be 'empty
      sessions should have length 3
      sessions(0).interlocutor.spokesmen should not be 'empty
      sessions(0).interlocutor.spokesmen should have size 2
      sessions(0).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632"))
      sessions(1).interlocutor.spokesmen should not be 'empty
      sessions(1).interlocutor.spokesmen should have size 3
      sessions(1).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632") and contain("ccc632"))
      sessions(2).interlocutor.spokesmen should not be 'empty
      sessions(2).interlocutor.spokesmen should have size 2
      sessions(2).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632"))
    }

    val reader = new AMSNMessageReader(fileName)
    val messages = reader.loadMessagesFrom(List(fileName))
    it("should get 40 messages from [" + fileName + "]") {
      messages should not be null
      messages should not be 'empty
      messages should have size 40
    }

    it("should 38th message have two blank lines ") {
      val pattern = """\S*\n(\n)(\n)\S*""".r
      val pattern(l1, l2) = (messages(38).text)
      assert(Some("\n", "\n") == Some(l1, l2))
    }
  }
}