@echo off
set path=%path%;D:\Program Files\Git\mingw64\bin;


set TARGET=application.keystore
openssl enc -d -aes-256-cbc -in %TARGET%.dat -out %TARGET%


pause