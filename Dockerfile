FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


# React setup taken from https://www.robinwieruch.de/docker-create-react-app-development
# Docker Image which is used as foundation to create
# a custom Docker Image with this Dockerfile
FROM node:10
# A directory within the virtualized Docker environment
# Becomes more relevant when using Docker Compose later
WORKDIR /usr/src/app
# Copies package.json and package-lock.json to Docker environment
COPY package*.json ./
# Installs all node packages
RUN npm install
# Copies everything over to Docker environment
COPY . .
# Uses port which is used by the actual application
EXPOSE 3000
# Finally runs the application
CMD [ "npm", "start" ]