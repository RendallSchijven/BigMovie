# Title     : Costly length
# Objective : Ga na of er een verband is tussen de kosten van een film en de lengte ervan.
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Rating, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'EUR' AND Rating IS NOT NULL")
  
movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

invisible(jpeg('MoviesBudget.jpg'))
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Verband tussen kosten en speeltijd van films", col = "green", lpars =list(col = "red", lwd = 3, lty = 3), xlab = "Budget in ??? x10000", ylab = "Speeltijd")
invisible(dev.off())
