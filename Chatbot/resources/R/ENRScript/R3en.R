# Title     : Budget rating
# Objective : Check if there is a relation between the budget and rating of a movie
# Created by: Rendall
# Created on: 23-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating FROM Movies WHERE Budget IS NOT NULL AND Currency = 'USD' AND Rating IS NOT NULL ORDER BY RAND() LIMIT 5000")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

#Make a scatter plot of the movie data so it can be displayed to the user
invisible(jpeg('BudgetRating.jpg'))
par(bg = "grey")
scatter.smooth(movies$Budget / 10000, movies$Rating, main="Relation between movie budget and ratings", 
               col = "blue", lpars = list(col = "red", lwd = 1, lty = 1), 
               xlab = "Budget in $ x10000", ylab = "Rating")
cat("The blue spots are the datapoints. As you can see movies with higher budget have slightly higher ratings so there is a relation.
    This is also seems to follow the red line quite well, the red line is the line we predicted.")
invisible(dev.off())