# Title     : MPAA PLOT
# Objective : Bepaal MPAA op basis van plot van film
# Created by: Rendall Schijven
# Created on: 17-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBot", user="Riley", password="jayden", host="hiddevanranden.nl")

movies <- dbGetQuery(mydb, "SELECT * FROM Movies")
