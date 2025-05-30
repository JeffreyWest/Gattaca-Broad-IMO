library(tidyverse)

# Set the directory containing the CSV files
csv_dir <- "/Users/4477547/Documents/GitHub/Gattaca-Broad-IMO/Gattaca/NSCLC/2d/modiefied"

merge_muller_files <- function(file_paths) {
  # Initialize with all columns from the first file
  first_file <- read.csv(file_paths[1], check.names = FALSE)
  merged <- data.frame(matrix(0, nrow = 1, ncol = ncol(first_file)))
  names(merged) <- names(first_file)
  merged$CloneID <- 0
  merged$ParentID <- 0
  # Set only the first row's Genome value to empty string
  if ("Genome" %in% names(merged)) {
    merged[1, "Genome"] <- ""
  }
  # Set the color of the first row to #2E1980
  if ("Color" %in% names(merged)) {
    merged[1, "Color"] <- "#2E1980"
  }
  offset <- 1
  
  for (path in file_paths) {
    df <- read.csv(path, check.names = FALSE)
    
    # Map old CloneIDs to new ones
    old_to_new <- setNames(seq(offset, length.out = nrow(df)), df$CloneID)
    
    df$CloneID <- old_to_new[as.character(df$CloneID)]
    df$ParentID <- ifelse(df$ParentID == 0, 0, old_to_new[as.character(df$ParentID)])
    
    # if (grepl("g1", path)) {
    #   df$Color <- "#FFC90F"
    # } else if (grepl("g2", path)) {
    #   df$Color <- "#2BA02B"
    # } else if (grepl("g4", path)) {
    #   df$Color <- "#8C6455"
    # } else if (grepl("g7", path)) {
    #   df$Color <- "#3E77B4" 
    # } else if (grepl("g26", path)) {
    #   df$Color <- "#8E67BD"
    # }

    merged <- rbind(merged, df)
    
    offset <- max(df$CloneID) + 1  # Prepare next offset
  }
  
  # Remove the cleanup of column names since we're preventing X prefixes
  # if (!"Color" %in% names(merged)) {
  #   merged$Color <- "black"  # Default color if missing
  # }
  
  return(merged)
}

# List of files to merge
file_paths <- c(
  file.path(csv_dir, "g2_modified.csv"),
  file.path(csv_dir, "g4_modified.csv"),
  file.path(csv_dir, "g1_modified.csv"),
  file.path(csv_dir, "g7_modified.csv"),
  file.path(csv_dir, "g26_modified.csv")
)

# Merge the files
merged_df <- merge_muller_files(file_paths)

# Save the merged result
write.csv(merged_df, file.path(csv_dir, "merged_muller.csv"), row.names = FALSE)


