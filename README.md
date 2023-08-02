# Project Name

Diplom_2

# Description

Educational project in which API testing of https://stellarburgers.nomoreparties.site/ is carried out. 
Documentation: https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf

# Technologies

Java 11, maven 3.7.1, JUnit 4.13.2, allure 2.23.0, rest-assured 5.3.1, gson 2.10.1

# Clean up and run tests

`mvn clean test`

# Allure report

`mvn allure:serve`

# Test Cases

User creation:

- creation a the unique user;
- creation of already registered user;
- creation of a user with the one of required fields blank;

User login:

- login with an existing user;
- login with incorrect username and password;

Changing user data:

- with authorization;
- without authorization;

Creating an order:

- with authorization;
- without authorization;
- with the ingredients;
- without ingredients;
- with the wrong ingredient hash;

Getting orders from a specific user:

- authorized user;
- unauthorized user.