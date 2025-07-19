#!/bin/bash
set -e

echo "--------------- START : Talkpick Server Deploy -----------------"
cd /home/ubuntu/TalkPick_Server

echo "📂 현재 디렉토리: $(pwd)"
echo "📄 파일 목록:"
ls -al

echo "🔐 ECR 로그인"
aws ecr get-login-password --region ap-northeast-2 \
  | docker login --username AWS --password-stdin 718513646976.dkr.ecr.ap-northeast-2.amazonaws.com

echo "🧹 기존 컨테이너 중지"
docker compose down || true

echo "🚀 새 컨테이너 시작"
docker compose up -d

echo "✅ 서버 배포 완료"