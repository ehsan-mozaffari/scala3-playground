# Docker
Is a container that package everything you need with the portability and has private or public repositories like `docker hub`.
A container is a layer of images. Mostly linux based image (alpine) layer because of the small size. Application layer image like postgres on top of the layers.

```sh
docker run postgres:9.6 # docker run pulls and starts it
docker run postgres # for the latest version
```
Docker downloads the postgres with all layers and the advantage is that if the image changes you can download just specific layers that are changed by that image.


```sh
docker ps # See all dockers
```
Docker image vs Docker container: The image is a file containing all of the configuration and not running but container is when that image runs by docker it creates a container that image runs on top of it so it means that that is the container of for example postgres image. So, the container is a running environment for image. A container has a binded port to it so we could talk with it from outside of the container. The file system and environment like environment variables are not the same with the OS docker running on.

Docker tool box abstracts docker container regarding the OS kernel like older version of windows for running linux based image of docker with Virtual box.

## Install docker
User community edition because it is free and also supported all of the needs for small business
Docker Engine: Core of docker to run docker containers
Docker CLI client: Run docker commands in terminal
Docker Compose: Orchestrate if you have multiple containers

Note: If you have docker installed in your laptop with multiple os account you get conflict so you have to stop it in your previous account and run it in your new account or just install it as a service with specific username.

## Basic commands
```sh
docker pull redis # It pulls redis docker image latest version on my computer
docker images # you could see all the downloaded images in your laptop
docker run redis # you can run the redis image and make a container from that image 
docker run --name my-custom-name-redis redis # you could name your container with name command and use it instead of container id
docker ps # see all containers like task manager and also see the exposed ports
docker run -d redis # run docker in a detached mode so it runs in background
docker run --rm # run docker and after stopping the service it removes the container automatically 
docker run --rmi # run docker and after stopping the service it removes the container image automatically 
docker stop <container id | container name > # for restarting docker container
docker start <container id | container name > # start docker container
docker ps -a # shows all the containers including not running to see the container id
docker run redis -p<host port>:<container port> # now you can see redis in the host port 
docker run redis:4.0 -p<host port>:<container port> # now you can see redis in the host port 
docker run -p6001:6379 --name redis-older redis:4.0 # download and run redis version 4.0 and bind the default port of redis (6379) to the specific host port (6000) so you could connect to redis with that 6000 port from your laptop
docker run -p6000:6379 --name redis-latest redis:4.0 # download and run redis version 4.0 and bind the default port of redis (6379) to the specific host port (6001) so you could connect to redis with that 6001 port from your laptop
```

All docker images have tags or versions like git.

Container port vs Host port: Container running into a virtual environment. So, you can have two containers that both listening on port 3000 and totally be fine as long as you bind them into separate port of your host (Your laptop) and you could connect to in with `someapp://localhost:3001` for example.

## Debugging the container
```sh
docker logs <container id >
docker logs <container name> ## if you not specified the name of the container it will get a random funny names of the famous persons 

docker exec -it <container id | container name> /bin/bash # get the terminal of docker container. -it means interactive terminal. /bin/bash means that runs the bash of the docker container for example see the environment variable with env command
```

Difference between `docker run` and `docker start`: `docker run` is when you basically want to create a new container and works with images as input but with `docker start` you work with the container not images and start them.

## Docker Network
The isolated networks where the containers are running in. If two docker container want to talk to each other the just have to know their name not localhost or etc.
```sh
docker network ls # shows all networks
docker network create mongo-network
docker run -d \ # run in a detached mode
--name mongodb \ #name of the container
-p:27017:27017 \ #exposed port
-e MONGO_INITDB_ROOT_USERNAME=mongoadmin \ #set environment variable of username 
-e MONGO_INITDB_ROOT_PASSWORD=secret \
--net mongo-network \ # chose the network of docker 
mongo
docker logs mongodb

docker run -d \
-p:8081:8081 \
-e ME_CONFIG_MONGODB_ADMINUSERNAME=mongoadmin \
-e ME_CONFIG_MONGODB_ADMINPASSWORD=secret \
-e ME_CONFIG_MONGODB_server=mongodb \ # in the same docker network you could just call the server by its container name without host and port 
--net mongo-network \
--name mongo-express \
mongo-experess
docker logs mongodb -f # -f streams the logs
```

## Docker compose
Docker compose can take a whole command to a yaml file.

```sh
version:'3' # latest version of docker compose 
services:
    mongodb: # container name
        image: mongo # image name like redis
        ports:
            - 27017:27017
        environment: # environment variables
            - MONGO_INITDB_ROOT_USERNAME=mongoadmin
        volumes:
            - mongo-db-data:/var/lib/mongo/data # See docker volumes section Ref name: mongo-db-data

    mongo-express:
        image: mongo-express
        ports:
            - 8081:8081
        environment:
            - ME_CONFIG_MONGODB_ADMINUSERNAME=mongoadmin
    my-app:
        image: repositoryurl/yourapprepo:latest # you have to logged to docker repository
        ports:
            - 3000:3000

volumes: 
    mongo-db-data: # you have to define it to see which container want to have persistence volumes and also you could share it in all your docker containers if you wanted. See if you not define it what happens because if you could persist it without sharing to other application it is isolated and beneficial for not changing it by other application
        driver: local # actual storage path created by docker to create it by local file system then use this volumes in containers

```

We don't have to create docker network and docker compose create a common network for the docker compose.
```sh
docker-compose -f mongo.yaml up
```
When you restart containers all data will lost. With volumes you can take care of consistency.
```sh
docker compose -f mongo.db down # removes network and stops the container
```

## Dockerfile
Your application must to package container we use `Dockerfile`. Docker file is a blueprint for creating docker images.
You could see the Dockerfile of all application in docker hub.
```shell
FROM node # like linux alpine. It is an alpine with the node installed. 
ENV MONGO_INITDB_ROOT_USERNAME=mongoadmin \ # better to not to do that and do it in docker compose file so you don't have to rebuild your application internally.
    MONGO_INITDB_ROOT_PASSWORD=secret
RUN mkdir -p /home/app # You can execute linux command and this container is available just in the container
COPY . /home/app # Difference with RUN is that it executed in host. so COPY could copy from host to docker container. copy what your app needed to docker image
CMD ["node", "/home/app/server.js"] # is like # node server.js that runs the node js file in cmd. Difference between CMD and RUN: CMD is an entry point command. A docker file can have multiple RUN s commands but CMD is just one and CMD marks the docker file that this is the command that you want to execute to run a server as an entry point

```
Build image from Docker file:
```sh
docker build -t my-app:1.0 . # . is where docker file. 1.0 is the tag of image
```
After building image you could push it into a docker repository and then pull it whenever you want.

NOTE: Whenever you adjust Dockerfile you have to rebuild docker 
```sh
docker rm <container id>
docker rmi <docker image id>
```
Push image to private repository:
```sh
docker pull mongo:4.2 # is equivalent of docker pull docker.io/library/mongo:4.2
docker login # to login into your private repository
docker pull repositoryurl/yourapprepo:tag
docker tag my-app:latest repositoryurl/yourapprepo:latest
docker push repositoryurl/yourapprepo:latest # pushes to your private repository
```

## Persisting data with Docker Volumes
Use for data persistance. Directory of host file system connected to docker file system.

```sh
docker run -v /hosthome/mount/data:/containervar/lib/mysql/data # you decide where the host dir mounts to docker container manually
docker run -v /containervar/lib/mysql/data # it automatically created by docker /var/lib/docker/volume/random-hash/_data it called anonymous volumes
docker run -v custom-name:/containervar/lib/mysql/data # so you can reference the volumes by name without knowing the directory is called Named Volumes and good for production
docker run -v<Host file address>:<Container file address> redis # you could add a file from host and mapped to a file in the container when you want to run or add a config file for example to the container 
docker run -v<Host file address>:<Container file address> -v<Host file address>:<Container file address> redis # you could add multiple files from host and mapped to multiple files in the container 
```

## 8 Docker best practices in prod
1) Always use official image of docker whenever possible:
```sh
From node # in docker is best in your docker file

# These are not best practice
FROM ubuntu
apt update && apt install -y node && rm -rf /var/lib/apt/lists/* 
```
2) Latest tag always be unpredictable and might break things so fix the image version the more specific is better
3) The less footprint of your OS is better for example ubuntu has some tools that you are not using it causes extra space, bigger image, low speed of uploading and more security issues. So use the leaner OS and less size OS for your app. Like Alpine.
4) Optimizing caching for image layer: In Dockerfile each command creates an image layer. if you go to alpine dockerfile in docker hub for example you could see all of commands (image layer) for it. And if you use an image in new docker file in addition to the image each new command adds new layer on top of that and you could see the image layer via the following command. The cache layer cause the faster image building and if you change two layers of application when you pull it from the image repository the other ones do not need to download again. So once one layer is changed all the following layers are changed and not reading from cache
```shell
docker history myapp:1.0
```
If the code changed the following layers are invalidated
```sh
FROM node:17.0.1-alpine 
WORKDIR /app
COPY myapp /app # it changed to invalidated so all of them invalidated too
RUN npm install --production # this is also invalidated because the COPY is invalidated but nothing changed in Frontend so the caching does not work on it
CMD["node", "src/index.js"] # This is also invalidated with the same reason above
```

To fix that:
```sh
FROM node:17.0.1-alpine 
WORKDIR /app
COPY package.json package-lock.json . # the front end haven't changed at all so the cache is valid
RUN npm install --production # this is not invalidated unless the package.json is changed and caching does work on it if nothing changed in frontend
COPY myapp /app # it changed to invalidated so all of them invalidated too
CMD["node", "src/index.js"] # This is also invalidated with the same reason above
```

So you could order your dockerfile from least to most frequently changing commands

5) To reduce the image size we don't need auto generated files like targets, build, README.md: Add `.dockerignore` to ignore folders we don't need.
6) Multi-stage build: Contents you need for build like unit tests, temp files, deployment tools, build tools but not for running your application. For instance, `package.json` or `pom.xml`. The multi-stage images gives us utility to use multiple temporary images to create and keep only the final image and all previous layers would be discarded and the layers are the layers of final image 
```docker
# Build stage
FROM maven AS build
WORKDIR /app
COPY myapp /app
RUN mvn package

# Run stage 
FROM tomcat
COPY --from=build /app/target/file.war /usr/local/tomcat/
```
7) Security: By not using which user runs the os by default the docker runs the application as root user and it causes some security issues so for defining dedicated user: 
```docker
# Create group and user
RUN groupadd -r tom && useradd -g tom tom # don't forget to set required permission
# Set ownership and permissions
RUN chown -R tom:tom /app

# Switch to user
USER tom # This is docker DIRECTIVE. some images has some generic user so you could run it under that for example node image has a user called node

CMD node index.js
```
8) Make sure your docker image has not vulnerabilities: using `docker scan myapp:1.0` and you have to be login in docker hub to scan your application and docker users a service called `snyk` to find out the issues