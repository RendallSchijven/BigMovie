# Title     : MPAA PLOT
# Objective : Bepaal MPAA op basis van plot van film
# Created by: Rendall Schijven
# Created on: 17-1-2018

#install.packages("tm")
#install.packages("RMySQL")
#install.packages("SnowballC")
#install.packages("caTools")
#install.packages("rpart")
#install.packages("rpart.plot")
library(RMySQL)
library(tm)
library(SnowballC)
library(caTools)
library(rpart)
library(rpart.plot)

#Get the arguments that java sent
args <- commandArgs(trailingOnly = TRUE)

#The connection string for the database
mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

#The queries for getting the different MPAA we left out NC-17 and G because only 1% of the movies had this MPAA
#We limited to 1783 so the model would be fairly distributed over all 3 values
getR <- ("SELECT ID, MPAA, Plot FROM Movies WHERE MPAA = 'R' AND Plot IS NOT NULL ORDER BY Plot LIMIT 1783")
getPG <- ("SELECT ID, MPAA, Plot FROM Movies WHERE MPAA = 'PG' AND Plot IS NOT NULL ORDER BY Plot LIMIT 1783")
getPG13 <- ("SELECT ID, MPAA, Plot FROM Movies WHERE MPAA = 'PG-13' AND Plot IS NOT NULL ORDER BY Plot LIMIT 1783")

#Get the movie data from the movie the user entered
getTestMovie <- sprintf("SELECT ID, Title, Plot, MPAA FROM Movies WHERE ID = %s LIMIT 1", args[[1]])
testMovie <- dbGetQuery(mydb, getTestMovie)

#Make MPAA empty so we can predict it
testMovie$MPAA <- ""

#Save the test movie title
testMovieTitle <- testMovie$Title
testMovie$Title <- NULL

#Save the ID so we can use it for the split
testMovieID <- testMovie$ID

#Check if plot is empty if so return this
if(is.na(testMovie$Plot)){stop("This movie has no plot we can predict of, please choose another movie.")}

#Get the movies for each MPAA
moviesR <- dbGetQuery(mydb, getR)
moviesR <- moviesR[!(moviesR$ID==testMovieID),]

moviesPG <- dbGetQuery(mydb, getPG)
moviesPG <- moviesPG[!(moviesPG$ID==testMovieID),]

moviesPG13 <- dbGetQuery(mydb, getPG13)
moviesPG13 <- moviesPG13[!(moviesPG13$ID==testMovieID),]

#Bind the datasets into one
movies <- rbind(moviesR, moviesPG, moviesPG13, testMovie)

#Disconnect from the database
invisible(dbDisconnect(mydb))

#Prepare all plots for testing by cleaning them up
corpus <- Corpus(VectorSource(movies$Plot))
corpus <- tm_map(corpus, tolower)
corpus <- tm_map(corpus, removePunctuation)
corpus <- tm_map(corpus, removeWords, c(stopwords("english")))
corpus <- tm_map(corpus, stemDocument)

#This function removes all but the top 6% of words from the dataset
frequencies <- DocumentTermMatrix(corpus)
sparse <- removeSparseTerms(frequencies, 0.94)

#Make a dataset of the sparse words we can use for the cart model.
moviesSparse <- as.data.frame(as.matrix(sparse))

#Make the colnames acceptable
colnames(moviesSparse) <- make.names(colnames(moviesSparse))

#Add the MPAA AND ID to the sparse dataset
moviesSparse$MPAA <- movies$MPAA
moviesSparse$ID <- movies$ID

#Split the data into a trainset and testset where the testset is the plot of the movie the user entered
train <- subset(moviesSparse, ID != testMovieID)
test <- subset(moviesSparse, ID == testMovieID)

#Make a cart model of the dataset
movieCart = rpart(MPAA ~ ., data=moviesSparse, method = "class")

#Make a prediction using the cart model and the test data
MPAA <- predict(movieCart, newdata = test, type = "class")

#Put the prediction data in a table
table <-data.frame(table(test$MPAA, MPAA))

#Collect the row from the table where a rating is 1
row <- table[table[,3] == 1,]

#Put the rating in a string
calcRating <- sprintf("%s",row$MPAA)
return <- sprintf("De voorspelde MPAA voor %s is: ", testMovieTitle)

#Print this so it is returned to java
cat(return, calcRating)