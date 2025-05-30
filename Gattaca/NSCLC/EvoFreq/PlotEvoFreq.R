## to install EvoFreq:
# install.packages('devtools')
# install.packages("Rcpp", dependencies = TRUE)
# install.packages("fs")
# library(devtools)
# install_github('MathOnco/EvoFreq')

library("EvoFreq")

mypath <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/merged_muller.csv"
# mypath <-"/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g1_modified.csv"
# mypath <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g2_modified.csv"
# mypath <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g4_modified.csv"
# mypath <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g7_modified.csv"

# mypath <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g26_modified.csv"


hal_info <- read.HAL(mypath, fill_name = "Color")

# Check for NA/NaN values in your frequency data


# Try with just the first few rows
# test_data <- hal_info$freq_frame[1:10,]
fp <- plot_evofreq(hal_info$freq_frame, bw=0.01) + theme(axis.text.x=element_blank(), #remove x axis labels
                                                         axis.ticks.x=element_blank(), #remove x axis ticks
                                                         axis.text.y=element_blank(),  #remove y axis labels
                                                         axis.ticks.y=element_blank()  #remove y axis ticks
)
print(fp)

csv_dir <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/EvoFreq/Figures"
ggsave(file.path(csv_dir, "AllGattaca.png"), width = 4, height = 2, units = "in")
