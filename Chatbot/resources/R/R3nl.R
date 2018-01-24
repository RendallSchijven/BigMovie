# Title     : Budget waardering
# Objective : Kijken of er een verband is tussen het budget en waardering van een film
# Created by: Rendall
# Created on: 23-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating FROM Movies WHERE Budget IS NOT NULL AND Currency = 'EUR' AND Rating IS NOT NULL")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

invisible(jpeg('BudgetRating.jpg'))
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Verband tussen ", col = "green", lpars =list(col = "red", lwd = 3, lty = 3), xlab = "Budget in ??? x10000", ylab = "Speeltijd")
invisible(dev.off())