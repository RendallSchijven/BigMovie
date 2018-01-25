# Title     : Culturele films
# Objective : Ga na welk land meet geweldadige films maakt, frankrijk of de vs
# Created by: Rendall
# Created on: 24-1-2018

#install.packages("RMySQL")
#install.packages("ggplot2")
library(RMySQL)
library(ggplot2)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

#Get the total amount of movies from each country and the total amount of violent movies
#We considered the gernes 'Horror', 'Thriller', 'War', and 'Action' as violent
getFrenchMovieAmount <- ("SELECT COUNT(*) AS movies FROM Movies_Countries AS mc
                         LEFT JOIN Countries AS c ON mc.Country_ID = c.ID
                         WHERE c.Country = 'France';")

getUSAMovieAmount <- ("SELECT COUNT(*) AS Movies FROM Movies_Countries AS mc
                      LEFT JOIN Countries AS c ON mc.Country_ID = c.ID
                      WHERE c.Country = 'USA';")

getFrenchViolentMovies <- ("SELECT COUNT(*) AS movies FROM Movies_Genres AS mg
                           LEFT JOIN Genres AS g ON g.Id = mg.Genre_ID
                           LEFT Join Movies_Countries AS mc ON mc.Movie_ID = mg.Movie_ID
                           LEFT JOIN Countries AS c ON c.ID = mc.Country_ID
                           WHERE (g.GenreName = 'Horror' OR g.GenreName = 'Thriller' OR g.GenreName = 'War' OR g.GenreName = 'Action')
                           AND c.Country = 'France';")

getUSAViolentMovies <- ("SELECT COUNT(*) AS movies FROM Movies_Genres AS mg
                        LEFT JOIN Genres AS g ON g.Id = mg.Genre_ID
                        LEFT Join Movies_Countries AS mc ON mc.Movie_ID = mg.Movie_ID
                        LEFT JOIN Countries AS c ON c.ID = mc.Country_ID
                        WHERE (g.GenreName = 'Horror' OR g.GenreName = 'Thriller' OR g.GenreName = 'War' OR g.GenreName = 'Action')
                        AND c.Country = 'USA';")

frenchMovies <- dbGetQuery(mydb, getFrenchMovieAmount)
usaMovies <- dbGetQuery(mydb, getUSAMovieAmount)
frenchViolent <- dbGetQuery(mydb, getFrenchViolentMovies)
usaViolent <- dbGetQuery(mydb, getUSAViolentMovies)

#Calculate the percentage of violent movies in eacht of the countries
francePercentage = (frenchViolent / frenchMovies) * 100
usaPercentage = (usaViolent / usaMovies) * 100

countries = c("France", "United States")
violence = c(francePercentage, usaPercentage, recursive = TRUE)
countriesViolence = data.frame(countries, violence)

invisible(jpeg('violence.jpg'))
ggplot(countriesViolence, aes(countries, violence)) +
  geom_bar(stat = "identity", position = "dodge") +
  xlab("Land") +
  ylab("Geweldadige films in %") +
  ggtitle("Percentage van geweldadige films t.o.v alle films")
invisible(dev.off())