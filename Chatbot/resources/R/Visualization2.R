# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

values <- dbGetQuery(mydb, "select ReleaseDate as years, count(*) as freq from Movies, Countries where Countries.Name = 'Zimbabwe' group by Countries.ReleaseDate ASC")

invisible(jpeg('MoviesYear.jpg'))
barplot(values$freq, names.arg = values$years, horiz=FALSE, cex.names=0.5)
invisible(dev.off())

dbDisconnect(mydb)