This application will search Tickets, Users and Organizations.

Assumptions:
A User can belong to an Organization.
A Ticket can belong to an Organization.
A Ticket has a submitter which  is a User.
A Ticket has an assignee which is a User.
Date is formatted as yyyy-MM-dd'T'HH:mm:ss X
String "true" and "false" are the valid values for boolean fields.

Limitations:
Search will look for the full text entered by user.

-------------------------------------------
What you'll need:
* Java 11
You can download Open JDK 11 here: https://jdk.java.net/java-se-ri/11

---- BUILD AND RUN ON THE COMMAND LINE ----

* cd into the project directory:
    cd supportsearch

* Build project:
    gradlew clean build
Note: Build will fail if one of the unit tests fails

* Run application by running the jar file:
    java -jar build\libs\supportsearch-0.1.0.jar

* To run the unit tests only
    gradlew test


---- RUN IN AN IDE ----

If you want to run in an IDE, such as IntelliJ, you should
be able to create a new project from existing resources.


