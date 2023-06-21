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
    mID  INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    mName VARCHAR(50),
    genre VARCHAR(30),
    topic VARCHAR(30),
    lyrics text,
    aID INT REFERENCES Artists(aID)
  ); 

CREATE TABLE Artists
  ( 
    aID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    aName VARCHAR(30)
  ); 

CREATE TABLE Reviews
  ( 
    rID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    mID INT REFERENCES Musics(mID),
    rating DOUBLE CHECK (rating >= 0 AND rating <= 100),
    comment TEXT,
    -- In case users delete their account
    CONSTRAINT FOREIGN KEY (userName) REFERENCES Users(userName) ON DELETE CASCADE
  ); 
  