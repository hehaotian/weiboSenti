#!/bin/sh

mkdir "ml/$1/files"
mkdir "ml/$1/files/res"
mkdir "ml/$1/classifier"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.v1.vectors.txt" --output "ml/$1/files/train.v1.vectors"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.v2.vectors.txt" --output "ml/$1/files/train.v2.vectors"
ml/mallet/bin/mallet train-classifier --input "ml/$1/files/train.v1.vectors" --output-classifier "ml/$1/classifier/$1.v1.classifier" \ --trainer MaxEnt
ml/mallet/bin/mallet train-classifier --input "ml/$1/files/train.v2.vectors" --output-classifier "ml/$1/classifier/$1.v2.classifier" \ --trainer MaxEnt
javac Tagged.java
ml/mallet/bin/mallet classify-file --input "ml/$1/files/unlabel_test.v1.txt" --output - --classifier "ml/$1/classifier/$1.v1.classifier" > "ml/$1/files/$1.v1.predict.txt"
ml/mallet/bin/mallet classify-file --input "ml/$1/files/unlabel_test.v2.txt" --output - --classifier "ml/$1/classifier/$1.v2.classifier" > "ml/$1/files/$1.v2.predict.txt"
java Tagged "ml/$1/files/$1.v1.predict.txt" > "ml/$1/files/res/$1.v1.res"
java Tagged "ml/$1/files/$1.v2.predict.txt" > "ml/$1/files/res/$1.v2.res"