package com.sino.base.common.util;

import com.sino.base.system.entity.SysUser;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.springside.modules.utils.Reflections;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExpExcelUtil {
	/**
	 * 导出Excel的方法
	 *
	 * @param title   excel中的sheet名称
	 * @param headers 表头
	 * @param list    结果集
	 * @param attr    输出参数
	 * @param out     输出流
	 * @throws Exception
	 */

	@SuppressWarnings("rawtypes")
	public static void expExcel(String title, String[] headers, List list, String attr, OutputStream out)
			throws Exception {

		String[] nodeAtts = attr.split(",");
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		//sheet.setDefaultColumnWidth(18);
		for (int n = 0; n < headers.length; n++) {
			sheet.setColumnWidth(n, 4800);
		}
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		//font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		int dataRow = 0;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataRow);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = dataRow + 1;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(index);
				Object obj = list.get(i);
				for (int j = 0; j < nodeAtts.length; j++) {
					String value = getJsonColValue(obj, nodeAtts[j]);
					row.createCell(j).setCellValue(value);
				}
				index++;
			}
		}
		workbook.write(out);
	}


	/**
	 * 导出Excel的方法
	 *
	 * @param title        excel中的sheet名称
	 * @param headers      表头
	 * @param exlColWidths 每列宽度设置
	 * @param list         结果集
	 * @param attr         输出参数
	 * @param out          输出流
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void expExcel(String title, String[] headers, int[] exlColWidths, List list, String attr,
			OutputStream out) throws Exception {

		String[] nodeAtts = attr.split(",");
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		//sheet.setDefaultColumnWidth(18);
		for (int n = 0; n < exlColWidths.length; n++) {
			sheet.setColumnWidth(n, exlColWidths[n]);
		}

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		//font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		int dataRow = 0;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataRow);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = dataRow + 1;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(index);
				Object obj = list.get(i);
				for (int j = 0; j < nodeAtts.length; j++) {
					String value = getJsonColValue(obj, nodeAtts[j]);
					row.createCell(j).setCellValue(value);
				}
				index++;
			}
		}
		workbook.write(out);
	}

	/**
	 * 导出Excel的方法
	 *
	 * @param title        excel中的sheet名称
	 * @param imgTitle     图片标题
	 * @param headers      表头
	 * @param imgs         输出图片url
	 * @param exlColWidths 每列宽度设置
	 * @param list         结果集
	 * @param attr         输出参数
	 * @param out          输出流
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void expImgExcel(String title, String[] imgTitle, String[] headers, String[] imgs, int[] exlColWidths,
			List list, String attr, OutputStream out) throws Exception {

		String[] nodeAtts = attr.split(",");
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		//sheet.setDefaultColumnWidth(18);
		int colWth = exlColWidths.length;
		for (int n = 0; n < colWth; n++) {
			sheet.setColumnWidth(n, exlColWidths[n]);
		}

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//总标题样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFont.setFontHeightInPoints((short) 14);
		titleStyle.setFont(boldFont);

		//副标题样式
		HSSFCellStyle subTitleStyle = workbook.createCellStyle();
		subTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont subBoldFont = workbook.createFont();
		subBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		subBoldFont.setFontHeightInPoints((short) 12);
		subTitleStyle.setFont(subBoldFont);

		/*
		 * 产生表格总标题行
		 * CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
		 */

		HSSFRow titleRow = sheet.createRow(0);
		HSSFCell titleCell = titleRow.createCell(0);
		titleRow.setHeightInPoints(20);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colWth - 1));

		HSSFRow titleRow1 = sheet.createRow(1);
		titleRow1.setHeightInPoints(18);

		int stImgRows = 2;
		int endImgRows = 2;
		for (int n = 0, m = imgs.length; n < m; n++) {
			int regionWidth = (int) Math.floor(colWth / m);

			HSSFCell titleCell1 = titleRow1.createCell(n * regionWidth);
			titleCell1.setCellStyle(subTitleStyle);
			titleCell1.setCellValue(imgTitle[n]);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, n * regionWidth, regionWidth * (n + 1) - 1));
			//生成图片
			endImgRows = stImgRows + 15;

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			BufferedImage bufferImg = ImageIO.read(new File(imgs[n]));
			ImageIO.write(bufferImg, "png", byteArrayOut);

			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255, (short) (n * regionWidth), stImgRows,
					(short) (regionWidth * (n + 1) - 1), endImgRows);
			patriarch.createPicture(anchor,
					workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
		}

		int dataRow = endImgRows + 2;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataRow);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = dataRow + 1;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(index);
				Object obj = list.get(i);
				for (int j = 0; j < nodeAtts.length; j++) {
					String value = getJsonColValue(obj, nodeAtts[j]);
					row.createCell(j).setCellValue(value);
				}
				index++;
			}
		}
		workbook.write(out);
	}


	/**
	 * 导出Excel的方法
	 *
	 * @param title             excel中的sheet名称
	 * @param imgTitle          图片标题
	 * @param headers(String[]) 表头
	 * @param imgs              输出图片url
	 * @param list              结果集
	 * @param attr              输出参数
	 * @param out               输出流
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void expImgExcel(String title, String[] imgTitle, String[] headers, String[] imgs, List list,
			String attr, OutputStream out) throws Exception {

		String[] nodeAtts = attr.split(",");
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		//sheet.setDefaultColumnWidth(18);
		int colWth = headers.length;
		for (int n = 0; n < colWth; n++) {
			sheet.setColumnWidth(n, 3600);
		}

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//总标题样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFont.setFontHeightInPoints((short) 14);
		titleStyle.setFont(boldFont);

		//副标题样式
		HSSFCellStyle subTitleStyle = workbook.createCellStyle();
		subTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont subBoldFont = workbook.createFont();
		subBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		subBoldFont.setFontHeightInPoints((short) 12);
		subTitleStyle.setFont(subBoldFont);

		/*
		 * 产生表格总标题行
		 * CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
		 */

		HSSFRow titleRow = sheet.createRow(0);
		HSSFCell titleCell = titleRow.createCell(0);
		titleRow.setHeightInPoints(20);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colWth - 1));

		HSSFRow titleRow1 = sheet.createRow(1);
		titleRow1.setHeightInPoints(18);

		int stImgRows = 2;
		int endImgRows = 2;
		for (int n = 0, m = imgs.length; n < m; n++) {
			int regionWidth = (int) Math.floor(colWth / m);

			HSSFCell titleCell1 = titleRow1.createCell(n * regionWidth);
			titleCell1.setCellStyle(subTitleStyle);
			titleCell1.setCellValue(imgTitle[n]);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, n * regionWidth, regionWidth * (n + 1) - 1));
			//生成图片
			endImgRows = stImgRows + 15;

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			BufferedImage bufferImg = ImageIO.read(new File(imgs[n]));
			ImageIO.write(bufferImg, "png", byteArrayOut);

			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255, (short) (n * regionWidth), stImgRows,
					(short) (regionWidth * (n + 1) - 1), endImgRows);
			patriarch.createPicture(anchor,
					workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
		}

		int dataRow = endImgRows + 2;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataRow);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = dataRow + 1;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(index);
				Object obj = list.get(i);
				for (int j = 0; j < nodeAtts.length; j++) {
					String value = getJsonColValue(obj, nodeAtts[j]);
					row.createCell(j).setCellValue(value);
				}
				index++;
			}
		}
		workbook.write(out);
	}

	/**
	 * 导出Excel的方法
	 *
	 * @param title           excel中的sheet名称
	 * @param imgTitle        图片标题
	 * @param headers(String) 表头
	 * @param imgs            输出图片url
	 * @param list            结果集
	 * @param attr            输出参数
	 * @param out             输出流
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void expImgExcel(String title, String[] imgTitle, String headers, String[] imgs, List list,
			String attr, OutputStream out) throws Exception {

		String[] nodeAtts = attr.split(",");
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		//sheet.setDefaultColumnWidth(18);
		String[] headtitle = headers.split(",");
		int colWth = headtitle.length;
		for (int n = 0; n < colWth; n++) {
			sheet.setColumnWidth(n, 3600);
		}

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//总标题样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFont.setFontHeightInPoints((short) 14);
		titleStyle.setFont(boldFont);

		//副标题样式
		HSSFCellStyle subTitleStyle = workbook.createCellStyle();
		subTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 总标题字体
		HSSFFont subBoldFont = workbook.createFont();
		subBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		subBoldFont.setFontHeightInPoints((short) 12);
		subTitleStyle.setFont(subBoldFont);

		/*
		 * 产生表格总标题行
		 * CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
		 */

		HSSFRow titleRow = sheet.createRow(0);
		HSSFCell titleCell = titleRow.createCell(0);
		titleRow.setHeightInPoints(20);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colWth - 1));

		HSSFRow titleRow1 = sheet.createRow(1);
		titleRow1.setHeightInPoints(18);

		int stImgRows = 2;
		int endImgRows = 2;
		for (int n = 0, m = imgs.length; n < m; n++) {
			int regionWidth = (int) Math.floor(colWth / m);

			HSSFCell titleCell1 = titleRow1.createCell(n * regionWidth);
			titleCell1.setCellStyle(subTitleStyle);
			titleCell1.setCellValue(imgTitle[n]);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, n * regionWidth, regionWidth * (n + 1) - 1));
			//生成图片
			endImgRows = stImgRows + 15;

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			BufferedImage bufferImg = ImageIO.read(new File(imgs[n]));
			ImageIO.write(bufferImg, "png", byteArrayOut);

			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255, (short) (n * regionWidth), stImgRows,
					(short) (regionWidth * (n + 1) - 1), endImgRows);
			patriarch.createPicture(anchor,
					workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
		}

		int dataRow = endImgRows + 2;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataRow);
		for (int i = 0; i < headtitle.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headtitle[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = dataRow + 1;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(index);
				Object obj = list.get(i);
				for (int j = 0; j < nodeAtts.length; j++) {
					String value = getJsonColValue(obj, nodeAtts[j]);
					row.createCell(j).setCellValue(value);
				}
				index++;
			}
		}
		workbook.write(out);
	}


	public static String getJsonColValue(Object obj, String colStr) {
		String[] colAtts = colStr.split("@");
		Object objCol = Reflections.getFieldValue(obj, colAtts[0]);

		if (colAtts.length == 1) {
			if (objCol != null)
				return String.valueOf(objCol);
			else
				return "";
		}

		String value = null;
		if (objCol != null) {
			switch (colAtts.length) {
				case 2:
					value = WebFuncUtils.getResValue(colAtts[1], objCol.toString());
					break;
				case 4:
					value = WebFuncUtils.getObjColValue(colAtts[1], colAtts[2], colAtts[3], objCol.toString());
					break;
			}
		}
		return value;
	}

	public static void main(String[] args) {
		//构造list
		List<SysUser> list = new ArrayList<SysUser>();
		SysUser user = new SysUser();
		user.setLoginName("adfd");
		user.setLoginPasswd("sadff");
		list.add(user);

		String[] headers = {"机构名称", "业务应用"};//导出字段名称,excel头部
		String attr = "loginName,loginPasswd";//导出映射字段
		String fileName =
				"业务访问明细表_" + DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis())) + ".xls";//excel文件名
		String path = "d:/" + fileName;//生成excel的路径
		try {
			OutputStream out = new FileOutputStream(path);
			ExpExcelUtil.expExcel("业务访问明细表", headers, list, attr, out);
			out.close();
		} catch (Exception e) {
			System.out.println("导出excel出错。。。");
			e.printStackTrace();
		}
	}
}
