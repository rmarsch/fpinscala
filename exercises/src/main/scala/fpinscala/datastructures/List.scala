package fpinscala.datastructures

sealed trait List[+A] // `List` data type, parameterized on a type, `A`
case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
case class Cons[+A](head: A, tail: List[A]) extends List[A] // Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`, which may be `Nil` or another `Cons`.

object List { // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
    case Nil         => 0 // The sum of the empty list is 0.
    case Cons(x, xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil          => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs)  => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  val x = List(1, 2, 3, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _)))          => x
    case Nil                                   => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t)                            => h + sum(t)
    case _                                     => 101
  }

  def append[A](a1: List[A], a2: List[A]): List[A] = reverse(foldLeft(a2, reverse(a1))((a1, a) => Cons(a, a1)))

  def concat[A](as: List[List[A]]): List[A] = foldLeft(as, Nil: List[A])(append)

  def foldRight[A, B](as: List[A], z: B)(f: (B, A) => B): B = // Utility functions
    foldLeft(reverse(as), z)(f)

  def sum2(ns: List[Int]) =
    foldLeft(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) =
    foldLeft(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar

  def tail[A](l: List[A]): List[A] = l match {
    case Cons(_, t) => t
    case Nil        => Nil
  }

  def setHead[A](l: List[A], h: A): List[A] = Cons(h, tail(l))

  @annotation.tailrec
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil                           => Nil
    case result @ Cons(h, t) if n == 0 => result
    case Cons(_, t)                    => drop(t, n - 1)
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case result @ Cons(h, t) => {
      if (f(h)) dropWhile(t, f) else result
    }
  }

  def init[A](l: List[A]): List[A] = l match {
    case Nil          => Nil
    case Cons(h, Nil) => Nil
    case Cons(h, t)   => Cons(h, init(t))
  }

  def length[A](l: List[A]): Int = foldLeft(l, 0)((acc, _) => 1 + acc)

  @annotation.tailrec
  def foldLeft[A, B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil        => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f)
  }

  def reverse[A](l: List[A]): List[A] = foldLeft(l, Nil: List[A])((acc, e) => Cons(e, acc))

  def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])((acc, e) => Cons(e + 1, acc))

  def doubleToStr(l: List[Double]): List[String] = foldRight(l, Nil: List[String])((acc, e) => Cons(e.toString, acc))

  def map[A, B](l: List[A])(f: A => B): List[B] = foldRight(l, Nil: List[B])((acc, e) => Cons(f(e), acc))

  def filter[A](as: List[A])(f: A => Boolean): List[A] = foldRight(as, Nil: List[A])((acc, e) => if (f(e)) Cons(e, acc) else acc)

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = foldLeft(as, Nil: List[B])((acc, e) => append(acc, f(e)))

  def filter2[A](as: List[A])(f: A => Boolean): List[A] = flatMap(as)(e => if (f(e)) Cons(e, Nil) else Nil)

  def addList(as: List[Int], bs: List[Int]): List[Int] = (as, bs) match {
    case (Nil, Nil)                 => Nil
    case (Nil, bs)                  => bs
    case (as, Nil)                  => as
    case (Cons(a, at), Cons(b, bt)) => Cons(a + b, addList(at, bt))
  }
  
  def zipWith[A](as: List[A], bs: List[A])(f: (A,A) => A): List[A] = (as,bs) match {
    case (Nil, Nil)                 => Nil
    case (Nil, bs)                  => bs
    case (as, Nil)                  => as
    case (Cons(a, at), Cons(b, bt)) => Cons(f(a,b), zipWith(at, bt)(f))
  }
}