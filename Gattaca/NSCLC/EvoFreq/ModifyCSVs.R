library(tidyverse)

# Function to convert HSV to RGB
hsv2rgb <- function(h, s, v) {
  # Input h, s, v are already in [0,1] range
  if (s == 0) { #This means Grayscale, so all the RGB is set to be V
    r <- g <- b <- v
  } else {
    h <- h * 6
    i <- floor(h)
    f <- h - i
    p <- v * (1 - s)
    q <- v * (1 - s * f)
    t <- v * (1 - s * (1 - f))
    
    switch(i + 1,
           {r <- v; g <- t; b <- p},
           {r <- q; g <- v; b <- p},
           {r <- p; g <- v; b <- t},
           {r <- p; g <- q; b <- v},
           {r <- t; g <- p; b <- v},
           {r <- v; g <- p; b <- q}
    )
  }
  
  return(c(r, g, b))
}

# Function to convert RGB to HEX
rgb2hex <- function(r, g, b) {
  rgb(r, g, b, maxColorValue = 1)
}

# Read the CSV file
input_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/gattaca26_output0.csv"
# input_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/gattaca7_output0.csv"
# input_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/gattaca1_output0.csv"
# input_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/gattaca4_output0.csv"
# input_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/gattaca2_output0.csv"
data <- read.csv(input_file, check.names = FALSE)

# # Convert h, s, v columns to numeric
# data$H <- as.numeric(data$H)
# data$S <- as.numeric(data$S)
# data$V <- as.numeric(data$V)

# Convert HSV to HEX
data$Color <- apply(data, 1, function(row) {
  h <- as.numeric(row['H'])
  s <- as.numeric(row['S'])
  v <- as.numeric(row['V'])
  rgb <- hsv2rgb(h, s, v)
  rgb2hex(rgb[1], rgb[2], rgb[3])
})

# Remove original h, s, v columns
data <- data %>% select(-H, -S, -V)

# Save the modified data
output_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g26_modified.csv"
# output_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g7_modified.csv"
# output_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g1_modified.csv"
# output_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g4_modified.csv"
# output_file <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied/g2_modified.csv"
write.csv(data, output_file, row.names = FALSE)

