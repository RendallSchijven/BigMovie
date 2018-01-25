# Title     : Costly length
# Objective : See if there is a relation between the budget and duration of a movie
# Created by: Rendall
# Created on: 23-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'USD'")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

#Make a scatter plot of the movie data so it can be displayed to the user
invisible(jpeg('BudgetDuration.jpg'))
par(bg = "grey")
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Relation between budget and duration of movies", 
               col = "blue", lpars = list(col = "red", lwd = 1, lty = 1), 
               xlab = "Budget in $ x10000", ylab = "Duration")
cat("The blue spots are the datapoints, you can see that budget does not really affect the duration at all, also the longest movies are mostly very cheap.
    The red line is what we would predict the budget and duration of a movie would be, as you can see this is not the case.")
invisible(dev.off())
