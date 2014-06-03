#!/bin/sh

mkdir ml/$2
mkdir ml/$2/files
javac add_co_train.java
java add_co_train $1 $2
cp ml/$1/files/train.v1.vectors.txt ml/$2/files/
cp ml/$1/files/train.v2.vectors.txt ml/$2/files/