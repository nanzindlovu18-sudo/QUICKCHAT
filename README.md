QUICKCHAT

Project Overview

QUICKCHAT is a Java application developed to demonstrate user registration, login functionality, and message management. The project uses object-oriented programming principles and input validation.

Features

* User registration and login validation.
* Username and password validation.
* Message creation and validation.
* Message ID, recipient, message text, hash, and status management.
* Options to send, disregard, or store messages.
* Unit testing using JUnit 4.
* Automated testing using GitHub Actions.

Technologies Used

* Java
* JUnit 4
* GitHub
* GitHub Actions

Project Files

* Main.java — application entry point.
* Login.java — login and user validation.
* Message.java — message management.
* logintest.java — login unit tests.
* MessageTest.java — message unit tests.
* lib/ — JUnit testing libraries.
* .github/workflows/java-tests.yml — automated test workflow.

Running the Tests on Windows

Open the terminal in the project folder and compile the login tests:

javac -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" Login.java logintest.java

Run the login tests:

java -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore logintest

Compile the message tests:

javac -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" Message.java MessageTest.java

Run the message tests:

java -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore MessageTest

Automated Testing

GitHub Actions is configured to compile the project and execute the login and message unit tests when changes are pushed to the repository.

Conclusion

This project demonstrates Java programming, object-oriented design, validation, unit testing, and version control using GitHub.
