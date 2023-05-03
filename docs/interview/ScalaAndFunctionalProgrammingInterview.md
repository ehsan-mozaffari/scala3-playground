# Scala and Functional Programming Interview

## Definitions:

### Immutable:
Every modification of a data structure will create new data structure. 

#### Why we use it: 
Because in practice it is easy to read and understand code and works miracles in 
multithreaded/distributed application because the race condition in your code will be very much
reduced.

#### Why we don't use it: 
* We need to create datastructures all the time (wasted memory) ---> solution (the JVM GC is smart) 
* slightly slower algorithm for example 2x in traversal because we have to traverse recursively
  due to immutability and order of recursion we would need to traverse datastructures 
  multiple times and have some minor inefficiencies there. ---> (not a big deal in day to day practice)

But they are solvable: the newly datastructures would be very easily collected by GC (Garbage Collector).
and the slowness of traversing in not huge in a day-to-day problem because we are going to iterate
on small datastructures.

### Sealed
We use sealed because all subtype of that structure in the same file. And also use it in ADT
(Algebraic Data Type)

### Right associative  methods (operators)
Scala compiler allows methods ending with a colon `:` to be right associative. 
For example:
```scala
Nil.::(2) == 2 :: Nil
Nil.::(3).::(2).::(1) = 1 :: 2 :: 3 :: Nil
def -->: : String = "test" // is a valid name and right associative
```

### Tail recursion:
In functional programming we use recursion instead of loops. Because the recursive depth might
exceed the stack on very large datastructures or recursion stack which is why we use tail recursion
in Scala. 

Tail recursion:
1. If a code branch has a recursive call, the call will always be the last expression to be evaluated.
2. If we use tail recursion then the Scala Compiler will compile the Scala code into the JVM bytecode
that will reuse JVM stack frames so the tail recursive function will not use any additional stack frames
which will avoid the StackOverFlow error. So, see the following exampel:
```scala
@tailrec def factorialHelper(x: Int, acc: Int): Int = {
  if (x <= 1) acc
  else factorialHelper(x - 1, x * acc) // this recursive section is the last section of all code brances
}
```
So, the key technique is to use accumulator parameter to keep the track of partial result which can
return later. If you want truly validate that your code is tail recursive just use ```scala @tailrec```
annotation which will force the compiler to check your function for tail recursion and
if not tail recursive your compiler issue a compiler error.

To write a tail recursive function you have to ask the following question:
1. What things are changing over time? you need to pass them as arguments 

## List problems
Why `List` is covariant?(`List[+T]`) because we have subtypes like `Nil` that extends `List[Nothing]`

## Algorithm

### Sort
* ### Insertion sort
  