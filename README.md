#Spring cloud and react exercise

##How to run
1. Start config-server `ConfigServerApplication`.
   * The config-server application needs a git repository.
   * This git repository should contain the config files for all the services.
   * The config-server `application.properties` should have a pointer to the git repository location.
   * The git repository with config files can be cloned from: TODO
2. Start eureka-server `EurekaServerApplication`.
3. Start each x-service `XServiceApplication`.

###Notes:
* Wait to start `EurekaServerApplication` until `ConfigServerApplication` is done.
* Wait to start `XServiceApplication` until `EurekaServerApplication` is done.

##Services
###student-service
Method | URL | Extra | Effect
| --- | --- | --- | ---
GET | '/student' | | returns all students without address
GET | '/student/{email}' | ?includeAddress=true/false *default=false*| return matching student with address
POST | '/student' | | create a student