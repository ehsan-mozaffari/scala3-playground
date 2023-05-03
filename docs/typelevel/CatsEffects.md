# Cats effects

## Definitions

* ### `Deferred`
    `Deferred[IO, Int] // requires a given/implicit Concurrent[IO] in scope`
* ### `Spawn` 
    Is a type class that describes the capability of creating `Fiber`s
* ### `Concurrent` 
    Is a STRONGER type class of `Spawn` that also has the capability of creating concurrency primitives like:
    `ref` and `deferred` and with these two we can define everything in terms of concurrent coordination.
    Like: `pure`, `map`/`flatMap`. `raiseError`, `uncancellable`, `start` (fibers), + `ref`/`deferred`.
    We can create everything else in terms of `ref` and `deferred` like `Mutex`, `CyclicBarrier`, `CountDownLatch`, etc. 
* ### `Temporal`
    Is a type class that describes time-blocking effects (Time limited blocking). 
    The idea is to generalize time-based code for any effect type.
    `Temporal` extends `Concurrent` and has methods like `sleep` that can blocks semantically 
    the fiber for a specified time. Fundamental operation is `sleep`. extra functionality is `timeout`
* ### `Sync` 
    Is the ability to suspend effects synchronously
    has the ability to `delay` or `blocking` a block of code. It extends `MonadCancel`. It has the
    `delay` and `blocking` method implemented.
    * `delay`: Wrapping any computation in `F`. 
      "suspension" of a computation and will run on Cats Effect (CE) thread pool
    * `blocking`: A semantically blocking computation, wrapped in `F` (runs on the blocking thread pool) 
    
    `Sync` extends:
    * `Defer`: From cats that implemented `Defer`. `Defer` is not related to other cats type classes.
       `defer` = `delay` + `flatMap`. 
    * `Clock`
    * `Unique`

* ### `Async`
    Is responsible for so called "suspending" asynchronous computation executed elsewhere(`Suspended` in F).
    It extends `Sync[F]` with `Temporal[F]`
    `def firstEffect[F[_]: Concurrent]` means that `F` requires am implicit of `Concurrent[F]` type class
    in scope. So, `Async` is the ability to suspend async effects from outside Cats Effect. The reason that
    we have this type class is to generalize asynchronous code for any effect type

