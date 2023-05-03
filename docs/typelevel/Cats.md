# Cats tutorial

## Definitions
* ### Essentials
  * #### values `val aBoolean:Boolean = flase` 
* ### Type class (type class pattern)
  Are a programming pattern we are using to offer certain capabilities to an existing type.
  For example offer a capability of serializing to Json. The type class pattern consist of
  Three parts:
  1. #### Type class definition: 
     Is a generic trait in most cases or some form of abstract class
  2. #### Type class INSTANCES (Implicit type class Instances)
     Is a generic trait in most cases or some form of abstract class
  3. #### Type class API 
     Use that **definition** and **instances** to create API for example for list or other types
  4. #### Offer API with Implicit classes in an object
     You can add some object to have implicit classes for adding those api on those defined API
## Cats structures
Import everything via:
```scala
import cats._
import cats.implicits._
``` 
or import them specifically by:
  1. ### type class: 
     Normally type classes are in `cats._` package or `cats.YourTypeClass`
  2. ### type class instances:
     Type class instances for the type you need normally are in `cats.instances.<your type>._`.
     By importing that you import all instances applicable to your type for example: `Eq` for `Int`.
     Or simply `cats.instances.yourType,_`
  3. ### type class API
     You can use them by the `apply` method like: `Eq[Int]` and use its API methods.
  4. ### type class API with implicit class ( extension method)
     They are in `cats.syntax.<instance>._`. Or simply 
     `cats.syntax.yourTypeClass._`. For example, `cats.syntax.eq._`
  5. ### Extra functionality
     Creation of type class instance for a custom type is used from 
     the companion object like the following:
     ```scala
      implicit val customTypeEq: Eq[CustomType] = Eq.instance[CustomType] {
        case (ct1, ct2) => ct1.field == ct2.field
      }
     ```

## Cats Type classes
  * #### `Semigroup`:
    Combines the elements of the same type
  * #### `Monoid`:
    Provides an `empty` (natural/zero) value for type `T`. Monoid extends from Semigroup with `empty` method.
    So basically it has `|+|` and `empty` in the implicit class method. You could import `|+|` from 
    `Semigroup` or `Monoid` (`cats.syntax.monoid._`). `Monoid.instance` is to define Monoid instance for
    custom type. So, the use cases are:
       1. A data structure that has to be combined with a starting value
       2. Data integration and Big data processing
       3. eventual consistency(like aggregating shopping carts in multiple tabs to one) and distributed computing
  * #### `Functor`:
    provides the `map` method. Use `Functor[List]` instead of ~~Functor[List[Int]]~~. It is used for 
    generalizing APIs. It is a higher-kinded type class
    Use cases: 
      1. Data structures that meant to be transformed in sequence  
      2. Specialize data structure for high performance algorithms
      3. Any "mappable" data structures under the same high level API 
    
    The followings are the same (`implicit` context bound)
    ```scala
        def do10x[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] = functor.map(container)(_ * 10)
        def do10x[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] = container.map(_ * 10)
        def do10x[F[_] : Functor](container: F[Int]): F[Int] = container.map(_ * 10)
    ```
  * #### Monad
    Don't make a mistake about `flatMap`. `flatMap` in sequential data structures is mimicking iteration.
    But don't make a mistake for example `Option` is not sequential and `flatMap` works on it to get value.
    `flatMap` does guarantee the order of evaluation. Monad is a pattern to 1)Wrapping a value into an
    M value and 2)the `flatMap` mechanism.
    
    
