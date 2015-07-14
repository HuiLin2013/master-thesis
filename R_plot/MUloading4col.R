data <- read.table("MUloading4col.txt", header = TRUE)

max_y <- max(data)
plot_colors <- c("black", "blue", "red4")
png(filename="MUloading4col.png", height = 1000, width = 1450, bg = "white")
plot(data$HTML5_Num, type = "o", col = plot_colors[1], pch=15,  ylim=c(0,max_y), axes=FALSE, ann=FALSE)
axis(1, at=1:5, lab=c("2000", "4000", "6000", "8000", "10000"))
axis(2, las=1, at=5*0:max_y, cex.axis = 1)

box()
lines(data$Android_Num, type="o", pch=15, lty=2, col=plot_colors[1])
lines(data$HTML5_Str, type="o", pch=16, lty=1,  col=plot_colors[2])
lines(data$Android_Str, type="o", pch=16, lty=2,  col=plot_colors[2])   
lines(data$HTML5_Mix, type="o", pch=17, lty=1, col=plot_colors[3])
lines(data$Android_Mix, type="o", pch=17, lty=2,   col=plot_colors[3])

title(main="Memory Usage_Loading_4Columns", col.main="red", font.main=6)
title(xlab= "Number of rows", col.lab="red")
title(ylab= "Memory Usage (mb) for Loading", col.lab="red")
legend(1, max_y, names(data), cex=1.5, col=c("black", "black","blue", "blue","red4","red4"), pch=c(15,15,16,16,17,17), lty=c(1,2,1,2,1,2));

dev.off()
