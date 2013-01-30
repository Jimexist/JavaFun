package project.euler


object ProblemOneTillFixty extends App {

  /**
   * Problem 1
   */
  lazy val prob1 = (1 until 100).filter(x => x % 3 == 0 || x % 5 == 0).sum

  /**
   * Problem 2
   */
  lazy val fibs: Stream[Int] = 1 #:: 1 #:: (fibs zip fibs.tail).map(x => x._1 + x._2)
  lazy val prob2 = fibs takeWhile (_ < 4000000) filter (_ % 2 == 0) sum

  /**
   * Problem 3
   */
  lazy val prob3 = ???

  /**
   * Problem 6
   */
  lazy val prob6 = {
    val s = 1 until 101
    val sum = s.fold(0)(_ + _)
    sum * sum - s.map(x => x * x).sum
  }

  println(prob6)

  /**
   * Problem 10
   * @param n
   * @return
   */
  def ints(n: Int): Stream[Int] = n #:: ints(n + 1)

  def primes(nums: Stream[Int]): Stream[Int] = nums.head #:: primes(nums.tail filter (_ % nums.head != 0))

  lazy val primeNumbers = primes(ints(2))

  println((primeNumbers takeWhile (_ < 10000)).reduce(_ + _))

}
