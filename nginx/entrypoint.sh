#!/bin/sh
set -e

mkdir -p /etc/ssl/cloudflare

# Nginx 실행
nginx -g 'daemon off;'