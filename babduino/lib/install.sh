#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

mvn install:install-file -Dfile=xbee-api-0.9.jar -DgroupId=com.rapplogic.xbee -DartifactId=xbee-api -Dversion=0.9 -Dpackaging=jar -DgeneratePom=true "$@"
mvn install:install-file -Dfile=rxtx-2.2pre2.jar -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2pre2 -Dpackaging=jar -DgeneratePom=true "$@"
