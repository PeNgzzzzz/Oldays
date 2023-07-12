# Description
This is a database-driven application using the following frameworks.

FrontEnd: JavaScript + HTML + CSS

BackEnd: Java + Spring Boot

Database: MySQL

We plan to create an app for those who love music to recommend, search and rate music, the data is from Saurabh Shahane in Kaggle.
https://www.kaggle.com/datasets/saurabhshahane/music-dataset-1950-to-2019.

# How to Start

Be sure to run the backend part on Spring Boot Framework first.

Backend: We use the Oracle OpenJDK Version 20.0.1 and Gradle build system to compile, build and run the Java code.

Frontend: Start from and run the welcome page and log in / sign up.

Remember to install [Connector/J](https://dev.mysql.com/downloads/connector/j/) driver to connect MySQL on Java.

## Setting Up the Database
1. [Install MySQL Community Server](https://dev.mysql.com/downloads/mysql/).
2. [Install MySQL MySQL Workbench](https://dev.mysql.com/downloads/workbench/).
3. Create a new connection named **test** on localhost in MySQL Workbench.
4. Run the query **sql/initDatabase.sql** in the Workbench.
5. Now you have the database established.

## Loading the production dataset into the database
1. Make sure enable this in the MySQL: **mysql --local_infile=1 -u root -ppassword DB_name**.
2. Run the query **sql/loadProductionDB.sql** in the Workbench.

## Backend
1. Open the backend part on the IDE (e.g. IDEA).
2. Compile and build the spring boot code.
3. Run the main program.

## Frontend
1. Open the frontend part on the IDE (e.g. IDEA).
2. Run the welcome.html.

# Current Features
1. Search for a song.
2. Log in & Sign up system.
3. Show all reviews of a user/song from most recent to least recent.
4. Show all reviews of a song from most recent to least recent.
5. Top 5 songs based on the average reviews by all users.
6. Users can review any song in the database by giving a score (0 out of 5) and some comment.
7. Determine users' favorite actist, song and genre based on their reviews.
8. If a user deletes his account, his reviews will be cleared as well.
9. Personalized recommendations.
