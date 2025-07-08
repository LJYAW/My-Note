
/*----------------------------------------------------------------------------*\
|       Subject:    IP function lib                                            |
|       Version:    1.0                                                        |
|       Author:     熊冬升【xdsnet】【xdspower】                               |
|	FileName:   ipv4lib.js                                                 |
|       Functions:  若干用于ipv4的脚本代码                                     |
|------------------------------------------------------------------------------|
|       email:      xdsnet@gmail.com                                           |
|       date:       2007.10.26                                                 |
\*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*\
|                                                                              |
|  功能函数列表说明:                                                           |
|  getmark(num):返回数字,表示num对应多少位掩码,返回值为-1表示num为无效掩码数   |
|               num={128,192,224,240,248,252,254,255}                          |
|  getallmarknum(lanmark):返回数字,表示lanmark对应多少位掩码,                  |
|                         返回值为-1表示有掩码位无效                           |
|                         lanmark形如xxx.xxx.xxx.xxx,0<=xxx<=255               |
|  lanmarktonum(lanip):返回地址字符串,把xxx.xxx.xxx.xxx/xxx.xxx.xxx.xxx        |
|                      转换为xxx.xxx.xxx.xxx/nn形式,nn<=32                     |
|                      返回值为""表示传入地址无效                              |
|  lanmarkisnum(lanip):返回bool类型值,lanip为xxx.xxx.xxx.xxx/nn形式则返回true  |
|  lanstd(lanip):返回地址段字符串标准化形式,标准形式为xxx.xxx.xxx.xxx/nn       |
|  ipinlan(inip,inlan):返回bool类型值,判断inip是否在inlan地址段内              |
|                      inip的形式为xxx.xxx.xxx.xxx,0<=xxx<=255                 |
|                      inlan的形式为xxx.xxx.xxx.xxx/nn,0<=xxx<=255,0<=nn<=32   |
|                      inip在inlan内返回true                                   |
|  ipok(inip):返回bool类型值,判断inip是否为有效形式的ip或者ip地址段,           |
|             是则返回true                                                     |
|  iptonum(inip):返回数字,该值为地址字符串inip对应的数字值                     |
|  mininlan(inlan):返回数字,该值为网段inlan对应的最小ip地址数字值              |
|                  网段字符串inlan采用xxx.xxx.xxx.xxx/nn形式                   |
|  maxinlan(inlan):返回数字,该值为网段inlan对应的最大ip地址数字值              |
|                  网段字符串inlan采用xxx.xxx.xxx.xxx/nn形式                   |
|  iptolan(inip):返回inip所在网段字符串,inip采用xxx.xxx.xxx.xxx/nn的形式       |
|  numtoip(innum):返回IP地址字符串,innum是地址数字值,0<=innum<=0xffffffff      |
|                                                                              |
\*----------------------------------------------------------------------------*/

//ip段掩码数含有ip数
var ipmaxtable = new Array(0xffffffff,//0
0x7fffffff,//1
0x3fffffff,//2
0x1fffffff,//3
0x0fffffff,//4
0x07ffffff,//5
0x03ffffff,//6
0x01ffffff,//7
0x00ffffff,//8
0x007fffff,//9
0x003fffff,//10
0x001fffff,//11
0x000fffff,//12
0x0007ffff,//13
0x0003ffff,//14
0x0001ffff,//15
0x0000ffff,//16
0x00007fff,//17
0x00003fff,//18
0x00001fff,//19
0x00000fff,//20
0x000007ff,//21
0x000003ff,//22
0x000001ff,//23
0x000000ff,//24
0x0000007f,//25
0x0000003f,//26
0x0000001f,//27
0x0000000f,//28
0x00000007,//29
0x00000003,//30
0x00000001,//31
0x00000000//32
);

var oklan=new Array(255,//0
127,//1
63,//2
31,//3
15,//4
7,//5
3,//6
1,//7
0//8
);

var ipreg=/(\d+).(\d+).(\d+).(\d+)/;

//获得掩码段位数
function getmark(lan){
	var i=0;
	for (;i<9;i++){
		if ((~oklan[i]&255)==lan) return i;
	}
	return -1; 
}

//获得掩码位数
function getallmarknum(lanmark){
	if (!ipreg.test(lanmark)) return -1;
	var tmp=lanmark.split(".");
	if ( 0>getmark(tmp[0]*1) || 
             0>getmark(tmp[1]*1) || 
             0>getmark(tmp[2]*1) || 
             0>getmark(tmp[3]*1) ) return -1;
        return (getmark(tmp[0]*1)+getmark(tmp[1]*1)+getmark(tmp[2]*1)+getmark(tmp[3]*1));
}

//转换掩码表示方式
function lanmarktonum(lanip){
	var tmp=lanip.split("/");
	var rtvalue_mark=getallmarknum(tmp[1]);
	var rtvalue_ip=tmp[0];
	rtvalue=rtvalue_ip+"/"+rtvalue_mark;
	if (rtvalue_mark==-1)rtvalue="";
	return rtvalue;
}

//判断网段掩码形式
function lanmarkisnum(lanip){
	var tmp=lanip.split("/");
	var lanmark=tmp[1];
	if (lanmark == (lanmark*1).toString()) return true;
	return false;
}


//标准化网段表示形式成xxx.xxx.xxx.xxx/nn ,IP地址端/掩码位数
function lanstd(lanip){
	if (lanmarkisnum(lanip)) return lanip;
	return lanmarktonum(lanip); 
}
	
//判断ip地址是否属于某个ip地址段
function ipinlan(inip,inlan){
//inip:输入的单个地址
//inlan:ip地址段，型如xxx.xxx.xxx.xxx/nn,例如192.245.0.0/12
	var inipnum=iptonum(inip);
	var lanmin=mininlan(inlan);
	var lanmax=maxinlan(inlan);
	if (inipnum>=lanmin && inipnum<=lanmax) return true;
	return false;
}

//ip地址(段)形式有效性判断
function ipok(inip){
	var tmp=inip.split("/");
	var lanmark=tmp[1];//获取掩码
	var rtvalue=false;
	if (ipreg.test(tmp[0])) {
	//判断主体部分
    	tmp=tmp[0].split(".");
    	tmp[0]=tmp[0]*1;
    	tmp[1]=tmp[1]*1;
    	tmp[2]=tmp[2]*1;
    	tmp[3]=tmp[3]*1;
    	if (0<=tmp[0] && 255>=tmp[0] && 0<=tmp[1] && 255>=tmp[1] && 0<=tmp[2] && 255>=tmp[2] && 0<=tmp[3] && 255>=tmp[3])   rtvalue=true;
    	else return false;
    }
	if (rtvalue && !lanmark){
	//判断网段部分是否正确
		return true;
	}else{
		if (ipreg.test(lanmark)){
		//网段以掩码形式表示
			if (!ipok(lanmark)) return false;
			tmp=lanmark.split(".");
			tmp[0]=tmp[0]*1;
			tmp[1]=tmp[1]*1;
			tmp[2]=tmp[2]*1;
			tmp[3]=tmp[3]*1;
			tmp[0]=getmark(tmp[0]);
			tmp[1]=getmark(tmp[1]);
			tmp[2]=getmark(tmp[2]);
			tmp[3]=getmark(tmp[3]);
			if (tmp[0]<0 && tmp[1]<0 && tmp[2]<0 && tmp[3]<0) return false;
			if (tmp[0]<8 && (tmp[3]>0 && tmp[2]>0 && tmp[1]>0)) return false;
			if (tmp[1]<8 && (tmp[3]>0 && tmp[2]>0)) return false;
			if (tmp[2]<8 && tmp[3]>0 ) return false;
			return true;
		}else{
    	//网段以掩码位数表示
			tmp=lanmark*1;
			if ( lanmark != tmp) return false;
			if (tmp>=0 && tmp <=32) return true;
    		}
	}
return false; 
}

//ip地址转换成数字
function iptonum(inip){
//inip:输入的ip地址主体
    var tmp=inip.split(".");
    var rtvalue=tmp[0]*256*256*256+tmp[1]*256*256+tmp[2]*256+tmp[3]*1;
    return rtvalue;
}

//获取某段ip地址的最小ip数字
function mininlan(inlan){
//inlan:ip地址段
    var tmp=inlan.split("/");
    var rtvalue=iptonum(tmp[0]);
    return rtvalue;
}
	
//获取某段ip地址的最大ip数字
function maxinlan(inlan){
//inlan:ip地址段
    var tmp=inlan.split("/");
    var lanmark=tmp[1];
    if (!lanmark)lanmark=32;
    lanmark=lanmark*1;
    var rtvalue=iptonum(tmp[0]);
    rtvalue=rtvalue+ipmaxtable[lanmark];
    return rtvalue;
}

//获取某IP地址对应的网段
function iptolan(inip){
//inip:输入的ip地址，需要用形式xxx.xxx.xxx.xxx/nn表示
    var tmp=inip.split("/");
    var lanmark=tmp[1];
    var outlan="";
    if (!lanmark){
        lanmark=32;
        outlan=tmp[0]+"/"+lanmark;
        return outlan;
    }
    var ipvalue=iptonum(tmp[0]);
    var markvalue=32-lanmark*1;
    ipvalue=(ipvalue>>markvalue)<<markvalue;
    outlan=numtoip(ipvalue);
    outlan=outlan+"/"+lanmark;
    return outlan;
}

//数字值转换成ip地址
function numtoip(innum){
    var ipmod3=innum & 255;
    var ipmod2=(innum>>8)&255
    var ipmod1=(innum>>16)&255;
    var ipmod0=(innum>>24)&255;
    var outip=ipmod0+"."+ipmod1+"."+ipmod2+"."+ipmod3;
    return outip;
}


$.ligerDefaults.Grid.sorters['ip'] = function (in_ip1, in_ip2)
{
	var ip1 = trim(in_ip1);
	var ip2 = trim(in_ip2);
	if((ip1==null || ip1=="") && (ip2==null || ip2=="")){
		return 0;
	}
	if(ip1==null || ip1==""){
		return -1;
	}
	if(ip2==null || ip2==""){
		return 1;
	}
    return iptonum(ip1) < iptonum(ip2) ? -1 : iptonum(ip1) > iptonum(ip2) ? 1 : 0;
};

// -->
