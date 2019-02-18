.DEFAULT_GOAL := build
.PHONY: build

CURRENT_DIR = $(shell pwd)
BUILDPATH := $(CURRENT_DIR)/buildpath

$(BUILDPATH)/coursier:
	mkdir -p $(BUILDPATH)
	curl -L -o $(BUILDPATH)/coursier https://git.io/coursier-cli && chmod +x $(BUILDPATH)/coursier

build: $(BUILDPATH)/coursier
	@echo "Insert coursier linking command here"


