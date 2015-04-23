#!/bin/bash

DIR="$( dirname "${BASH_SOURCE[0]}" )"

#echo 'regeneration de la bdd'
echo $DIR
mysql -h localhost -u root -proot -e "source $DIR/../config/db-test/QueryDbTest.sql"
#admincdb qwerty1234