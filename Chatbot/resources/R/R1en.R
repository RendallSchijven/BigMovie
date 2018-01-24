# Title     : Cultural genre
# Objective : Check if france or USA have more violence/horror movies
# Created by: Rendall
# Created on: 15-1-2018

#install.packages("RMySQL")
#install.packages("ggplot2")
library(RMySQL)
library(ggplot2)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

args <- commandArgs(trailingOnly = TRUE)

getCountry1 <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%' LIMIT 1;", args[[1]])
getCountry2 <- sprintf("SELECT Country FROM Countries WHERE Country LIKE '%%%s%%' LIMIT 1;", args[[2]])

country1 <- dbGetQuery(mydb, getCountry1)
country2 <- dbGetQuery(mydb, getCountry2)

country1
country2

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

countries = c(toString(country1), toString(country2))
violence = c(c1Percentage, c2Percentage, recursive = TRUE)
countriesViolence = data.frame(countries, violence)

if(c1Percentage > c2Percentage){
  cat(sprintf("As you can see %s has a higher percentage of violent movies than %s",country1, country2))
} else{
  cat(sprintf("As you can see %s has a higher percentage of violent movies than %s",country2, country1))
}

invisible(jpeg('violence.jpg'))
ggplot(countriesViolence, aes(countries, violence)) +
  geom_bar(stat = "identity", position = "dodge") +
  xlab("Country") +
  ylab("Violent movies in %") +
  ggtitle("Violent movies as percentage of total movies")
invisible(dev.off())