### Release GAE-Studio

1. Change all the pom files to the correct version
2. mvn clean install -Pbuild-jar
3. Copy gae-studio-webapp-<VERSION>.jar from gae-studio-webapp/target to release/
4. Modify the release.pom.xml file and set the correct version
5. On Windows, run the prepare-release.cmd : prepare-release.cmd <KEY ID> <VERSION>

### How to manually sign artifacts
gpg -u <KEY ID> --sign --detach-sign -a <FILE>
