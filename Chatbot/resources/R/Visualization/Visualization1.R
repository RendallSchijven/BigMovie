# Title     : Films per land per jaar
# Objective : Laat voor een land het aantal films zien wat in een jaar is gemaakt
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
#install.packages("ggplot2")
library(ggplot2)
library(RMySQL)

#The database connection string
mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

#Function to get the args sent with the run command and save them in a variable
args <- commandArgs(trailingOnly = TRUE)

#Get the country from the variable
getCountry <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%' ORDER BY LENGTH(Country)", args[[1]])
country <- dbGetQuery(mydb, getCountry)

#Query for getting the releaseyears and amount of movies for each year for selected countries.
#Use sprintf to paste the country in the queryString
query = sprintf("SELECT m.ReleaseYear AS Years, count(*) AS Movies 
                FROM Movies AS m 
                LEFT JOIN Movies_Countries AS mc ON mc.Movie_ID = m.ID 
                LEFT JOIN Countries AS c ON mc.Country_ID = c.ID 
                WHERE m.ReleaseYear IS NOT NULL AND m.ReleaseYear < 2018 
                AND c.Country = '%s'
                GROUP BY m.ReleaseYear 
                ORDER BY m.ReleaseYear ASC", country)

#Run the query and save them in variable
values <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

#Send this to user
cat("Look at this graaaaaaaph!")

#Use ggplot geom_Line to make a linegraph of the movies per year
invisible(jpeg('MoviesYear.jpg'))
par(bg = "grey")
ggplot(values, aes(x=Years, y=Movies, colour="#CC6666")) +
  geom_line(size=1) +
  xlab("Years") +
  ylab("Movies") +
  ggtitle(sprintf("Amount of movies each year in %s", country)) +
  guides(color=FALSE)
invisible(dev.off())