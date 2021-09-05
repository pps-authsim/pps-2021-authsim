@main def hello: Unit = 
  println("Hello world!")
  val counter = Counter()
  counter.increment()
  println("Count is " + counter.count)

def msg = "I was compiled by Scala 3. :)"
