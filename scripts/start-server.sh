#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"

cd /home/ubuntu/TalkPick_Server

echo "현재 디렉토리: $(pwd)"
echo "파일 목록:"
ls -al

docker compose down || true
docker compose up -d

echo "--------------- SUCCESS : Talkpick Server Deploy -----------------"