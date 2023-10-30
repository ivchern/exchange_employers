#!/bin/bash

git clone "https://github.com/tanto39/ShareTeam.git"
git clone "https://github.com/ivchern/exchange_employers_recommendation.git"
git clone --branch dev "https://github.com/ivchern/exchange_employers.git"

source_dir="/dockerfiles/ShareTeam"
target_dir="/ShareTeam"
cp -f "$(pwd)/$source_dir"/* "$(pwd)/$target_dir"/

#Для использования ssl раскомментировать, и сделать порты доступными изнутри docker-network
#sudo $SHELL -c "cd exchange_employers_recommendation; mkdir keys;"
#sleep 1

# # sert
#sleep 1
#sudo $SHELL -c "cd exchange_employers_recommendation; cd keys; openssl req -newkey rsa:2048 -nodes -keyout server.key -x509 -days 365 -out server.crt -subj "/C=/ST=/L=/O=/CN=" ;"
#
#source_dir="/keys/server.crt"
#target_dir="/exchange_employers/src/main/resources"
#cp -nr "$(pwd)/$source_dir" "$(pwd)/$target_dir"/
#
#source_dir="/keys"
#target_dir="/exchange_employers_recommendation/keys"
#cp -nr "$(pwd)/$source_dir"/* "$(pwd)/$target_dir"/
sudo docker-compose up --build
