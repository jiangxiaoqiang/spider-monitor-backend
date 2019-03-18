#!/usr/bin/env bash

# 当使用未初始化的变量时，程序自动退出
# 也可以使用命令 set -o nounset
set -u

# 当任何一行命令执行失败时，自动退出脚本
# 也可以使用命令 set -o errexit
set -e

set -x

PROGRAM_NAME="dolphin-web"
APP_PATH="/home/dolphin/app/backend/spider-monitor-backend"
BUILD_PATH="/var/jenkins_home/workspace/spider-monitor-backend/monitor"

if [[ -f "${BUILD_PATH}/version.properties" ]];then
    source ${BUILD_PATH}/version.properties
else
    echo "version file not exits!"
fi

scp ${BUILD_PATH}/script/deploy/upgrade-app.sh root@spider-monitor-app-server:${APP_PATH}
scp ${BUILD_PATH}/version.properties root@spider-monitor-app-server:${APP_PATH}
scp ${BUILD_PATH}/script/config/production/application.properties root@spider-monitor-app-server:${APP_PATH}

APP_FULL_NAME="dolphin-web-${VERSION}.jar"
scp ${BUILD_PATH}/web/build/libs/${APP_FULL_NAME} root@spider-monitor-app-server:${APP_PATH}

ansible spider-monitor-app-server -m command -a "chdir=${APP_PATH} bash ./upgrade-app.sh"
