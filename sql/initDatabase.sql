CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON *.* TO 'test'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
CREATE DATABASE test;
USE test;

CREATE TABLE Users
  ( 
    userName  VARCHAR(50) NOT NULL PRIMARY KEY, 
    fName    VARCHAR(30), 
    lName   VARCHAR(30), 
    email    VARCHAR(50),
    pwd      VARCHAR(50) not NULL
  ); 

CREATE TABLE Musics
  ( 
    mID  INT NOT NULL PRIMARY KEY, 
    mName VARCHAR(50),
    genre VARCHAR(30),
    topic VARCHAR(30),
    lyrics text,
    aID INT REFERENCES Artists
  ); 

CREATE TABLE Artists
  ( 
    aID INT NOT NULL PRIMARY KEY,
    aName VARCHAR(30)
  ); 

CREATE TABLE Reviews
  ( 
    userName VARCHAR(50) REFERENCES Users,
    mID INT REFERENCES Musics,
    rating FLOAT(10, 5),
    comment text,
    PRIMARY KEY(userName, mID)
  ); 