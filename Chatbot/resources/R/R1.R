# Title     : Leeftijd actrice
# Objective : Kijk of een actrice minder werk heeft naarmate ze ouder word
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

actress <- dbGetQuery(mydb, "select ID, BirthDay, Name, count(*) as freq FROM Persons LEFT JOIN Persons_Movies ON Persons_Movies.Person_ID = Persons.ID WHERE Persons.Sex = 'Female' AND Persons_Movies.Role = 'actor' GROUP BY Persons.ID ORDER BY freq DESC LIMIT 2000")
moviesPerYearPerActress <- dbGetQuery(mydb, "select Name, ReleaseYear, count(*) as freq FROM Persons, Movies, Persons_Movies WHERE Persons_Movies.Role = 'actor' AND Persons_Movies.Person_ID = Persons.ID GROUP BY Movies.ReleaseYear ORDER BY freq DESC LIMIT 4000")
careerLength <- dbGetQuery(mydb, "SELECT Name, (MAX(m.ReleaseYear) - MIN(m.ReleaseYear)) AS CareerLength, (CAST('2018-01-16' AS DATE) - CAST(BirthDay AS DATE))/10000 AS AGE FROM Persons AS p
                                  LEFT JOIN Persons_Movies pm
                                  ON pm.Person_ID = p.ID AND pm.Role = 'actor'

                                  LEFT JOIN Movies m
                                  ON pm.Movie_ID = m.ID

                                  GROUP BY p.ID
                                  ORDER BY CareerLength DESC;")


movies <- dbGetQuery(mydb, paste("select Title from Movies, Persons_Movies where Persons_Movies.Person_ID = (" ,id, ")", sep=""))

dbDisconnect(mydb)