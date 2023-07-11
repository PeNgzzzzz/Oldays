-- Assume addTestMusics.sql and addTestUsers.sql have been run

-- Assume user with userName testUser1 is rating on music with mID 1
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('testUser1', 1, 5, 'nice song!', curdate());
SELECT * FROM Reviews;

-- Assume user wants to see all musics in the database
SELECT mName FROM Musics;

-- Assume user with username testUser1 deletes his account, so his reviews will be deleted as well
DELETE FROM Users WHERE userName LIKE 'testUser1';
SELECT * FROM Users;
SELECT * FROM Reviews;

-- Assume user wants to search for a song that contains hours in lyrics
SELECT mName FROM Musics where lyrics LIKE '%hours%'
