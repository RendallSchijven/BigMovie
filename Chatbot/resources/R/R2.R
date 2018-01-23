# Title     : Costly length
# Objective : Ga na of er een verband is tussen de kosten van een film en de lengte ervan.
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'USD' AND Rating IS NOT NULL")
  
movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

plot(movies$Budget / 10000, movies$Duration, main="Correlatie tussen de kosten en de lengte van een film", xlab="Budget van een film in $ x10000", ylab="Duur van een film")
scatter.smooth(movies$Budget, movies$Duration, main="Budget ~ Duration")
cor(movies)

#Maak een lineair regressie model van movies
movielm = lm(Rating ~ Budget, data=movies)
summary(movielm)

plot(movies)