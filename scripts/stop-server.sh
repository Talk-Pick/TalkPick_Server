#!/bin/bash

# 컨테이너 stop
docker stop talkpick-server || true
docker rm talkpick-server || true

docker pull 718513646976.dkr.ecr.ap-northeast-2.amazonaws.com/talkpick-server:latest