# Title     : Costly length
# Objective : See if there is a relation between the budget and duration of a movie
# Created by: Rendall
# Created on: 23-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'USD' AND Rating IS NOT NULL")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

invisible(jpeg('MoviesBudget.jpg'))
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Relation between budget and duration of movies", col = "green", lpars = list(col = "red", lwd = 3, lty = 3), xlab = "Budget in $ x10000", ylab = "Duration")
invisible(dev.off())
