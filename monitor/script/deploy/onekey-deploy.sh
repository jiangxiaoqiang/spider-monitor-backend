#!/usr/bin/env bash

#
# 一键部署后端项目到远程1台或多台服务器
# 发布流程为：编译构建-打包-发布包-拷贝包到指定目录-解压包
# 注：本机需要安装Ansible并与服务器做免密登录
#
# 2017-05-25 dolphin 增加动态读取版本号，根据版本自动发布程序
# 2017-09-15 dolphin 增加代理机器探测机制
#

# 当使用未初始化的变量时，程序自动退出
# 也可以使用命令 set -o nounset
set -u

# 当任何一行命令执行失败时，自动退出脚本
# 也可以使用命令 set -o errexit
set -e

# send=`date '+%Y-%m-%d %H:%M:%S'`
CURRENT_TIME=`date '+%Y%m%d%H%M%S'`

#constant
readonly SERVER_APP_PATH="/opt/app/backend"
readonly PROGRAM_PATH_INFORMATION_CENTER="/opt/app/backend"
readonly PROGRAM_DEVELOP_PATH_BACKEND="/home/hldev/"
readonly CONFIG_FILE_FULLNAME="${PROGRAM_DEVELOP_PATH_BACKEND}/script/config/application-jjxxzx-test.properties"
readonly PROXY_SERVER_IP="10.55.10.77"
readonly SERVER_IP="59"
readonly LOGIN_USER="root"
readonly LOCAL_DEPLOY_PACKAGE_PATH="$PROGRAM_DEVELOP_PATH_BACKEND/cc-web-boot/build/libs"

# Read program version number
source ${PROGRAM_DEVELOP_PATH_BACKEND}/version.properties

PROGRAM_NAME="web-boot-$VERSION.jar"

echo "开始构建..."
# Build project
${PROGRAM_DEVELOP_PATH_BACKEND}/gradlew -p ${PROGRAM_DEVELOP_PATH_BACKEND}/cc-web-boot -x test build

