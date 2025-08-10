#!/bin/bash

# Find all .json files in the current directory and subdirectories
find . -type f -name "*.json" | while read -r file; do
    # Replace "yaf" with "yaf" in each file
    sed -i 's/omf/yaf/g' "$file"
    echo "Updated: $file"
done

echo "Replacement complete!"
