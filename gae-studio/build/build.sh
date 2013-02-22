#!/bin/sh

BASE=`pwd`

# add jars to classpath
CP=""
for jar in `find ../target/gae-studio-1.0-SNAPSHOT/WEB-INF/lib -maxdepth 1 -name '*\.jar'`; do
CP="$CP$BASE/$jar:"
done


# debug output
echo "\nCP=$CP\n"


# combine the jars
#jar -cfv alljars.jar ../target/gae-studio-1.0-SNAPSHOT/WEB-INF/lib/*


# change package path jar
java -jar jarjar-1.4.jar process ruleset gaestudio.jar garstudio-app.jar
