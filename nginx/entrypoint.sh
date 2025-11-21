#!/bin/sh
set -e

# Secret 환경변수를 파일로 변환
mkdir -p /etc/ssl/cloudflare
echo "$ORIGIN_CERTIFICATE" > /etc/ssl/cloudflare/origin.crt
echo "$PRIVATE_CERTIFICATE_KEY" > /etc/ssl/cloudflare/origin.key
chmod 600 /etc/ssl/cloudflare/origin.key

# Nginx 실행
nginx -g 'daemon off;'