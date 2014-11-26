package fpinscala.datastructures

object ch3Tree {
  import Tree._

  val t1 = Branch(Leaf(1), Branch(Branch(Leaf(3), Leaf(7)), Leaf(5)))
                                                  //> t1  : fpinscala.datastructures.Branch[Int] = Branch(Leaf(1),Branch(Branch(Le
                                                  //| af(3),Leaf(7)),Leaf(5)))

  size(t1)                                        //> res0: Int = 7
  map(t1)(_ + 2)                                  //> res1: fpinscala.datastructures.Tree[Int] = Branch(Leaf(3),Branch(Branch(Leaf
                                                  //| (5),Leaf(9)),Leaf(7)))
  fold(t1)(l => 1)((l, r) => l + r + 1)           //> res2: Int = 7
  fold(t1)(x => Leaf(x + 2): Tree[Int])((l, r) => Branch(l, r))
                                                  //> res3: fpinscala.datastructures.Tree[Int] = Branch(Leaf(3),Branch(Branch(Leaf
                                                  //| (5),Leaf(9)),Leaf(7)))
  map2(t1)(_ + 2)                                 //> res4: fpinscala.datastructures.Tree[Int] = Branch(Leaf(3),Branch(Branch(Leaf
                                                  //| (5),Leaf(9)),Leaf(7)))
  maximum(t1)                                     //> res5: Int = 7
  depth(t1)                                       //> res6: Int = 4
}