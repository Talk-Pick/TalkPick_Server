#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"

  cd /home/ubuntu/TalkPick_Server

  # docker-compose 중지 및 컨테이너 제거
  docker compose down || true

  # docker-compose 컨테이너 실행
  docker compose up -d

echo "--------------- SUCCESS : Talkpick Server Deploy -----------------"