# Title     : Costly length
# Objective : Ga na of er een verband is tussen de kosten van een film en de lengte ervan.
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

movies <- dbGetQuery(mydb, "SELECT ID, Budget, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL")

dbDisconnect(mydb)

plot(movies)
cor(movies)

#Maak een lineair regressie model van movies
movielm = lm(Budget ~ Duration, data=movies)
summary(movielm)
