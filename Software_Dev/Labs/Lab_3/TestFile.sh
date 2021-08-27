#!/bin/bash

# Author : Erica Shivers
# Date: 6/14/2021

if [ $# -eq 0 ]
  then
    echo "The correct way: \"./TestFile.sh file_name\""
    exit 1
fi

FILE=$1

count_lines(){
    lines=$( sed -n \$= $FILE )
    echo -e "\nThe number of lines in the file is: $lines"
}
count_words(){
    words=$( grep -e Lorem -e Ipsum -e model -e will $FILE  )
    echo -e "\nThe words in the file are in lines: $words"
}
add_text(){
    echo -e "\nEnter the string you want to append: "
    read -r sentence | echo $sentence >> $FILE
}
copy_and_exit(){
    mkdir solution 
    cp $FILE solution/
}



if test -f "$FILE"; then
    SENTINEL=1
    while [ $SENTINEL -gt 0 ]
    do
        echo -e "\n1. Count Lines\n2. Count Words\n3. Add to file\n4. Copy file to another folder\n0. Exit the loop\n Enter the value: "
        read -r SENTINEL
        case $SENTINEL in
            1)
            count_lines
            ;;
            2)
            count_words
            ;;
            3)
            add_text
            ;;
            4)
            copy_and_exit
            ;;
        esac
    done
else 
    echo "$FILE does not exist in the current folder."
    exit 1
fi