package com.chatlogs.core

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

/**
 *
 * User: fqtrnt [2011/11/30]
 * Version: 1.0.0
 */

class InterlocutorTest extends Spec with ShouldMatchers {
  describe("Interlocutor of session") {
    val interlocutor = new Interlocutor
    interlocutor.spokesmen += "AAAA" += "BBBB" += "CCCC"
    it("AAAA should be a spokesman, only BBBB and CCCC should be listeners") {
      val listeners = interlocutor.listeners("AAAA")
      listeners should have size 2
      listeners should (contain("BBBB") and contain("CCCC") and not contain ("AAAA"))
    }
  }
}