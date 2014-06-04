#!/bin/sh

mkdir "ml/$1"
mkdir "ml/$1/files"
mkdir "ml/$1/files/unlabel_res"
mkdir "ml/$1/classifier"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.vectors.txt" --output "ml/$1/files/train.vectors"
ml/mallet/bin/mallet train-classifier --input "ml/$1/files/train.vectors" --output-classifier "ml/$1/classifier/$1.classifier" \ --trainer MaxEnt
javac Tagged.java
ml/mallet/bin/mallet classify-file --input "ml/$1/files/unlabel_test.txt" --output - --classifier "ml/$1/classifier/$1.classifier" > "ml/$1/files/unlabel_res/$1.predict.txt"
java Tagged "ml/$1/files/unlabel_res/$1.predict.txt" > "ml/$1/files/unlabel_res/$1.res"