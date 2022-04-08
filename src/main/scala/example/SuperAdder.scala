package example

import cats.Monoid
import cats.instances.int._    // for Monoid
import cats.syntax.semigroup._ // for |+|

trait SuperAdder {
  def add[A](items: List[A])(implicit m: Monoid[A]): A
}

object SuperAdder {
  
}
