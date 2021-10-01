package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.DiskManager
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class DiskTest  extends AnyWordSpec with Matchers with BeforeAndAfter {
  val fileName2:String= "diskTest2.txt"
  val fileName:String= "diskTest1.txt"
  val testString:String= "foo"

  before {
    if(File(fileName).exists) then
      println("keyfile already exist")
      FileUtils.forceDelete(new File(fileName))
    DiskManager.saveObject(testString, fileName2)
  }

  "A Disk manager" should {
    "be able to control if a file exists" in {
      DiskManager.isExisting(fileName) shouldBe false
    }
    "be able to save an object in a file creating it if not exists" in {
      DiskManager.saveObject(testString, fileName)
      DiskManager.isExisting(fileName) shouldBe true
    }
    "and it be able to read it" in {
      DiskManager.loadObject(fileName2).get shouldBe testString
    }
  }
}
