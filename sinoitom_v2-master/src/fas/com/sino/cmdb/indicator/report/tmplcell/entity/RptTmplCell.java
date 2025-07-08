/**
 * 
 */
package com.sino.cmdb.indicator.report.tmplcell.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-7-22上午9:30:35
 * @className RptTmplCell
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Rpt_TmplCell", uniqueConstraints = @UniqueConstraint(columnNames = "tmplCellId"))
public class RptTmplCell {
	
//	`tmplId` int(11) DEFAULT NULL COMMENT '模板ID',
//	  `tmplCellId` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板单元格ID',
//	  `cellEnName` varchar(32) DEFAULT NULL COMMENT '英文名称',
//	  `cellDispName` varchar(32) DEFAULT NULL COMMENT '显示名称',
//	  `borderStyle` int(1) DEFAULT NULL COMMENT '边框样式',
//	  `rowNo` int(1) DEFAULT NULL COMMENT '行号',
//	  `colNo` int(1) DEFAULT NULL COMMENT '列号',
//	  `width` int(2) DEFAULT NULL COMMENT '宽度（单元格宽度）',
//	  `height` int(2) DEFAULT NULL COMMENT '单元格高度',
//	  `align` varchar(8) DEFAULT NULL COMMENT '水平排列方式（left,center,right）',
//	  `color` varchar(16) DEFAULT NULL COMMENT '颜色（缺省：黑色）',
//	  `font` varchar(16) DEFAULT NULL COMMENT '字体(缺省：宋体)',
//	  `size` int(1) DEFAULT NULL COMMENT '字号大小（缺省：12）',
//	  `isBold` int(1) DEFAULT NULL COMMENT '粗体（1：是；0:否）',
//	  `valuePos` varchar(8) DEFAULT NULL COMMENT '值显示位置（left,down）',
//	  `valueType` varchar(16) DEFAULT NULL COMMENT '取值类型',
//	  `valueCellWidth` int(2) DEFAULT NULL COMMENT '值最大长度',
//	  `valueAlign` varchar(8) DEFAULT NULL COMMENT '值水平排列方式（String：left；数字：right）',
	
	private Integer tmplId;
	private Integer tmplCellId;
	private String cellEnName;
	private String cellDispName;
	private Integer borderStyle;
	private Integer rowNo;
	private Integer colNo;
	private Integer keyWidth;
	private Integer valueWidth;
	private Integer height;
	private String align;
	private String color;
	private String font;
	private Integer size;
	private Integer isBold;
	private String valuePos;
	private String valueType;
	private Integer valueCellWidth;
	private String valueAlign;
	
	
	public Integer getTmplId() {
		return tmplId;
	}
	public void setTmplId(Integer tmplId) {
		this.tmplId = tmplId;
	}
	
	@Id
	@Column(name = "tmplCellId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getTmplCellId() {
		return tmplCellId;
	}
	public void setTmplCellId(Integer tmplCellId) {
		this.tmplCellId = tmplCellId;
	}
	public String getCellEnName() {
		return cellEnName;
	}
	public void setCellEnName(String cellEnName) {
		this.cellEnName = cellEnName;
	}
	public String getCellDispName() {
		return cellDispName;
	}
	public void setCellDispName(String cellDispName) {
		this.cellDispName = cellDispName;
	}
	public Integer getBorderStyle() {
		return borderStyle;
	}
	public void setBorderStyle(Integer borderStyle) {
		this.borderStyle = borderStyle;
	}
	public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
	public Integer getColNo() {
		return colNo;
	}
	public void setColNo(Integer colNo) {
		this.colNo = colNo;
	}
	
	public Integer getKeyWidth() {
		return keyWidth;
	}
	public void setKeyWidth(Integer keyWidth) {
		this.keyWidth = keyWidth;
	}
	public Integer getValueWidth() {
		return valueWidth;
	}
	public void setValueWidth(Integer valueWidth) {
		this.valueWidth = valueWidth;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getIsBold() {
		return isBold;
	}
	public void setIsBold(Integer isBold) {
		this.isBold = isBold;
	}
	public String getValuePos() {
		return valuePos;
	}
	public void setValuePos(String valuePos) {
		this.valuePos = valuePos;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public Integer getValueCellWidth() {
		return valueCellWidth;
	}
	public void setValueCellWidth(Integer valueCellWidth) {
		this.valueCellWidth = valueCellWidth;
	}
	public String getValueAlign() {
		return valueAlign;
	}
	public void setValueAlign(String valueAlign) {
		this.valueAlign = valueAlign;
	}
	
	
	

}
