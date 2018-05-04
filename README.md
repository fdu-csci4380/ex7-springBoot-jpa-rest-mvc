# ex7-springBoot-jpa-rest-mvc
Example Spring Boot MVC (Model View Controller) project using;
+ thymeleaf templates for view ov MVC
+ Data JPA as ORM (Object Relational Mapping) to interact with persistence
+ H2 or MySQL as DB (RDBMS)
+ MVC serving UI
+ MVC Rest serving REST Services
+ DevTool for automatic restart of app upon code changes
+ Bootstrap for styles and layout (CSS and some JS) of UI
+ jQuery for dynamic UI
+ Webjars for UI (JS and CSS) dependencies like Bootstrap and jQuery in jar dependencies

## Paginated and dynamic Students list table
+ the students are stored in RDBMS (H2), persisted via Spring Data JPA, which uses by default hibernate behind the scenes
+ students are displayed with a Spring MVC page (StudentController.java, studentList.html, studentList.js)
+ students are fetched from DB in a paginated fashion and displayed in a table (/mvc/student/list), studentList.html
+ dynamic behavior is added with jQuery JS code in studentList.js
+ a student record can be edited, deleted
+ a new student record can be created with "New" button on top
+ student edit and new is done through "modal" pop-up of Bootstrap code

## To see it's UI running
+ start the app
+ access it's UI via browser at
http://localhost:8888/mvc/student/echoMessage
http://localhost:8888/mvc/student/echoMessage?msg=Yahooo
http://localhost:8888/mvc/student/list

## To startup postman or ARC(Advanced Rest Client)
+ open chrome browser, in url enter 
chrome://apps/
+ double click on Postman or ARC

## To see it's REST services running
+ start the app
+ access it's REST services via a REST client tool like postman, Advanced Rest Client (ARC) or even just a browser (for GET ones);
http://localhost:8888/rest/v1/students/echoMessage?msg=Hi			GET
http://localhost:8888/rest/v1/students								GET
http://localhost:8888/rest/v1/students/all							GET
http://localhost:8888/rest/v1/students								POST to create

## sample curl commands to test REST services running
+ issue any of below curl commands at git bash command line to test REST services
```
curl -i -X GET http://localhost:8888/rest/v1/students/echoMessage
curl -i -X GET http://localhost:8888/rest/v1/students/echoMessage?msg=Hi
curl -i  http://localhost:8888/rest/v1/students
curl -i  http://localhost:8888/rest/v1/students?page=2
curl -i  http://localhost:8888/rest/v1/students?rowsPerPage=3
curl -i "http://localhost:8888/rest/v1/students?page=2&rowsPerPage=3"
curl -i http://localhost:8888/rest/v1/students/findByNameIgnoreCaseQuery/ILKer
```
```
curl -X POST -H "Content-Type: application/json" -i  -d '{"name":"ilker_0", "lastname":"kiris_0", "grade":"freshman ", "age":200, "isFullTime":false, "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students
curl -X POST -H "Content-Type: application/json"     -d '{"name":"ilker_1", "lastname":"kiris_1", "grade":"FreshMan",  "age":201, "isFullTime":false, "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students
curl -X POST -H "Content-Type: application/json" --data '{"name":"ilker_2", "lastname":"kiris_2", "grade":" freshman", "age":202, "isFullTime":true,  "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students
```
```
curl -i http://localhost:8888/rest/v1/teachers/messageInJsonObject?msg=Hi
curl -i http://localhost:8888/rest/v1/teachers/ageRange/19/25
curl -X DELETE -i http://localhost:8888/rest/v1/teachers/teacherId/1
```

## To access h2 db console with browser
+ start the app
+ make sure app is not protected with spring security (it is not a dependency in pom.xml). Because /h2-console will be protected by Spring security as well, if it is on
+ access the h2 db console (assuming app started at port 8888) with a browser,
 just click "Connect" leaving; 
 Driver Class: org.h2.Driver, 
 JDBC URL: jdbc:h2:mem:testdb, 
 User Name: sa, 
 Password:         leave it empty

http://localhost:8888/h2-console


## To start mongoDb
+ assuming mongodb has been installed and properly added to the path, at command prompt issue
```
mongod
```
+ make sure you the default data directory is there for mongodb to use
```
ls -lrtd /c/data/db
mkdir -p /c/data/db
```
+ then connect to it via mongodb client that comes with mongodb install, at command prompt issue
```
mongo
mongo --version
mongo -h
```
+ see your collection in mongodb via mongo and to query mongoDb
```
mongo
show dbs
use UniversityDb
show collections
db.Teachers.find()
```
+ to shutdown the mongoDb that is running from mongodb client mongo command prompt
```
use admin
db.shutdownServer()
```
+ or you can shutdown mongoDb by issuing at command prompt
```
mongo --eval "db.getSiblingDB('admin').shutdownServer()"
```

## To create the project in STS
1. File -> New -> Spring Starter Project
  - Name: exp7-springBoot-mvc-thymeleaf-student
  - Packaging: Jar
  - Group: ex7.sb.mvc.thymeleaf
  - Artifact: student
  - Package: ex7.sb.mvc.thymeleaf.student
2. click Next
  * Boot Version: 2.0.1
  * (click at least, Web, Thymeleaf, JPA, H2, DevTools)
  * click Web -> Web
  * click Template Engines -> Thymeleaf
  * click SQL -> JPA, JDBC, MySQL, H2
  * click NoSQL -> MongoDB
  * click Core -> DevTools, Lombok
  * click Ops -> Actuator
3. click Finish
## To automatically populate h2 db
+ add bunch of insert statements in data.sql (or data-h2.sql or both) file in src/main/resources

## To create view file
+ right click on src/main/resources/templates , click New, select Web -> Html File -> Html5
+ then start sprinkling thymeleaf tags into the html
+ then pass data from controller to UI's view via model.addAttribute method

## To cretae small hello world ish angular app that is part of SpringBoot app
+ assuming node and npm are installed
+ make sure angular CLI is installed (check its version). Make sure it is higher than 1.6.3. And if not installed, install it.
```
ng -v
npm install -g @angular/cli
```
+ if your CLI version is 1.6.3 or lower or lower than 1.6.5, then probably "ng serve" will fail, then check your angular CLI version and upgrade to latest
```
ng -v
npm update -g @angular/cli
```
+ create the app shell via CLI using its "ng new", which takes a while to download all npm dependencies
```
cd /c/fdu/csci4380/projects
cd ex7-springBoot-jpa-rest-mvc
mkdir -p src/ui-angular
cd src/ui-angular
ng new angular-hello
+ run the angular app to serve at default port 4200
```
ng serve
```
+ connect to angular app via browser
```
http://localhost:4200
```
+ will manually trigger a build of it using "ng build", which will output as described in below outDir value in .angular-cli.json
```
"outDir": "../../main/resources/static/angularDist"
```
```
ng build
```