# Title     : Cultural genre
# Objective : Check if country1 or country2 have more violence/horror movies return it in dutch
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
#install.packages("ggplot2")
library(RMySQL)
library(ggplot2)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

#Get the arguments that java sent
args <- commandArgs(trailingOnly = TRUE)

#Make a string and put all the arguments in iut
countryString = ""
for(arg in args){countryString <- paste(countryString, arg)}

#Trim the countries on /. In java we put a / between the country variables
split = unlist(strsplit(countryString, "/"))

#Save the countries in a variable and trim them so there are no spaces infront or behind
arg1 = trimws(split[1])
arg2 = trimws(split[2])

#The queries to get the country from the database
getCountry1 <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%' ORDER BY LENGTH(Country) LIMIT 1;", arg1)
getCountry2 <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%' ORDER BY LENGTH(Country) LIMIT 1;", arg2)

#Run the queries and save the results
country1 <- dbGetQuery(mydb, getCountry1)
country2 <- dbGetQuery(mydb, getCountry2)

#Get the total amount of movies from each country and the total amount of violent movies
#We considered the gernes 'Horror', 'Thriller', 'War', and 'Action' as violent
getCountry1Movies <- sprintf("SELECT COUNT(*) AS movies FROM Movies_Countries AS mc
                             LEFT JOIN Countries AS c ON mc.Country_ID = c.ID
                             WHERE c.Country = '%s';", country1)

getCountry2Movies <- sprintf("SELECT COUNT(*) AS Movies FROM Movies_Countries AS mc
                             LEFT JOIN Countries AS c ON mc.Country_ID = c.ID
                             WHERE c.Country = '%s';", country2)

getCountry1Violent <- sprintf("SELECT COUNT(*) AS movies FROM Movies_Genres AS mg
                              LEFT JOIN Genres AS g ON g.Id = mg.Genre_ID
                              LEFT Join Movies_Countries AS mc ON mc.Movie_ID = mg.Movie_ID
                              LEFT JOIN Countries AS c ON c.ID = mc.Country_ID
                              WHERE (g.GenreName = 'Horror' OR g.GenreName = 'Thriller' OR g.GenreName = 'War' OR g.GenreName = 'Action')
                              AND c.Country = '%s';", country1)

getCountry2Violent <- sprintf("SELECT COUNT(*) AS movies FROM Movies_Genres AS mg
                              LEFT JOIN Genres AS g ON g.Id = mg.Genre_ID
                              LEFT Join Movies_Countries AS mc ON mc.Movie_ID = mg.Movie_ID
                              LEFT JOIN Countries AS c ON c.ID = mc.Country_ID
                              WHERE (g.GenreName = 'Horror' OR g.GenreName = 'Thriller' OR g.GenreName = 'War' OR g.GenreName = 'Action')
                              AND c.Country = '%s';", country2)

Country1Movies <- dbGetQuery(mydb, getCountry1Movies)
Country2Movies <- dbGetQuery(mydb, getCountry2Movies)
Country1Violent <- dbGetQuery(mydb, getCountry1Violent)
Country2Violent <- dbGetQuery(mydb, getCountry2Violent)

#Calculate the percentage of violent movies in eacht of the countries
c1Percentage = (Country1Violent / Country1Movies) * 100
c2Percentage = (Country2Violent / Country2Movies) * 100

#Make a dataset of the data we retrieved
countries = c(toString(country1), toString(country2))
violence = c(c1Percentage, c2Percentage, recursive = TRUE)
countriesViolence = data.frame(countries, violence)

cat("Look at this graph!\n")

#This statement checks which country has the higher percentage and sends the correct string back to the user
if(c1Percentage > c2Percentage){
  cat(sprintf("Zoals je ziet heeft %s een hoger percentage geweldadige films dan %s",country1, country2))
} else{
  cat(sprintf("Zoals je ziet heeft %s een hoger percentage geweldadige films dan %s",country2, country1))
}

#Create the barplot and save it as jpg
invisible(jpeg('violence.jpg'))
ggplot(countriesViolence, aes(countries, violence, fill="#FF6666")) +
  geom_bar(stat = "identity", position = "dodge") +
  xlab("Land") +
  ylab("Geweldadige films in %") +
  ggtitle("Geweldadige films als percentage van totale films") +
  guides(fill=FALSE)
invisible(dev.off())