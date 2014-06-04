#!/bin/sh

mkdir "ml/$1"
mkdir "ml/$1/files"
mkdir "ml/$1/files/label_res"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.vectors.txt" --output "ml/$1/files/train.vectors"
ml/mallet/bin/mallet import-file --input "ml/$1/files/label_test.txt" --output "ml/$1/files/label_test.vectors" --use-pipe-from "ml/$1/files/train.vectors"
ml/mallet/bin/vectors2classify --training-file "ml/$1/files/train.vectors" --testing-file "ml/$1/files/label_test.vectors" --trainer MaxEnt > "ml/$1/files/label_res/label_test.stdout" 2>"ml/$1/files/label_res/label_test.stderr"