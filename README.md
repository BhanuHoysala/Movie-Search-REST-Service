# Movie-Search-REST-Service
RESTful API service to search movies

## For code analysis and review import project as Maven project and build in your favorite IDE

## To unit test the application
###### mvn test

## To bootstrap the application
###### mvn spring-boot:run

## To explore the APIs over Swagger UI visit
###### http://localhost:8080/swagger-ui.html

## Example URL

###### http://localhost:8080/v1/movies/search?fskLabel=FSK16
###### http://localhost:8080/v1/movies/download?fskLabel=FSK16

## Negative case handling

###### http://localhost:8080/v1/movies/download?fskLabel=FSK1
