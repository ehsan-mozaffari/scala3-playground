# Yaml or yml file

See the [cheat sheet](https://gist.github.com/jonschlinkert/5170877)

```yaml
person: 
    name: "Mike" # simple String key value
    occupation: &OCCUP 'programmer' # Anchor String key value
    age: !!float 23 # 23.0 float
    gpa: !!str 3.5
    fav_number: 1e+10
    male: true
    birthday: 1994-02-06 14:30:22 # ISO 861
    flaws: null
    hobbies: 
        - hiking
        - movies
        - riding bike
    movies: ["Dark knight", 'Good Will Hunting']
    friends: 
        - name: "Steph"
          age: 22
        - {name: "Adam", age: 33}
        -
          name: "Joe"
          age: 25
    description-in-single-line: >
        Here is the simple single line
        description to see how it works
        and what is that 
    signature-in-multiple-lines: |
        Mike
        Giraffe Academy
        email - test@yahoo.com
    job-title: *OCCUP # programmer
    base: &base
        var1: value1

    foo:
        <<: *base # var1: value1
        var2: value2
    --- # This is the separation of two documents in one yaml file
    ... # document terminator
```