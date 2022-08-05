# class-master

Spring Boot project.

To run the application, you need Maven + Java 18 installed and run the following command (inside the class-master folder):

`mvn spring-boot:run`

To run JUnit tests, run the following command:

`mvn test`

Some notes on my approach:
- Opted to use an in-memory array to store classes. 
- Assumed that booked members couldn't be repeated for a given class, so I used a set to store them. 
- The API allows scheduling/booking classes in the past.
- The class endpoint requires a name, capacity and startDate (if not present, returns a 400 BAD REQUEST). 
- The endDate is not a mandatory parameter for creating classes. If not present, it will default to the startDate (which means it will only create one class).
- The class endpoint ensures that the endDate is after or the same as the startDate (if not, returns a 400 BAD REQUEST).
- The booking endpoint only saves the booking if a class exists for the given day (if not, returns a 404 NOT FOUND).
- When booking a class, the date and member name is mandatory (if not, returns a 400 BAD REQUEST).



Sample requests for each endpoint:

/classes

`curl --location --request POST 'localhost:8080/classes' \
--header 'Content-Type: application/json' \
--data-raw '{
"startDate":"2022-09-09",
"endDate":"2022-09-22",
"className":"Test class 2",
"capacity":10
}'`


/bookings

`curl --location --request POST 'localhost:8080/bookings' \
--header 'Content-Type: application/json' \
--data-raw '{
"classDate":"2022-09-12",
"memberName":"Rita"
}'`