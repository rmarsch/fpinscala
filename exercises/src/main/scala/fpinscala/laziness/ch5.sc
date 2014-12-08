package fpinscala.laziness

object ch5 {
  import Stream._
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val s1 = Stream.cons(5, Empty)                  //> s1  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  val s2 = Stream.cons(3, Stream.cons(2, Stream.cons(6, Stream.cons(152, Empty))))
                                                  //> s2  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  s1.toList                                       //> res0: List[Int] = List(5)
  s2.toList                                       //> res1: List[Int] = List(3, 2, 6, 152)

  s1.take(1).toList                               //> res2: List[Int] = List(5)
  s1.take(0).toList                               //> res3: List[Int] = List()
  s1.take(5).toList                               //> res4: List[Int] = List(5)
  s2.take(1).toList                               //> res5: List[Int] = List(3)
  s2.take(3).toList                               //> res6: List[Int] = List(3, 2, 6)
  s2.take(5).toList                               //> res7: List[Int] = List(3, 2, 6, 152)

  s1.drop(1).toList                               //> res8: List[Int] = List()
  s1.drop(0).toList                               //> res9: List[Int] = List(5)
  s1.drop(5).toList                               //> res10: List[Int] = List()
  s2.drop(1).toList                               //> res11: List[Int] = List(2, 6, 152)
  s2.drop(3).toList                               //> res12: List[Int] = List(152)
  s2.drop(5).toList                               //> res13: List[Int] = List()

  s1.takeWhile(_ < 1).toList                      //> res14: List[Int] = List()
  s1.takeWhile(_ > 1).toList                      //> res15: List[Int] = List(5)
  s2.takeWhile(_ < 1).toList                      //> res16: List[Int] = List()
  s2.takeWhile(_ > 1).toList                      //> res17: List[Int] = List(3, 2, 6, 152)
  s2.takeWhile(_ >= 3).toList                     //> res18: List[Int] = List(3)
  s2.takeWhile(_ <= 3).toList                     //> res19: List[Int] = List(3, 2)

  s1.forAll(_ < 1)                                //> res20: Boolean = false
  s1.forAll(_ > 1)                                //> res21: Boolean = true
  s2.forAll(_ < 1)                                //> res22: Boolean = false
  s2.forAll(_ > 1)                                //> res23: Boolean = true
  s2.forAll(_ < 10)                               //> res24: Boolean = false

  s1.takeWhile2(_ < 1).toList                     //> res25: List[Int] = List()
  s1.takeWhile2(_ > 1).toList                     //> res26: List[Int] = List(5)
  s2.takeWhile2(_ < 1).toList                     //> res27: List[Int] = List()
  s2.takeWhile2(_ > 1).toList                     //> res28: List[Int] = List(3, 2, 6, 152)
  s2.takeWhile2(_ >= 3).toList                    //> res29: List[Int] = List(3)
  s2.takeWhile2(_ <= 3).toList                    //> res30: List[Int] = List(3, 2)

  s1.headOption                                   //> res31: Option[Int] = Some(5)
  s2.headOption                                   //> res32: Option[Int] = Some(3)
  Empty.headOption                                //> res33: Option[Nothing] = None

  s1.map(_ + 2).toList                            //> res34: List[Int] = List(7)
  s2.map(_ * 2).toList                            //> res35: List[Int] = List(6, 4, 12, 304)
  s1.filter(_ < 1).toList                         //> res36: List[Int] = List()
  s1.filter(_ > 1).toList                         //> res37: List[Int] = List(5)
  s2.filter(_ < 10).toList                        //> res38: List[Int] = List(3, 2, 6)
  s2.filter(_ > 10).toList                        //> res39: List[Int] = List(152)
  s1.append(s2).toList                            //> res40: List[Int] = List(5, 3, 2, 6, 152)
  s2.append(s1).toList                            //> res41: List[Int] = List(3, 2, 6, 152, 5)

  val mapFilterStream = s2.map({ x =>
    println(s"mapping ${x}")
    x + 2
  }).filter({ x =>
    println(s"filtering ${x}")
    x < 10
  })                                              //> mapping 3
                                                  //| filtering 5
                                                  //| mapFilterStream  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<funct
                                                  //| ion0>)
  mapFilterStream.toList                          //> mapping 2
                                                  //| filtering 4
                                                  //| mapping 6
                                                  //| filtering 8
                                                  //| mapping 152
                                                  //| filtering 154
                                                  //| res42: List[Int] = List(5, 4, 8)

  def intTimesTo3(x: Int) = {
    println(s"evaluated ${x}")
    Cons(() => x, () => Cons(() => x * 2, () => Cons(() => x * 3, () => Empty)))
  }                                               //> intTimesTo3: (x: Int)fpinscala.laziness.Cons[Int]
  s1.flatMap(intTimesTo3)                         //> evaluated 5
                                                  //| res43: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  s1.flatMap(intTimesTo3).toList                  //> evaluated 5
                                                  //| res44: List[Int] = List(5, 10, 15)
  s2.flatMap(intTimesTo3)                         //> evaluated 3
                                                  //| res45: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  s2.flatMap(intTimesTo3).toList                  //> evaluated 3
                                                  //| evaluated 2
                                                  //| evaluated 6
                                                  //| evaluated 152
                                                  //| res46: List[Int] = List(3, 6, 9, 2, 4, 6, 6, 12, 18, 152, 304, 456)
  s2.flatMap(x => Empty).toList                   //> res47: List[Nothing] = List()

  Stream.constant("blort").take(5).toList         //> res48: List[String] = List(blort, blort, blort, blort, blort)
  Stream.from(39).take(5).toList                  //> res49: List[Int] = List(39, 40, 41, 42, 43)
  Stream.fibs.take(20).toList                     //> res50: List[Int] = List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377
                                                  //| , 610, 987, 1597, 2584, 4181, 6765)
  Stream.ones2.take(5).toList                     //> res51: List[Int] = List(1, 1, 1, 1, 1)
  Stream.constant2("blort").take(5).toList        //> res52: List[String] = List(blort, blort, blort, blort, blort)
  Stream.from2(39).take(5).toList                 //> res53: List[Int] = List(39, 40, 41, 42, 43)
  Stream.fibs2.take(20).toList                    //> res54: List[Int] = List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377
                                                  //| , 610, 987, 1597, 2584, 4181, 6765)
  val map2FilterStream = s2.map({ x =>
    println(s"mapping ${x}")
    x + 2
  }).filter({ x =>
    println(s"filtering ${x}")
    x < 10
  })                                              //> mapping 3
                                                  //| filtering 5
                                                  //| map2FilterStream  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<func
                                                  //| tion0>)
  map2FilterStream.toList                         //> mapping 2
                                                  //| filtering 4
                                                  //| mapping 6
                                                  //| filtering 8
                                                  //| mapping 152
                                                  //| filtering 154
                                                  //| res55: List[Int] = List(5, 4, 8)
  s2.take(3)                                      //> res56: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)

  s2.take(3).toList                               //> res57: List[Int] = List(3, 2, 6)

  val s2first2 = Stream.cons(3, Stream.cons(2, Empty))
                                                  //> s2first2  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  val s3 = Stream.cons(3, Stream.cons(2, Stream.cons(15, Empty)))
                                                  //> s3  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)

  s2.zipAll(s2first2).toList                      //> res58: List[(Option[Int], Option[Int])] = List((Some(3),Some(3)), (Some(2),
                                                  //| Some(2)), (Some(6),None), (Some(152),None))
  s2.startsWith(s2first2)                         //> res59: Boolean = true
  s2.startsWith(s3)                               //> res60: Boolean = false

  s2.tails.map(_.toList).toList                   //> res61: List[List[Int]] = List(List(3, 2, 6, 152), List(2, 6, 152), List(6, 
                                                  //| 152), List(152), List())
  def scanPrintAdd(a: Int, b: => Int): Int = {
    println(s"scan step ${a} ${b}")
    a + b
  }                                               //> scanPrintAdd: (a: Int, b: => Int)Int

  s2.scanRight(0)(scanPrintAdd).toList            //> scan step 152 0
                                                  //| scan step 6 152
                                                  //| scan step 2 158
                                                  //| scan step 3 160
                                                  //| res62: List[Int] = List(163, 160, 158, 152, 0)
  Stream(1, 2, 3).scanRight(0)(scanPrintAdd).toList
                                                  //> scan step 3 0
                                                  //| scan step 2 3
                                                  //| scan step 1 5
                                                  //| res63: List[Int] = List(6, 5, 3, 0)
  Stream(1, 2, 3).scanRight(0)(scanPrintAdd)      //> res64: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  Stream(1, 2, 3).scanRight(0)(scanPrintAdd).drop(3).toList
                                                  //> res65: List[Int] = List(0)
}