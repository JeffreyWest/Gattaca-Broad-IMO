## to install EvoFreq:
# install.packages('devtools')
# install.packages("Rcpp", dependencies = TRUE)
# install.packages("fs")
# library(devtools)
# install_github('MathOnco/EvoFreq')

library("EvoFreq")

mypath <- "/Users/4477547/IdeaProjects/Gattaca-Broad-IMO-main/Gattaca/NSCLC/2d/gattaca_output0.csv"
hal_info <- read.HAL(mypath,fill_name = "Color")
# hal_info <- read.HAL(mypath)
# print(hal_info$evofreq_plot)


fp <- plot_evofreq(hal_info$freq_frame, bw=0.01) + theme(axis.text.x=element_blank(), #remove x axis labels
                                                        axis.ticks.x=element_blank(), #remove x axis ticks
                                                        axis.text.y=element_blank(),  #remove y axis labels
                                                        axis.ticks.y=element_blank()  #remove y axis ticks
)
print(fp)

# ggsave("evofreq.png", width = 6, height = 2, units = "in")
