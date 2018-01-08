CREATE DATABASE IF NOT EXISTS NickyBot;

use NickyBot;

DROP TABLE IF EXISTS tempMovieCSVTable;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS keywords;


CREATE TABLE tempMovieCSVTable (
  `Name` varchar(200) NOT NULL,
  `ReleaseDate` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TEMPORARY TABLE genres(
	`movie` varchar(50),
    `genre` varchar(50)
);

CREATE TEMPORARY TABLE keywords(
	`movie` varchar(50),
    `keyword` varchar(50)
);

LOAD DATA LOCAL INFILE 'data/MovieTest.csv' INTO TABLE tempMovieCSVTable
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'data/genres.csv' INTO TABLE genres
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'data/keywords.csv' INTO TABLE keywords
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

SELECT * FROM tempMovieCSVTable;