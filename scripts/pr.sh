#!/bin/bash

BINTRAY_REPO=jgogstad/bottles-testtap

brew uninstall tws
brew test-bot --root-url="https://dl.bintray.com/$BINTRAY_REPO" tws
git reset --hard head # discard working copy changes

