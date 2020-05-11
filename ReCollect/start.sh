#!/bin/bash

mvn clean install -DskipTests

docker-compose rm -f -s -v
docker-compose down
docker-compose build
docker-compose up -d
# docker-compose logs -f
