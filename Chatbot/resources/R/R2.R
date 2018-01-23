# Title     : Costly length
# Objective : Ga na of er een verband is tussen de kosten van een film en de lengte ervan.
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

movies <- dbGetQuery(mydb, "SELECT ID, Budget, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL")

dbDisconnect(mydb)

plot(movies$Budget, movies$Duration, main="Correlatie tussen de kosten en de lengte van een film", xlab="Budget van een film", ylab="Lengte van een film")
cor(movies)

#Maak een lineair regressie model van movies
movielm = lm(Budget ~ Duration, data=movies)
summary(movielm)

movieglm = glm(Budget ~ Duration, data=movies, family=binomial)
summary(movieglm)
