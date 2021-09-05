import org.scalatest._
import flatspec._
import matchers._

class CounterSpec extends AnyFlatSpec with should.Matchers {

  "A counter" should "be initialized with count set to 0" in {
    val counter = Counter()

    counter.count should be (0)
  }

  it should "be incremented by 1 when increment() is called" in {
    val counter = Counter()

    counter.increment()

    counter.count should be (1)
  }

}


