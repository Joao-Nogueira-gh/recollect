#!/bin/sh
# wait-for-mysql.sh

set -e
  
shift
cmd="$@"

# wait until MySQL is really available
maxcounter=60
 
counter=1
while ! mysql --protocol TCP -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "show databases;" > /dev/null 2>&1; do
    sleep 1
    counter=`expr $counter + 1`
    if [ $counter -gt $maxcounter ]; then
        >&2 echo "Connecting to DB."
        break
    fi;
done
  
exec $cmd &
>&2 echo "Finished."