# Title     : Budget speeltijd
# Objective : Ga na of er een verband is tussen speeltijd en het budget van films
# Created by: Rendall
# Created on: 15-1-2018

library(RMySQL)

mydb <- dbConnect(MySQL(), dbname="NickyBotUtf8", user="riley", password="jayden", host="db.sanderkastelein.nl")

query <- ("SELECT Budget, Duration FROM Movies WHERE Duration IS NOT NULL AND Budget IS NOT NULL AND Currency = 'USD'")

movies <- dbGetQuery(mydb, query)

invisible(dbDisconnect(mydb))

invisible(jpeg('BudgetDuration.jpg'))
scatter.smooth(movies$Budget / 10000, movies$Duration, main="Verband tussen budget en speeltijd van films", col = "green", lpars = list(col = "red", lwd = 3, lty = 3), xlab = "Budget in ??? x10000", ylab = "Speeltijd")
invisible(dev.off())
