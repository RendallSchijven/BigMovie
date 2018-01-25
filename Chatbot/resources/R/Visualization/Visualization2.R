# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
#install.packages("ggplot2")
library(ggplot2)
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

args <- commandArgs(trailingOnly = TRUE)

getCountry <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%'", args[[1]])
country <- dbGetQuery(mydb, getCountry)

query = sprintf("SELECT m.ReleaseYear AS Years, count(*) AS Movies 
                FROM Movies AS m 
                LEFT JOIN Movies_Countries AS mc ON mc.Movie_ID = m.ID 
                LEFT JOIN Countries AS c ON mc.Country_ID = c.ID 
                WHERE m.ReleaseYear IS NOT NULL AND m.ReleaseYear < 2018 
                AND c.Country = '%s'
                GROUP BY m.ReleaseYear 
                ORDER BY m.ReleaseYear ASC", country)

values <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

cat("Look at this graaaaaaaph!")

invisible(jpeg('MoviesYear.jpg'))
ggplot(values, aes(x=Years, y=Movies, colour="#CC6666")) +
  geom_line(size=1) +
  xlab("Years") +
  ylab("Movies") +
  ggtitle(sprintf("Amount of movies each year in %s", country)) +
  guides(color=FALSE)
invisible(dev.off())