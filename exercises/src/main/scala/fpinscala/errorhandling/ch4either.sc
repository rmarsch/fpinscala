package fpinscala.errorhandling

object ch4either {
  import Either._

  val e1: Either[Exception, Int] = Left(new Exception("Oh noes!"))
                                                  //> e1  : fpinscala.errorhandling.Either[Exception,Int] = Left(java.lang.Excepti
                                                  //| on: Oh noes!)
  val e2 = Right("39")                            //> e2  : fpinscala.errorhandling.Right[String] = Right(39)
  val e3 = Right("noes")                          //> e3  : fpinscala.errorhandling.Right[String] = Right(noes)

  e1.map(_ + 2)                                   //> res0: fpinscala.errorhandling.Either[Exception,Int] = Left(java.lang.Excepti
                                                  //| on: Oh noes!)
  e2.map(_.toInt + 2)                             //> res1: fpinscala.errorhandling.Either[Nothing,Int] = Right(41)

  e1.flatMap(x => Try(x + 2))                     //> res2: fpinscala.errorhandling.Either[Exception,Int] = Left(java.lang.Excepti
                                                  //| on: Oh noes!)
  e2.flatMap(x => Try(x.toInt))                   //> res3: fpinscala.errorhandling.Either[Exception,Int] = Right(39)
  e3.flatMap(x => Try(x.toInt))                   //> res4: fpinscala.errorhandling.Either[Exception,Int] = Left(java.lang.NumberF
                                                  //| ormatException: For input string: "noes")
  e1.orElse(e2)                                   //> res5: fpinscala.errorhandling.Either[Exception,Any] = Right(39)
  e1.orElse(e3)                                   //> res6: fpinscala.errorhandling.Either[Exception,Any] = Right(noes)
  e2.orElse(e1)                                   //> res7: fpinscala.errorhandling.Either[Exception,Any] = Right(39)
  e2.orElse(e3)                                   //> res8: fpinscala.errorhandling.Either[Nothing,String] = Right(39)
  e3.orElse(e1)                                   //> res9: fpinscala.errorhandling.Either[Exception,Any] = Right(noes)
  e3.orElse(e2)                                   //> res10: fpinscala.errorhandling.Either[Nothing,String] = Right(noes)

  sequence(List(e2, e3, e2, e3, e3))              //> res11: fpinscala.errorhandling.Either[Nothing,List[String]] = Right(List(noe
                                                  //| s, noes, 39, noes, 39))
  sequence(List(e1, e2, e2, e2, e2, e2))          //> res12: fpinscala.errorhandling.Either[Exception,List[Any]] = Left(java.lang.
                                                  //| Exception: Oh noes!)
  sequence(List(e2, e3, e1))                      //> res13: fpinscala.errorhandling.Either[Exception,List[Any]] = Left(java.lang.
                                                  //| Exception: Oh noes!)
  traverse(List("39","15","12","ohnoes","12"))(x => Try(x.toInt))
                                                  //> res14: fpinscala.errorhandling.Either[Exception,List[Int]] = Left(java.lang.
                                                  //| NumberFormatException: For input string: "ohnoes")
  traverse(List("39","15","12","10053","12"))(x => Try(x.toInt * -25))
                                                  //> res15: fpinscala.errorhandling.Either[Exception,List[Int]] = Right(List(-300
                                                  //| , -251325, -300, -375, -975))
}