#!/bin/sh
###
 # @Author: your name
 # @Date: 2020-05-11 18:50:31
 # @LastEditTime: 2021-06-09 16:24:08
 # @LastEditors: Please set LastEditors
 # @Description: In User Settings Edit
 # @FilePath: /production_web.sh
###
cd /Users/cc/Desktop/weijian/tm-admin
branch=$(git branch | sed -n -e 's/^\* \(.*\)/\1/p')

echo $branch
if [ $branch ==  "dev" ];then
npm run dev-build
# git push origin dev
fi
if [ $branch == "master" ];then
cd /Users/cc/Desktop/weijian/tm-admin/
git push origin master
npm run build
fi

git add .
git commit -m'打包发布'
if [ $branch == "dev" ];then
git push origin dev
echo "已推送到远端 dev 分支"
fi
if [ $branch == "master" ];then
git push origin master
echo "已推送到远端 master 分支"
fi

