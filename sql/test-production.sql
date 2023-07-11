-- Sign in / sign up process
-- Add user
INSERT INTO Users (userName, pwd) VALUES ('wwan', 'test');
-- Check userName
SELECT * FROM Users where userName LIKE 'wwan';
-- Check password
SELECT pwd FROM Users where userName LIKE 'wwan';

-- Reviews
-- Add review
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 1, 5, 'nice song!', curdate());
-- Show all reviews
SELECT * FROM Reviews;

-- Show all reviews of a user from most recent to lease recent
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 2, 4, 'not bad!', '2023-07-09');
SELECT * FROM Reviews WHERE userName LIKE 'wwan' order by dt DESC;

-- Show all reviews of a song from most recent to lease recent
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 1, 3, 'ok!', '2023-07-08');
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 1, 0, 'bad!', '2023-07-09');
SELECT * FROM Reviews WHERE mID = 1 order by dt DESC;

-- Show top five songs by users' reviews (top five in average ratings)
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 3, 5, 'love it!', curdate());
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 4, 0, 'hate it!', curdate());
INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES ('wwan', 5, 2, 'dislike!', curdate());
SELECT mName, aName, genre FROM Reviews NATURAL JOIN Musics GROUP BY mID ORDER BY AVG(rating) DESC LIMIT 5;

-- The favorite actist, song and genre of a user (based on their rating)
SELECT mName, aName, genre, AVG(rating) as rating FROM Reviews NATURAL JOIN Users NATURAL JOIN Musics WHERE userName LIKE 'wwan' GROUP BY mID HAVING AVG(rating) > 3 ORDER BY AVG(rating) DESC LIMIT 1;

-- Assume user with username testUser1 deletes his account, so his reviews will be deleted as well
DELETE FROM Users WHERE userName LIKE 'wwan';
SELECT * FROM Users;
SELECT * FROM Reviews;

-- Recommend songs by users' reviews
-- If he likes blues
SELECT mName, aName, genre FROM Musics WHERE genre LIKE 'blues' LIMIT 5;
-- If he does not like blues
SELECT mName, aName, genre FROM Musics WHERE genre NOT LIKE 'blues' LIMIT 5;
