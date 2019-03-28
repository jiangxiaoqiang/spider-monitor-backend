#!/usr/bin/env bash

# 当使用未初始化的变量时，程序自动退出
# 也可以使用命令 set -o nounset
set -u

# 当任何一行命令执行失败时，自动退出脚本
# 也可以使用命令 set -o errexit
set -e

set -x

PROGRAM_NAME="dolphin-web"
APP_PATH="/home/hldev/jiangxiaoqiang/spider-monitor-backend/monitor"
REMOTE_APP_PATH="/home/dolphin/app/backend/spider-monitor-backend/monitor"
BUILD_PATH="/home/hldev/jiangxiaoqiang/spider-monitor-backend/monitor/web/build/libs"

if [[ -f "${APP_PATH}/version.properties" ]];then
    source ${APP_PATH}/version.properties
else
    echo "version file not exits!"
fi

scp ${APP_PATH}/script/deploy/upgrade-app.sh root@spider-monitor-app-server:${REMOTE_APP_PATH}
scp ${APP_PATH}/version.properties root@spider-monitor-app-server:${REMOTE_APP_PATH}
scp ${APP_PATH}/script/config/production/application.properties root@spider-monitor-app-server:${REMOTE_APP_PATH}

APP_FULL_NAME="dolphin-web-${VERSION}.jar"
scp ${APP_PATH}/web/build/libs/${APP_FULL_NAME} root@spider-monitor-app-server:${REMOTE_APP_PATH}

ansible spider-monitor-app-server -m command -a "chdir=${REMOTE_APP_PATH} bash ./upgrade-app.sh"
