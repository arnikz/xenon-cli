# Xenon Command Line Interface

[![Build Status](https://travis-ci.org/NLeSC/xenon-cli.svg?branch=master)](https://travis-ci.org/NLeSC/xenon-cli)
[![Build status](https://ci.appveyor.com/api/projects/status/vki0xma8y7glpt09/branch/master?svg=true)](https://ci.appveyor.com/project/NLeSC/xenon-cli/branch/master)
[![SonarQube Gate](https://sonarcloud.io/api/badges/gate?key=nl.esciencecenter.xenon.cli%3Axenon-cli)](https://sonarcloud.io/dashboard?id=nl.esciencecenter.xenon.cli%3Axenon-cli)
[![SonarQube Coverage](https://sonarcloud.io/api/badges/measure?key=nl.esciencecenter.xenon.cli%3Axenon-cli&metric=coverage)](https://sonarcloud.io/component_measures?id=nl.esciencecenter.xenon.cli%3Axenon-cli&metric=Coverage)
[![DOI](https://zenodo.org/badge/80642209.svg)](https://zenodo.org/badge/latestdoi/80642209)

Command line interface which uses the [Xenon library](https://nlesc.github.io/Xenon) to perform job and file operations.

# Install

Goto https://github.com/NLeSC/xenon-cli/releases and download a tarball (or zipfile).
The tarball can be installed with:
```bash
tar -xf build/distributions/xenon*.tar
xenon*/bin/xenon --help
```
Add `xenon*/bin` to your PATH environment variable for easy usage.

# Usage

```bash
# List files on local filesystem
xenon filesystem file list /etc
# List files on remote filesystem using sftp
xenon filesystem sftp --location localhost list /etc
# Copy local file to remote filesystem
xenon filesystem sftp --location localhost upload /etc/passwd /tmp/copy-of-passwd
# Execute a program remotely using ssh
xenon scheduler ssh --location localhost exec /bin/hostname
# Pipe to a remote file
echo "sleep 30;echo Hello" | xenon sftp --location localhost upload - /tmp/myjob.sh
# Submit to a remote Slurm batch scheduler
xenon scheduler slurm --location ssh://localhost submit /bin/sh /tmp/myjob.sh
```

The above commands use your current username and keys from ~/.ssh.

To keep password or passphrase invisible in process list put the password in a text file (eg. 'password.txt') and then use '@password.txt' as argument.
For example:
```
xenon filesystem sftp --location localhost --username $USER --password @password.txt list $PWD/src
```

# Build

```
./gradlew build
```

Generates application tar/zip in `build/distributions/` directory.

# Tests

Requirements for the integration tests:
* [docker](https://docs.docker.com/engine/installation/), v1.13 or greater
* [docker-compose](https://docs.docker.com/compose/), v1.10 or greater

The unit and integration tests can be run with:
```
./gradlew check
```

# Release

1. Bump version in `build.gradle`, add version to `CHANGELOG.md` and commit/push
2. Create a new GitHub release
3. Upload the files in `build/distributions/` directory to that release
4. Publish release
5. Edit [Zenodo entry](https://doi.org/10.5281/zenodo.597603), correct license, add [Xenon doi](https://doi.org/10.5281/zenodo.597993) as `is referenced by this upload`.

## Docker

Run Xenon CLI as a Docker container.

The Docker image can be build with
```
./gradlew docker
```

Generates a `nlesc/xenon-cli` Docker image.

To use local files use volume mounting (watch out as the path should be relative to mount point):
```
docker run -ti --rm nlesc/xenon-cli --user $USER -v $PWD:/work --adaptor ssh upload --source /work/somefile.txt --location localhost --path /tmp/copy-of-somefile.txt 
```

## Common Workflow Language

Run Xenon CLI using a cwl-runner or as a tool in a [Common Workflow Language](http://www.commonwl.org/) workflow.

Requires `nlesc/xenon-cli` Docker image to be available locally.

Example to list contents of `/etc` directory via a ssh to localhost connection with cwl-runner:
```
./xenon-ls.cwl --adaptor sftp --location $USER@172.17.0.1 --certfile ~/.ssh/id_rsa --path /etc
# Copy file from localhost to working directory inside Docker container
./xenon-upload.cwl --adaptor sftp --certfile ~/.ssh/id_rsa --location $USER@172.17.0.1 --source $PWD/README.md --target /tmp/copy-of-README.md
# Copy file inside Docker container to localhost
./xenon-download.cwl --adaptor sftp --certfile ~/.ssh/id_rsa --location $USER@172.17.0.1 --source /etc/passwd --target $PWD/copy-of-passwd
```
(Replace `<user>@<host>` with actual username and hostname + expects docker with default network range)
