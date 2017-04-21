@@echo off
echo Compilando, aguarde...
cd src
"C:/Program Files/Java/jdk1.8.0_60/bin/javac" client/*.java
"C:/Program Files/Java/jdk1.8.0_60/bin/javac" server/*.java
echo Compilacao finalizou.
pause