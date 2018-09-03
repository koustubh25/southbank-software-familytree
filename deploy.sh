#!/usr/bin/env bash

# Build API from source code (Prerequisite: Have JDK 1.8 and maven installed)

mvn clean install # Creates jar in target->familytree-1.0.jar
# To run the jar on local, you can run this command -> java -jar target/familytree-1.0.jar

# Build container from Docker file (Prerequisite: Docker installed)

docker build -t familytree:latest .
# Pushing of docker image to registry is not included to avoid sharing credentials
# But the same docker image is already pushed here ->

# Deploy this to Kubernetes (Prerequisite: docker image should be present in a registry from where Kubernetes should be able to download it)

kubectl create -f deploy.yml --save-config

kubectl expose deployment family-tree-deployment --type=LoadBalancer --name=familytree-service


