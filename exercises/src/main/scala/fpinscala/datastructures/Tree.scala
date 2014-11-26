package fpinscala.datastructures

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {
  def size[A](t: Tree[A]): Int = t match {
    case Leaf(a) => 1
    case Branch(l,r) => 1 + size(l) + size(r)
  }
  
  def map[A,B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Leaf(a) => Leaf(f(a))
    case Branch(l,r) => Branch(map(l)(f), map(r)(f))
  }

  def fold[A,B](t: Tree[A])(leaf: A => B)(combine: (B,B) => B): B = t match {
    case Leaf(a) => leaf(a)
    case Branch(l,r) => combine(fold(l)(leaf)(combine), fold(r)(leaf)(combine))
  }
  
  def map2[A,B](t: Tree[A])(f: A => B): Tree[B] = fold(t)(x => Leaf(f(x)): Tree[B])((l, r) => Branch(l, r))
  
  def maximum(t: Tree[Int]): Int = fold(t)(x => x)(Math.max(_,_))
  
  def depth[A](t: Tree[A]): Int = fold(t)(x => 1)(1+Math.max(_,_))
}