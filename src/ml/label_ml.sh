#!/bin/sh

mkdir "ml/$1/files"
mkdir "ml/$1/files/res"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.v1.vectors.txt" --output "ml/$1/files/train.v1.vectors"
ml/mallet/bin/mallet import-file --input "ml/$1/files/train.v2.vectors.txt" --output "ml/$1/files/train.v2.vectors"
ml/mallet/bin/mallet import-file --input "ml/$1/files/label_test.v1.txt" --output "ml/$1/files/label_test.v1.vectors" --use-pipe-from "ml/$1/files/train.v1.vectors"
ml/mallet/bin/mallet import-file --input "ml/$1/files/label_test.v2.txt" --output "ml/$1/files/label_test.v2.vectors" --use-pipe-from "ml/$1/files/train.v2.vectors"
ml/mallet/bin/vectors2classify --training-file "ml/$1/files/train.v1.vectors" --testing-file "ml/$1/files/label_test.v1.vectors" --trainer MaxEnt > "ml/$1/files/label_test.v1.stdout" 2>"ml/$1/files/label_test.v1.stderr"
ml/mallet/bin/vectors2classify --training-file "ml/$1/files/train.v2.vectors" --testing-file "ml/$1/files/label_test.v2.vectors" --trainer MaxEnt > "ml/$1/files/label_test.v2.stdout" 2>"ml/$1/files/label_test.v2.stderr"