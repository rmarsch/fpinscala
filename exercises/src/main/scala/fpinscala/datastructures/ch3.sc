package fpinscala.datastructures

object ch3List {
  import List._
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val l1 = List(1, 2, 3, 4, 5)                    //> l1  : fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons(
                                                  //| 5,Nil)))))
  val l2 = List(7, 8, 9, 0)                       //> l2  : fpinscala.datastructures.List[Int] = Cons(7,Cons(8,Cons(9,Cons(0,Nil))
                                                  //| ))
  val ns = List(1.0, 4.5, 7.6, 3.0)               //> ns  : fpinscala.datastructures.List[Double] = Cons(1.0,Cons(4.5,Cons(7.6,Con
                                                  //| s(3.0,Nil))))
  tail(l1)                                        //> res0: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Nil))
                                                  //| ))
  setHead(l1, 3)                                  //> res1: fpinscala.datastructures.List[Int] = Cons(3,Cons(2,Cons(3,Cons(4,Cons(
                                                  //| 5,Nil)))))
  drop(l1, 2)                                     //> res2: fpinscala.datastructures.List[Int] = Cons(3,Cons(4,Cons(5,Nil)))
  drop(l1, 3)                                     //> res3: fpinscala.datastructures.List[Int] = Cons(4,Cons(5,Nil))
  drop(l1, 4)                                     //> res4: fpinscala.datastructures.List[Int] = Cons(5,Nil)
  dropWhile(l1, { x: Int => x < 2 })              //> res5: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Nil))
                                                  //| ))
  dropWhile(l1, { x: Int => x <= 2 })             //> res6: fpinscala.datastructures.List[Int] = Cons(3,Cons(4,Cons(5,Nil)))
  init(l1)                                        //> res7: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))
                                                  //| ))
  length(l1)                                      //> res8: Int = 5
  sum(l1)                                         //> res9: Int = 15
  product(ns)                                     //> res10: Double = 102.6
  reverse(l1)                                     //> res11: fpinscala.datastructures.List[Int] = Cons(5,Cons(4,Cons(3,Cons(2,Cons
                                                  //| (1,Nil)))))
  foldRight(l1, 0)(_ + _)                         //> res12: Int = 15
  append(l1, l2)                                  //> res13: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons
                                                  //| (5,Cons(7,Cons(8,Cons(9,Cons(0,Nil)))))))))
  concat(List(l1, l2, l2, l1))                    //> res14: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons
                                                  //| (5,Cons(7,Cons(8,Cons(9,Cons(0,Cons(7,Cons(8,Cons(9,Cons(0,Cons(1,Cons(2,Con
                                                  //| s(3,Cons(4,Cons(5,Nil))))))))))))))))))
  addOne(l1)                                      //> res15: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Cons
                                                  //| (6,Nil)))))
  doubleToStr(ns)                                 //> res16: fpinscala.datastructures.List[String] = Cons(1.0,Cons(4.5,Cons(7.6,Co
                                                  //| ns(3.0,Nil))))
  map(l1)(_ + 1)                                  //> res17: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Cons
                                                  //| (6,Nil)))))
  filter(l1)(_ % 2 == 0)                          //> res18: fpinscala.datastructures.List[Int] = Cons(2,Cons(4,Nil))

  flatMap(l1)(x => List(x, x, x))                 //> res19: fpinscala.datastructures.List[Int] = Cons(1,Cons(1,Cons(1,Cons(2,Cons
                                                  //| (2,Cons(2,Cons(3,Cons(3,Cons(3,Cons(4,Cons(4,Cons(4,Cons(5,Cons(5,Cons(5,Nil
                                                  //| )))))))))))))))
  filter2(l1)(_ % 2 == 0)                         //> res20: fpinscala.datastructures.List[Int] = Cons(2,Cons(4,Nil))

  addList(l1, l1)                                 //> res21: fpinscala.datastructures.List[Int] = Cons(2,Cons(4,Cons(6,Cons(8,Cons
                                                  //| (10,Nil)))))
  addList(l1, l2)                                 //> res22: fpinscala.datastructures.List[Int] = Cons(8,Cons(10,Cons(12,Cons(4,Co
                                                  //| ns(5,Nil)))))
  zipWith(l1, l2)(_ + _)                          //> res23: fpinscala.datastructures.List[Int] = Cons(8,Cons(10,Cons(12,Cons(4,Co
                                                  //| ns(5,Nil)))))
}