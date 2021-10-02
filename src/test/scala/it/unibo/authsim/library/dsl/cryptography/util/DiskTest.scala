package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.DiskManager
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class DiskTest  extends AnyWordSpec with Matchers with BeforeAndAfter {
  val fileName:String= "diskTest1.txt"
  val testString:String= "foo"

  before {
    if(DiskManager.isExisting(fileName)) then
      DiskManager.delete(fileName)
  }

  "A Disk manager" should {
    "be able to control if a file exists" in {
      DiskManager.isExisting(fileName) shouldBe false
    }
    "be able to save an object in a file creating it if not exists" in {
      DiskManager.saveObject(testString, fileName)
      DiskManager.isExisting(fileName) shouldBe true
    }
    "it should be able to read it" in {
      DiskManager.saveObject(testString, fileName)
      DiskManager.loadObject(fileName).get shouldBe testString
    }
    "and it should be able to delete it" in {
      DiskManager.saveObject(testString, fileName)
      DiskManager.isExisting(fileName) shouldBe true
      DiskManager.delete(fileName)
      DiskManager.isExisting(fileName) shouldBe false
    }
  }
}
