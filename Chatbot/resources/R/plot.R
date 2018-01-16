#install.packages("RMySQL")
library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

values <- dbGetQuery(mydb, "select Name as names, count(*) as freq from Persons, Persons_Movies where Persons.ID = 1 group by Persons.Name")

invisible(jpeg('rplot.jpg'))
barplot(values$freq, names.arg = values$names, horiz=FALSE, cex.names=0.5)
invisible(dev.off())

dbDisconnect(mydb)


