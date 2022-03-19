#!/bin/bash

sudo fuser -k -n tcp 80 || true

cd /home/ubuntu/jlpt/build/libs

sudo nohup java -jar jlpt-0.0.1-SNAPSHOT.jar --spring.config.location=/home/ubuntu/application-serv.yml > /home/ubuntu/jlpt/nohup.out 2>&1 &
# nohup실행 시 CodeDeploy는 무한 대기 합니다. 이 이슈를 해결하기 위해 nohub.out파일을 표준 입출력용으로 별도로 사용합니다.
# 이렇게 하지 않으면 nohup.out파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됩니다. nohub이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야합니다.

# --spring.config.location=/home/ubuntu/application-serv.yml
# 환경변수가 안먹혀서 대체함