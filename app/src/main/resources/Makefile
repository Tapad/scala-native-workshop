.DEFAULT_GOAL := build
.PHONY: build

SCALA_NATIVE_VERSION := 0.3_2.11

CURRENT_DIR = $(shell pwd)
BUILDPATH := $(CURRENT_DIR)/buildpath
TARGET := $(CURRENT_DIR)

$(BUILDPATH)/coursier:
	mkdir -p $(BUILDPATH)
	curl -L -o $(BUILDPATH)/coursier https://git.io/coursier-cli && chmod +x $(BUILDPATH)/coursier

build: $(BUILDPATH)/coursier
ifndef VERSION
	$(error Please provide version, 'make VERSION=x.y.z')
else
	export LDFLAGS='-L/usr/local/opt/curl/lib'; $(BUILDPATH)/coursier bootstrap --native com.tapad.workshop:app_native$(SCALA_NATIVE_VERSION):$(VERSION) -o $(TARGET)/app
endif