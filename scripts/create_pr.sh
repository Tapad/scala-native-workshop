#!/bin/bash -e

[[ $# -ne 2 ]] && echo "Create PR for formula in $TAP. Usage: $0 <tap> <formula.rb>" >&2 && exit 0
[[ -z "$GITHUB_TOKEN" ]] && echo "Please set GITHUB_TOKEN" >&2 && exit 1

TAP="$1"
FORMULA="$2"
NAME=tws-$(git describe --abbrev=0)
WORKING_COPY=$(brew --repo "$TAP")

! type hub >/dev/null 2>&1 && brew install hub

if [[ ! -d "$WORKING_COPY" ]]; then
  tap_name=${TAP#*/}
  repository=${TAP%*/${tap_name}}
  brew tap "$TAP" "git@github.com:${repository}/homebrew-${tap_name}.git"
fi

pushd "$WORKING_COPY"
git reset --hard head
git checkout master
git reset --hard origin/master
git pull
popd

mkdir -p "${WORKING_COPY}/Formula/"
cp "$FORMULA" "${WORKING_COPY}/Formula/"

pushd "$WORKING_COPY"

set +e
git branch -D "$NAME" 2>/dev/null && git push origin :"$NAME" 2>/dev/null
set -e
git checkout -b "$NAME"
git commit -a -m "$NAME"
git push origin "$NAME"
git fetch origin '+refs/heads/*:refs/remotes/origin/*'

hub pull-request -m "$NAME" >&2
popd