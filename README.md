# Description
This is a database-driven application using the following frameworks.

FrontEnd: JavaScript(React) + HTML + CSS

BackEnd: Java

Database: MySQL

We plan to create an app for those who love music to recommend, search and rate music, the data is from Saurabh Shahane in Kaggle.
https://www.kaggle.com/datasets/saurabhshahane/music-dataset-1950-to-2019.

# How to Start

Frontend: You may find the [readme file](front/README.md) under front folder helpful to run the react component.

Backend: We use the Oracle OpenJDK Version 1.8.0_331 and IntelliJ build system to compile, build and run the Java code.

Remember to install [Connector/J](https://dev.mysql.com/downloads/connector/j/) driver to connect MySQL on Java.

## Setting Up the Database
1. [Install MySQL Community Server](https://dev.mysql.com/downloads/mysql/).
2. [Install MySQL MySQL Workbench](https://dev.mysql.com/downloads/workbench/).
3. Create a new connection named **test** on localhost in MySQL Workbench.
4. Run the query **sql/initDatabase.sql** in the Workbench.
5. Now you have the sample database established.

## Backend
1. Run the program.
2. It requires input from the command-line.
3. If you type show, it outputs all musics in the database.
4. If you type search and some lyrics, it outputs the music name if there exists such a song. Otherwise, it prints no music found.
5. If you type rate with mID, score and comment, it records your review in the database.

# Current Features
1. Search for a song by some lyrics.
2. Show all musics in the database.
3. Rate a song with a score (out of 100) and a comment.
4. If a user deletes his account, his reviews will be cleared as well.

# To-do Lists
1. Sample SQL code to initialize the database (reating tables, constraints, stored procedures and triggers). **DONE** (**sql/initDatabase.sql**)
2. Simple test input and output (test-sample.sql, test-sample.out). **DONE** (**sql/test-sample.sql**, **sql/test-sample.out**)
3. Search songs with input lyrics. **DONE** (**Backend**)
4. Rate songs. **DONE** (**Backend**)
5. Login system.
6. Provide recommendation songs for users.
