package example

import cats.Monoid
import cats.instances.string._ // for Monoid
import cats.instances.int._
import cats.instances.option._
import cats.syntax.semigroup._

object CatsMonoid extends App {
  val combinedString = "Hello" |+| "World"
  println(s"Combined String: $combinedString!")

  val combinedInt = Monoid[Int].combine(1, 2)
  println(s"Combined Int: $combinedInt!")

  val combinedOption = Monoid[Option[Int]].combine(Option(1), Option(2))
  println(s"Combined Option: $combinedOption!")
}
