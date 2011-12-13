package com.chatlogs.amsn

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
    val sessionIdentifier = "aaa632@hotmail.com.log"
    val folder = "./src/test/resources/amsn/"
    val messageReader: MessageReader = new AMSNMessageReader(folder, sessionIdentifier)
    val sessions: List[Session] = messageReader.sessions()
    it("should only have 3 sessions in folder[" + folder  + "] with file[" + sessionIdentifier + "] and spokesmans are allright") {
      sessions should not be null
      sessions should not be 'empty
      sessions should have length 3
      sessions(0).interlocutor.spokesmen should not be 'empty
      sessions(0).interlocutor.spokesmen should have size 2
      sessions(0).identifier should be(sessionIdentifier)
      sessions(0).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632"))
      sessions(1).interlocutor.spokesmen should not be 'empty
      sessions(1).interlocutor.spokesmen should have size 3
      sessions(1).identifier should be(sessionIdentifier)
      sessions(1).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632") and contain("ccc632"))
      sessions(2).interlocutor.spokesmen should not be 'empty
      sessions(2).interlocutor.spokesmen should have size 2
      sessions(2).identifier should be(sessionIdentifier)
      sessions(2).interlocutor.spokesmen should(contain("aaa632") and contain("bbb632"))
    }

    val messages = sessions flatMap(_.messages)
    it("should get 40 messages for [" + sessionIdentifier + "]") {
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