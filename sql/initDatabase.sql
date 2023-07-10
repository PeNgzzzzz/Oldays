CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON *.* TO 'test'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
CREATE DATABASE test;
USE test;

CREATE TABLE Users
  ( 
    userName  VARCHAR(50) NOT NULL PRIMARY KEY, 
    pwd      VARCHAR(50) not NULL
  ); 

CREATE TABLE Musics
  ( 
    mID  INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    mName VARCHAR(200),
    aName VARCHAR(200),
    rDate YEAR,
    genre VARCHAR(30),
    topic VARCHAR(30),
    lyrics text
  ); 

CREATE TABLE Reviews
  ( 
    rID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL,
    mID INT REFERENCES Musics(mID),
    rating INT CHECK (rating >= 0 AND rating <= 5),
    comment TEXT,
    -- In case users delete their account
    CONSTRAINT FOREIGN KEY (userName) REFERENCES Users(userName) ON DELETE CASCADE
  ); 
  