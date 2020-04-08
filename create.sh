docker build -t thedavekwon/coopmo .
docker run --name coopmo-mysql -e MYSQL_ROOT_PASSWORD=coopmopass -e MYSQL_DATABASE=coopmo_db -e MYSQL_USER=coopmo -e MYSQL_PASSWORD=coopmopass -p 3306:3306 -d mysql/mysql-server:latest
docker run -p 8080:8080 -t thedavekwon/coopmo