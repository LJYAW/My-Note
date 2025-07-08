package com.sino.base.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 */
public class CommonUtils {

    /**
     * 根据实体类自动生成json模型
     */
    public static String getObjEntity(Object obj){
        StringBuilder sb = new StringBuilder();
        try {
            if(obj != null){
                Class<?> cla = obj.getClass();
                Field[] fields = cla.getDeclaredFields();
                String name = cla.getName();

                int num = 0;
                for (Object field : fields) {
                    num ++;
                    String prop = field.toString();
                    boolean isFiled = prop.contains(name);
                    if(isFiled){
                        String[] splits = prop.split(name);
                        if(num < fields.length){
                            sb.append(splits[1].substring(1));
                            sb.append(",");
                        }else {
                            sb.append(splits[1].substring(1));
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 根据实体类自动生成json模型，将主键改为id
     */
    public static String getEntityJson(Object obj){
        StringBuilder sb = new StringBuilder();
        String pkName = CommonUtils.getPKName(obj.getClass());
        try {
	        Class<?> cla = obj.getClass();
	        Field[] fields = cla.getDeclaredFields();
	        String name = cla.getName();

	        int num = 0;
	        for (Object field : fields) {
	            num ++;
	            String prop = field.toString();
	            boolean isFiled = prop.contains(name);
	            if(isFiled){
	                String[] splits = prop.split(name);
	                String ss = splits[1].substring(1);
	                if(num < fields.length){
	                    if(ss.equals(pkName) ){
	                        sb.append("id");
	                        sb.append(",");
	                    }else{
	                        sb.append(ss);
	                        sb.append(",");
	                    }
	                }else {
	                    sb.append(ss);
	                }
	            }
	        }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获得实体类主键
     */
    public static String getPKName(Class entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field f : fields) {
            Annotation[] annotations = f.getAnnotations();
            if (annotations.length <= 0) {
                String name = f.getName();
                String setMethodName = "get"
                        + StringUtils.left(name, 1).toUpperCase()
                        + StringUtils.substring(name, 1);
                try {
                    Method method = entityClass.getDeclaredMethod(setMethodName);
                    annotations = method.getAnnotations();
                } catch (Exception e) {
                    // do nothing.
                }
            }
            for (Annotation anno : annotations) {
                if (anno.toString().contains("@javax.persistence.Id()"))
                    return f.getName();
            }
        }
        return null;
    }

    /**
     * 获得通用编码，如订单编码、合同编码
     */
    public static String getSinoCommonNo(String firstCode,String secondCode){
        StringBuffer sinoCommonNo = new StringBuffer();
        if(firstCode != null && firstCode.length() > 0){
            sinoCommonNo.append(firstCode);
            sinoCommonNo.append("-");
        }
        if(secondCode != null && secondCode.length() > 0 ){
            sinoCommonNo.append(secondCode);
            sinoCommonNo.append("-");
        }

        String systemCurrentTime = DateUtil.getContinueDate();
        sinoCommonNo.append(systemCurrentTime);
        return sinoCommonNo.toString();
    }


    public static void main(String[] args) {
////        String entityJson = CommonUtils.getEntityJson(new MspContractEntity());
////        System.out.println(entityJson);
////        String pkName = CommonUtils.getPKName(MspContractEntity.class);
////        System.out.println(pkName);
//        String sinoCommonNo = CommonUtils.getSinoCommonNo("CMI", "SINO");
//        System.out.println(sinoCommonNo);
        String systemCurrentDateTime = DateUtil.getSystemCurrentDateTime();
        System.out.println(systemCurrentDateTime);
    }
}
