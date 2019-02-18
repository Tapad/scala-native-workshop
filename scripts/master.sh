#!/bin/bash -e

[[ -z $HOMEBREW_BINTRAY_KEY ]] && echo "Please provide HOMEBREW_BINTRAY_KEY" >&2 && exit 1

# Replace the following variables with your own tap, github user and bintray repo

BINTRAY_ORG=jgogstad
GIT_EMAIL=jostein.gogstad@gmail.com
GIT_NAME="Jostein Gogstad"
BINTRAY_REPO="${BINTRAY_ORG}/bottles-testtap"

brew test-bot --ci-upload --root-url="https://dl.bintray.com/$BINTRAY_REPO" --git-name="$GIT_NAME" --git-email="$GIT_EMAIL" --bintray-org="$BINTRAY_ORG" --verbose
git push

# shellcheck disable=SC2207
# Gives tws--0.12 tws--0.13 etc.
unique_jsons=($(find . -name '*.json' -maxdepth 1 | sed -E 's/^(.*--[.0-9]+)\..*/\1/' | uniq))

for file in "${unique_jsons[@]}"; do
  package=${file%--*}
  version=${file#*--}
  curl -i -X POST -u "$BINTRAY_ORG:$HOMEBREW_BINTRAY_KEY" "https://api.bintray.com/content/$BINTRAY_REPO/$package/$version/publish"
done