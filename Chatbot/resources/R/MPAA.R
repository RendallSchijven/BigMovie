# Title     : MPAA PLOT
# Objective : Bepaal MPAA op basis van plot van film
# Created by: Rendall Schijven
# Created on: 17-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

movies <- dbGetQuery(mydb, "SELECT * FROM Movies")

dbDisconnect(mydb)

plot(movies)
cor(movies)

#Maak een lineair regressie model van movies
movielm = lm(MPAA ~ Plot , data=movies)
summary(movielm)
