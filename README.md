# Welcome to the Hoppy Hour API!

## What is Hoppy Hour API?

Hoppy Hour API is the backend server and database logic for the Hoppy Hour app, a homebrew recipe database and brewing scheduler.

## This is the API for the Hoppy Hour app.

To run this on your own system, you'll need [Maven](https://maven.apache.org/) and [MySQL](https://www.mysql.com/downloads/). You will also need to create the database "hoppy_hour_dev" for development purposes. You will need to create a user with username 'devuser' and password 'badpassword' that is granted all privileges on the "hoppy_hour_dev" database. A database loader included in this repo will load up your database with some awesome fake data. Alternatively, you can import the testdb sql file found in the root folder. If you ever need to change some database configurations, make changes to the application.properties file AND the hibernate.cfg.xml file as these both need to know the database source details.

## Database structure
The Hoppy Hour API uses Spring annotations to build the tables and relationships. The three main tables are the user, recipe, and ingredient tables. The other tables hold secondary information or act as join tables. Please refer to the diagram below to see a detailed view of the database.

For every table added, you need to also tell the SessionFactory that you built a new table by adding a .addAnnotatedClass(MyNewClass.class) to its configuration.

![eer diagram of the database](https://github.com/tkopdx/Hoppy-Hour-API/blob/main/er_diagram_4-19-22.png?raw=true)

## REST API

This API uses Spring Data REST and Spring DATA. The docs for this Spring project can be found [here](https://spring.io/projects/spring-data-rest). Spring Data REST automatically creates endpoints for CRUD operations for all tables, but for Hoppy Hour most of these have custom methods. Please refer to the [endpoints list](https://docs.google.com/spreadsheets/d/1tg0WksdvEkDg5od55iVhsS6yFg1IsYVA-5UrEeND2Vw/edit?usp=sharing) for more information.  To hit these endpoints, start up the main method in the HoppyHourApiApplication class and wait for the database to populate. There is also a test database available at https://hoppy-hour-api-test.herokuapp.com/api. It runs on a free-tier database, so it can only handle up to 10 connections at a time and holds only a few megs of data. To perform a desired request, follow these rules:
    
1. To use the remote test database, the base url is https://hoppy-hour-api-test.herokuapp.com/api.  http://localhost:8080 is default for local development.
2. The base path is /api.
3. Each table is available at /tablename + s. For example, users can be found at http://localhost:8080/api/users.
4. To work with specific rows, add the entity id, like this: http://localhost:8080/api/users/1.
5. Custom methods will handle any side effects related to CRUD operations, such as linking related entities in the database. Please check the controllers for more on this.
6. There are endpoints for custom methods that go beyond simple CRUD operations. Again, check out the endpoints list and controllers if you're looking for a certain kind of method.
7. Some methods require parameters in the request body as JSON and some require parameters in the path (i.e., /api/recipes?name=foo). Requests that require a request body are meant for use in the Hoppy Hour app only while those with only path parameters are open to all.

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
- [X] Add constant hops
- [X] Add constant malts
- [X] Add constant yeasts
- [X] Add constant places
- [ ] Add constant other ingredients
- [ ] Add constant brands