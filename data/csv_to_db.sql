use NickyBot;

DROP TABLE IF EXISTS tempMovieCSVTable;

CREATE TABLE tempMovieCSVTable (
  `Name` varchar(200) NOT NULL,
  `ReleaseDate` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOAD DATA LOCAL INFILE 'data/MovieTest.csv' INTO TABLE tempMovieCSVTable
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

SELECT * FROM tempMovieCSVTable;