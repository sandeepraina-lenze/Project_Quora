# Quora REST API #
 ## Modules: ##
  * Admin Controller
  * Answer Controller
  * Common Controller
  * Question Controller
  * User Controller

 ### To run the file on your local machine, follow the given steps: ###
 
 >
   1. Make a databse name ***quora*** in you postgresSQL server\.
   1. Modify the database **user** and **password** in following files \: 
        * quora-api\src\main\resources\application.yaml
        * quora-db\src\main\resources\config\localhost.properties
   1. Build the project in the main directory of the project by running the command 
 ``mvn clean install``

   1. In order to activate the profile setup 
     * move to quora-db folder using ``cd quora-db`` command in the terminal 
     run ``mvn clean install -Psetup`` command to activate the profile setup. 
>
