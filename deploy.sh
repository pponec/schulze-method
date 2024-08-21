#!/bin/sh
#
# Usage: sh /fullpath/deploy.sh

set -e
DOMAIN="songbook.webfort.net"
PROTOCOL="https"

REMOTEDIR=$(echo $DOMAIN | tr "." "\n" | head -n 1)
PDIR=$(dirname $0)
MSG=FAILED
cd $PDIR

sh mvnw clean install
rsync -v target/*.war ponec@webfort:/home/tomcat/webapps/$REMOTEDIR/ROOT.war
msg="$PROTOCOL://$DOMAIN/"

echo Result-1 "$PROTOCOL://zpevnik.ponec.net/"
echo Result-2 $msg


