# Debugging in Scala

## Using Sbt and remote debugging

1) Run in your Terminal
```sh
sbt -jvm-debug 5005
```

2) In your IDE for example Intellij Idea please go to: 
    1) Edit configuration
    2) Add Configuration
    3) Chose Remote JVM Debugging 
        i. Debugger Mode: `Attache to remote JVM`
        ii. Command line argument for remote JVM: 
        `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005`
        or `-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005`
        iii. Host: `localhost` and port: `5005`

3) Then run `your-module/run`

## Using `sbt task` Intellij Idea
To set environment variables: `-Dfoo=${MY_ENV_VAR}`
//TODO: [See](https://www.jetbrains.com/help/idea/run-debug-configuration-sbt-task.html)