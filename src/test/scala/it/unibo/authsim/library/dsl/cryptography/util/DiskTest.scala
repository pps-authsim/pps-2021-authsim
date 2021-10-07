package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.DiskManager
import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class DiskTest extends AnyFlatSpec with GivenWhenThen with Matchers  with BeforeAndAfter{
  val fileName:String= "diskTest1.txt"

  before {
    if(DiskManager.isExisting(fileName)) then
      DiskManager.deleteFile(fileName)
  }

  "A Disk manager" should "support the the following tasks" in {
    Given("an object")
    val testString:String= "foo"

    When("it is asked to check for the file existance")
    DiskManager.isExisting(fileName) shouldBe false

    Then("it is asked to write the object in the file")
    DiskManager.writeObject(testString, fileName)

    And("now the file shuold exist")
    DiskManager.isExisting(fileName) shouldBe true

    Then("it is asked to read it")
    DiskManager.readObject(fileName).get shouldBe testString

    Then("it is asked to delete it")
    DiskManager.deleteFile(fileName)

    And("now the file shuold not exist anymore")
    DiskManager.isExisting(fileName) shouldBe false

    info("These are the disk manager basic operations!")
  }
}