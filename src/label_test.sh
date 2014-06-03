#!/bin/sh

# $1 = accumulate number; $2 = k-n

javac train_emo.java
java train_emo label
./segment.sh pku emoed_test_unseg UTF-8 0 > dataset/training/label_test
javac label_test_data.java
java label_test_data $1 $2
./ml/label_ml.sh $2