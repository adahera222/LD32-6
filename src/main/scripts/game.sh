#!/bin/sh

BASE=`dirname $0`
java -jar "$BASE/${project.build.finalName}-jar-with-dependencies.${project.packaging}" &
