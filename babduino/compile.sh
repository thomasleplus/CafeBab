#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

./mvnw clean package "$@"
