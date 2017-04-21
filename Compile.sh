echo Compilando, aguarde...
mkdir -p .\bin
javac -encoding Cp1252 -d bin -cp src src/client/*.java
javac -encoding Cp1252 -d bin -cp src src/server/*.java
echo Compilacao finalizou.
