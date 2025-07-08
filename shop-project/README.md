##国云商城
国云商城是基于uniapp和uniCloud开发的在线商城系统，目前已适配H5、微信小程序、安卓和ios。项目正在逐步完善中，如果在试用或购买后发现问题，欢迎您随时提出。
第一次上架uniCloud项目，如遇问题请加作者QQ咨询，请勿随意差评，感谢您的理解。

### 咨询与售后
* 作者QQ：472045067
* 如您已购买本插件，可加入售后服务群获得技术、部署与上架指导，技术指导不限于本插件
* 承接各类软件定制开发业务

###后台管理系统演示
[后台管理系统](https://tx-cloud-mix-mall-d6944c-1302673523.tcloudbaseapp.com/admin/index.html)  
帐号test 密码12345678

###微信小程序演示
![微信小程序](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-mix-mall-admin/451ccc60-d285-11ea-8a36-ebb87efcf8c0.jpg)

###H5演示
* h5版本尚未对接三方支付平台，支付、分享等功能无法使用，后续版本会提供支持。
![H5演示](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-mix-mall-admin/e51c3ad0-d284-11ea-81ea-f115fe74321c.png)

###App演示
* App所用开放平台与小程序非同一主体，所以微信登录帐号与微信小程序不互通，微信分享小程序无法使用，配置同一主体不存在此问题。
![安卓版本演示](https://7478-tx-cloud-mix-mall-d6944c-1302673523.tcb.qcloud.la/1599142095177614441.jpg)

##开始使用
1. 创建服务空间，详情参考[创建和绑定服务空间](https://uniapp.dcloud.net.cn/uniCloud/quickstart?id=%e5%88%9b%e5%bb%ba%e5%92%8c%e7%bb%91%e5%ae%9a%e6%9c%8d%e5%8a%a1%e7%a9%ba%e9%97%b4) 
2. 插件市场点击导入插件
3. 再HBuildX右键项目根目录uniCloud文件夹 -> 运行云服务空间初始化向导，按照步骤完成部署
4. 右键uniCloud/database/db_init.json->初始化云数据库
5. 运行即可体验

* 参数配置：根目录uniCloud/cloudfunctions/common/config, 配置完后右键config文件夹，上传公共模块
* 注意：H5涉及跨域问题解决 [H5中使用uniCloud的跨域处理](https://uniapp.dcloud.io/uniCloud/quickstart?id=useinh5)
* 特别注意：一定要把根目录config.js中地图key换成自己的，不然调用上限会导致无法添加收货地址[腾讯位置服务](https://lbs.qq.com/)

##发行微信小程序
1. 在HBuildx顶部菜单点击运行->运行到微信小程序 
2. 小程序后台配置request合法域名 [腾讯地图sdk](https://apis.map.qq.com)
3. 小程序后台配置request和uploadFile合法域名 [unicloud的白名单配置](https://uniapp.dcloud.io/uniCloud/quickstart?id=%e5%b0%8f%e7%a8%8b%e5%ba%8f%e4%b8%ad%e4%bd%bf%e7%94%a8unicloud%e7%9a%84%e7%99%bd%e5%90%8d%e5%8d%95%e9%85%8d%e7%bd%ae)
4. 上传版本并审核即可

##发行H5
1. 在HBuildx顶部菜单点击发行->网站-H5手机版
2. 在弹出的对话框中选中将编译后的资源部署到[uniCloud-前端网页托管]并选择云服务空间，需注意HBx直接部署网页托管需要最新版，老版本没有这个选项，可以自己到web控制台进行托管。
3. 点击发行，等待项目编译部署即可。 
* 如果您不想购买服务器，那就来uniCloud白嫖一波吧~


###本项目使用插件
根据插件发布日期排序，如果您需要查看文档，请至以下地址查看：
* [uni-id](https://uniapp.dcloud.io/uniCloud/uni-id)
* [unipay](https://uniapp.dcloud.io/uniCloud/unipay)
* [SwipeAction 滑动操作](https://ext.dcloud.net.cn/plugin?id=181)
* [Popup 弹出层](https://ext.dcloud.net.cn/plugin?id=329)
* [【wxs+renderjs实现】高性能的下拉刷新上拉加载组件](https://ext.dcloud.net.cn/plugin?id=343)
* [Simple-Loading](https://ext.dcloud.net.cn/plugin?id=397)
* [Parser富文本插件【全端支持】](https://ext.dcloud.net.cn/plugin?id=805)
* [Transition 过渡动画](https://ext.dcloud.net.cn/plugin?id=985)
* [simple-Cache 让缓存指定时间范围内有效的插件](https://ext.dcloud.net.cn/plugin?id=1129)
* [选择地图](https://ext.dcloud.net.cn/plugin?id=1133)
* [短信验证码-阿里云版](https://ext.dcloud.net.cn/plugin?id=1947)
* [BaseCloud - APP版本更新业务模块（动效）](https://ext.dcloud.net.cn/plugin?id=2510)





