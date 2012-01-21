package com.chatlogs.core

import java.util.{Calendar, Date}
import java.text.SimpleDateFormat

/**
 * Trait of the Chat message.<br>
 * It defined the base attribute and behavior on message access.
 * User: fqtrnt [2011/11/23]
 * Version: 1.0.0
 */

trait Message {
  val datetime: MessageTimestamp
  val from: String
  val text: String
  val color: Option[String]
  val messageType: MessageType

}

/**
 *  This utility converts message time of String to {@link MessageTimestamp}.
 *  The valid formats is:
 *  1. 01 Nov 2011 18:00:00
 *  2. 2011-11-01'T'18:00:00.123
 *  3. 1320118776
 *  4. 1320118776000
 */
object MessageTimestamp {
  val dataPattern1 = """\d{2} \w{3} \d{4} \d{2}:\d{2}:\d{2}""".r
  val datePattern2 = """(\d{4})-(\d{2})-(\d{2})['T]*(\d{2}):(\d{2}):(\d{2}).(\d{3})[Z]*""".r
  val datePattern3 = """(\d{10})""".r
  val datePattern4 = """(\d{13})""".r
  val format1 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss")
  val format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
  implicit def messageTimestamp2Object(time: String) = new MessageTimestamp(time)
}

import  MessageTimestamp._
class MessageTimestamp(timeString: String) extends Ordered[MessageTimestamp] {
  (if (null == timeString) "" else timeString.trim) match {
    case datePattern3(x) => time = new Date(x.toLong * 1000)
    case datePattern4(x) => time = new Date(x.toLong)
    case dataPattern1() => {
      try {
        time = format1.parse(timeString)
      } catch {
        case _: Exception => time = new Date()
      }
    }
    case datePattern2(year, month, day, hour, minute, second, milli) => {
      val cal = Calendar.getInstance()
      cal.set(year.toInt, month.toInt - 1, day.toInt, hour.toInt, minute.toInt, second.toInt)
      cal.set(Calendar.MILLISECOND, milli.toInt)
      time = cal.getTime
    }
    case _ => time = new Date()
  }

  var time: Date = _

  override def toString = format2.format(time)
  def toString(format: String) = new SimpleDateFormat(format).format(time)
  def compare(that: MessageTimestamp) = if (null == that) 1 else this.time.compareTo(that.time)
}