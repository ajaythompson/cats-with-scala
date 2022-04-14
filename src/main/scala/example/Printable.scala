/*
Exercise: Printable Library
Scala provides a toString method to let us convert any value to a String.

However, this method comes with a few disadvantages:
it is implemented for every type in the language, many implementations are of limited use,
and we can’t opt-in to specific implementations for specific types.

Let’s define a Printable type class to work around these problems:

Define a type class Printable[A] containing a single method format.
format should accept a value of type A and return a String.

Create an object PrintableInstances containing instances of Printable for String and Int.

Define an object Printable with two generic interface methods:
format accepts a value of type A and a Printable of the corresponding type.
It uses the relevant Printable to convert the A to a String.

print accepts the same parameters as format and returns Unit.
It prints the formatted A value to the console using println.
 */

package example

import cats.instances.int._
import cats.instances.string._
import cats.syntax.eq._
import cats.syntax.show._
import cats.{Eq, Show}

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val stringPrintable: Printable[String] = new Printable[String] {
    override def format(value: String): String = value
  }

  implicit val intPrintable: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }
}

object Printable {
  def format[A](value: A)(implicit printable: Printable[A]): String = {
    printable.format(value)
  }

  def print[A](value: A)(implicit printable: Printable[A]): Unit = {
    println(printable.format(value))
  }
}

object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {

    def format(implicit printable: Printable[A]): String = {
      printable.format(value)
    }

    def print(implicit printable: Printable[A]): Unit = {
      println(format(printable))
    }
  }

  implicit val catShow: Show[Cat] = Show.show[Cat] { cat =>
    val name  = cat.name.show
    val age   = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }
}

final case class Cat(name: String, age: Int, color: String)

object PrintableApp extends App {

  implicit val customIntPrintable: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = {
      s" ${value.name} is a ${value.age} year-old ${value.color} cat."
    }
  }

  import PrintableSyntax._

  val smellCat = Cat("Smelly", 1, "green")
  smellCat.print
  println(smellCat.show)

  implicit val catEqual: Eq[Cat] = Eq.instance[Cat] { (cat1, cat2) =>
    cat1.name === cat2.name &&
    cat1.age === cat2.age &&
    cat1.color === cat2.color
  }

  val garfield   = Cat("Garfield", 38, "orange and black")
  val heathcliff = Cat("Heathcliff", 32, "orange and black")

  println(garfield =!= heathcliff)
  println(garfield === heathcliff)
}
