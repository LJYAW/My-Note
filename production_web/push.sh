#!/bin/sh
###
 # @Author: your name
 # @Date: 2020-05-11 18:50:31
 # @LastEditTime: 2021-06-09 15:48:12
 # @LastEditors: Please set LastEditors
 # @Description: In User Settings Edit
 # @FilePath: /production_web.sh
###
cd /Users/cc/Desktop/weijian/production_web
branch=$(git branch | sed -n -e 's/^\* \(.*\)/\1/p')

echo $branch
if [ $branch ==  "dev" ];then
cd /Users/cc/Desktop/weijian/production_web_pub/
git checkout dev
git pull origin dev

cd /Users/cc/Desktop/weijian/production_web
npm run dev-build
fi
if [ $branch == "master" ];then
cd /Users/cc/Desktop/weijian/production_web_pub/
git checkout master
git pull origin master
cd /Users/cc/Desktop/weijian/production_web
npm run build
fi

rm -rf /Users/cc/Desktop/weijian/production_web_pub/*
cp -r /Users/cc/Desktop/weijian/production_web/dist/* /Users/cc/Desktop/weijian/production_web_pub
cd /Users/cc/Desktop/weijian/production_web_pub
git add .
git commit -m'ok'

if [ $branch == "dev" ];then
git push origin dev
echo "已推送到远端 dev 分支"
fi
if [ $branch == "master" ];then
git push origin master
echo "已推送到远端 master 分支"
fi

