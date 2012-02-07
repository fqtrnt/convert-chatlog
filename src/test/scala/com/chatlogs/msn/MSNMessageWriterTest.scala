/**
 * 
 */
package com.chatlogs.msn

import scala.runtime.Nothing$
import scala.collection.Traversable
import org.scalatest.verb.BehaveWord
import scala.reflect.Manifest
import scala.collection.immutable.List
import java.util.Collection
import java.lang.reflect.Method
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import com.chatlogs.core.MessageReader
import com.chatlogs.core.Session
import com.chatlogs.core.MessageWriter
import com.chatlogs.amsn.AMSNMessageReader
import java.io.File

/**
 * @author fqtrnt [2012/01/18]
 * @version 1.0.0
 *
 */
class MSNMessageWriterTest extends Spec with ShouldMatchers {

  describe("Write the MSN Message") {
    val sessionIdentifier = "aaa632@hotmail.com.log"
    val folder = "./src/test/resources/amsn/"
    val messageReader: MessageReader = new AMSNMessageReader
    val sessions: List[Session] = messageReader.read(folder, sessionIdentifier)
    it("should be correct that to write aMSN message to MSN message file") {
      val msnMessageFile = "aaa632.xml"
      val msnFolder = "./src/test/resources/msn/"
      val file: File = new File(msnFolder, msnMessageFile)
      if (file.exists()) file.delete()
      val writer: MessageWriter = new MSNMessageWriter
      writer.write(sessions, msnFolder, msnMessageFile)
//      val msnReader: MessageReader = new MSNMessageReader
//      val msnSessions: List[Session] = msnReader.read(msnFolder, msnMessageFile)
//      msnSessions should not be null
//      msnSessions should not be 'empty
//      msnSessions should be === sessions
    }
  }
}