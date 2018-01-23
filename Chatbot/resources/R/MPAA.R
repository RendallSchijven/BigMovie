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

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT MPAA, Plot FROM Movies WHERE MPAA IS NOT NULL AND Plot IS NOT NULL")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

corpus = Corpus(VectorSource(movies$Plot))
corpus = tm_map(corpus, tolower)
corpus = tm_map(corpus, removePunctuation)
corpus = tm_map(corpus, removeWords, c(stopwords("english")))
corpus = tm_map(corpus, stemDocument)

frequencies = DocumentTermMatrix(corpus)
sparse = removeSparseTerms(frequencies, 0.995)

moviesSparse = as.data.frame(as.matrix(sparse))

colnames(moviesSparse) = make.names(colnames(moviesSparse))

moviesSparse$MPAA = movies$MPAA

set.seed(123)
split = sample.split(moviesSparse$MPAA, SplitRatio = 0.7)
train = subset(moviesSparse, split == TRUE)
test = subset(moviesSparse, split == FALSE)

movieCart = rpart(MPAA ~ ., data=train, method = "class")
prp(movieCart)

predictCart = predict(movieCart, newdata = test, type = "class")

table = table(test$MPAA, predictCart)
print(table)
