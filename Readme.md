# Familytree


This is the solution to Problem 1 from the attached document:

**Model out the Shan family tree so that when given a ‘name’ and a ‘relationship’ as an input, the output are the people that correspond to the relationship.**

## Deliverables

1. All source code
https://github.com/koustubh25/southbank-software-familytree

2. A .jar (or other runnable program) from part 1 of the test.
https://www.dropbox.com/sh/ojmz8rf06dcgiwo/AACMFUbT-0Y6FM8yvBVAUguwa?dl=0

3. A dockerfile from part 2 of the test.
https://github.com/koustubh25/southbank-software-familytree/blob/master/Dockerfile

4. A kubernetes configuration file from part 3 of the test.
https://github.com/koustubh25/southbank-software-familytree/blob/master/deploy.yml

5. A shell script (or equivilent) that builds and runs your test kubernetes cluster.
https://github.com/koustubh25/southbank-software-familytree/blob/master/deploy.sh

A demo of the application has already been setup on my personal GCP account (deployed on Kubernetes). It can be accessed here

`GET http://35.221.113.235:8080/getPeople?name=shan&relation=children`


## I. Implementation

The `TreeNode` class represents a generic node of a tree which includes references to parent and children.
`Person` class is a subclass of `TreeNode` class which is a specific node for representing the family tree.
`FamilyTree` class is responsible for inititalizing the familytree and also exposing several apis

There are 15 JUnit test cases which essentially test the minimum relations that need to be handled. These are run during `mvn clean install`

Since the structure for only King Shan's family is show in the problem statement and not of their spouse's (e.g. `Chit's'` brothers and sisters are known, but his spouse (`Amib's '` aren't known))
So, the nodes in the tree are created with only King Shan's relatives and a field for the spouse is added `partnerName`
![class relationship](https://raw.githubusercontent.com/koustubh25/southbank-familytree/master/classRelationship.png)

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


The endpoint is always `/getPeople` and it takes two attributes `name` and `relation`
The output is an array of people.

name can be one of the following:
```
Shan
Anga
Ish
Chit
Ambi
Vich
Lika
Saty
Vya
Drita
Jaya
Vrita
Vila
Jnki
Chika
Kpila
Satvy
Asva
Savya
Krpi
Saayan
Mina
Jata
Driya
Mnu
Lavnya
Gru
Kriya
Misa
```

relation can be one of the following:
```
paternaluncles
maternaluncles
paternalaunt
maternalaunt
sisterinlaw
brotherinlaw
cousins
father
mother
children
sons
daughters
brothers
sisters
granddaughter
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

 ### Part 4 - Deployment script

 You can run the deployment script
 `./deploy.sh`
 This will do the following
 1. Create a jar file in `target/familytree-1.0.jar`
 2. Build a Docker image on local with the tag `familytree:v1.0`
3. Create a Kubernetes deployment
4. Expose the deployment to the outside world