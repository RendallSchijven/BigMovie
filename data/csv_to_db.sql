DROP DATABASE IF EXISTS NickyBot;

CREATE DATABASE NickyBot;

USE NickyBot;

/* Create NickyBot Tables */

-- TODO add correct indexes

CREATE TABLE Countries (
  `Movie_ID`    INT(11)      NOT NULL,
  `Country`     VARCHAR(200) NOT NULL,
  `ReleaseDate` DATE         NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Genres (
  `ID`        INT(11)      NOT NULL,
  `GenreName` VARCHAR(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Keywords (
  `ID`      INT(11)     NOT NULL,
  `Keyword` VARCHAR(200) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Movies (
  `ID`     INT(11)      NOT NULL,
  `Title`  VARCHAR(512) NOT NULL,
  `Plot`   LONGTEXT,
  `Rating` FLOAT       DEFAULT NULL,
  `Votes`  INT(11)     DEFAULT NULL,
  `MPAA`   VARCHAR(20) DEFAULT NULL,
  `Budget` INT(11)     DEFAULT NULL,
  `Gross`  INT(11)     DEFAULT NULL,
  `duration`  INT(11)     DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Movies_Genres (
  `Movie_ID` INT(11) NOT NULL,
  `Genre_ID` INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Movies_Keywords (
  `Movie_ID`   INT(11) NOT NULL,
  `Keyword_ID` INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Persons (
  `ID`       INT(11)      NOT NULL,
  `Name`     VARCHAR(200) NOT NULL,
  `BirthDay` DATE        DEFAULT NULL,
  `Sex`      VARCHAR(10) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE Persons_Movies (
  `Movie_ID`  INT(11) NOT NULL,
  `Person_ID` INT(11) NOT NULL,
  `Role`   VARCHAR(100) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/* Add indexes, PK's and FK's */

ALTER TABLE Countries
  ADD PRIMARY KEY( `Movie_ID`, `Country`, `ReleaseDate`);

ALTER TABLE Genres
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `GenreName` (`GenreName`);

ALTER TABLE Keywords
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `Keyword` (`Keyword`);

ALTER TABLE Movies
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `Title` (`Title`);

ALTER TABLE Movies_Genres
  ADD KEY `Movie_ID` (`Movie_ID`, `Genre_ID`);

ALTER TABLE Movies_Keywords
  ADD KEY `Movie_ID` (`Movie_ID`, `Keyword_ID`);

ALTER TABLE Persons
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `Name` (`Name`);

ALTER TABLE Persons_Movies
  ADD KEY `Movie_ID` (`Movie_ID`, `Person_ID`, `Role`);

/* Set AUTO_INCREMENT */

ALTER TABLE Genres
  MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Keywords
  MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Movies
  MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Persons
  MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

/* Create temporary tables TODO make these tables TEMPORARY!! */

CREATE TABLE movies_temp (
  `Name` VARCHAR(512) NOT NULL
);

CREATE TABLE genres_temp (
  `movie` VARCHAR(512),
  `genre` VARCHAR(200)
);

CREATE TABLE keywords_temp (
  `movie`   VARCHAR(512),
  `keyword` VARCHAR(200)
);

CREATE TABLE actors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(2048)
);

CREATE TABLE actresses_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(2048)
);

CREATE TABLE editors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512)
);

CREATE TABLE producers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512),
  `role` VARCHAR(2048)
);

CREATE TABLE writers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512)
);

CREATE TABLE mpaa_temp (
  `movie` VARCHAR(512),
  `mpaa`  VARCHAR(200)
);

CREATE TABLE plot_temp (
  `movie` VARCHAR(512),
  `plot`  LONGTEXT
);

CREATE TABLE ratings_temp (
  `movie`  VARCHAR(512),
  `rating` FLOAT,
  `votes`  INT(11)
);

CREATE TABLE runningTimes_temp (
  `movie`  VARCHAR(512),
  `minutes`  INT(11)
);

CREATE TABLE releaseDates_temp (
  `movie`  VARCHAR(512),
  `country`  VARCHAR(64),
  `date`  DATE
);

CREATE TABLE biographies_temp (
  `actor`  VARCHAR(300),
  `birthdate`  DATE
);

/* Load CSV files on the server*/

LOAD DATA INFILE '/var/lib/mysql-files/movies.csv' INTO TABLE movies_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/genres.csv' INTO TABLE genres_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/keywords.csv' INTO TABLE keywords_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/actors.csv' INTO TABLE actors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/actresses.csv' INTO TABLE actresses_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/editors.csv' INTO TABLE editors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/producers.csv' INTO TABLE producers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/writers.csv' INTO TABLE writers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/mpaa.csv' INTO TABLE mpaa_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/plot.csv' INTO TABLE plot_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/ratings.csv' INTO TABLE ratings_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/running-times.csv' INTO TABLE runningTimes_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/release-dates.csv' INTO TABLE releaseDates_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/biographies.csv' INTO TABLE biographies_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';


/* Add indexes to temp tables */

ALTER TABLE `movies_temp`
  ADD KEY `Name` (`Name`);

ALTER TABLE `mpaa_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `plot_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `genres_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `keywords_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `actors_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `actresses_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `editors_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `producers_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `writers_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `runningTimes_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `releaseDates_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `biographies_temp`
  ADD KEY `movie` (`actor`);

ALTER TABLE `ratings_temp`
  ADD KEY `movie` (`movie`);

/* Truncate Tables before inserting data */

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE `Countries`;
TRUNCATE `Genres`;
TRUNCATE `Keywords`;
TRUNCATE `Movies`;
TRUNCATE `Movies_Genres`;
TRUNCATE `Movies_Keywords`;
TRUNCATE `Persons`;
TRUNCATE `Persons_Movies`;
SET FOREIGN_KEY_CHECKS = 1;

/* import temporary data to NickyBot */

INSERT INTO Genres (GenreName)
  SELECT DISTINCT genre
  FROM genres_temp;

INSERT INTO Keywords (Keyword)
  SELECT DISTINCT keyword
  FROM keywords_temp;

/* Fill Movies */

INSERT INTO Movies (Title, Plot, Rating, Votes, MPAA, Budget, Gross, Duration)
  (SELECT
     movies_temp.name,
     plot_temp.plot,
     ratings_temp.rating,
     ratings_temp.votes,
     mpaa_temp.mpaa,
     NULL,
     NULL,
     runningTimes_temp.minutes
   FROM movies_temp
     LEFT JOIN plot_temp ON plot_temp.movie = movies_temp.name
     LEFT JOIN ratings_temp ON ratings_temp.movie = movies_temp.name
     LEFT JOIN mpaa_temp ON mpaa_temp.movie = movies_temp.name
     LEFT JOIN runningTimes_temp ON runningTimes_temp.movie = movies_temp.name);

/* Fill Persons */

INSERT INTO Persons(Name, Sex)
  SELECT DISTINCT name,sex FROM (
                                  SELECT name,"Male" AS sex FROM actors_temp UNION
                                  SELECT name,"Female" AS sex FROM actresses_temp UNION
                                  SELECT name,null AS sex FROM producers_temp UNION
                                  SELECT name,null AS sex FROM writers_temp
                                ) AS PersonsTemp;

/* Fill Countries TODO not working, column Movie_ID cannot be NULL */

INSERT INTO Countries (Movie_ID, Country, ReleaseDate)
  SELECT DISTINCT ID, country, date FROM releaseDates_temp
    LEFT JOIN Movies ON movie = Movies.Title;

/* Fill Movies_Genres */

INSERT INTO Movies_Genres (Movie_ID, Genre_ID)
  SELECT Movies.ID, x.ID FROM Movies
    RIGHT JOIN (SELECT ID, movie FROM Genres
      INNER JOIN genres_temp ON genres_temp.genre = Genres.GenreName) AS x
      ON x.movie = Movies.Title
  WHERE Movies.ID IS NOT NULL AND x.ID IS NOT NULL;

/* Fill Movies_Keywords */

INSERT INTO Movies_Keywords (Movie_ID, Keyword_ID)
  SELECT Movies.ID, x.ID FROM Movies
    RIGHT JOIN
    (SELECT ID, movie FROM Keywords
      INNER JOIN keywords_temp ON keywords_temp.keyword = Keywords.Keyword) AS x
      ON x.movie = Movies.Title
  WHERE Movies.ID IS NOT NULL AND x.ID IS NOT NULL;

/* Fill Persons_Movies */

INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT p.ID AS Person_ID, m.ID AS Movie_ID, "producer" as Role FROM producers_temp AS pt
    JOIN Persons p ON p.Name = pt.name
    JOIN Movies m ON m.Title = pt.movie;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT p.ID AS Person_ID, m.ID AS Movie_ID, "writer" AS Role FROM writers_temp AS wt
    JOIN Persons p ON p.Name = wt.name
    JOIN Movies m ON m.Title = wt.movie;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT p.ID AS Person_ID, m.ID AS Movie_ID, "editor" AS Role FROM editors_temp AS et
    JOIN Persons p ON p.Name = et.name
    JOIN Movies m ON m.Title = et.movie;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT p.ID AS Person_ID, m.ID AS Movie_ID, "actor" AS Role FROM actors_temp AS at
    JOIN Persons p ON p.Name = at.name
    JOIN Movies m ON m.Title = at.movie;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT p.ID AS Person_ID, m.ID AS Movie_ID, "actor" AS Role FROM actresses_temp AS ast
    JOIN Persons p ON p.Name = ast.name
    JOIN Movies m ON m.Title = ast.movie;