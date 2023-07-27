-- Sign in / sign up process
-- Add user
INSERT INTO Users (userName, pwd) VALUES ('wwan', 'test');
-- Check userName
SELECT * FROM Users where userName LIKE 'wwan';
-- Check password
SELECT pwd FROM Users where userName LIKE 'wwan';

-- Reviews
-- Add review
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 1, 5, 0, 'nice song!', curdate());
-- Show all reviews
SELECT * FROM Reviews;

-- A user's review is liked by someone else
UPDATE Reviews SET likes = likes + 1 WHERE rID = 1;

-- Show all reviews of a user from most recent to lease recent
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 2, 4, 0, 'not bad!', '2023-07-09');
SELECT * FROM Reviews WHERE userName LIKE 'wwan' order by dt DESC;

-- Show all reviews of a song from most recent to lease recent
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 1, 3, 0, 'ok!', '2023-07-08');
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 1, 1, 0, 'bad!', '2023-07-09');
SELECT * FROM Reviews WHERE mID = 1 order by dt DESC;

-- Show top five songs by users' reviews (top five in average ratings)
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 3, 5, 0, 'love it!', curdate());
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 4, 1, 0, 'hate it!', curdate());
INSERT INTO Reviews (userName, mID, rating, likes, comment, dt) VALUES ('wwan', 5, 2, 0, 'dislike!', curdate());
SELECT mName, aName, genre FROM Reviews NATURAL JOIN Musics GROUP BY mID ORDER BY AVG(rating) DESC LIMIT 5;

-- The favorite actist, song and genre of a user (based on their rating)
SELECT mName, aName, genre, AVG(rating) as rating FROM Reviews NATURAL JOIN Users NATURAL JOIN Musics WHERE userName LIKE 'wwan' GROUP BY mID HAVING AVG(rating) > 3 ORDER BY AVG(rating) DESC LIMIT 1;

-- Assume user with username testUser1 deletes his account, so his reviews will be deleted as well
DELETE FROM Users WHERE userName LIKE 'wwan';
SELECT * FROM Users;
SELECT * FROM Reviews;

-- Recommend songs by users' reviews
-- Get most recent comment of a user
SELECT * FROM Reviews NATURAL JOIN Musics WHERE userName LIKE 'wwan' order by dt DESC;
-- If he likes blues
SELECT mName, aName, genre FROM Musics WHERE genre LIKE 'blues' LIMIT 5;
-- If he does not like blues
SELECT mName, aName, genre FROM Musics WHERE genre NOT LIKE 'blues' LIMIT 5;
