#!/usr/bin/env bash

# Build API from source code (Prerequisite: Have JDK 1.8 and maven installed)

mvn clean install # Creates jar in target->familytree-1.0.jar
# To run the jar on local, you can run this command -> java -jar target/familytree-1.0.jar

# Build container from Docker file (Prerequisite: Docker installed)

docker build -t familytree:v1.0 .
# Pushing of docker image to registry is not included to avoid sharing credentials
# But the same docker image is already pushed here -> https://hub.docker.com/r/koustubh/familytree/


# Deploy this to Kubernetes
#(Prerequisites:
# 1. docker image should be present in a registry from where Kubernetes should be able to download it)
# 2. kubectl should be set up on local
kubectl create -f deploy.yml --save-config
kubectl expose deployment family-tree-deployment --type=LoadBalancer --name=familytree-service

#After this, you can access the api like the following:
# http://<load balancer ip>:8080/getPeople?name=shan&relation=children


