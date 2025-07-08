/**
 * 
 */
package com.sino.cmdb.indicator.report.template.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-9-23下午3:42:41
 * @className RptTmplFieldsCell
 *
 * @Description TODO
 *
 */


@Entity
@Table(name = "Rpt_TmplFieldsCell", uniqueConstraints = @UniqueConstraint(columnNames = "tmplFieldsCellId"))
public class RptTmplFieldsCell {
	
//	`tmplId` int(11) NOT NULL COMMENT '模板ID',
//	  `tmplFieldsCellId` int(11) NOT NULL AUTO_INCREMENT,
//	  `fieldNameEn` varchar(16) DEFAULT NULL COMMENT '字段英文名',
//	  `fieldName` varchar(16) DEFAULT NULL COMMENT '字段名',
//	  `valueType` varchar(16) DEFAULT NULL COMMENT '值类型',
//	  `borderStyle` int(1) DEFAULT NULL COMMENT '边框样式',
//	  `tableWidth` int(4) DEFAULT NULL COMMENT '列表表格宽度',
//	  `height` int(4) DEFAULT NULL COMMENT '列表表格高度',
//	  `color` varchar(16) DEFAULT NULL COMMENT '颜色（缺省：黑色）',
//	  `font` varchar(16) DEFAULT NULL COMMENT '字体(缺省：宋体)',
//	  `size` int(1) DEFAULT NULL COMMENT '字号大小（缺省：16）',
//	  `isBold` int(1) DEFAULT NULL COMMENT '粗体（1：是；0:否）',
//	  `valuePos` varchar(8) DEFAULT NULL COMMENT '值显示位置（right,down）',
	
	private Integer tmplId;
	private Integer tmplFieldsCellId;
	private String fieldNameEn;
	private String fieldName;
	private String valueType;
	private Integer borderStyle;
	private Integer tableWidth;
	private Integer height;
	private String color;
	private String font;
	private Integer size;
	private Integer isBold;
	private String valuePos;
	
	private Long indItemID;
	
	public Integer getTmplId() {
		return tmplId;
	}
	public void setTmplId(Integer tmplId) {
		this.tmplId = tmplId;
	}
	
	@Id
	@Column(name = "tmplFieldsCellId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getTmplFieldsCellId() {
		return tmplFieldsCellId;
	}
	public void setTmplFieldsCellId(Integer tmplFieldsCellId) {
		this.tmplFieldsCellId = tmplFieldsCellId;
	}
	public String getFieldNameEn() {
		return fieldNameEn;
	}
	public void setFieldNameEn(String fieldNameEn) {
		this.fieldNameEn = fieldNameEn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public Integer getBorderStyle() {
		return borderStyle;
	}
	public void setBorderStyle(Integer borderStyle) {
		this.borderStyle = borderStyle;
	}
	public Integer getTableWidth() {
		return tableWidth;
	}
	public void setTableWidth(Integer tableWidth) {
		this.tableWidth = tableWidth;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
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
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	
	

}
