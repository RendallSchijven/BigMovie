# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

args = commandArgs(trainingOnly=TRUE)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

values <- dbGetQuery(mydb, "SELECT ReleaseYear AS years, count(*) AS freq FROM Movies, Movies_Countries
                            WHERE Movies_Countries.Country_ID = (SELECT ID FROM Countries WHERE Country = 'USA')
                            GROUP BY Movies.ReleaseYear ASC")

                            "SELECT m.ReleaseYear AS years, count(*) AS m.movies FROM Movies AS m
                            LEFT JOIN Movies_Countries AS mc
                            ON mc.Country_ID = m.ID AND m.Country = 'USA'
                            GROUP BY m.ReleaseYear ASC"

invisible(jpeg('MoviesYear.jpg'))
barplot(values$freq, names.arg = values$years, horiz=FALSE, cex.names=0.5)
invisible(dev.off())

dbDisconnect(mydb)