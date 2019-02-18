# 9 - Creating bottles

In this task we're going to publish the formula to your own tap. A Tap is simply a Github repo following the naming convention "homebrew-tapname". It's mandatory to prefix the repository with "homebrew-".

We've modified the release in this step to produce PRs to your tap repository whenever a new version of the application is released. Please read through this document before you do a new release.

* This functionality is provided in the bash script [create_pr.sh](./scripts/create_pr.sh)
* We've embedded it in the sbt release cycle for convenience

The tap `jgogstad/testtap` is used in this description. Please substitute with your own tap.

## Before you start

1. Create your own tap on GitHub: Create a repository that follows this pattern `homebrew-tapname`
2. Create a user on [Bintray](https://bintray.com) (this is where we’re going to host our binaries)
    * Create a new generic repository, prefix the name with “bottles-“, e.g. “bottles-mytap”
    * Add a new package to the repository with name “tws”. Let the repository be anything (it doesn’t matter)
    * Fetch the API key from you user settings and set the following two variables
    
           export HOMEBREW_BINTRAY_KEY=???
           export HOMEBREW_BINTRAY_USER=jgogstad
           
3. The publish step of the homebrew sbt project has been updated to produce a PR to your tap with the updated formula
    * Configure your tap name in `build.sbt`, replace the `???`
    * Create a new github token: https://github.com/settings/tokens/new?description=scala-native-workshop&scopes=repo 
    * Set environment variable `export GITHUB_TOKEN=token_value`
    * Test out that a release produces a PR to your newly created tap: `sbt release`
    
## Tasks

In this task we’re going to produce the CI scripts necessary for building bottles for your formula.

* Create two scripts:
    * `pr.sh`: Runs on PRs. Produces a bottle for the current operating system. In a production scenario, CI will spawn workers on all OSes we’re building binaries for and run this script. For this task, just building for macOS on localhost is fine.
    * `master.sh`: Runs on every commit to master. Updates the “bottle” block for the formula in question with links to the published binaries.

Use `brew test-bot --root-url=bintray-url` to build binaries on the PR branch, and `brew test-bot —ci-upload` to upload binaries and generate the bottle DSL on `master`.

## Suggested approach

Homebrew test-bot (or vanilla `brew install --build-bottle` and `brew bottle`) can be quite time consuming to get right. You're welcome to just have a look at the provided bash scripts in the next commit, and instead spend time on getting them to work for your repository.  

In a production scenario we would need to make the binaries produced in PR builds available to the master build. We'll cheat a bit in this workshop and just keep the files on the local file system. You're welcome to sync to GCS/S3/ABS if you want to.

We're going to simulate what a build server would do on our localhost:

```bash
# The build for our repository would at some point do a release, let's do that
$ sbt release
```

This triggers a PR on the tap-repository. Let's simulate what would happen there:

```
$ git clone git@github.com:jgogstad/homebrew-testtap.git
$ cd homebrew-testtap
$ git checkout -t origin/newly-created-pr-branch
$
$ # We need to let homebrew work against the PR repository, not the one on github/master. Symlink it
$ rm -rf $(brew --repo jgogstad/testtap)
$ ln -s $(pwd) $(brew --repo jgogstad/testtap)
$
# If you're using the provided scripts, symlink them. These would be placed in the tap repository in a production setup.
$ ln -s ~/development/tapad/scala-native-workshop/scripts/pr.sh pr.sh
$ ln -s ~/development/tapad/scala-native-workshop/scripts/master.sh master.sh
$
$ ./pr.sh
```

The output from `./pr.sh` should be a `.json` file describing the package and a `tar.gz` that is the linked binary.

```bash
# Let's pretend we're happy with the PR. Go to GitHub and merge it
# Now let's emulate the master build
$ git checkout master

# The built binaries are still available in the filesystem
$ ./master.sh
```

## Verify

```bash
$ brew update
$ brew install tws
```

You should see that the binary is installed from the bottle