# 7 - Coursier

### Goal

* Use coursier to generate a natively linked binary
* This command will be run by `make` to generate binaries later on for various platforms
* The SBT module produces a ZIP file containing the Makefile. This ZIP file is the input to the homebrew formula you will write in Step 8

### Start with

1. Verify that the provided Makefile downloads coursier: `make -f makefile/src/main/resources/Makefile`
2. Create a natively linked binary with coursier
    * Use `coursier bootstrap --native`
    * Use `-r ivy2local` to make your local ivy cache available
2. Add your `coursier` command to the “build” target in the provided Makefile

### Verify

`make` should produce a linked binary for your platform
