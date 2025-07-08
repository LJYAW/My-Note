package com.sino.base.common.util;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * POI HSSF Utility.
 * 
 * @author TSING
 */
public class HSSFUtil {
	
	public static double getDoubleValue(HSSFCell cell, double defaultValue){
		try{
			if (cell != null) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
					return cell.getNumericCellValue();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
					return cell.getNumericCellValue();
				return Double.parseDouble(cell.getRichStringCellValue().toString());
			}
			return defaultValue;
		}catch(Exception e){
			return defaultValue;
		}
	}
	public static int getIntegerValue(HSSFCell cell, int defaultValue){
		double value = getDoubleValue(cell, defaultValue);
		return (int)value;
	}
	public static Date getDateValue(HSSFCell cell, HSSFDataFormat dateFormat, Date defaultValue){
		Date date = defaultValue;
		if (cell != null) {
			short cellFormat = cell.getCellStyle().getDataFormat();
			String formatString = dateFormat.getFormat(cellFormat) ;
			if (HSSFDateUtil.isCellDateFormatted(cell) || 
				(formatString.indexOf("d") > -1) &&
				(formatString.indexOf("m") > -1) &&
				(formatString.indexOf("y") > -1)) {
				
				date = cell.getDateCellValue();
			}
		}
		return date;
	}
	public static String getStringValue(HSSFCell cell, String defaultValue) {
		String value = defaultValue;
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
				value = String.valueOf(cell.getNumericCellValue());
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				value = String.valueOf(cell.getNumericCellValue());
			else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				value = cell.getRichStringCellValue().toString();
			}
		}
		if(defaultValue != null){
			value = value.replaceAll("\\n", " ");//换行符号转换为空格
			value = value.replaceAll("^[　| ]*", "").replaceAll("\\?", "");//去掉左边的中英文空格
			value = value.replaceAll("[　| ]*$", "").replaceAll("%", "");//去掉右边的中英文空格
		}
		return value;
	}
}
