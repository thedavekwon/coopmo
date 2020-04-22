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

# currently react server is not in docker

# docker setup
./create.sh

# docker clean up
./delete.sh
```

