#!/bin/bash

# 컨테이너 stop
docker stop talkpick-server || true
docker rm talkpick-server || true