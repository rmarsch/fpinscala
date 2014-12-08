package fpinscala.laziness

import Stream._
trait Stream[+A] {

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h, t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _          => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.

  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty      => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }

  def toList: List[A] = foldRight(List.empty[A])((e, l) => e :: l)

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) => if (n > 0) Cons(h, () => t().take(n - 1)) else Empty
    case Empty      => Empty
  }

  @annotation.tailrec
  final def drop(n: Int): Stream[A] = this match {
    case c @ Cons(h, t) => if (n > 0) t().drop(n - 1) else c
    case Empty          => Empty
  }

  final def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) => if (p(h())) Cons(h, () => t().takeWhile(p)) else Empty
    case Empty      => Empty
  }

  def takeWhile2(p: A => Boolean): Stream[A] = foldRight(Empty: Stream[A])((a, b) => if (p(a)) Stream.cons(a, b) else Empty)

  def forAll(p: A => Boolean): Boolean = foldRight(true)((a, b) => p(a) && b)

  def headOption: Option[A] = foldRight(None: Option[A])((a, b) => Some(a))

  def map[B](f: A => B): Stream[B] = foldRight(Empty: Stream[B])((a, b) => Stream.cons(f(a), b))

  def filter(p: A => Boolean): Stream[A] = foldRight(Empty: Stream[A])((a, b) => if (p(a)) Stream.cons(a, b) else b)

  def append[A](s: => Stream[A]): Stream[A] = foldRight(Empty: Stream[A]) {
    case (a: A, b: Stream[A]) => b match {
      case Cons(h, t) => Stream.cons(a, b)
      case Empty      => Stream.cons(a, s)
    }
  }

  def flatMap[B](f: A => Stream[B]): Stream[B] = foldRight(Empty: Stream[B])((a, b) => f(a) append b)

  def map3[B](f: A => B): Stream[B] = unfold(this) {
    case Cons(h, t) => Some((f(h()), t()))
    case Empty      => None
  }

  def take2(n: Int): Stream[A] = unfold((this, n))({
    case (Cons(h, t), n) if n > 0 => Some(h(), (t(), n - 1))
    case _                        => None
  })

  def takeWhile3(f: A => Boolean): Stream[A] = unfold(this) {
    case Cons(h, t) => {
      val head = h()
      if (f(head)) Some(head, t()) else None
    }
    case _ => None
  }

  def zipWith[B, C](bs: Stream[B])(f: (A, B) => C): Stream[C] = unfold((this, bs)) {
    case (Cons(h1, t1), Cons(h2, t2)) => Some(f(h1(), h2()), (t1(), t2()))
    case _                            => None
  }

  def zipAll[B](bs: Stream[B]): Stream[(Option[A], Option[B])] = unfold((this, bs)) {
    case (Cons(h1, t1), Cons(h2, t2)) => Some((Some(h1()), Some(h2())), (t1(), t2()))
    case (Cons(h1, t1), _)            => Some((Some(h1()), None), (t1(), Empty))
    case (_, Cons(h2, t2))            => Some((None, Some(h2())), (Empty, t2()))
    case _                            => None
  }

  def startsWith[B](s: Stream[B]): Boolean = zipAll(s) forAll {
    case (Some(a), Some(b)) => a == b
    case (_, None)          => true
    case _                  => false
  }

  def tails: Stream[Stream[A]] = unfold(Some(this): Option[Stream[A]])({
    case Some(Cons(h, t)) => Some((Cons(h, t), Some(t())))
    case Some(c)          => Some((c, None: Option[Stream[A]]))
    case None             => None
  })
  
  def scanRight[B](z: => B)(f: (A, => B) => B): Stream[B] = foldRight(cons(z, empty)) {
    (a,stream) => stream match {
      case c @Cons(h,t) => cons(f(a,h()), c)
      case Empty => cons(f(a,z), empty)
    }
  }
}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)
  def constant[A](a: A): Stream[A] = Stream.cons(a, constant(a))

  def from(n: Int): Stream[Int] = Stream.cons(n, from(n + 1))

  def fibs: Stream[Int] = {
    def fibRec(a: Int, b: Int): Stream[Int] = {
      val c = if (b > 0) a + b else 1
      Stream.cons(c, fibRec(b, c))
    }

    fibRec(0, 0)
  }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case Some((a, s)) => Stream.cons(a, unfold(s)(f))
    case _            => Empty
  }

  def ones2: Stream[Int] = unfold(1)(x => Some((x, 1)))
  def constant2[A](a: A): Stream[A] = unfold(a)(x => Some((x, a)))
  def from2(n: Int): Stream[Int] = unfold(n)(x => Some((x, x + 1)))
  def fibs2: Stream[Int] = unfold((0, 0)) {
    case (a, b) =>
      val c = if (b > 0) a + b else 1
      Some(c, (b, c))
  }
}