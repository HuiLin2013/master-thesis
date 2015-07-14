data <- read.table("(CS)ETnum10col.txt", header = TRUE)

max_y <- max(data)
plot_colors <- c("black", "blue", "red4")
png(filename="(CS)ETnum10Col.png", height = 1000, width = 1300, bg = "white")
plot(data$HTML5_Loading, type = "o", col = plot_colors[1], pch=15,  ylim=c(0,max_y), axes=FALSE, ann=FALSE)
axis(1, at=1:5, lab=c("100", "200", "300", "400", "500"))
axis(2, las=1, at=80*0:max_y, cex.axis = 1)

box()
lines(data$Android_Loading, type="o", pch=15, lty=2, col=plot_colors[1])
lines(data$HTML5_Sorting, type="o", pch=16, lty=1,  col=plot_colors[2])
lines(data$Android_Sorting, type="o", pch=16, lty=2,  col=plot_colors[2])   
lines(data$HTML5_Filtering, type="o", pch=17, lty=1, col=plot_colors[3])
lines(data$Android_Filtering, type="o", pch=17, lty=2,   col=plot_colors[3])

title(main="Execution Time_Numbers_10Columns (Case Study)", col.main="red", font.main=6)
title(xlab= "Number of rows", col.lab="red")
title(ylab= "Execution Time (ms) for Numbers", col.lab="red")
legend(1, max_y, names(data), cex=1.5, col=c("black", "black","blue", "blue","red4","red4"), pch=c(15,15,16,16,17,17), lty=c(1,2,1,2,1,2));


dev.off()