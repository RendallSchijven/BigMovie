# Title     : Genres
# Objective : Give a chart of the distribution of genres
# Created by: Rendall
# Created on: 24-1-2018

#install.packages("RMySQL")
#install.packages("plotrix")
library(RMySQL)
library(plotrix)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

moviesPerGenre <- ("SELECT g.GenreName AS Genre, COUNT(*) AS Movies FROM Genres AS g
                    LEFT JOIN Movies_Genres AS mg ON mg.Genre_ID = g.ID
                    GROUP BY g.GenreName")
totalMovies <- ("SELECT COUNT(*) FROM Movies_Genres")

genreDistribution <- dbGetQuery(mydb, moviesPerGenre)

slices <- genreDistribution$Genre
weight <- genreDistribution$Movies

pie3D(slices,labels=weight,explode=0.1,
      main="Pie Chart of Countries ")

invisible(jpeg('CountriesMovies.jpg'))
barplot(values$Country, names.arg = values$Movies, main="Films per land" , col = c("lightblue","lightcyan",
"lavender", "mistyrose", "cornsilk"),
horiz=FALSE, cex.names=0.5)


invisible(dev.off())

invisible(dbDisconnect(mydb))
