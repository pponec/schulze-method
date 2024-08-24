#!/bin/sh
#
# Usage: sh /fullpath/deploy.sh

set -e
DOMAIN="schulz.webfort.net"
PROTOCOL="https"

REMOTEDIR=$(echo $DOMAIN | tr "." "\n" | head -n 1)
PDIR=$(dirname $0)
MSG=FAILED
cd $PDIR

sh mvnw clean install -DskipTests
rsync -v target/*.war ponec@webfort:/home/tomcat/webapps/gwt/ROOT.war
msg="$PROTOCOL://$DOMAIN/"

echo Result-2 $msg


