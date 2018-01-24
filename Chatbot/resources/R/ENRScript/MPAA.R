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

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf82", user="riley", password="jayden", host="db.sanderkastelein.nl")

getR <- ("SELECT MPAA, Plot FROM Movies WHERE MPAA = 'R' AND Plot IS NOT NULL ORDER BY RAND() LIMIT 1783")
getPG <- ("SELECT MPAA, Plot FROM Movies WHERE MPAA = 'PG' AND Plot IS NOT NULL ORDER BY RAND() LIMIT 1783")
getPG13 <- ("SELECT MPAA, Plot FROM Movies WHERE MPAA = 'PG-13' AND Plot IS NOT NULL ORDER BY RAND() LIMIT 1783")

testMovie <- dbGetQuery(mydb, ("SELECT Plot, MPAA FROM Movies WHERE Title = 'Zodiac (2007)'"))
testMovie$MPAA <- 'z'

moviesR <- dbGetQuery(mydb, getR)
moviesPG <- dbGetQuery(mydb, getPG)
moviesPG13 <- dbGetQuery(mydb, getPG13)

movies = rbind(moviesR, moviesPG, moviesPG13, testMovie)

invisible(dbDisconnect(mydb))

#Prepare all plots for testing
corpus = Corpus(VectorSource(movies$Plot))
corpus = tm_map(corpus, tolower)
corpus = tm_map(corpus, removePunctuation)
corpus = tm_map(corpus, removeWords, c(stopwords("english")))
corpus = tm_map(corpus, stemDocument)

frequencies = DocumentTermMatrix(corpus)
sparse = removeSparseTerms(frequencies, 0.94)

moviesSparse = as.data.frame(as.matrix(sparse))

colnames(moviesSparse) = make.names(colnames(moviesSparse))

moviesSparse$MPAA = movies$MPAA

#set.seed(123)
split = sample.split(moviesSparse$MPAA, SplitRatio = 0.7)

#WERKT NIET
train = subset(moviesSparse, MPAA != 'z')
test = subset(moviesSparse, MPAA = 'z')

movieCart = rpart(MPAA ~ ., data=moviesSparse, method = "class")
prp(movieCart)

predictCart = predict(movieCart, newdata = test, type = "class")

table = table(test$MPAA, predictCart)
print(table)

accuracy = sum(diag(table))/sum(table)
print(accuracy)
