# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

args = commandArgs(trainingOnly=TRUE)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

values <- dbGetQuery(mydb, "select ReleaseDate as years, count(*) as freq from ReleaseDates, Countries, Movies_Countries where Countries.ID = Movies_Countries.Country_ID AND Countries.Country = 'USA' group by ReleaseDates.ReleaseDate ASC")

invisible(jpeg('MoviesYear.jpg'))
barplot(values$freq, names.arg = values$years, horiz=FALSE, cex.names=0.5)
invisible(dev.off())

dbDisconnect(mydb)