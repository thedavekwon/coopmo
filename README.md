# coopmo
![Coopmo Test (JAVA CI + MYSQL)](https://github.com/thedavekwon/coopmo/workflows/Coopmo%20Test%20(JAVA%20CI%20+%20MYSQL)/badge.svg)
## USAGE
```
git clone https://github.com/thedavekwon/coopmo.git
cd coopmo
mvn clean install

# run the program
mvn spring-boot:run
# or 
mvn -jar target/coopmo-0.0.1-SNAPSHOT.jar

# run the test (demo)
mvn test

# to set up database
mysql < createdb.sh

# to set up react
cd src/main/resources/client
npm install
npm start

# TODO add environment to select host name
# currently react server is not in docker
# in src/main/resources/application.properties choose datasource.url accordingly
# if you want docker
# spring.datasource.url=jdbc:mysql://mysql:3306/coopmo_db?useSSL=false&allowPublicKeyRetrieval=true
# if you want without docker
# spring.datasource.url=jdbc:mysql://localhost:3306/coopmo_db?useSSL=false&allowPublicKeyRetrieval=true

# docker setup
docker-compose build
docker-compose up
```

