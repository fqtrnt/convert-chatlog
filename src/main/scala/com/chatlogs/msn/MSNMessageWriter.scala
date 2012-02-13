/**
 *
 */
package com.chatlogs.msn

import scala.collection.immutable.List
import scala.xml.XML
import com.chatlogs.core.MessageWriter
import com.chatlogs.core.Session
import scala.io.Codec
import com.chatlogs.core.MessageType
import scala.xml.NodeSeq
import java.nio.channels.Channels
import java.io.FileOutputStream
import scala.util.control.Exception.ultimately
import scala.xml.Utility
import scala.xml.Node
import com.chatlogs.core.Message

/**
 * @author fqtrnt [2012/01/19]
 * @version 1.0.0
 *
 */
class MSNMessageWriter extends MessageWriter {

  def write(sessions: List[Session], folder: String, target: String) = {
    val msnMessages = 
    <Log FirstSessionID="1" LastSessionID={sessions.size.toString}>
      {sessions.map(createXMLFrom)}
    </Log>
	save(folder + target, msnMessages)
  }

  def createXMLFrom(session: Session) = {
    val defatulTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    def textStyle(x: Message) = "font-family:Microsoft YaHei; color:#" + x.color.getOrElse("000000") + "; "
    session.messages map({
      message => message.messageType match {
        case x: Invitation => 
          <Invitation DateTime={x.datetime.toString(defatulTimeFormat)}
                   SessionID={session.sessionId.toString}
                   Date={x.datetime.toString("yyyy/MM/dd")}
                   Time={x.datetime.toString("HH:mm:ss")}>
          <From><User FriendlyName={x.from}/></From>
          <File>{x.file}</File>
          <Text Style={textStyle(x)}>{x.text}</Text>
          </Invitation>
        case x: InvitationResponse => 
          <InvitationResponse DateTime={x.datetime.toString(defatulTimeFormat)}
                   SessionID={session.sessionId.toString}
                   Date={x.datetime.toString("yyyy/MM/dd")}
                   Time={x.datetime.toString("HH:mm:ss")}>
          <From><User FriendlyName={x.from}/></From>
          <File>{x.file}</File>
          <Text Style={textStyle(x)}>{x.text}</Text>
          </InvitationResponse>
        case MessageType.CHAT =>
          <Message DateTime={message.datetime.toString(defatulTimeFormat)}
                   SessionID={session.sessionId.toString}
                   Date={message.datetime.toString("yyyy/MM/dd")}
                   Time={message.datetime.toString("HH:mm:ss")}>
          <From><User FriendlyName={message.from}/></From>
          <To><User FriendlyName={session.interlocutor.listeners(message.from).mkString(",")}/></To>
          <Text Style={textStyle(message)}>{message.text}</Text>
          </Message>
        case _ =>
      }
    })
  }

  def save(filename: String, node: Node): Unit = {
    val fos = new FileOutputStream(filename)
    val w = Channels.newWriter(fos.getChannel(), Codec.UTF8.name)
    ultimately(w.close())(
      write(w, node)
    )
  }
  
  def write(w: java.io.Writer, node: Node): Unit = {
    w.write("<?xml version='1.0' encoding='" + Codec.UTF8.name + "'?>\n")
    w.write("<?xml-stylesheet type='text/xsl' href='MessageLog.xsl'?>\n")
    w.write(Utility.toXML(node).toString)
  }
}