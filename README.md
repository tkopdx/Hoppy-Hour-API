# Welcome to the Hoppy Hour API!

## This is the API for the Hoppy Hour app.

To run this on your own system, you'll need [Maven](https://maven.apache.org/) and [MySQL](https://www.mysql.com/downloads/). You will also need to create the database "hoppy_hour_dev" for development purposes. You will need to create a user with username 'devuser' and password 'badpassword' that is granted all privileges on the "hoppy_hour_dev" database. A database loader included in this repo will load up your database with some awesome fake data. If you ever need to change some database configurations, make changes to the application.properties file AND the hibernate.cfg.xml file as these both need to know the database source details.

## Database structure
The Hoppy Hour API uses Spring annotations to build the tables and relationships. For every table added, you need to also tell the SessionFactory that you built a new table by adding a .addAnnotatedClass(MyNewClass.class) to its configuration.

![eer diagram of the database](https://github.com/tkopdx/Hoppy-Hour-API/blob/main/er_diagram_3-18-22.png?raw=true)

## REST API

This API uses Spring Data REST. The docs for this Spring project can be found [here](https://spring.io/projects/spring-data-rest). Spring Data REST automatically creates endpoints for CRUD operations for all tables. To hit these endpoints, start up the main method in the HoppyHourApiApplication class and wait for the database to populate. Then, perform your desired request on a url following these rules:
    
1. http://localhost:8080 is default
2. The base path is /api
3. Each table is available at /tablename + s. For example, users can be found at http://localhost:8080/api/users.
4. To work with specific rows, add the row id, like this: http://localhost:8080/api/users/1.
5. You can also work with relationships. For more on that, check out [this](https://www.baeldung.com/spring-data-rest-relationships).

## A note on measurement systems
All liquid volumes, weights, etc. are in metric unless otherwise noted. All temperatures are in Celsius unless noted otherwise.

## That's all for now!

## Next moves:

See [here](https://github.com/NoahSylwester/Hoppy-Hour/projects) for a full list of Hoppy Hour to-dos.

- [X] add ingredient tables
- [X] add ingredient_detail tables
- [X] add rating table
- [X] add comment table
- [X] add reply table
- [X] add recipe_event table
- [X] add place table
- [X] Add Spring Security to manage user passwords, roles, and secure data
- [X] Email verification
- [X] Refresh tokens
- [ ] Write rules for CRUD operations in tables
- [ ] Mess around and make tweaks
- [ ] Add constant hops
- [ ] Add constant malts
- [ ] Add constant yeasts
- [ ] Add constant other ingredients
- [ ] Add constant brands