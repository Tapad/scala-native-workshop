# Scala Native Workshop

## Run

To run the application one can just type:
```
> sbt "app/run World!"
```

## Build

The application can be build using:
```
> sbt "app/nativeLink"
```
and run via:
```
> cd ./app/target/scala-2.11
> ./app-out World
Hello, World!
```

## Navigation

The SBT's `groll` command is used to navigate between the commits of this workshop.
Command must be followed by one of the following arguments:

`show` – shows the current commit id and message, if current commit is in history,
`list` – shows the full commit history,
`next` – moves to the next commit,
`prev` – moves to the previous commit.