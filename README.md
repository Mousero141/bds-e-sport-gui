# BPC BDS e-Sport-gui
## Info
This project is from subject BPC-BDS (Database security) from BUT in Brno. Is written in Java.
ERD model can be found here https://github.com/Mousero141/bds-db-design. It's a database for League of Legends e-Sport.

## Important
To run this project from git bash is it essential to download Java JDK and Maven and configure them in the system environment variables.

## Setup DB
- start pgAdmin
- create new DB named "test"
- open "Query Tool" to create new login role (use this txt https://github.com/Mousero141/bds-e-sport-gui/blob/main/Create_user.txt)
- after that you can restore my db using this backup file https://github.com/Mousero141/bds-e-sport-gui/blob/main/BDS-Project3_1
- at the end you have to grant privileges to user "ZACHRANCE", for that open "Query Tool" and use this txt https://github.com/Mousero141/bds-e-sport-gui/blob/main/Grant_privileges.txt

## Startup
Before start project is necessary to build this project with Maven. You have to change directory to this repo in git bash. 
After that you can type

    mvn clean install

If the build was successful you can continue to start
    
    java -jar target/bds-e-sport-gui-1.0.0.jar

    email: hrnekdavid@seznam.cz | password: batman 


