# Title     : Films per land
# Objective : Laat het aantal films per land zien
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

query = ("SELECT c.Country AS Country, count(*) AS Movies FROM Countries AS c LEFT JOIN Movies_Countries AS mc ON mc.Country_ID = c.ID")

values <- dbGetQuery(mydb, query)

invisible(jpeg('CountriesMovies.jpg'))
barplot(values$Country, names.arg = values$Movies, main="Films per land" , col = c("lightblue","lightcyan",
"lavender", "mistyrose", "cornsilk"),
horiz=FALSE, cex.names=0.5)


invisible(dev.off())

invisible(dbDisconnect(mydb))
