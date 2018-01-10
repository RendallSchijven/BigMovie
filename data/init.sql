CREATE DATABASE NickyBot;

use NickyBot;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `Countries` (
  `Movie_ID` int(11) NOT NULL,
  `Country` varchar(200) NOT NULL,
  `ReleaseDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Genres` (
  `ID` int(11) NOT NULL,
  `GenreName` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Keywords` (
  `ID` int(11) NOT NULL,
  `Keyword` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Movies` (
  `ID` int(11) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `Plot` longtext NOT NULL,
  `Rating` float NOT NULL,
  `Votes` int(11) NOT NULL,
  `MPAA` varchar(20) NOT NULL,
  `Budget` int(11) NOT NULL,
  `Gross` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Movies_Genres` (
  `Movie_ID` int(11) NOT NULL,
  `Genre_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Movies_Keywords` (
  `Movie_ID` int(11) NOT NULL,
  `Keyword_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Persons` (
  `ID` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `BirthDay` date,
  `Sex` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Persons_Movies` (
  `Movie_ID` int(11) NOT NULL,
  `Person_ID` int(11) NOT NULL,
  `Role_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Roles` (
  `ID` int(11) NOT NULL,
  `Role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `Countries`
  ADD PRIMARY KEY (`Movie_ID`),
  ADD UNIQUE KEY `Country_2` (`Country`),
  ADD UNIQUE KEY `Movie_ID_2` (`Movie_ID`),
  ADD KEY `Country` (`Country`),
  ADD KEY `Movie_ID` (`Movie_ID`),
  ADD KEY `Movie_ID_3` (`Movie_ID`),
  ADD KEY `Country_3` (`Country`),
  ADD KEY `Movie_ID_4` (`Movie_ID`);

ALTER TABLE `Genres`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `GenreName` (`GenreName`),
  ADD KEY `ID_2` (`ID`);

ALTER TABLE `Keywords`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `Keyword` (`Keyword`),
  ADD KEY `ID_2` (`ID`);

ALTER TABLE `Movies`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `ID_2` (`ID`),
  ADD KEY `ID_3` (`ID`);

ALTER TABLE `Movies_Genres`
  ADD KEY `Movie_ID` (`Movie_ID`,`Genre_ID`);

ALTER TABLE `Movies_Keywords`
  ADD KEY `Movie_ID` (`Movie_ID`,`Keyword_ID`);

ALTER TABLE `Persons`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `ID_2` (`ID`);

ALTER TABLE `Persons_Movies`
  ADD KEY `Movie_ID` (`Movie_ID`,`Person_ID`,`Role_ID`);

ALTER TABLE `Roles`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`);


ALTER TABLE `Genres`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `Keywords`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `Movies`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE `Persons`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE `Roles`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;