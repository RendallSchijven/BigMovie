# Title     : Budget speeltijd
# Objective : Ga na of er een verband is tussen speeltijd en het budget van films
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'EUR'")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

#Make a scatter plot of the movie data so it can be displayed to the user
invisible(jpeg('BudgetDuration.jpg'))
par(bg = "grey")
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Verband tussen budget en speeltijd van films", 
               col = "blue", lpars = list(col = "red", lwd = 1, lty = 1), 
               xlab = "Budget in ??? x10000", ylab = "Duration")
cat("De blauwe stipjes zijn de datapunten, zoals je ziet is er niet bepaald een verband tussen deze punten de meeste films hebben ongeveer dezelde speeltijd.
    De rode lijn is de lijn die we hadden voorspeld dat de data punten volgden, zoals je ziet is dit niet het geval.")
invisible(dev.off())

