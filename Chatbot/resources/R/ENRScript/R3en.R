# Title     : Budget rating
# Objective : Check if there is a relation between the budget and rating of a movie
# Created by: Rendall
# Created on: 23-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating FROM Movies WHERE Budget IS NOT NULL AND Currency = 'USD' AND Rating IS NOT NULL")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

invisible(jpeg('BudgetRating.jpg'))
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Relation between movie budget and ratings", col = "green", lpars =list(col = "red", lwd = 3, lty = 3), xlab = "Budget in $ x10000", ylab = "Rating")
invisible(dev.off())