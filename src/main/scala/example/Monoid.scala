package example

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]) =
    monoid
}

object BooleanMonoidInstances {
  implicit val orMonoid = new Monoid[Boolean] {
    override def empty                           = false
    override def combine(x: Boolean, y: Boolean) = x || y
  }

  implicit val andMonoid = new Monoid[Boolean] {
    override def empty                           = true
    override def combine(x: Boolean, y: Boolean) = x && y
  }
}

object SetMonoid {
  implicit def unionMonoid[A] = new Monoid[Set[A]] {
    override def empty                         = Set.empty[A]
    override def combine(x: Set[A], y: Set[A]) = x union y
  }
}

object ListMonoid {
  implicit def listMonoid[A] = new Monoid[List[A]] {
    override def empty                           = List.empty[A]
    override def combine(x: List[A], y: List[A]) = x ++ y
  }
}
object SuperAdderApp extends App {
  val firstList  = List(1, 2, 3)
  val secondList = List(4, 5, 6)

  val listMonoid   = ListMonoid.listMonoid[Int]
  val combinedList = listMonoid.combine(firstList, secondList)
  println(s"Combined list is $combinedList!")
}
