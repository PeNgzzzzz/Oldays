set global local_infile=true;
LOAD DATA LOCAL INFILE '/Users/wilson/Documents/GitHub/CS-348/sql/data.csv'
INTO TABLE Musics
FIELDS TERMINATED BY ','
IGNORE 1 ROWS
(@col1, @col2, @col3, @col4, @col5, @col6, @col7, @col8, @col9, @col10, @col11, @col12, @col13, @col14, @col15, @col16, @col17, @col18, @col19, @col20, @col21, @col22, @col23, @col24, @col25, @col26, @col27, @col28, @col29, @col30, @col31)
SET aName = @col2, mName = @col3, rDate = @col4, genre = @col5, lyrics = @col6, topic = @col30;