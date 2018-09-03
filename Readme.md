# Familytree

## I. Implementation

The `TreeNode` class represents a generic node of a tree which includes references to parent and children.
`Person` class is a subclass of `TreeNode` class which is a specific node for representing the family tree.
`FamilyTree` class is responsible for inititalizing the familytree and also exposing several apis

There are 15 JUnit test cases which essentially tests the minimum relations that need to be handled.


### i. Part I - Create a simple REST API
The rest API implementation of the familytree java application is realized using `Spring` boot framework. The controller `FamilyTreeController` routes specific paths to the endpoint. The service class `FamilyTreeService` is used the query the model and return the results to the controller.

The Java Application can be run using the following command:
```
mvn clean install
java -jar target/familytree-1.0.jar
```

The above commands will install the needed dependencies using maven and also run the program and start a tomcat server running on port 8080

You will now be able to make `GET` requests like the following:

```
http://localhost:8080/getPeople?name=lika&relation=granddaughter
```

In the REST api, the http response code will always be 200. However, the response will have an attibute called `statusCode` which will try to map to the actual HTTP response code. It will also contain `data` field which will contain the actual result.

Sample Output
```
{"data":["Lavnya"],"statusCode":200}
```

This behavior is similar to the behavior from Facebook apis.


### ii. Part II - Containerize the API

Running the following command will create a docker image and run it on your local machine on port 8080

```
docker build -t familytree:v1.0 .
docker run -p 8080:8080 <Image id from above>
```
Then you can run the api mentioned in Part I

This docker image is already pushed here https://hub.docker.com/r/koustubh/familytree/

### Part III - Create a Kube Configuration file

To deploy this onto Kubernetes, a `deploy.yml` file is added. The following commands need to be run to deploy it on to Kubernetes

```
kubectl create -f deploy.yml
kubectl expose deployment family-tree-deployment --type=LoadBalancer --name=familytree-service
```
The above two commands will deploy the app on Kubernetes and expose the service through a TCP load balancer.
 You should be able to access the service in this manner:

 ```
 http://<load balancer ip>:8080/getPeople?name=shan&relation=children
 ```