# coopmo
![Coopmo Test (JAVA CI + MYSQL)](https://github.com/thedavekwon/coopmo/workflows/Coopmo%20Test%20(JAVA%20CI%20+%20MYSQL)/badge.svg)
## USAGE
```
git clone https://github.com/thedavekwon/coopmo.git
cd coopmo
```

### Docker
```
# using docker-compose
docker-compose build
docker-compose up
```

### Local
```
mvn clean install

# run the program
mvn spring-boot:run
# or 
mvn -jar target/coopmo-{VERSION}-SNAPSHOT.jar

# run the test (demo)
mvn test

# to set up database
mysql < createdb.sh

# to set up react
cd client
npm install
npm start
```

