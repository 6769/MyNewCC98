@echo off
set path=%path%;D:\Program Files\Git\mingw64\bin;


set TARGET=application.keystore
openssl enc -aes-256-cbc -in %TARGET% -out %TARGET%.dat


pause