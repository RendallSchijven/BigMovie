# Title     : Genres
# Objective : Give a chart of the distribution of genres
# Created by: Rendall
# Created on: 24-1-2018

#install.packages("RMySQL")
#install.packages("plotly")
#install.packages("webshot")
library(RMySQL)
library(plotly)
#library(webshot)

#Set plotly username and api key for exporting image
Sys.setenv("plotly_username" = "Nickelbot")
Sys.setenv("plotly_api_key" = "qh6P6EyGmQ41lH0WTu11")

#Conection string to connect to database
mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

#Query for getting the amount of movies per genre
getMoviesPerGenre <- ("SELECT g.GenreName AS Genre, COUNT(*) AS Movies FROM Genres AS g
                    LEFT JOIN Movies_Genres AS mg ON mg.Genre_ID = g.ID
                    GROUP BY g.GenreName;")

#Query for getting the total amount of movies with a genre
getTotalMovies <- ("SELECT COUNT(*) FROM Movies_Genres;")

#Execute the queries on the database and save them in variables
moviesPerGenre <- dbGetQuery(mydb, getMoviesPerGenre)
totalMovies <- dbGetQuery(mydb, getTotalMovies)

#Disconnect from the database
invisible(dbDisconnect(mydb))

#Threshold of 2.5% of total movies, needed later
threshold <- (totalMovies / 100) * 2.5

#Check if a genre reaches the threshold, if it doesnt give it the genre Other. This is to make the chart look uncluttered
for(i in 1:nrow(moviesPerGenre)){
  if (moviesPerGenre$Movies[i] < threshold){
    moviesPerGenre$Genre[i] = "Other"
  }
}

#Put the variables in a "slices(genre)" and a "weight(movie amount) variable for the pie chart.
slices <- moviesPerGenre$Genre
weight <- moviesPerGenre$Movies

#Create a pie chart using the plotly library
genrePlot <- plot_ly(moviesPerGenre, labels = ~slices, values = ~weight, type = 'pie',
        textposition = 'outside',
        textinfo = 'label+percent',
        insidetextfont = list(color = '#FFFFFF'),
        hoverinfo = 'text',
        text = ~paste(weight, ' movies'),
        marker = list(colors = colors,
                      line = list(color = '#FFFFFF', width = 1)),
        #The 'pull' attribute can also be used to create space between the sectors
        showlegend = FALSE) %>%
  layout(title = 'Distribution of genres from movies worldwide',
         xaxis = list(showgrid = FALSE, zeroline = FALSE, showticklabels = FALSE),
         yaxis = list(showgrid = FALSE, zeroline = FALSE, showticklabels = FALSE))

#Use the plotly api to export image to the current working directory
plotly_IMAGE(genrePlot, format="png", out_file = "genreMovies.png")
cat("Look at this graaaappphhh!")