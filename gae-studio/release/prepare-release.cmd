REM Usage : prepare-release <KEY ID> <VERSION>

copy release.pom.xml gae-studio-webapp-%2.pom
copy release.pom.xml gae-studio-webapp-%2-javadoc.jar
copy release.pom.xml gae-studio-webapp-%2-sources.jar

gpg -u %1 --sign --detach-sign -a gae-studio-webapp-%2.war
gpg -u %1 --sign --detach-sign -a gae-studio-webapp-%2.pom
gpg -u %1 --sign --detach-sign -a gae-studio-webapp-%2-javadoc.jar
gpg -u %1 --sign --detach-sign -a gae-studio-webapp-%2-sources.jar

