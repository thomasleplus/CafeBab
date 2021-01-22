#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

mvn clean package "$@"
