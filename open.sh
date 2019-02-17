#!/usr/bin/env bash

python -m SimpleHTTPServer 12345 &
pid=$!
open http://localhost:12345
wait $pid