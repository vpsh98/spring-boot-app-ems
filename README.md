# spring-boot-app-ems
A spring boot server with CRUD features, shown as a employee management system.  

-This server uses mysql database underneath.  
-It has an interceptor to track and manage all incoming requests. Tokens extraction can be done in this, with exclusion of login requests.  
-Runs an apache tomcat server on port 8041.  

To create an entry - send POST request to `localhost:8041/api/v1/employees/ `  
with a json body containing following fields :   
``` javascript
{
    "firstName" : "sheldon",
    "lastName" : "cooper",
    "emailid" : "sheldon@coop.com"
}
```
To get all employees, send a GET request to
`localhost:8041/api/v1/employees/getAll`
