# Title     : Budget waardering
# Objective : Kijken of er een verband is tussen het budget en waardering van een film
# Created by: Rendall
# Created on: 23-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating FROM Movies WHERE Budget IS NOT NULL AND Currency = 'EUR' AND Rating IS NOT NULL ORDER BY RAND() LIMIT 5000")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

#Make a scatter plot of the movie data so it can be displayed to the user
invisible(jpeg('BudgetRating.jpg'))
par(bg = "grey")
scatter.smooth(movies$Budget / 10000, movies$Rating, main="Verband tussen budget en waardering van films", 
               col = "blue", lpars = list(col = "red", lwd = 1, lty = 1), 
               xlab = "Budget in ??? x10000", ylab = "Waardering")
cat("De blauwe stipjes zijn de datapunten, zoals je ziet is er niet echt sprake van een verband.
    Wel is er over het algemeen te zien dat films met een iets hoger budget een iets hogere waardering hebben.
    De rode lijn is de lijn die wij hadden voorspeld voor de datapunten.")
invisible(dev.off())