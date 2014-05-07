#!/bin/sh

javac *.java
java Main $1 $2
./seg/segment.sh ctb $1 UTF-8 > "$1.seg"
./seg/segment.sh ctb $2 UTF-8 > "$2.seg"