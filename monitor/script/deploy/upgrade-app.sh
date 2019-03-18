#!/usr/bin/env bash

#
# 升级应用
#

# 当使用未初始化的变量时，程序自动退出
# 也可以使用命令 set -o nounset
set -u

# 当任何一行命令执行失败时，自动退出脚本
# 也可以使用命令 set -o errexit
set -e

set -x

PROGRAM_NAME="dolphin-web"
APP_PATH="/home/dolphin/app/backend/spider-monitor-backend"
JAVA_HOME="/home/dolphin/app/third-party/java/jdk1.8.0_121"
export TOP_PID=$$
trap 'exit 1' TERM

PID=`ps -ef|grep -w ${PROGRAM_NAME}|grep -v grep|cut -c 9-15`
if [[ ${PID} -gt 1 ]]; then
        kill -15 ${PID}
else
        echo "Process not found"
fi

# Read program version number
if [[ -f "${APP_PATH}/version.properties" ]];then
    source ${APP_PATH}/version.properties
else
    echo "version file not exits!"
    kill -s TERM ${TOP_PID}
fi

APP_FULL_NAME="dolphin-web-${VERSION}.jar"

count=`ps -ef | grep ${PROGRAM_NAME} | grep -v "grep" | wc -l`
if [[ ${count} -lt 1 ]]; then
	nohup ${JAVA_HOME}/bin/java -Xmx298M -Xms296M -jar -Xdebug -Xrunjdwp:transport=dt_socket,suspend=n,server=y,address=5005 ${APP_PATH}/${APP_FULL_NAME} --spring.config.location=${APP_PATH}/application.properties>/dev/null &
else
	echo "unable to start app, process already exists!"
fi