#!/bin/sh
###
 # @Author: your name
 # @Date: 2020-05-11 18:50:31
 # @LastEditTime: 2021-07-13 14:17:38
 # @LastEditors: Please set LastEditors
 # @Description: In User Settings Edit
 # @FilePath: /monit-admin-v2.sh
###
cd /Users/cc/Desktop/weijian/monit-admin-v2
branch=$(git branch | sed -n -e 's/^\* \(.*\)/\1/p')

echo $branch
if [ $branch ==  "dev" ];then
cd /Users/cc/Desktop/weijian/monit-admin-v2-pub/
git checkout dev
git pull origin dev

cd /Users/cc/Desktop/weijian/monit-admin-v2
npm run dev-build
fi
if [ $branch == "master" ];then
cd /Users/cc/Desktop/weijian/monit-admin-v2-pub/
git checkout master
git pull origin master
cd /Users/cc/Desktop/weijian/monit-admin-v2
npm run build
fi

rm -rf /Users/cc/Desktop/weijian/monit-admin-v2-pub/*
cp -r /Users/cc/Desktop/weijian/monit-admin-v2/dist/* /Users/cc/Desktop/weijian/monit-admin-v2-pub
cd /Users/cc/Desktop/weijian/monit-admin-v2-pub
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

