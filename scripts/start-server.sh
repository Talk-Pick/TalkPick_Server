#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"

cd /home/ubuntu/TalkPick_Server

echo "현재 디렉토리: $(pwd)"
echo "파일 목록:"
ls -al

if [ ! -f docker-compose.yml ]; then
  echo "❌ docker-compose.yml 파일이 존재하지 않습니다."
  exit 1
fi

docker compose down || true
docker compose up -d

echo "--------------- SUCCESS : Talkpick Server Deploy -----------------"