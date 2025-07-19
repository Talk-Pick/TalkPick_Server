#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"

CONTAINER_NAME=talkpick-server
IMAGE_URI=718513646976.dkr.ecr.ap-northeast-2.amazonaws.com/talkpick-server/talkpick-server:latest

# 기존 컨테이너 제거
if docker ps -a | grep -q "$CONTAINER_NAME"; then
  docker stop "$CONTAINER_NAME"
  docker rm "$CONTAINER_NAME"
fi

# 최신 이미지 pull
docker pull "$IMAGE_URI"

# 컨테이너 실행
docker run -d \
  --name "$CONTAINER_NAME" \
  -p 8080:8080 \
  "$IMAGE_URI"

echo "--------------- SUCCESS : Talkpick Server Deploy -----------------"