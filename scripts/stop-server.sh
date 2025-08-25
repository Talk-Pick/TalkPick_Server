#!/bin/bash

# 컨테이너 중지
docker stop talkpick-server || true
docker rm talkpick-server || true