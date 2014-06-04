#!/bin/sh

javac train_emo.java
java train_emo $1 unlabel
./segment.sh pku test_unseg UTF-8 0 > dataset/training/unlabel_test
javac training_data.java
java training_data $1
./ml/unlabel_ml.sh $1