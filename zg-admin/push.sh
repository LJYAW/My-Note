
###
 # @Author: your name
 # @Date: 2021-08-20 12:01:40
 # @LastEditTime: 2021-10-29 12:02:30
 # @LastEditors: Please set LastEditors
 # @Description: In User Settings Edit
 # @FilePath: /cloud-media-web/push.sh
###
WEB=$(cd $(dirname $0); pwd)
WEB_PUB=$(cd $(dirname $0);cd ../zhongu-app-admin-pub; pwd)

echo $WEB
echo $WEB_PUB

cd $WEB
branch=$(git branch | sed -n -e 's/^\* \(.*\)/\1/p')
echo $branch

if [ $branch ==  "dev" ]
then
  npm run dev-build
  echo "打包 dev"
elif [ $branch == "master" ]
then
  npm run build
  echo "打包 master"
fi

cd $WEB_PUB
git checkout $branch
git pull origin $branch
echo $branch

rm -rf "${WEB_PUB}"/*
cp -r "${WEB}/dist"/* $WEB_PUB
git add .
git commit -m'ok'

if [ $branch == "dev" ]
then
  git push origin dev
  echo "已推送到远端 dev 分支"
elif [ $branch == "master" ]
then
  git push origin master
  echo "已推送到远端 master 分支"
fi

