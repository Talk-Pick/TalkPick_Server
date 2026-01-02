FROM nginx:stable-alpine

# Nginx 설정 복사
COPY conf /etc/nginx/conf.d
COPY docs /usr/share/nginx/html/docs
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# 컨테이너 시작 시 entrypoint.sh 실행
ENTRYPOINT ["/entrypoint.sh"]