docker network create coopmoNetwork

set -e
# https://stackoverflow.com/questions/25503412/how-do-i-know-when-my-docker-mysql-container-is-up-and-mysql-is-ready-for-taking
function getContainerHealth {
  docker inspect --format "{{.State.Health.Status}}" $1
}

function waitContainer {
  while STATUS=$(getContainerHealth $1); [ $STATUS != "healthy" ]; do
    if [ $STATUS == "unhealthy" ]; then
      echo "Failed!"
      exit 1
    fi
    printf .
    lf=$'\n'
    sleep 1
  done
  printf "$lf"
}

# setup database (pull from mysql docker image)
docker run --name=coopmoMysql --hostname=coopmoMysql --network=coopmoNetwork --health-cmd='mysqladmin ping --silent' \
           -e MYSQL_ROOT_PASSWORD=coopmopass -e MYSQL_DATABASE=coopmo_db -e MYSQL_USER=coopmo \
           -e MYSQL_PASSWORD=coopmopass -p 3306:3306 -d mysql

waitContainer coopmoMysql

sleep 5

docker run -it --rm --name=coopmoMaven --hostname=coopmoMaven --network=coopmoNetwork -v `pwd`:`pwd` -w `pwd` maven:3-jdk-11 mvn package

# build the container and run the app in 8080
docker build -t thedavekwon/coopmo .
docker run --name=coopmo --hostname=coopmo --network=coopmoNetwork -p 8080:8080 -t thedavekwon/coopmo
