#!/bin/sh

# Babduino - Arduino project to detect usage of a table football.
# Copyright (C) 2016 Thomas Leplus
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

mvn install:install-file -Dfile=xbee-api-0.9.jar -DgroupId=com.rapplogic.xbee -DartifactId=xbee-api -Dversion=0.9 -Dpackaging=jar -DgeneratePom=true $*
mvn install:install-file -Dfile=rxtx-2.2pre2.jar -DgroupId=org.rxtx -DartifactId=rxtx -Dversion=2.2pre2 -Dpackaging=jar -DgeneratePom=true $*
