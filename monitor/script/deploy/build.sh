#!/usr/bin/env bash

# 当使用未初始化的变量时，程序自动退出
# 也可以使用命令 set -o nounset
set -u

# 当任何一行命令执行失败时，自动退出脚本
# 也可以使用命令 set -o errexit
set -e

set -x

APP_PATH="/Users/dolphin/source/spider-monitor-backend/monitor"

# Why should run clean task?
# https://stackoverflow.com/questions/29028748/why-run-gradle-clean
${APP_PATH}/gradlew clean
${APP_PATH}/gradlew -p ${APP_PATH}/web -x test build
