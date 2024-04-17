#!/bin/sh

KEYSTORE_NAME=$1
STOREPASS=$2
shift 2

while IFS=',' read -r url alias
do
    echo "Processing $url with alias $alias"
    echo | openssl s_client -servername $url -connect $url:443 2>/dev/null |\
    sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > $alias.crt
    keytool -import -noprompt -trustcacerts -alias $alias -file $alias.crt \
        -keystore $KEYSTORE_NAME -storepass $STOREPASS -storetype JKS
done < urls.txt