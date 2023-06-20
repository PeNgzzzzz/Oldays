# Description
This is a database-driven application using the following frameworks.

FrontEnd: JavaScript(React) + HTML + CSS

BackEnd: Java

Database: MySQL

We plan to create an app for those who love music to recommend, search and rate music, the data is from Saurabh Shahane in Kaggle.
https://www.kaggle.com/datasets/saurabhshahane/music-dataset-1950-to-2019.

We have provided simple "hello world" code on both frontend (React) and backend (Java).


# How to Start

Frontend: You may find the [readme file](front/README.md) under front folder helpful to run the react component.

Backend: We use the Oracle OpenJDK Version 1.8.0_331 and IntelliJ build system to compile, build and run the Java code.

## Setting Up the Database
1. [Install MySQL Community Server](https://dev.mysql.com/downloads/mysql/).
2. [Install MySQL MySQL Workbench](https://dev.mysql.com/downloads/workbench/).
3. Create a new connection named **test** on localhost in MySQL Workbench.
4. Run the query **sql/initDatabase.sql** in the Workbench.
5. Now you have the sample database established.

# Current Features
TBD

# To-do Lists
1. Sample SQL code to initialize the database (reating tables, constraints, stored procedures and triggers).
2. Simple test input and output (test-sample.sql, test-sample.out).
3. Search songs with input lyrics.
4. Rate songs.
5. Login system.
6. Provide recommendation songs for users.
