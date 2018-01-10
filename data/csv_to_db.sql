CREATE DATABASE IF NOT EXISTS TEMP;

USE TEMP;

DROP TABLE IF EXISTS movies_temp;
DROP TABLE IF EXISTS genres_temp;
DROP TABLE IF EXISTS keywords_temp;
DROP TABLE IF EXISTS actors_temp;
DROP TABLE IF EXISTS actresses_temp;
DROP TABLE IF EXISTS editors_temp;
DROP TABLE IF EXISTS producers_temp;
DROP TABLE IF EXISTS writers_temp;
DROP TABLE IF EXISTS mpaa_temp;
DROP TABLE IF EXISTS plot_temp;
DROP TABLE IF EXISTS ratings_temp;


CREATE TABLE movies_temp (
  `Name` VARCHAR(200) NOT NULL
);

CREATE TABLE genres_temp (
  `movie` VARCHAR(200),
  `genre` VARCHAR(200)
);

CREATE TABLE keywords_temp (
  `movie`   VARCHAR(200),
  `keyword` VARCHAR(200)
);

CREATE TABLE actors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(200)
);

CREATE TABLE actresses_temp(
	`name` VARCHAR(200),
    `movie` VARCHAR(200)
);

CREATE TABLE editors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(200)
);

CREATE TABLE producers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(200),
  `role` VARCHAR(50)
);

CREATE TABLE writers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(200)
);

CREATE TABLE mpaa_temp (
  `movie`  VARCHAR(200),
  `mpaa` VARCHAR(200)
);

CREATE TABLE plot_temp (
  `movie`  VARCHAR(200),
  `plot` LONGTEXT
);

CREATE TABLE ratings_temp (
  `movie`  VARCHAR(200),
  `rating` FLOAT,
  `votes` INT(11)
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

LOAD DATA LOCAL INFILE 'data/actresses.csv' INTO TABLE actresses_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/editors.csv' INTO TABLE editors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/producers.csv' INTO TABLE producers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/writers.csv' INTO TABLE writers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/mpaa.csv' INTO TABLE mpaa_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/plot.csv' INTO TABLE plot_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/ratings.csv' INTO TABLE ratings_temp
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