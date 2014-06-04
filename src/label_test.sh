#!/bin/sh

javac train_emo.java
java train_emo $1 label
./segment.sh pku test_unseg UTF-8 0 > dataset/training/label_test
javac label_test_data.java
java label_test_data $1
./ml/label_ml.sh $1