#!/bin/bash

docker build --build-arg JAR_FILE=build/libs/TestHub-1.0-SNAPSHOT.jar -t alwa/testhub:1.0 .

