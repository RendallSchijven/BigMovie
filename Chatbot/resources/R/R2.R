# Title     : Leeftijd actrice
# Objective : Kijk of een actrice minder werk heeft naarmate ze ouder word
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

actriceData <- dbGetQuery(mydb, "select *, count(*) as freq from Persons, Persons_Movies WHERE Persons.Sex = 'Female' AND Persons.BirthDay IS NOT NULL GROUP by Persons.Name")
moviesPerActress <- dbGetQuery(mydb, "select Name, count(*) as freq FROM Persons LEFT JOIN Persons_Movies ON Persons_Movies.Person_ID = Persons.ID WHERE Persons.Sex = 'Female' AND Persons_Movies.Role = 'actor' GROUP BY Persons.Name ORDER BY freq DESC LIMIT 10000")

dbDisconnect(mydb)