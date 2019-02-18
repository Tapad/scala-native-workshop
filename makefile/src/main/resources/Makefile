.DEFAULT_GOAL := build
.PHONY: build

SCALA_NATIVE_VERSION := 0.3_2.11
HOMEBREW_PREFIX ?= $(shell brew --prefix)
CURL_PREFIX := $(shell $(HOMEBREW_PREFIX)/bin/brew --prefix curl)

CURRENT_DIR = $(shell pwd)
BUILDPATH := $(CURRENT_DIR)/buildpath

$(BUILDPATH)/coursier:
	mkdir -p $(BUILDPATH)
	curl -L -o $(BUILDPATH)/coursier https://git.io/coursier-cli && chmod +x $(BUILDPATH)/coursier

build: $(BUILDPATH)/coursier
	export LDFLAGS='-L$(CURL_PREFIX)/lib'; export _JAVA_OPTIONS="-Xmx1g"; $(BUILDPATH)/coursier bootstrap --native com.tapad.workshop:app_native$(SCALA_NATIVE_VERSION):$(VERSION) --cache $(BUILDPATH) -r ivy2local -o $(CURRENT_DIR)/tws
