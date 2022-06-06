#!/bin/bash

# Get Current Year and Previous Month
YEAR="$(date +%Y)"
LAST_MONTH="$(date -d "$(date +%Y-%m-1) - 1 month" +%B)"

# Check if previous month is December
# If it is then decrease year by 1
if [[ $LAST_MONTH == "December" ]] 
then
  YEAR="$(date -d "last-year" +%Y)"
fi

echo "Program being run for - $LAST_MONTH $YEAR"

# Add Variables to GitHub Actions Environment
echo "YEAR=$YEAR" >> $GITHUB_ENV
echo "LAST_MONTH=$LAST_MONTH" >> $GITHUB_ENV