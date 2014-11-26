package fpinscala.errorhandling

object ch4options {
  import Option._
  val opt1 = Some(2)                              //> opt1  : fpinscala.errorhandling.Some[Int] = Some(2)
  val opt2: Option[Int] = None                    //> opt2  : fpinscala.errorhandling.Option[Int] = None
  val opt3 = Some("3")                            //> opt3  : fpinscala.errorhandling.Some[String] = Some(3)
  val opt4 = Some("notAnInt")                     //> opt4  : fpinscala.errorhandling.Some[String] = Some(notAnInt)

  opt1.map(_ + 2)                                 //> res0: fpinscala.errorhandling.Option[Int] = Some(4)
  opt2.map(_ + 2)                                 //> res1: fpinscala.errorhandling.Option[Int] = None
  opt1.getOrElse(3)                               //> res2: Int = 2
  opt2.getOrElse(3)                               //> res3: Int = 3
  opt1.flatMap(x => Some(x + 2))                  //> res4: fpinscala.errorhandling.Option[Int] = Some(4)
  opt2.flatMap(x => Some(x + 2))                  //> res5: fpinscala.errorhandling.Option[Int] = None
  opt1.orElse(opt2)                               //> res6: fpinscala.errorhandling.Option[Int] = Some(2)
  opt2.orElse(opt1)                               //> res7: fpinscala.errorhandling.Option[Int] = Some(2)
  opt1.filter(_ % 2 == 0)                         //> res8: fpinscala.errorhandling.Option[Int] = Some(2)
  opt1.filter(_ % 2 != 0)                         //> res9: fpinscala.errorhandling.Option[Int] = None
  opt2.filter(_ % 2 == 0)                         //> res10: fpinscala.errorhandling.Option[Int] = None

  mean(Seq(2.0, 2.0, 4.0, 8.0))                   //> res11: fpinscala.errorhandling.Option[Double] = Some(4.0)
  variance(Seq(2.0, 2.0, 4.0, 8.0))               //> res12: fpinscala.errorhandling.Option[Double] = Some(6.0)

  map2(opt1, opt1)(_ + _)                         //> res13: fpinscala.errorhandling.Option[Int] = Some(4)
  map2(opt1, opt2)(_ + _)                         //> res14: fpinscala.errorhandling.Option[Int] = None
  map2(opt2, opt1)(_ + _)                         //> res15: fpinscala.errorhandling.Option[Int] = None

  sequence(List(opt1, opt1, opt1, opt1))          //> res16: fpinscala.errorhandling.Option[List[Int]] = Some(List(2, 2, 2, 2))
  sequence(List(opt2, opt1, opt1))                //> res17: fpinscala.errorhandling.Option[List[Int]] = None
  sequence(List(opt1, opt1, opt1, opt2))          //> res18: fpinscala.errorhandling.Option[List[Int]] = None
  sequence2(List(opt1, opt1, opt1, opt1))         //> res19: fpinscala.errorhandling.Option[List[Int]] = Some(List(2, 2, 2, 2))
  sequence2(List(opt2, opt1, opt1))               //> res20: fpinscala.errorhandling.Option[List[Int]] = None
  sequence2(List(opt1, opt1, opt1, opt2))         //> res21: fpinscala.errorhandling.Option[List[Int]] = None
  traverse(List("3", "3", "3"))(i =>  Some(i))    //> res22: fpinscala.errorhandling.Option[List[String]] = Some(List(3, 3, 3))
}