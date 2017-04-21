@@echo off
echo Compilando, aguarde...
javac -d bin -cp src src/client/*.java
javac -d bin -cp src src/server/*.java
echo Compilacao finalizou.
pause