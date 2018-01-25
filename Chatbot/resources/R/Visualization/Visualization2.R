# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

args <- commandArgs(trailingOnly = TRUE)

query = sprintf("SELECT m.ReleaseYear AS Years, count(*) AS Movies 
                FROM Movies AS m 
                LEFT JOIN Movies_Countries AS mc ON mc.Movie_ID = m.ID 
                LEFT JOIN Countries AS c ON mc.Country_ID = c.ID 
                WHERE m.ReleaseYear IS NOT NULL AND m.ReleaseYear < 2018 
                AND c.Country LIKE '%%%s%%' 
                GROUP BY m.ReleaseYear 
                ORDER BY m.ReleaseYear ASC", args[[1]])

values <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

cat("Look at this graaaaaaaph!")

invisible(jpeg('MoviesYear.jpg'))
barplot(values$Movies, names.arg = values$Years, main="Movies per year in " + args[[1]] , col = c("lightblue","lightcyan",
                                                                                                 "lavender", "mistyrose", "cornsilk"), 
                                                                                                  horiz=FALSE, cex.names=0.5)
invisible(dev.off())