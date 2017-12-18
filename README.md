# introsde 2017 assignment 3 server
Marco Michelotti (marco.michelotti-1@studenti.unitn.it)

**WSDL heroku: http://sde3usa.herokuapp.com/people?wsdl**  

## Project

### Code analysis

- **PeoplePublisher.java**: This class let the server to be executed as Java Application creating it locally.

- **WS**: This package contains the exposed interface and its implementation.

- **Dao**: For this project I chose to manage all the operation with the database with one single Dao called ```PersonDao```.
With this Java class it is possibile to perform queryes to the specified database

- **Models**: There are the classes corresponding to the Entities in the database. With these classes it is possible to handle the database structure specifying 
the attributes and the relations (ManyToOne, ManyToMany, ...)


### Code tasks

**ivy.xml**

Contains the dependencies to download for the correct execution of the server

**build.xml**

The file build.xml contains the functions to perform with Ant.
	
### Execution

Go to the project folder and run ```ant create.war``` (terminal)

Server is hosted here: ```http://sde3usa.herokuapp.com/people?wsdl``` but it is possible to use the WAR file just created to deploy this service on your own server.
For a heroku server you can just run this command ```heroku war:deploy <warname.war> --app <name of the app>```

### Additional Notes
A huge problem has been found and resolved when trying to comunicate between client and server. Strange errors about ```multiple library definition``` and ```database access permissions```
appeared and avoid the server to response to the client correctly.
The solution was to restart multiple times the server where the WAR file was deployed until no errors appeared in Heroku application log.
