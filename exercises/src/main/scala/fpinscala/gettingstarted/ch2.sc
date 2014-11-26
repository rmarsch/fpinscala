package fpinscala.gettingstarted

object ch2 {
  import MyModule._
  import PolymorphicFunctions._

  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  fib(1)                                          //> res0: Int = 1
  MyModule.fib(2)                                 //> res1: Int = 1
  MyModule.fib(3)                                 //> res2: Int = 2
  fib(25)                                         //> res3: Int = 75025
  fib(4)                                          //> res4: Int = 3
  fib(5)                                          //> res5: Int = 5
  fib(24)                                         //> res6: Int = 46368
  fib(70)                                         //> res7: Int = 885444751
  fib(76)                                         //> res8: Int = 1412467027

  val intSort = { (a: Int, b: Int) => a > b }     //> intSort  : (Int, Int) => Boolean = <function2>
  isSorted(Array(1, 2, 3, 4, 5), intSort)         //> res9: Boolean = true
  isSorted(Array(), intSort)                      //> res10: Boolean = true
  isSorted(Array(1, 2, 3, 6, 5, 43), intSort)     //> res11: Boolean = false
  
  val curriedIntSort = curry(intSort)             //> curriedIntSort  : Int => (Int => Boolean) = <function1>
  curriedIntSort(1)(0)                            //> res12: Boolean = true
  val uncurriedIntSort = uncurry(curriedIntSort)  //> uncurriedIntSort  : (Int, Int) => Boolean = <function2>
  uncurriedIntSort(1,2)                           //> res13: Boolean = false
  
  def add2(a: Int): Int = a+2                     //> add2: (a: Int)Int
  def square(a: Int): Int = a*a                   //> square: (a: Int)Int
  
  val add2square = compose(square, add2)          //> add2square  : Int => Int = <function1>
  add2square(2)                                   //> res14: Int = 16
  add2square(7)                                   //> res15: Int = 81
}