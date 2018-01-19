DROP DATABASE IF EXISTS NickyBot;

CREATE DATABASE NickyBot
  DEFAULT CHARACTER SET latin1;

USE NickyBot;

/* Create NickyBot Tables */

-- TODO add correct indexes

CREATE TABLE Countries (
  `ID` int(11) NOT NULL,
  `Country` varchar(128) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Genres (
  `ID` int(11) NOT NULL,
  `GenreName` varchar(200) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Keywords (
  `ID` int(11) NOT NULL,
  `Keyword` varchar(200) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Movies (
  `ID` int(11) NOT NULL,
  `Title` varchar(512) NOT NULL,
  `Plot` longtext,
  `ReleaseYear` int(4) DEFAULT NULL,
  `Rating` float DEFAULT NULL,
  `Votes` int(11) DEFAULT NULL,
  `MPAA` varchar(20) DEFAULT NULL,
  `Currency` varchar(16) DEFAULT NULL,
  `Budget` BIGINT DEFAULT NULL,
  `Duration` int(11) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE Movies_Countries (
  `Movie_ID` int(11) NOT NULL,
  `Country_ID` int(11) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Movies_Genres (
  `Movie_ID` int(11) NOT NULL,
  `Genre_ID` int(11) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Movies_Keywords (
  `Movie_ID` int(11) NOT NULL,
  `Keyword_ID` int(11) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Persons (
  `ID` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `BirthDay` date DEFAULT NULL,
  `DeathDay` date DEFAULT NULL,
  `Sex` varchar(10) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE Persons_Movies (
  `Movie_ID` int(11) NOT NULL,
  `Person_ID` int(11) NOT NULL,
  `Role` varchar(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE ReleaseDates (
  `Movie_ID` int(11) NOT NULL,
  `Country_ID` int(11) NOT NULL,
  `ReleaseDate` date NOT NULL
) ENGINE=InnoDB;

/* Add indexes, PK's and FK's */

ALTER TABLE Countries
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Country` (`Country`) USING BTREE;

ALTER TABLE Genres
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `GenreName` (`GenreName`) USING BTREE;

ALTER TABLE Keywords
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Keyword` (`Keyword`);

ALTER TABLE Movies
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Title` (`Title`),
  ADD KEY `Title` (`ReleaseYear`),
  ADD KEY `Title` (`Rating`),
  ADD KEY `Title` (`Votes`),
  ADD KEY `Title` (`MPAA`),
  ADD KEY `Title` (`Budget`),
  ADD KEY `Title` (`Duration`);

ALTER TABLE Movies_Countries
  ADD PRIMARY KEY (`Movie_ID`,`Country_ID`),
  ADD KEY `Country_ID` (`Country_ID`);

ALTER TABLE Movies_Genres
  ADD KEY `Movie_ID` (`Movie_ID`,`Genre_ID`),
  ADD KEY `Movies_Genres-GenreID` (`Genre_ID`);

ALTER TABLE Movies_Keywords
  ADD KEY `Movie_ID` (`Movie_ID`,`Keyword_ID`),
  ADD KEY `Movies_Keywords-KeywordID` (`Keyword_ID`);

ALTER TABLE Persons
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Name` (`Name`) USING BTREE,
  ADD KEY `Persons` (`BirthDay`, `DeathDay`, `Sex`);

ALTER TABLE Persons_Movies
  ADD UNIQUE KEY `Movie_ID` (`Role`,`Movie_ID`,`Person_ID`) USING BTREE ,
  ADD KEY `Persons_Movies_PersonID` (`Person_ID`);

ALTER TABLE ReleaseDates
  ADD PRIMARY KEY (`Movie_ID`,`Country_ID`,`ReleaseDate`),
  ADD KEY `CountryID` (`Country_ID`);

/* Set AUTO_INCREMENT */

ALTER TABLE Countries
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Genres
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Keywords
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Movies
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE Persons
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

/* Add foreign keys */

ALTER TABLE Movies_Countries
  ADD CONSTRAINT `Movies_Countries_ibfk_1` FOREIGN KEY (`Movie_ID`) REFERENCES Movies (`ID`),
  ADD CONSTRAINT `Movies_Countries_ibfk_2` FOREIGN KEY (`Country_ID`) REFERENCES Countries (`ID`);

ALTER TABLE Movies_Genres
  ADD CONSTRAINT `Movies_Genres-GenreID` FOREIGN KEY (`Genre_ID`) REFERENCES Genres (`ID`),
  ADD CONSTRAINT `Movies_Genres-MovieID` FOREIGN KEY (`Movie_ID`) REFERENCES Movies (`ID`);

ALTER TABLE Movies_Keywords
  ADD CONSTRAINT `Movies_Keywords-KeywordID` FOREIGN KEY (`Keyword_ID`) REFERENCES Keywords (`ID`),
  ADD CONSTRAINT `Movies_Keywords-MovieID` FOREIGN KEY (`Movie_ID`) REFERENCES Movies (`ID`);

ALTER TABLE Persons_Movies
  ADD CONSTRAINT `Persons_Movies_MovieID` FOREIGN KEY (`Movie_ID`) REFERENCES Movies (`ID`),
  ADD CONSTRAINT `Persons_Movies_PersonID` FOREIGN KEY (`Person_ID`) REFERENCES Persons (`ID`);

ALTER TABLE ReleaseDates
  ADD CONSTRAINT `ReleaseDates-MovieID` FOREIGN KEY (`Movie_ID`) REFERENCES Movies (`ID`),
  ADD CONSTRAINT `ReleaseDates_CountryID` FOREIGN KEY (`Country_ID`) REFERENCES Countries (`ID`);

/* Create temporary tables */

CREATE TEMPORARY TABLE movies_temp (
  `Name` VARCHAR(512),
  `releaseYear` INT(4)
);

CREATE TEMPORARY TABLE genres_temp (
  `movie` VARCHAR(512),
  `genre` VARCHAR(200)
);

CREATE TEMPORARY TABLE keywords_temp (
  `movie`   VARCHAR(512),
  `keyword` VARCHAR(200)
);

CREATE TEMPORARY TABLE actors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(2047)
);

CREATE TEMPORARY TABLE actresses_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(2047)
);

CREATE TEMPORARY TABLE editors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512)
);

CREATE TEMPORARY TABLE directors_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512)
);

CREATE TEMPORARY TABLE producers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512),
  `role` VARCHAR(2048)
);

CREATE TEMPORARY TABLE writers_temp (
  `name`  VARCHAR(200),
  `movie` VARCHAR(512)
);

CREATE TEMPORARY TABLE mpaa_temp (
  `movie` VARCHAR(512),
  `mpaa`  VARCHAR(200)
);

CREATE TEMPORARY TABLE plot_temp (
  `movie` VARCHAR(512),
  `plot`  LONGTEXT
);

CREATE TEMPORARY TABLE ratings_temp (
  `movie`  VARCHAR(512),
  `rating` FLOAT,
  `votes`  INT(11)
);

CREATE TEMPORARY TABLE runningTimes_temp (
  `movie`  VARCHAR(512),
  `minutes`  INT(11)
);

CREATE TEMPORARY TABLE releaseDates_temp (
  `movie`  VARCHAR(512),
  `country`  VARCHAR(64),
  `date`  DATE
);

CREATE TEMPORARY TABLE biographies_temp (
  `actor`  VARCHAR(300),
  `birthdate`  DATE,
  `deathdate`  DATE
);

CREATE TEMPORARY TABLE countries_temp (
  `movie`  VARCHAR(512),
  `country`  VARCHAR(64)
);

CREATE TEMPORARY TABLE business_temp (
  `movie`  VARCHAR(512),
  `budget`  BIGINT DEFAULT NULL,
  `currency`  VARCHAR(16) DEFAULT NULL
);

/* Load CSV files on the server */

LOAD DATA INFILE '/var/lib/mysql-files/movies.list.csv' INTO TABLE movies_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/genres.list.csv' INTO TABLE genres_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/keywords.list.csv' INTO TABLE keywords_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/actors.list.csv' INTO TABLE actors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/actresses.list.csv' INTO TABLE actresses_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/editors.list.csv' INTO TABLE editors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/directors.list.csv' INTO TABLE directors_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/producers.list.csv' INTO TABLE producers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/writers.list.csv' INTO TABLE writers_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/mpaa-ratings-reasons.list.csv' INTO TABLE mpaa_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/plot.list.csv' INTO TABLE plot_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/ratings.list.csv' INTO TABLE ratings_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/running-times.list.csv' INTO TABLE runningTimes_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/release-dates.list.csv' INTO TABLE releaseDates_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/biographies.list.csv' INTO TABLE biographies_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/countries.list.csv' INTO TABLE countries_temp
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

LOAD DATA INFILE '/var/lib/mysql-files/business.list.csv' IGNORE INTO TABLE business_temp
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
  ADD KEY `movie` (`name`,`movie`);

ALTER TABLE `actresses_temp`
  ADD KEY `movie` (`name`,`movie`);

ALTER TABLE `editors_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `directors_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `producers_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `writers_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `runningTimes_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `releaseDates_temp`
  ADD KEY `movie` (`movie`,`country`);

ALTER TABLE `biographies_temp`
  ADD KEY `actor` (`actor`);

ALTER TABLE `ratings_temp`
  ADD KEY `movie` (`movie`);

ALTER TABLE `countries_temp`
  ADD KEY `movie` (`movie`,`country`);

ALTER TABLE `business_temp`
  ADD KEY `movie` (`movie`);

/* import temporary data to NickyBot */

INSERT INTO Genres (GenreName)
  SELECT DISTINCT genre
  FROM genres_temp;

INSERT INTO Keywords (Keyword)
  SELECT DISTINCT keyword
  FROM keywords_temp;

/* Fill Movies */

INSERT INTO Movies (Title, Plot, ReleaseYear, Rating, Votes, MPAA, Currency, Budget, Duration)
  (SELECT
     movies_temp.name,
     plot_temp.plot,
     movies_temp.releaseYear,
     ratings_temp.rating,
     ratings_temp.votes,
     mpaa_temp.mpaa,
     business_temp.currency,
     business_temp.budget,
     runningTimes_temp.minutes
  FROM movies_temp
     RIGHT JOIN plot_temp ON plot_temp.movie = movies_temp.name
     LEFT JOIN ratings_temp ON ratings_temp.movie = movies_temp.name
     LEFT JOIN mpaa_temp ON mpaa_temp.movie = movies_temp.name
     LEFT JOIN runningTimes_temp ON runningTimes_temp.movie = movies_temp.name
     LEFT JOIN business_temp ON business_temp.movie = movies_temp.name
  WHERE movies_temp.name IS NOT NULL);

/* Fill Persons */

INSERT IGNORE INTO Persons(Name, Sex, BirthDay, DeathDay)
  SELECT name, "Male" AS sex, biographies_temp.birthdate, biographies_temp.deathdate AS birthdate FROM actors_temp
    LEFT JOIN biographies_temp ON actors_temp.name = biographies_temp.actor;
INSERT IGNORE INTO Persons(Name, Sex, BirthDay, DeathDay)
  SELECT name, "Female" AS sex, biographies_temp.birthdate, biographies_temp.deathdate FROM actresses_temp
    LEFT JOIN biographies_temp ON name = biographies_temp.actor;
INSERT IGNORE INTO Persons(Name, BirthDay, DeathDay)
  SELECT name, biographies_temp.birthdate, biographies_temp.deathdate FROM producers_temp
    LEFT JOIN biographies_temp ON name = biographies_temp.actor;
INSERT IGNORE INTO Persons(Name, BirthDay, DeathDay)
  SELECT name, biographies_temp.birthdate, biographies_temp.deathdate FROM writers_temp
    LEFT JOIN biographies_temp ON name = biographies_temp.actor;
INSERT IGNORE INTO Persons(Name, BirthDay, DeathDay)
  SELECT name, biographies_temp.birthdate, biographies_temp.deathdate FROM editors_temp
    LEFT JOIN biographies_temp ON name = biographies_temp.actor;
INSERT IGNORE INTO Persons(Name, BirthDay, DeathDay)
  SELECT name, biographies_temp.birthdate, biographies_temp.deathdate FROM directors_temp
    LEFT JOIN biographies_temp ON name = biographies_temp.actor;

/* Fill Persons_Movies */

INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT DISTINCT m.ID, p.ID, "producer" as Role FROM producers_temp AS pt
    RIGHT JOIN Persons p ON p.Name = pt.name
    RIGHT JOIN Movies m ON m.Title = pt.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT DISTINCT m.ID, p.ID,"writer" AS Role FROM writers_temp AS wt
    RIGHT JOIN Persons p ON p.Name = wt.name
    RIGHT JOIN Movies m ON m.Title = wt.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT DISTINCT m.ID, p.ID, "editor" AS Role FROM editors_temp AS et
    RIGHT JOIN Persons p ON p.Name = et.name
    RIGHT JOIN Movies m ON m.Title = et.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT DISTINCT m.ID, p.ID, "director" AS Role FROM directors_temp AS dt
    RIGHT JOIN Persons p ON p.Name = dt.name
    RIGHT JOIN Movies m ON m.Title = dt.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT m.ID, p.ID, "actor" AS Role FROM actors_temp AS at
    RIGHT JOIN Persons p ON p.Name = at.name
    RIGHT JOIN Movies m ON m.Title = at.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;
INSERT INTO Persons_Movies (Movie_ID, Person_ID, Role)
  SELECT DISTINCT m.ID, p.ID, "actor" AS Role FROM actresses_temp AS ast
    RIGHT JOIN Persons p ON p.Name = ast.name
    RIGHT JOIN Movies m ON m.Title = ast.movie
    RIGHT JOIN biographies_temp b ON p.Name = b.actor
  WHERE m.ID IS NOT NULL;

/* Fill Countries */

INSERT INTO Countries(Country)
  SELECT DISTINCT DISTINCT Country FROM releaseDates_temp;

/* Fill ReleaseDates */

INSERT INTO ReleaseDates (Movie_ID, Country_ID, ReleaseDate)
  SELECT DISTINCT Movies.ID, Countries.ID, date FROM releaseDates_temp
    LEFT JOIN Movies ON movie = Movies.Title
    LEFT JOIN Countries ON Countries.Country = releaseDates_temp.country
  WHERE Movies.ID IS NOT NULL;

/* Fill Movies_Countries */

INSERT INTO Movies_Countries(Movie_ID, Country_ID)
  SELECT DISTINCT Movies.ID, c.ID FROM Movies
    RIGHT JOIN countries_temp as ct ON Movies.Title = ct.movie
    RIGHT JOIN Countries as c ON c.Country = ct.country
  WHERE Movies.ID IS NOT NULL;

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
