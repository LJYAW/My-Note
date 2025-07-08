package com.sino.bpm.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ParseTest
 * @Description TODO
 * @Author fengyao
 * Date 2020/6/18 13:45
 * @Version 1.0
 **/
public class ParseTest {
    public static void main(String[] args) {
//        method1();
//        method2();
        method3();
    }

    private static void method1(){
        String str = "CPU utilization for five seconds: 25%/0%; one minute: 26%; five minutes: 26%";
        String startKeyWD = "five minutes:";
        String endKeyWD = "%";

        int startIndex = str.indexOf(startKeyWD);
        String substring = str.substring(startIndex);
        startIndex = substring.indexOf(startIndex);
        int endIndex = substring.indexOf(endKeyWD);
        startIndex = startIndex + startKeyWD.length()+1;
        String result = substring.substring(startIndex, endIndex).trim();
        System.out.println(result);

//        求26%
    }

    private static void method2(){
        String str = "Processor Pool Total:   73910696 Used:   30235064 Free:   43675632\n" +
                "      I/O Pool Total:   14680064 Used:   12457996 Free:    2222068\n" +
                "Driver te Pool Total:    1048576 Used:         40 Free:    1048536";
        String pattern = "Processor Pool Total:\\s+([0-9]{1,})\\s+";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);
        if (m.find()) {
            String group = m.group(0).trim();
            System.out.println("Found value: " + group );

            String resultKeyWords = "Processor Pool Total:";
            int startIndex = group.indexOf(resultKeyWords)+resultKeyWords.length()+1;
            String result = group.substring(startIndex);
            System.out.println(result);
        } else {
            System.out.println("NO MATCH");
        }
    }

    private static void method3(){
        String str = "Cisco IOS Software, C2960S Software (C2960S-UNIVERSALK9-M), Version 12.2(55)SE8, RELEASE SOFTWARE (fc2)";

//        OsType
//        String startKeyWD = "Cisco";
//        String endKeyWD = "Software";

//        OsName
//        String startKeyWD = "Software (";
//        String endKeyWD = "), ";

//        OsVersion
        String startKeyWD = "Version";
        String endKeyWD = ", RELEASE";

        int startIndex = str.indexOf(startKeyWD);
        String substring = str.substring(startIndex);

        startIndex = substring.indexOf(startIndex)+ startKeyWD.length()+1;
        int endIndex = substring.indexOf(endKeyWD);

        String result = substring.substring(startIndex, endIndex).trim();
        System.out.println(result);

    }

    private static void method4(){
        String str = "                Head    Total(b)     Used(b)     Free(b)   Lowest(b)  Largest(b)\n" +
                "Processor    17BD718    96741608    27804592    68937016    67371160    67210888\n" +
                "      I/O    7400000    12574720     9031656     3543064     3434008     3535816\n";

    }

}
