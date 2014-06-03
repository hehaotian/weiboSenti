#!/bin/sh

# $1 = accumulate number; $2 = k-n

javac train_emo.java $2
java train_emo unlabel
./segment.sh pku emoed_test_unseg UTF-8 0 > dataset/training/unlabel_test
javac training_data.java
java training_data $1 $2
./ml/unlabel_ml.sh $2
javac ma.java
java ma $2