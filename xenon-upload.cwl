#!/usr/bin/env cwl-runner
# Native:
# xenon --format cwljson sftp --location localhost upload README.md $PWD/copy-of-README.md
# Using cwl-runnner:
# ./xenon-upload.cwl --certfile ~/.ssh/id_rsa --scheme sftp --location $USER@172.17.0.1 --source README.md --target $PWD/copy-of-README.md
cwlVersion: v1.0
class: CommandLineTool
doc: Upload file to remote storage
hints:
- class: DockerRequirement
  # xenon-cli Docker container needs to be manually build before
  dockerImageId: nlesc/xenon-cli
baseCommand: xenon
arguments:
- prefix: --json
  position: 0
- valueFrom: upload
  position: 3
inputs:
  certfile:
    type: File?
    doc: Certificate file
    inputBinding:
      prefix: --certfile
      position: 0
  username:
    type: string?
    inputBinding:
      prefix: --username
      position: 0
  password:
    type: string?
    doc: Password, watch out do not use on systems with untrusted users
    inputBinding:
      prefix: --password
      position: 0
  scheme:
    type: string
    doc: Scheme, eg. file, sftp, ftp
    inputBinding:
      position: 1
  location:
    type: string?
    doc: List contents of path at location
    inputBinding:
      prefix: --location
      position: 2
# TODO prop should be optional, atm is must be set
#  prop:
#    doc: Xenon adaptor properties
#    type:
#      type: array
#      items: string
#      inputBinding:
#        prefix: --prop
#    inputBinding:
#      position: 2
  overwrite:
    type: boolean
    inputBinding:
      prefix: --overwrite
      position: 4
  ignore:
    type: boolean
    inputBinding:
      prefix: --ignore
      position: 4
  source:
    type: File
    inputBinding:
      position: 5
  target:
    type: string
    inputBinding:
      position: 6
outputs: []
stdout: cwl.output.json
