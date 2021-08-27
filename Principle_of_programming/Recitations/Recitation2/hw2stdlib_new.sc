
// Nats

sealed trait Nat
case object Zero extends Nat
case class Succ(pred : Nat) extends Nat

val one = Succ(Zero)
val two = Succ(one)
val three = Succ(two)
val four = Succ(three)
val five = Succ(four)
val six = Succ(five)
val seven = Succ(six)
val eight = Succ(seven)
val nine = Succ(eight)
val ten = Succ(nine)

def plus(x : Nat, y : Nat) : Nat = x match {
    case Zero    => y
    case Succ(x) => Succ(plus(x, y))
}

def nat_to_int(x : Nat) : Int = x match {
    case Zero => 0
    case Succ( x ) => 1 + nat_to_int(x)
}

def print_nat(x : Nat) : String = nat_to_int(x).toString

def mult(x : Nat, y : Nat) : Nat = x match {
    case Zero    => Zero
    case Succ(x) => plus(mult(x,y), y)
}


// Booleans

sealed trait Bool
case object True extends Bool
case object False extends Bool

def t = True
def f = False

def id(x : Bool) : Bool = x

def not(x : Bool) : Bool = x match {
    case True => False
    case False => True
}

def and(x : Bool, y : Bool) : Bool = (x,y) match {
    case (True, True) => True
    case _ => False
}

def or(x : Bool, y : Bool) : Bool = (x, y) match {
    case (False, False) => False
    case _              => True
}

def xor(x : Bool, y : Bool) : Bool = (x, y) match{
    case (True, False) => True
    case (False, True) => True
    case _ => False
}

def nand(x : Bool, y : Bool) : Bool = not(and(x,y))

def lte(x: Nat, y: Nat): Bool = (x, y) match {
    case (Zero, _) => True
    case (_, Zero) => False
    case (Succ(p1), Succ(p2)) => lte(p1, p2)
}

// Lists

sealed trait List[+A]
case object Empty extends List[Nothing]
case class Cons[A](x : A, xs : List[A]) extends List[A]

def length[A](xs : List[A]) : Nat = xs match {
    case Empty => Zero
    case Cons(_, xs) => Succ(length(xs))
}

def append[A](xs : List[A], ys : List[A]) : List[A] = xs match {
    case Empty => ys
    case Cons(x, xs) => Cons(x, append(xs, ys))
}

def reverse[A](xs : List[A]) : List[A] = xs match {
    case Empty => Empty
    case Cons(x, xs) => append(xs, Cons(x, Empty))
}

def map[A,B](f : (A => B), xs : List[A]) : List[B] = xs match {
    case Empty => Empty
    case Cons(x, xs) => Cons(f(x), map(f,xs))
}

def fold[A, B](f : ((A, B) => B), acc : B, xs : List[A]) : B = xs match {
    case Empty       => acc
    case Cons(x, xs) => fold(f, f(x, acc), xs)
}

// TEST HELPER
def passed(points: Int) {
    require(points >=0)
    if (points == 1) print(s"\n*** Tests Passed (1 point) ***\n")
    else print(s"\n*** Tests Passed ($points points) ***\n")
}
