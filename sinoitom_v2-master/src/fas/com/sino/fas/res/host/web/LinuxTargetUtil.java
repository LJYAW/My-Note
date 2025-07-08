package com.sino.fas.res.host.web;

import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.StringUtils.split;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.fas.res.host.web
 * @ClassName: LinuxTargetUtil
 * @auther: Mr.Lp
 * @date: 2020/5/21 9:32
 * @desc: Linux指令采集部分方法封装
 * Created with: IntelliJ IDEA
 */

public class LinuxTargetUtil {

    //访问检查以及搜索添加按钮点击后获取Linux版本（回显页面使用）
    public static String getLinuxOSType(JschUtils jschUtils){

        String linuxOSType = null;
        try {
            jschUtils.initShellChannel();
            jschUtils.getPrompt();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        String osRelaseCmd = "cat /etc/redhat-release";	//获取linux版本	(centOS可用SUSE不可用)
        String osRelaseCmdResult = null;
        try {
            osRelaseCmdResult = jschUtils.sendCmd(osRelaseCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String osRelase[] = getStrResult(osRelaseCmdResult);
        String str = null;
        //第一行为命令不进行解析。
        if(osRelase.length>=2){
            str = osRelase[1];
            if(str.contains("cat: /etc/redhat-release:")){
                String osRelaseCmd2 = "lsb_release -a";	//获取linux版本(对于CentOS和SUSE都适用,部分CentOS7.X找不到命令，暂时沿用上面方法)
                String osRelaseCmdResult2 = null;
                try {
                    osRelaseCmdResult2 = jschUtils.sendCmd(osRelaseCmd2);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String osRelase2[] = getStrResult(osRelaseCmdResult2);
                //第一行为命令不进行解析。
                if(osRelase2.length>=2){
                    for (int j=1; j<osRelase2.length-1; j++) {
                        //将所有一个或者多个空格转换成一个空格
                        String newStr = osRelase2[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                        if(newStr.contains("Description:")){		//linux版本	CentOS release 6.10 (Final)
                            str = split(newStr,": ")[1];
                        }
                    }
                }
            }
            if(str.startsWith("Red Hat")){
                linuxOSType = "Red Hat";
                //jo.put("linuxOSType", "Red Hat");
            }else if(str.startsWith("CentOS")){
                linuxOSType = "CentOS";
                //jo.put("linuxOSType", "CentOS");
            }else if(str.startsWith("SUSE")){
                linuxOSType = "SUSE";
                //jo.put("linuxOSType", "SUSE");
            }else if(str.startsWith("Ubuntu")){
                linuxOSType = "Ubuntu";
                //jo.put("linuxOSType", "Ubuntu");
            }

        }

        return linuxOSType;

    }

    /**
     * 根据ip判断当前ip是否能够ping通
     * 
     * @param ip
     * @return
     */
    public static boolean isConnect(String ip) {
        boolean bool = false;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping " + ip);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                // 优化速度
                if (line.indexOf("请求超时") >= 0) {
                    // System.out.println(ip + "网络断开，时间 " + new Date());
                    return false;
                }
            }
            is.close();
            isr.close();
            br.close();


            if (null != sb && !sb.toString().equals("")) {
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    // System.out.println(ip + "网络正常 ，时间" + new Date());
                    bool = true;
                } else {
                    // 网络不畅通
                    // System.out.println(ip + "网络断开，时间 " + new Date());
                    bool = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 获取CPU信息
     * @param jschUtils
     * @param host
     * @return
     */
    public static List<ResHostCpu> getCpus(JschUtils jschUtils, ResHosts host){

        List<ResHostCpu> cpuList = new ArrayList<ResHostCpu>();

        String cpuWidthCmd = "getconf LONG_BIT";	//获取CPU位数
        String cpuWidthCmdResult = null;
        try {
            cpuWidthCmdResult = jschUtils.sendCmd(cpuWidthCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuWidth[] = getStrResult(cpuWidthCmdResult);
        int strCpuWidth = 0 ;
        //第一行为命令不进行解析。
        if(cpuWidth.length>=2){
            strCpuWidth = Integer.parseInt(cpuWidth[1]);
        }

        String cpuQtyCmd = "grep -c 'processor' /proc/cpuinfo";	//获取CPU数量
        String cpuQtyCmdResult = null;
        try {
            cpuQtyCmdResult = jschUtils.sendCmd(cpuQtyCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuQty[] = getStrResult(cpuQtyCmdResult);
        int strCpuQty = 0;
        //第一行为命令不进行解析。
        if(cpuQty.length>=2){
            strCpuQty = Integer.parseInt(cpuQty[1]);
        }

        String cpuStructCmd = "lscpu";	//获取cpu架构（例：x86）
//			获取命令结果
        String cpuStructCmdResult = null;
        try {
            cpuStructCmdResult = jschUtils.sendCmd(cpuStructCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuStruct[] = getStrResult(cpuStructCmdResult);
        String strCpuStruct = null;
        //第一行为命令不进行解析。
        if(cpuStruct.length>=2){
            for (int i=1; i<cpuStruct.length-1; i++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr1 = cpuStruct[i].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(newStr1.contains("Architecture")){
                    strCpuStruct = split(newStr1,": ")[1];
                }
            }

        }

        //可用来扩展ＣＰＵ的信息（一个服务器有一个或多个CPU的情况可用）
        String cpuCmd = "cat /proc/cpuinfo";
//				获取命令结果
        String cpuCmdResult = null;
        try {
            cpuCmdResult = jschUtils.sendCmdExpect(cpuCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.out.println(cpuCmdResult);

        String cpus[] = getStrResult(cpuCmdResult);

        int num=0;
        //第一行为命令不进行解析
        if(cpus.length>1){
            ResHostCpu hostCpu = null;
            for (int j=1; j<cpus.length-1; j++) {

                //将所有一个或者多个空格转换成一个空格
                String newStr = cpus[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(newStr.startsWith("processor")){		//系统中逻辑处理核的编号
                    hostCpu = new ResHostCpu();
                    hostCpu.setHostId(host.getHostId());

                    hostCpu.setCpuWidth(strCpuWidth);

                    hostCpu.setCpuQty(strCpuQty);

                    hostCpu.setCpuStruct(strCpuStruct);


                    if(hostCpu!=null){
                        cpuList.add(hostCpu);
                    }

                    num++;
                }
//						if(newStr.startsWith("core id")){	//当前物理核在其所处CPU中的编号
//							String coreId = split(newStr,": ")[1];
//							hostCpu.setCpuCoreId("cpu_"+coreId);
//						}
                if(newStr.startsWith("apicid")){	//用来区分不同逻辑核的编号，系统中每个逻辑核的此编号必然不同，此编号不一定连续
                    String coreId = split(newStr,": ")[1];
                    hostCpu.setCpuCoreId("cpu_"+coreId);
                }
                if(newStr.contains("vendor_id")){	//厂商
                    String cpuVendor = split(newStr,": ")[1];
                    hostCpu.setCpuVendor(cpuVendor);
                }
                if(newStr.contains("model name")){	//型号
                    String cpuModel = split(newStr,": ")[1];
                    hostCpu.setCpuModel(cpuModel);
                }
                if(newStr.contains("cpu cores")){	//内核数量
                    String cpuCoreQty = split(newStr,": ")[1];
                    hostCpu.setCpuCoreQty(Integer.parseInt(cpuCoreQty));
                }
            }
            if(hostCpu.getCpuCoreId()==""||hostCpu.getCpuCoreId()==null){
                hostCpu.setCpuCoreId("cpu_"+(num-1));
            }

            cpuList.add(hostCpu);

        }

        return cpuList;
    }

    /**
     * 获取FileSystem信息
     * @param jschUtils
     * @param host
     * @return
     */
    public static List<ResHostFileSystem> getfileSys(JschUtils jschUtils, ResHosts host){
        List<ResHostFileSystem> fileSyslist = new ArrayList<ResHostFileSystem>();
        String diskCmd = "df -k";	//获取分区信息
//			获取命令结果
        String diskCmdResult = null;
        try {
            diskCmdResult = jschUtils.sendCmd(diskCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int num = 0;
        int total = 0;
        int qty= 0;
        String disks[] = getStrResult(diskCmdResult);
        //第一行为命令不进行解析。
        if(disks.length>=2){
            //第二行为字段名不进行解析，从第三行开始
            String ss = null;
            for (int j=2; j<disks.length-1; j++) {
                ResHostFileSystem hostFileSystem = new ResHostFileSystem();

                //将所有一个或者多个空格转换成一个空格
                String newStr = disks[j].replaceAll("\\s+", " ").replaceAll("\t+", " ");
//	                   System.out.println(newStr);
                if(newStr.contains(" ")){
                    String str = newStr.split(" ")[0];	//文件系统

                    if(str.equals("")){
                        str = ss;
                    }
                    hostFileSystem.setFilesystem(str);
                    String str1 = newStr.split(" ")[1];		//容量
                    hostFileSystem.setSize(Long.parseLong(str1));

                    long bytes = 0;		//字节数
                    bytes = Long.parseLong(str1) * 1024 ;
                    hostFileSystem.setBytes(bytes);

                    String str2 = newStr.split(" ")[2];		//已用
                    hostFileSystem.setUsedSize(Long.parseLong(str2));
                    String str3 = newStr.split(" ")[3];		//可用
                    hostFileSystem.setAvailSize(Long.parseLong(str3));
                    String str4 = newStr.split(" ")[4];		//已用%
                    hostFileSystem.setDiskUse(str4);
                    String str5 = newStr.split(" ")[5];		//挂载点（磁盘名）
                    hostFileSystem.setMountPoint(str5);
                    hostFileSystem.setFileSysId(UUID.randomUUID().toString());
                    hostFileSystem.setHostId(host.getHostId());
                    fileSyslist.add(hostFileSystem);

                }else{
                    //System.out.println(newStr);	//磁盘分区
                    ss = newStr;
                    num ++;
                }
                total++;

            }

            qty = total - num ;		//磁盘分区个数
            System.out.println(qty+"***");

        }
        return fileSyslist;
    }


    /**
     * 获取Interface信息
     * @param jschUtils
     * @param host
     * @return
     */
    public static List<ResHostInterface> getInterfaces(JschUtils jschUtils, ResHosts host){
        List<ResHostInterface> infoList = new ArrayList<ResHostInterface>();
        String ifconfigCmd = "ifconfig";	//获取网络接口信息
//			获取命令结果
        String ifconfigCmdResult = null;
        try {
            ifconfigCmdResult = jschUtils.sendCmd(ifconfigCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String ifconfig[] = getStrResult(ifconfigCmdResult);
        int num=0;
        int flag=0;

        //第一行为命令不进行解析
        if(ifconfig.length>=2){
            ResHostInterface hostInterface = null;
            String ipaddr = null;
            String bcast = null;
            String mask = null;
            for (int j=1; j<ifconfig.length-1; j++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr = ifconfig[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(host.getOsVersionType().equals("7.X")){

                    if(newStr.contains("flags=")){
                        num++;
                        if(num>flag+1){
                            hostInterface.setIpAddress(ipaddr);
                            hostInterface.setBcastAddress(bcast);
                            hostInterface.setMaskAddress(mask);
                            infoList.add(hostInterface);
                            flag++;
                        }

                        hostInterface = new ResHostInterface();
                        hostInterface.setHostId(host.getHostId());

                    }
                    if(newStr.contains("flags=")){ //网络接口名称
                        String infoName = split(newStr,":")[0];
                        hostInterface.setInfoName(infoName);
                    }
                    if(newStr.contains("ether ")){	//MAC地址
                        String macAddress = split(newStr,"ether ")[1];
                        int i = macAddress.indexOf(" ");
                        macAddress = macAddress.substring(0, i);
                        hostInterface.setMacAddress(macAddress);
                    }

                    if(newStr.startsWith("inet ")){	//ip地址
                        ipaddr = split(newStr,"inet ")[1];
                        int i = ipaddr.indexOf(" ");
                        ipaddr = ipaddr.substring(0, i);

                    }
                    if(newStr.contains("broadcast ")){	//广播地址
                        bcast = split(newStr,"broadcast ")[1];

                    }
                    if(newStr.contains("netmask ")){	//掩码地址
                        mask = split(newStr,"netmask ")[1];
                        int i = mask.indexOf(" ");
                        mask = mask.substring(0, i);

                    }

                    if(newStr.startsWith("inet6 ")){	//ip v6地址
                        String ipv6 = split(newStr,"inet6 ")[1];
                        int i = ipv6.indexOf(" ");
                        ipv6 = ipv6.substring(0, i);
                        hostInterface.setIpv6(ipv6);
                    }
                    if(newStr.contains("lo:")){
                        break;
                    }

                }else {
                    if((j-1)%8==0){	//8,16,24...
                        hostInterface = new ResHostInterface();
                        hostInterface.setHostId(host.getHostId());
                    }
                    if(newStr.contains("Link encap:Ethernet")){ //网络接口名称
                        String infoName = split(newStr," ")[0];
                        hostInterface.setInfoName(infoName);
                    }
                    if(newStr.contains("HWaddr")){	//MAC地址
                        String macAddress = split(newStr,"HWaddr ")[1];
                        hostInterface.setMacAddress(macAddress);
                    }
                    if(newStr.contains("inet addr")){	//ip地址
                        String ipaddr2 = split(newStr,"inet addr:")[1];
                        int i = ipaddr2.indexOf(" ");
                        ipaddr2 = ipaddr2.substring(0, i);
                        hostInterface.setIpAddress(ipaddr2);
                    }
                    if(newStr.contains("Bcast")){	//广播地址
                        String bcast2 = split(newStr,"Bcast:")[1];
                        int i = bcast2.indexOf(" ");
                        bcast2 = bcast2.substring(0, i);
                        hostInterface.setBcastAddress(bcast2);
                    }
                    if(newStr.contains("Mask")){	//掩码地址
                        String mask2 = split(newStr,"Mask:")[1];
                        hostInterface.setMaskAddress(mask2);
                    }
                    if(newStr.contains("inet6 addr")){	//ip v6地址
                        String ipv6 = split(newStr,"inet6 addr: ")[1];
                        ipv6 = split(ipv6," ")[0];
                        ipv6 = split(ipv6,"/")[0];
                        hostInterface.setIpv6(ipv6);
                    }
                    if(newStr.contains("lo")){
                        break;
                    }
                    if((j-1)%8==7){	//8,16,24...
                        infoList.add(hostInterface);
                    }

                }

            }

        }
        return infoList;
    }


    /**
     * 获取所有disk名称
     * @param jschUtils
     * @param host
     * @return
     */
    public static String[] getDiskNames(JschUtils jschUtils,ResHosts host){

        String checkCmd = "lsblk";
//			获取命令结果
        String cmdResult = null;
        try {
            cmdResult = jschUtils.sendCmd(checkCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.out.println(cmdResult);

        String disk[] = getStrResult(cmdResult);
        String str[] = new String [disk.length];

        int i = 0;
        //第一行为命令不进行解析
        if(disk.length>=2){
            //第二行为字段名不解析
            for (int j=2; j<disk.length-1; j++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr = disk[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                String sd = newStr.split(" ")[5];
                if(sd.equals("disk")){
                    str[i] = newStr.split(" ")[0];
                    i++;
                }
            }
        }
        String newArrStr[] = new String [i];
        for (int j = 0; j < newArrStr.length; j++) {
            newArrStr[j] = str[j];
        }

        return newArrStr;
    }

    /**
     * 获取disk信息(操作系统类型为：CentOS，redhat)
     * @param jschUtils
     * @param host
     * @return
     */
    public static List<ResHostDisks> getDisks(JschUtils jschUtils, ResHosts host){
        List<ResHostDisks> disksList = new ArrayList<ResHostDisks>();

        String newArrStr[] = getDiskNames(jschUtils,host);
        String diskName = null;
        if(newArrStr.length>0){
            for (int j = 0; j < newArrStr.length; j++) {
                diskName = newArrStr[j];
                String checkCmd = "fdisk -l "+"/dev/"+diskName;		//磁盘信息
//					获取命令结果
                String cmdResult = null;
                try {
                    cmdResult = jschUtils.sendCmd(checkCmd);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String disk[] = getStrResult(cmdResult);
                ResHostDisks hostDisks = new ResHostDisks();
                if(disk.length>=2){
                    for (int i=1; i<disk.length-1; i++) {

                        //将所有一个或者多个空格转换成一个空格
                        String newDisk = disk[i].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();

                        if(newDisk.startsWith("Disk /")){	//磁盘大小
                            String size = split(newDisk,": ")[1];
                            int index = size.indexOf(",");
                            size = size.substring(0, index);
                            hostDisks.setSize(size);
                        }
                        if(newDisk.startsWith("Disk /")){	//字节数
                            String bytes = split(newDisk," bytes")[0];
                            bytes = split(bytes,", ")[1];
                            hostDisks.setBytes(Long.parseLong(bytes));
                        }
                        if(newDisk.startsWith("Disk identifier:")){	//磁盘标识符
                            String identifier = split(newDisk,"Disk identifier:")[1].trim();
                            hostDisks.setDiskIdentifier(identifier);
                        }
                        if(host.getOsVersionType().equals("7.X")){

                            if(newDisk.startsWith("Disk /")){	//总扇区个数
//									String totalSectors = split(newDisk," sectors")[0];
                                int index = newDisk.lastIndexOf(" ");
                                String totalSectors = newDisk.substring(0,index);
                                totalSectors = split(newDisk," bytes, ")[1];
                                if(totalSectors.contains("sectors")){
                                    totalSectors = totalSectors.split(" sectors")[0];
                                }
                                hostDisks.setTotalSectors(Long.parseLong(totalSectors));
                            }
                            if(newDisk.startsWith("Units")){	//每个扇区的容量
                                int index = newDisk.lastIndexOf(" ");
                                String units = newDisk.substring(0,index);
                                int lastIndex = units.lastIndexOf(" ");
                                units = newDisk.substring(lastIndex+1);
                                units = split(units," ")[0];
                                //System.out.println(units);
//									DecimalFormat df = new DecimalFormat("0.000");//格式化小数
//									String cylinders = df.format((float)Integer.parseInt(units)/1000);//返回的是String类型
                                hostDisks.setSectorsSize(Long.parseLong(units));
                            }

                        }else{

                            if(newDisk.contains("heads")){	//磁面个数
                                String diskHeads = split(newDisk," heads")[0];
                                hostDisks.setDiskHeads(Integer.parseInt(diskHeads));
                            }
                            if(newDisk.contains("sectors/track")){
                                String sectors = split(newDisk," sectors/track")[0];	//每个磁道的扇区个数
                                int index = sectors.indexOf(",");
                                sectors = sectors.substring(index+1).trim();
                                hostDisks.setDiskSectors(Integer.parseInt(sectors));
                                String cylinders = split(newDisk,"sectors/track, ")[1];	//磁柱个数
                                int otIndex = cylinders.indexOf(" ");
                                cylinders = cylinders.substring(0,otIndex);
                                hostDisks.setDiskCylinders(Integer.parseInt(cylinders));
                            }
                            if(newDisk.startsWith("Units")){	//每个磁柱的容量
                                int index = newDisk.lastIndexOf(" ");
                                String units = newDisk.substring(0,index);
                                int lastIndex = units.lastIndexOf(" ");
                                units = newDisk.substring(lastIndex+1);
                                units = split(units," ")[0];
                                //System.out.println(units);
//									DecimalFormat df = new DecimalFormat("0.000");//格式化小数
//									String cylinders = df.format((float)Integer.parseInt(units)/1000);//返回的是String类型
                                hostDisks.setCylinderSize(Long.parseLong(units));
                            }

                        }

                    }
                    hostDisks.setHostId(host.getHostId());
                    disksList.add(hostDisks);
                }

            }
        }

        return disksList;
    }


    /**
     * 获取disk信息(操作系统类型为：SUSE)
     * @param jschUtils
     * @param host
     * @return
     */
    public static List<ResHostDisks> getSUSEDisks(JschUtils jschUtils,ResHosts host){
        List<ResHostDisks> disksList = new ArrayList<ResHostDisks>();

        String checkCmd = "fdisk -l";		//磁盘信息
//			获取命令结果
        String cmdResult = null;
        try {
            cmdResult = jschUtils.sendCmd(checkCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String disk[] = getStrResult(cmdResult);
        String str[] = new String [disk.length];

        int count = 0;
        int totalNum = 0;

        //第一行为命令不进行解析
        if(disk.length>=2){
            for (int j=1; j<disk.length-1; j++) {

                //将所有一个或者多个空格转换成一个空格
                String newStr = disk[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(newStr.startsWith("Disk")){
                    count ++;
                    int newnum = 6*count-5;
                    int flag = 0;
                    int number = j;
                    for (int i = newnum; i < disk.length-1; i++) {
                        totalNum ++;
                        String newStr1 = disk[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                        j++;
                        str[i-1] = newStr1;
                        flag ++;
                        if(flag==6){
                            break;
                        }

                    }
                    j = number+5;
                }
            }
        }

        String newDisk[] = new String [totalNum];
        for (int i = 0; i < newDisk.length; i++) {
            newDisk[i] = str[i];
        }
        ResHostDisks hostDisks = null;
        for (int i = 0; i < newDisk.length; i++) {

            if(i%6 == 0){
                hostDisks = new ResHostDisks();
                hostDisks.setHostId(host.getHostId());
            }
            //System.out.println(newDisk[i]);
            if(newDisk[i].startsWith("Disk /")){	//磁盘大小
                String size = split(newDisk[i],": ")[1];
                int index = size.indexOf(",");
                size = size.substring(0, index);
                hostDisks.setSize(size);
            }
            if(newDisk[i].startsWith("Disk /")){	//字节数
                String bytes = split(newDisk[i]," bytes")[0];
                bytes = split(bytes,", ")[1];
                hostDisks.setBytes(Long.parseLong(bytes));
            }
            if(newDisk[i].contains("heads")){	//磁面个数
                String diskHeads = split(newDisk[i]," heads")[0];
                hostDisks.setDiskHeads(Integer.parseInt(diskHeads));
            }
            if(newDisk[i].contains("sectors/track")){
                String sectors = split(newDisk[i]," sectors/track")[0];	//每个磁道的扇区个数
                int index = sectors.indexOf(",");
                sectors = sectors.substring(index+1).trim();
                hostDisks.setDiskSectors(Integer.parseInt(sectors));

                String cylinders = split(newDisk[i],"sectors/track, ")[1];	//磁柱个数
                int otIndex = cylinders.indexOf(" ");
                cylinders = cylinders.substring(0,otIndex);
                hostDisks.setDiskCylinders(Integer.parseInt(cylinders));

                String totalSectors = split(newDisk[i],", total ")[1];	//总扇区个数
                totalSectors = totalSectors.split(" sectors")[0];
//	               	int totalIndex = cylinders.indexOf(" ");
//	               	totalSectors = totalSectors.substring(0,totalIndex);
                hostDisks.setTotalSectors(Long.parseLong(totalSectors));
            }
            if(newDisk[i].startsWith("Units")){	//每个扇区的容量
                int index = newDisk[i].lastIndexOf(" ");
                String units = newDisk[i].substring(0,index);
                int lastIndex = units.lastIndexOf(" ");
                units = newDisk[i].substring(lastIndex+1);
                units = split(units," ")[0];
                //System.out.println(units);
//					DecimalFormat df = new DecimalFormat("0.000");//格式化小数
//					String cylinders = df.format((float)Integer.parseInt(units)/1000);//返回的是String类型
                hostDisks.setSectorsSize(Long.parseLong(units));
            }
            if(newDisk[i].startsWith("Disk identifier:")){	//磁盘标识符
                String identifier = split(newDisk[i],"Disk identifier:")[1].trim();
                hostDisks.setDiskIdentifier(identifier);
            }
            if(i%6 == 5){
                disksList.add(hostDisks);
            }
        }
        return disksList;
    }

    /**
     * 获取命令结果解析并存储到实体类中
     * @param jschUtils
     * @param hosts
     * @return
     */
    public static ResHosts getCmdResult(JschUtils jschUtils,ResHosts hosts){

        ResHosts resHosts = new ResHosts();
        resHosts = hosts;
		    /*String cpuCmd = "cat /proc/cpuinfo";	//获取CPU相关的信息
//			获取命令结果
			String cpuCmdResult = null;
			try {
				cpuCmdResult = jschUtils.sendCmd(cpuCmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String cpu[] = getStrResult(cpuCmdResult);
	        //第一行为命令不进行解析。
	        if(cpu.length>=2){
	                for (int j=1; j<cpu.length-1; j++) {
	                    //将所有一个或者多个空格转换成一个空格
	                    String newStr = cpu[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
	                    if(newStr.contains("vendor_id")){	//厂商
	                    	String cpuVendor = split(newStr,": ")[1];
	                    	resHosts.setCpuVendor(cpuVendor);
	                    }
	                    if(newStr.contains("model name")){	//型号
	                    	String cpuModel = split(newStr,": ")[1];
	                    	resHosts.setCpuModel(cpuModel);
	                    }
	                    if(newStr.contains("cpu cores")){	//内核数量
	                    	String cpuCoreQty = split(newStr,": ")[1];
	                    	resHosts.setCpuCoreQty(cpuCoreQty);
	                    }
	                }

	        }*/


        String cpuWidthCmd = "getconf LONG_BIT";	//获取CPU位数
        String cpuWidthCmdResult = null;
        try {
            cpuWidthCmdResult = jschUtils.sendCmd(cpuWidthCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuWidth[] = getStrResult(cpuWidthCmdResult);
        //第一行为命令不进行解析。
        if(cpuWidth.length>=2){
            resHosts.setCpuWidth(cpuWidth[1]);
        }


        String cpuStructCmd = "lscpu";	//获取cpu架构（例：x86）
//			获取命令结果
        String cpuStructCmdResult = null;
        try {
            cpuStructCmdResult = jschUtils.sendCmd(cpuStructCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuStruct[] = getStrResult(cpuStructCmdResult);

        //第一行为命令不进行解析。
        if(cpuStruct.length>=2){
            for (int j=1; j<cpuStruct.length-1; j++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr = cpuStruct[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(newStr.contains("Architecture")){
                    resHosts.setCpuStruct(split(newStr,": ")[1]);
                }
            }

        }


        String cpuQtyCmd = "grep -c 'processor' /proc/cpuinfo";	//获取CPU数量
        String cpuQtyCmdResult = null;
        try {
            cpuQtyCmdResult = jschUtils.sendCmd(cpuQtyCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cpuQty[] = getStrResult(cpuQtyCmdResult);
        //第一行为命令不进行解析。
        if(cpuQty.length>=2){
            resHosts.setCpuQty(Integer.parseInt(cpuQty[1]));
        }



        String osRelaseCmd = "cat /etc/redhat-release";	//获取linux版本	(centOS可用SUSE不可用)
        String osRelaseCmdResult = null;
        try {
            osRelaseCmdResult = jschUtils.sendCmd(osRelaseCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String osRelase[] = getStrResult(osRelaseCmdResult);
        String str = null;
        //第一行为命令不进行解析。
        if(osRelase.length>=2){
            str = osRelase[1];
            if(str.contains("cat: /etc/redhat-release:")){
                String osRelaseCmd2 = "lsb_release -a";	//获取linux版本(对于CentOS和SUSE都适用,部分CentOS7.X找不到命令，暂时沿用上面方法)
                String osRelaseCmdResult2 = null;
                try {
                    osRelaseCmdResult2 = jschUtils.sendCmd(osRelaseCmd2);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String osRelase2[] = getStrResult(osRelaseCmdResult2);
                //第一行为命令不进行解析。
                if(osRelase2.length>=2){
                    for (int j=1; j<osRelase2.length-1; j++) {
                        //将所有一个或者多个空格转换成一个空格
                        String newStr = osRelase2[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                        if(newStr.contains("Description:")){		//linux版本	CentOS release 6.10 (Final)
//		                    	String osRelaseDesc = split(newStr,": ")[1];
                            String osRelaseDesc = split(newStr,"Server ")[1];
                            resHosts.setOsRelease(osRelaseDesc);

                            if(str.startsWith("Red Hat")){
                                resHosts.setOsType("Red Hat");
                            }else if(str.startsWith("CentOS")){
                                resHosts.setOsType("CentOS");
                            }else if(str.startsWith("SUSE")){
                                resHosts.setOsType("SUSE");
                            }else if(str.startsWith("Ubuntu")){
                                resHosts.setOsType("Ubuntu");
                            }
                        }
                        if(newStr.contains("Release:")){		//linux版本	CentOS release 6.10 (Final)
                            String osVersionType = split(newStr,": ")[1];
                            if(osVersionType.contains(".")){
                                int s = osVersionType.indexOf(".");
                                osVersionType = osVersionType.substring(0,s+1);
                            }else{
                                osVersionType = osVersionType+".";
                            }
                            resHosts.setOsVersionType(osVersionType+"X");
                        }
                    }
                }
            }else{
                if(str.startsWith("Red Hat")){
                    resHosts.setOsType("Red Hat");
                }else if(str.startsWith("CentOS")){
                    resHosts.setOsType("CentOS");
                }else if(str.startsWith("SUSE")){
                    resHosts.setOsType("SUSE");
                }else if(str.startsWith("Ubuntu")){
                    resHosts.setOsType("Ubuntu");
                }

                str = str.split("release ")[1];
                resHosts.setOsRelease(str);
                int s = str.indexOf(".");
                str = str.substring(0,s+1);
                resHosts.setOsVersionType(str+"X");
            }

        }


        String hostNameCmd = "uname -n";	//获取主机名称
        String hostNameCmdResult = null;
        try {
            hostNameCmdResult = jschUtils.sendCmd(hostNameCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String hostName[] = getStrResult(hostNameCmdResult);
        //第一行为命令不进行解析。
        if(hostName.length>=2){
            resHosts.setHostName(hostName[1]);
        }


        String osVersionCmd = "uname -r";	//获取操作系统版本
        String osVersionCmdResult = null;
        try {
            osVersionCmdResult = jschUtils.sendCmd(osVersionCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String osVersion[] = getStrResult(osVersionCmdResult);
        //第一行为命令不进行解析。
        if(osVersion.length>=2){
            resHosts.setOsVersion(osVersion[1]);
        }


        String osStructCmd = "uname -m";	//获取操作系统架构
        String osStructCmdResult = null;
        try {
            osStructCmdResult = jschUtils.sendCmd(osStructCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String osStruct[] = getStrResult(osStructCmdResult);
        //第一行为命令不进行解析。
        if(osStruct.length>=2){
            resHosts.setOsStruct(osStruct[1]);
        }


        String memCmd = "cat /proc/meminfo";	//获取Mem相关的信息
//			获取命令结果
        String memCmdResult = null;
        try {
            memCmdResult = jschUtils.sendCmd(memCmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String mem[] = getStrResult(memCmdResult);

        //第一行为命令不进行解析。
        if(mem.length>=2){
            for (int j=1; j<mem.length-1; j++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr = mem[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
                if(newStr.contains("MemTotal")){	//总内存
                    String memTotal = split(newStr,": ")[1].split(" kB")[0];
                    resHosts.setMemorySize(Long.parseLong(memTotal));
                }
            }

        }


        resHosts.setResClassCode(11);
        resHosts.setResClassName("计算资源");
        resHosts.setPowerStatus(1);
        resHosts.setHostStatus(1);

        return resHosts;

    }


    /**
     * 性能指标——获取CPU，内存信息
     * @param jschUtils
     * @return
     */
    public static ParamPerformance getPerformance(JschUtils jschUtils){

        ParamPerformance paramPerformance = new ParamPerformance();
        String cmd = "top -bn 1 -i";	//获取top信息
//			获取命令结果
        String cmdResult = null;
        try {
            cmdResult = jschUtils.sendCmdExpect(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String result[] = getStrResult(cmdResult);
        long memorySize = 0;
        long freeMemorySize = 0;
        long usedMemorySize = 0;
        String usedMemPencent = null;
        //第一行为命令不进行解析。
        if(result.length>1){
            for (int j=1; j<result.length-1; j++) {
                //将所有一个或者多个空格转换成一个空格
                String newStr = result[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();

                if(newStr.startsWith("Cpu(s):")){
                    String str = newStr.split(":")[1];
                    String[] cpuResult = str.split(",");
                    for (int i = 0; i < cpuResult.length; i++) {
                        if(cpuResult[i].contains("us")){	//用户所用CPU占比
                            String userUsed = cpuResult[i].split("us")[0].trim();
                            paramPerformance.setUserUsed(userUsed);
                        }
                        if(cpuResult[i].contains("sy")){	//内核使用CPU占比
                            String sysUsed = cpuResult[i].split("sy")[0].trim();
                            paramPerformance.setSysUsed(sysUsed);
                        }
                    }
                }

                if(newStr.startsWith("Mem:")){	//总内存
                    String str = newStr.split(":")[1];
                    String[] memResult = str.split(",");
                    for (int i = 0; i < memResult.length; i++) {
                        if(memResult[i].contains("total")){
                            String memTotal =  memResult[i].split("total")[0].trim();
                            memTotal = memTotal.split("k")[0].trim();
                            memorySize = Long.parseLong(memTotal);
                            paramPerformance.setMemorySize(memorySize);
                        }
                        if(memResult[i].contains("used")){
                            String usedMem =  memResult[i].split("used")[0].trim();
                            usedMem = usedMem.split("k")[0].trim();
                            usedMemorySize = Long.parseLong(usedMem);
                            paramPerformance.setUsedMemorySize(usedMemorySize);
                        }
                        if(memResult[i].contains("free")){
                            String freeMem =  memResult[i].split("free")[0].trim();
                            freeMem = freeMem.split("k")[0].trim();
                            freeMemorySize = Long.parseLong(freeMem);
                            paramPerformance.setFreeMemorySize(freeMemorySize);
                        }
                    }

                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数  保留两位小数
                    String usedPencent = df.format((float)usedMemorySize/memorySize*100);//返回的是String类型
                    usedMemPencent = usedPencent+"%";
                    paramPerformance.setUsedMem(usedMemPencent);
                }

            }

        }



        return paramPerformance;
    }

    /**
     * 将命令结果转换成String型
     * @param cmdResult
     * @return
     */
    public static String[] getStrResult(String cmdResult){
        //将一个或者多个“\r”去掉
        cmdResult = cmdResult.replaceAll("\r+","");
        //将一个或者多个“\n”转换成一个
        cmdResult = cmdResult.replaceAll("\n+","\n").trim();

        //List<ParamItems> paramItemList = new ArrayList<ParamItems>();

        String maipu[] = cmdResult.split("\n");
        return maipu;
    }


}
