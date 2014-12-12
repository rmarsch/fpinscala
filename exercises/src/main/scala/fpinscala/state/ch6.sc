package fpinscala.state

object ch6 {
  import RNG._

  val r1: Rand[Int] = unit(1)                     //> r1  : fpinscala.state.RNG.Rand[Int] = <function1>
  val r2: Rand[Int] = unit(2)                     //> r2  : fpinscala.state.RNG.Rand[Int] = <function1>

  val ri: RNG = Simple(1)                         //> ri  : fpinscala.state.RNG = Simple(1)

  map(r1)(_ + 38)(ri)                             //> res0: (Int, fpinscala.state.RNG) = (39,Simple(1))
  map(r2)(_ * 5)(ri)                              //> res1: (Int, fpinscala.state.RNG) = (10,Simple(1))

  nonNegativeInt(ri)                              //> res2: (Int, fpinscala.state.RNG) = (384748,Simple(25214903928))
  double(ri)                                      //> res3: (Double, fpinscala.state.RNG) = (1.79162249052507E-4,Simple(2521490392
                                                  //| 8))
  doubleMap(ri)                                   //> res4: (Double, fpinscala.state.RNG) = (1.79162249052507E-4,Simple(2521490392
                                                  //| 8))
  doubleInt(ri)                                   //> res5: ((Double, Int), fpinscala.state.RNG) = ((1.79162249052507E-4,-11512523
                                                  //| 39),Simple(206026503483683))
  intDouble(ri)                                   //> res6: ((Int, Double), fpinscala.state.RNG) = ((384748,0.5360936464444239),Si
                                                  //| mple(206026503483683))
  double3(ri)                                     //> res7: ((Double, Double, Double), fpinscala.state.RNG) = ((1.79162249052507E-
                                                  //| 4,0.5360936464444239,0.2558267895392267),Simple(245470556921330))
  ints(3)(ri)                                     //> res8: (List[Int], fpinscala.state.RNG) = (List(-549383847, -1151252339, 3847
                                                  //| 48),Simple(245470556921330))
  ints(5)(ri)                                     //> res9: (List[Int], fpinscala.state.RNG) = (List(-883454042, 1612966641, -5493
                                                  //| 83847, -1151252339, 384748),Simple(223576932655868))
  map2(r1, r2)(_ + _ + 2)(ri)                     //> res10: (Int, fpinscala.state.RNG) = (5,Simple(1))
  map2(r1, r2)(_ * _ + 54)(ri)                    //> res11: (Int, fpinscala.state.RNG) = (56,Simple(1))
  sequence(List(r1, r2, r2, r1, r2, r2, r1))(ri)  //> res12: (List[Int], fpinscala.state.RNG) = (List(1, 2, 2, 1, 2, 2, 1),Simple(
                                                  //| 1))
  flatMap(r1)(x => rng => (x + 4, rng))(ri)       //> res13: (Int, fpinscala.state.RNG) = (5,Simple(1))

  mapFromFl(r1)(_ + 38)(ri)                       //> res14: (Int, fpinscala.state.RNG) = (39,Simple(1))
  mapFromFl(r2)(_ * 5)(ri)                        //> res15: (Int, fpinscala.state.RNG) = (10,Simple(1))
  map2FromFl(r1, r2)(_ + _ + 2)(ri)               //> res16: (Int, fpinscala.state.RNG) = (5,Simple(1))
  map2FromFl(r1, r2)(_ * _ + 54)(ri)              //> res17: (Int, fpinscala.state.RNG) = (56,Simple(1))
}