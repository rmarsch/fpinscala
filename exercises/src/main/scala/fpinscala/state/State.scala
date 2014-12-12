package fpinscala.state

trait RNG {
  def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
}

object RNG {
  // NB - this was called SimpleRNG in the book text

  case class Simple(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = Simple(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def nonNegativeInt(rng: RNG): (Int, RNG) = rng.nextInt match {
    case (Int.MinValue, r) => nonNegativeInt(r)
    case (x, r)            => (Math.abs(x), r)

  }

  def double(rng: RNG): (Double, RNG) = nonNegativeInt(rng) match {
    case (x, r) => ((x.toDouble / Int.MaxValue), r)
  }

  def doubleMap(rng: RNG): (Double, RNG) = map(nonNegativeInt)(_.toDouble / Int.MaxValue)(rng)

  def intDouble(rng: RNG): ((Int, Double), RNG) = rng.nextInt match {
    case (i, r) => double(r) match {
      case (d, r2) => ((i, d), r2)
    }
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = double(rng) match {
    case (d, r) => r.nextInt match {
      case (i, r2) => ((d, i), r2)
    }
  }

  def double3(rng: RNG): ((Double, Double, Double), RNG) = double(rng) match {
    case (d1, r1) => double(r1) match {
      case (d2, r2) => double(r2) match {
        case (d3, r3) => ((d1, d2, d3), r3)
      }
    }
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    @annotation.tailrec
    def go(r: RNG, ls: List[Int], n: Int): (List[Int], RNG) = if (n > 0) r.nextInt match {
      case (i, r2) => go(r2, i :: ls, n - 1)
    }
    else {
      (ls, r)
    }

    go(rng, Nil, count)
  }

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    val (a, r1) = ra(rng)
    val (b, r2) = rb(r1)
    (f(a, b), r2)
  }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = rng => {
    def go(ls: List[Rand[A]], r: RNG): (List[A], RNG) = ls match {
      case x :: xs => {
        val (a, rNext) = x(r)
        val (as, rLast) = go(xs, rNext)
        (a :: as, rLast)
      }
      case _ => (List.empty[A], r)
    }

    go(fs, rng)
  }

  def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] = rng => {
    val (a, r) = f(rng)
    g(a)(r)
  }

  def mapFromFl[A, B](s: Rand[A])(f: A => B): Rand[B] = flatMap(s)(a => unit(f(a)))

  def map2FromFl[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = flatMap({
    rng =>
      val (a, r) = ra(rng)
      val (b, r2) = rb(r)
      ((a, b), r2)
  })({ case (a, b) => unit(f(a, b)) })
}

case class State[S, +A](run: S => (A, S)) {
  import State._

  def map[B](f: A => B): State[S, B] = flatMap(a => unit(f(a)))

  def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] = flatMap(a => State({ state =>
    val (b, s) = sb.run(state)
    (f(a, b), s)
  }))

  def flatMap[B](f: A => State[S, B]): State[S, B] = State({ state =>
    val (a, s) = run(state)
    f(a).run(s)
  })
}

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object State {
  type Rand[A] = State[RNG, A]

  def unit[A, S](a: A): State[S, A] = State({ state: S => (a, state) })

  def sequence[A, S](ss: List[State[S, A]]): State[S, List[A]] = State({ state =>
    def go(ls: List[State[S, A]], r: S): (List[A], S) = ls match {
      case x :: xs => {
        val (a, rNext) = x.run(r)
        val (as, rLast) = go(xs, rNext)
        (a :: as, rLast)
      }
      case _ => (List.empty[A], r)
    }

    go(ss, state)
  })

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = State({
    _ match {
      case startMachine @ Machine(_, startCandy, startCoins) => {
        val finalMachine = inputs.foldLeft((startMachine)) {
          (_, _) match {
            case (m @ Machine(_, 0, _), _)            => m
            case (Machine(true, candy, coins), Coin)  => Machine(false, candy, coins + 1)
            case (Machine(false, candy, coins), Turn) => Machine(true, candy - 1, coins)
            case (m @ Machine(_, candy, coins), _)    => m
          }
        }

        ((finalMachine.coins, finalMachine.candies), finalMachine)
      }
    }
  })
}
