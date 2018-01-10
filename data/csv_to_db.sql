CREATE DATABASE IF NOT EXISTS TEMP;

use NickyBot;

DROP TABLE IF EXISTS movies_temp;
DROP TABLE IF EXISTS genres_temp;
DROP TABLE IF EXISTS keywords_temp;
DROP TABLE IF EXISTS actors_temp;
DROP TABLE IF EXISTS actresses_temp;


CREATE TABLE movies_temp (
  `Name` varchar(200) NOT NULL
);

CREATE TABLE genres_temp (
	`movie` varchar(200),
    `genre` varchar(200)
);

CREATE TABLE keywords_temp (
	`movie` varchar(200),
    `keyword` varchar(200)
);

CREATE TABLE actors_temp (
	`name` varchar(200),
    `movie` varchar(200)
);

CREATE TABLE actresses_temp(
	`name` varchar(200),
    `movie` varchar(200)
);



LOAD DATA LOCAL INFILE '/home/zroya/Documents/School/Jaar 2/Periode 2/Project Big Data/BigMovie/data/movies.csv' INTO TABLE movies_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/zroya/Documents/School/Jaar 2/Periode 2/Project Big Data/BigMovie/data/genres.csv' INTO TABLE genres_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/zroya/Documents/School/Jaar 2/Periode 2/Project Big Data/BigMovie/data/keywords.csv' INTO TABLE keywords_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/zroya/Documents/School/Jaar 2/Periode 2/Project Big Data/BigMovie/data/actors.csv' INTO TABLE actors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE '/home/zroya/Documents/School/Jaar 2/Periode 2/Project Big Data/BigMovie/data/actresses.csv' INTO TABLE actresses_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

INSERT INTO Genres (GenreName)
SELECT DISTINCT genre
FROM genres_temp;

INSERT INTO Keywords (Keyword)
SELECT DISTINCT keyword
FROM keywords_temp;

INSERT INTO Persons (Name, Sex)
SELECT DISTINCT name, 'M'
FROM actors_temp;

INSERT INTO Persons (Name, Sex)
SELECT DISTINCT name, 'F'
FROM actresses_temp;

INSERT INTO Roles  (Role) VALUES
(actors);