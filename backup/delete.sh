#!/bin/bash

docker container stop coopmo
docker container stop coopmoMysql
docker container prune
docker network rm coopmoNetwork
docker network prune