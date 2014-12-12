package fpinscala.state

object stateSheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val s1: State[RNG, Int] = State.unit(5)         //> s1  : fpinscala.state.State[fpinscala.state.RNG,Int] = State(<function1>)
  val s2: State[RNG, Int] = State.unit(39)        //> s2  : fpinscala.state.State[fpinscala.state.RNG,Int] = State(<function1>)

  val rng1 = RNG.Simple(2345245)                  //> rng1  : fpinscala.state.RNG.Simple = Simple(2345245)

  s1.run(rng1)                                    //> res0: (Int, fpinscala.state.RNG) = (5,Simple(2345245))
  s2.run(rng1)                                    //> res1: (Int, fpinscala.state.RNG) = (39,Simple(2345245))

  s1.map(_ * 5).run(rng1)                         //> res2: (Int, fpinscala.state.RNG) = (25,Simple(2345245))
  s2.map(_ / 4.5 + 15).run(rng1)                  //> res3: (Double, fpinscala.state.RNG) = (23.666666666666664,Simple(2345245))

  s1.map2(s2)(_ * _).run(rng1)                    //> res4: (Int, fpinscala.state.RNG) = (195,Simple(2345245))
  s1.map2(s2)((a, b) => (a * b) + a + b - (a % b)).run(rng1)
                                                  //> res5: (Int, fpinscala.state.RNG) = (234,Simple(2345245))
  State.sequence(List(s1, s1, s2, s2, s1, s2, s1, s2)).run(rng1)
                                                  //> res6: (List[Int], fpinscala.state.RNG) = (List(5, 5, 39, 39, 5, 39, 5, 39),S
                                                  //| imple(2345245))
  var sim1 = State.simulateMachine(List(Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn))
                                                  //> sim1  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = State(<f
                                                  //| unction1>)
  var sim2 = State.simulateMachine(List(Coin, Coin, Turn, Turn, Coin, Turn, Turn, Coin, Coin, Turn))
                                                  //> sim2  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = State(<f
                                                  //| unction1>)

  var mac1 = Machine(true, 5, 10)                 //> mac1  : fpinscala.state.Machine = Machine(true,5,10)
  var mac2 = Machine(true, 2, 0)                  //> mac2  : fpinscala.state.Machine = Machine(true,2,0)
  var mac3 = Machine(false, 100, 0)               //> mac3  : fpinscala.state.Machine = Machine(false,100,0)

  sim1.run(mac1)                                  //> res7: ((Int, Int), fpinscala.state.Machine) = ((14,1),Machine(true,1,14))
  sim1.run(mac2)                                  //> res8: ((Int, Int), fpinscala.state.Machine) = ((2,0),Machine(true,0,2))
  sim1.run(mac3)                                  //> res9: ((Int, Int), fpinscala.state.Machine) = ((3,96),Machine(true,96,3))
  sim2.run(mac1)                                  //> res10: ((Int, Int), fpinscala.state.Machine) = ((13,2),Machine(true,2,13))
  sim2.run(mac2)                                  //> res11: ((Int, Int), fpinscala.state.Machine) = ((2,0),Machine(true,0,2))
  sim2.run(mac3)                                  //> res12: ((Int, Int), fpinscala.state.Machine) = ((2,97),Machine(true,97,2))
}