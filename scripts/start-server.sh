#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"
cd /home/ubuntu/TalkPick_Server

echo "📂 현재 디렉토리: $(pwd)"
echo "📄 파일 목록:"
ls -al

# ✅ .env 존재 확인
if [ ! -f .env ]; then
  echo "❌ .env 파일이 존재하지 않습니다. 배포 중단."
  exit 1
fi

echo "🔐 ECR 로그인"
aws ecr get-login-password --region ap-northeast-2 \
  | docker login --username AWS --password-stdin 718513646976.dkr.ecr.ap-northeast-2.amazonaws.com

echo "🧹 기존 컨테이너 중지 및 정리"
docker compose --env-file .env down || true

echo "🚀 새 컨테이너 시작"
docker compose --env-file .env up -d

echo "✅ 서버 배포 완료"