CREATE DATABASE IF NOT EXISTS TEMP;

use TEMP;

DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS keywords;
DROP TABLE IF EXISTS actors;
DROP TABLE IF EXISTS actresses;


CREATE TABLE movies (
  `Name` varchar(200) NOT NULL
);

CREATE TABLE genres(
	`movie` varchar(200),
    `genre` varchar(200)
);

CREATE TABLE keywords(
	`movie` varchar(200),
    `keyword` varchar(200)
);

CREATE TABLE actors(
	`name` varchar(200),
    `movie` varchar(200)
);

CREATE TABLE actresses(
	`name` varchar(200),
    `movie` varchar(200)
);



LOAD DATA LOCAL INFILE 'data/movies.csv' INTO TABLE movies
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/genres.csv' INTO TABLE genres
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/keywords.csv' INTO TABLE keywords
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/actors.csv' INTO TABLE actors
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE 'data/actresses.csv' INTO TABLE actresses
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';
