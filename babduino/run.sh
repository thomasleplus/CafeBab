#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

mvn exec:java -Dexec.mainClass="com.cafebab.app.Main" "$@"
