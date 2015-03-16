# Introduction #

The release is performed to the Sonatype OSS Maven repository - which can push artifacts to Maven central (see http://central.sonatype.org/pages/apache-maven.html).

# Prerequisites #

  * JDK 6+ is installed on your command line path.
  * Subversion 1.5+ is installed on your command line path.
  * Maven 3.1+ is installed on your command line path.
  * A GPG client is installed on your command line path. For more information, please refer to http://www.gnupg.org/.
  * You have created your GPG keys and distributed your public key to hkp://pool.sks-keyservers.net/. For more information, please refer to How To Generate PGP Signatures With Maven.
  * Add the following servers to your Maven settings.xml:
```
    <server>
      <id>sonatype-nexus-snapshots</id>
      <username>derkoe</username>
      <password>password</password>
    </server>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>derkoe</username>
      <password>password</password>
    </server>
```

# Publish Snapshots #

To publish a snapshot, simply run (on a checked out SNAPSHOT version):
```
$ mvn clean deploy
```

# Stage a release #

Note: Please be sure that gpg is on your PATH.

First you need to prepare a release:
```
$ mvn release:clean
$ mvn release:prepare
```
Note: Preparing the release will create the new tag in SVN, automatically checking in on your behalf.

Then stage the release:
```
$ mvn release:perform
```

Maven will checkout the tag you just prepared, then build and deploy it into Nexus staging repository.

# Release it to Maven Central #

  1. Go to https://oss.sonatype.org/
  1. Login as derkoe
  1. Select "Staging Repositories"
  1. You should find your previously built artifacts here
  1. Select your build and click "Close" - this prompts for a comment
  1. Select your build and click "Release" - this prompts for a comment as well

If you want to drop the release select it and click "Drop".