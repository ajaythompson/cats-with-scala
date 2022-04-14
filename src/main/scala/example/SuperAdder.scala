package example

import cats.Monoid
import cats.instances.int._ // for Monoid
import cats.instances.option._
import cats.syntax.semigroup._ // for |+|

trait SuperAdder {
  def add[A: Monoid](items: List[A]): A
}

object SuperAdderInstances {
  implicit val superAdder: SuperAdder = new SuperAdder {
    override def add[A: Monoid](items: List[A]): A =
      items.foldLeft(Monoid.empty[A])(_ |+| _)
  }
}

object SuperAdderSyntax {
  implicit class SuperAdderOps[A: Monoid](items: List[A]) {
    def add(implicit superAdder: SuperAdder): A = {
      superAdder.add(items)
    }
  }
}

object AdderApp extends App {
  import SuperAdderSyntax._
  import SuperAdderInstances._

  val result = List[Option[Int]](Some(1)).add
  println(s"Super adder result is $result")
}
