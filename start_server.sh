#!/bin/sh

if [ "$1" = "-f" ]; then
    echo "remove target"
    rm -rf target/
fi

mvn package
java -jar target/spring-webapi-1.0.0.jar
