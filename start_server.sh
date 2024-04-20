#!/bin/sh

mvn package
java -jar target/spring-webapi-1.0.0.jar
