package variance

object VarianceExamples{

  // Definitions:
  // == means equal
  // =!= means not equal

  type DosentMatter = Unit

  class Covariant[+A](classValueA: A) {

    // A is covariant(+A) so it can appear in positive positions

    // 1. return type of methods, fuctions and values is always in positive position.
    def exampleMethod1: A         = classValueA       // return position + == A position +
    val exampleFunction1: () => A = () => classValueA // return position + == A position +
    val exampleValue1: A          = classValueA       // return position + == A position +

    // 2. Input arguments of classes, methods and functions are always in negative position
    // class ExampleInnerClass(a: A) // input position - =!= A position +
    // def exampleMethod2(a: A): DosentMatter  = ()           // input position - =!= A position +
    // val exampleFunction2: A => DosentMatter = (a: A) => () // input position - =!= A position +

    // 3. Type parameters of classes and methods are always in negative position
    class ExampleInnerClass2[B](b: B) // input position - == B position -
    def exampleMethod3[B](b: B): DosentMatter      = () // input position - == B position -
    // Type parameters are always in negative position, so, B is in negative position
    // return type is always in positive position
    // B position - =!= Return position +
    // Why it compiles?
    // Scala Compiler type inference is smart enough to make sure B is being used consistently
    // through the method in a positive position by treating B as invariant (neutral position) in this context
    // Simply put, if a type parameter is used in return type, the type parameter is in neutral position.
    def exampleMethod4[B](b: B): B                 = b
    // B is supertype(upperbound) of A or A itself.
    // so B is supertype of A itself and its children because of that
    // B is in negative position from A's perspective. input position - == B position -
    def exampleMethod5[B >: A](b: B): DosentMatter = ()
    // B is subtype(lowerbound) of A or A itself.
    // since type parameters of methods are always in negative position B must be in negative position
    // however, B is subtype of A so it is in positive position from A's perspective.
    // B position + =!= Type parameter position -
    // def exampleMethod6[B <: A](): DosentMatter     = ()

    // 5. Always consider the return type of fuctions in the input arguments or return type of methods to check the position
    // input argument position - == inputFunction returns B position -
    def exampleMethod7[B](inputFuction: A => B): DosentMatter = ()
    // type parameter B - =!= return function which returns B position + but treated as neutral position see exampleMethod4
    def exampleMethod8[B](b: B): B => B                       = (b: B) => b
    // it is not treated like exampleMethod8 or exampleMethod4 because the type is not consistent to just B type
    // So, type parameter B position - =!= return position +
    // def exampleMethod9[B](b: B): A => B                       = (a: A) => b
    // input argument position - =!= inputFunction returns A position +
    // def exampleMethod10[B](inputFuction: B => A): DosentMatter = ()

    // TODO: why it compiles
    def exampleMethod11[B](inputFuction: A => B): DosentMatter = ()
  }

  class Contravariant[-A](classValueA: A) {

    // A is contravariant(-A) so it can appear in negative positions

    // 1. return type of methods, fuctions and values is always in positive position.
    // def exampleMethod1: A         = classValueA       // return position + =!= A position -
    // val exampleFunction1: () => A = () => classValueA // return position + =!= A position -
    // val exampleValue1: A          = classValueA       // return position + =!= A position -

    // 2. Input arguments of classes, methods and functions are always in negative position
    class ExampleInnerClass(a: A) // input position - == A position -
    def exampleMethod2(a: A): DosentMatter  = ()           // input position - == A position -
    val exampleFunction2: A => DosentMatter = (a: A) => () // input position - == A position -

    // 3. Type parameters of classes and methods are always in negative position
    class ExampleInnerClass2[B](b: B) // input position - == B position -
    def exampleMethod3[B](b: B): DosentMatter = () // input position - == B position -
    // Type parameters are always in negative position, so, B is in negative position
    // return type is always in positive position
    // B position - =!= Return position +
    // Why it compiles?
    // Scala Compiler type inference is smart enough to make sure B is being used consistently
    // through the method in a positive position by treating B as invariant (neutral position) in this context
    // Simply put, if a type parameter is used in return type, the type parameter is in neutral position.
    def exampleMethod4[B](b: B): B            = b

    // B is supertype(upperbound) of A or A itself.
    // since type parameters of methods are always in negative position an B must be in negative position
    // however, B is supertype of A so B acts like a child of A so it will be in positive position from A's perspective.
    // B position + =!= Type parameter position -
    // def exampleMethod5[B >: A](b: B): DosentMatter = ()

    // B is subtype(lowerbound) of A or A itself.
    // so B is subtype of A itself and its ancessters because of that
    // B is in negative position from A's perspective.
    // B position - == parameter position -
    def exampleMethod6[B <: A](): DosentMatter = ()

    // 5. Always consider the return type of fuctions in the input arguments or return type of methods to check the position
    // input argument position - == inputFunction returns B position -
    def exampleMethod7[B](inputFuction: DosentMatter => B): DosentMatter = ()
    // type parameter B - =!= return function which returns B position + but treated as neutral position see exampleMethod4
    def exampleMethod8[B](b: B): B => B                                  = (b: B) => b
    // TODO: ??? why it compiles
    def exampleMethod9[B](b: B): A => B                                  = (a: A) => b
    // input argument position - =!= inputFunction returns A position +
    def exampleMethod10[B](inputFuction: B => A): DosentMatter           = ()

    // TODO: ??? why it doesn't compile
    // def exampleMethod11[B](inputFuction: A => B): DosentMatter = ()
  }

  class Invariant[A](classValueA: A) {

    // A is invariant so it can appear in both positive and negative positions

    def exampleMethod5[B >: A](b: B): DosentMatter = ()
    def exampleMethod6[B >: A](b: B): B            = b
    def exampleMethod7[B <: A](): DosentMatter     = ()
    def exampleMethod7[B <: A](b: B): B            = b
  }

}
