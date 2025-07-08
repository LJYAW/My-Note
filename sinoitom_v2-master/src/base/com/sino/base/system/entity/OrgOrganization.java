package com.sino.base.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;



/**
 * 
 * @ClassName: OrgOrganization
 * @Description: 机构的实体类
 * @author .
 * @date 2017年5月2日 下午6:53:03
 *
 */
@Entity
@Table(name = "Org_Organization")
public class OrgOrganization implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6073680609062212697L;
	// Fields

	private String orgId;	//机构ID
	private String creator;	//创建者
	private Timestamp createTime;	//创建时间
	private String modifier;		//修改人
	private Timestamp modifyTime;	//修改时间
	private Integer state;	//状态
	private String orgName;	//机构名称
	private Integer orgType;	//机构类型
	private String parentId;	//父机构ID
	private String treeCode;	//机构树编码
	private String orgCode;	//机构编码
	private String phone;	//电话
	private String hotLine;	//热线电话
	private String fax;	//传真
	private String country;	//国家
	private String countryCode;
	private Integer countyCode;
	private String county;  //区县
	private String region;	//区域
	private String province;	//省份
	
	private Integer provCode;
	private String city;	//城市
	private Integer cityCode;
	private String zipCode;	//邮编
	private String address;	//地址
	private String otherAddress;	//其他地址
	private String web;	//WEB地址
	private String description;	//描述
	private String remark;	//备注
	private Integer level;
	private String isLeaf;
	private String expanded;
	
	private Integer orgClass;//机构分类：0：MSP服务商；1：运营商
	private String orgShortName;
	
	private String parentOrgName;	//父机构名称
	
	@Transient
	public String getParentOrgName() {
		return parentOrgName;
	}
	@Transient
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	private Set<OrgPosition> orgPositions = new HashSet<OrgPosition>(0);
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Constructors

	/** default constructor */
	public OrgOrganization() {
	}

	/** minimal constructor */
	public OrgOrganization(String orgId, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime, Integer state,
			String orgName, Integer orgType, String treeCode) {
		this.orgId = orgId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.state = state;
		this.orgName = orgName;
		this.orgType = orgType;
		this.treeCode = treeCode;
	}

	/** full constructor */
	public OrgOrganization(String orgId, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime, Integer state,
			String orgName, Integer orgType, String parentId, String treeCode,
			String orgCode, String phone, String hotLine, String fax,
			String country, String region, String province, String city,
			String zipCode, String address, String otherAddress, String web,
			String description, String remark, Set<OrgPosition> orgPositions,
			Set<SysUser> sysUsers) {
		this.orgId = orgId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.state = state;
		this.orgName = orgName;
		this.orgType = orgType;
		this.parentId = parentId;
		this.treeCode = treeCode;
		this.orgCode = orgCode;
		this.phone = phone;
		this.hotLine = hotLine;
		this.fax = fax;
		this.country = country;
		this.region = region;
		this.province = province;
		this.city = city;
		this.zipCode = zipCode;
		this.address = address;
		this.otherAddress = otherAddress;
		this.web = web;
		this.description = description;
		this.remark = remark;
		this.orgPositions = orgPositions;
		this.sysUsers = sysUsers;
	}

	// Property accessors
	@Id
	@Column(name = "OrgId", unique = true, nullable = false, length = 36)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "Creator",  length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CreateTime",  length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Modifier",  length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "ModifyTime",  length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "OrgName",  length = 64)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "OrgType", nullable = false)
	public Integer getOrgType() {
		return this.orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	@Column(name = "ParentId", length = 36)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TreeCode",  length = 36)
	public String getTreeCode() {
		return this.treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	@Column(name = "OrgCode", length = 32)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "Phone", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "HotLine", length = 32)
	public String getHotLine() {
		return this.hotLine;
	}

	public void setHotLine(String hotLine) {
		this.hotLine = hotLine;
	}

	@Column(name = "Fax", length = 32)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "Country", length = 32)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "Region", length = 32)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "Province", length = 32)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "City", length = 32)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ZipCode", length = 8)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "Address", length = 64)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "OtherAddress", length = 64)
	public String getOtherAddress() {
		return this.otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	@Column(name = "Web", length = 64)
	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "orgOrganization")
	public Set<OrgPosition> getOrgPositions() {
		return this.orgPositions;
	}

	public void setOrgPositions(Set<OrgPosition> orgPositions) {
		this.orgPositions = orgPositions;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "orgOrganizations")
	public Set<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}
	public Integer getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(Integer countyCode) {
		this.countyCode = countyCode;
	}
	public Integer getProvCode() {
		return provCode;
	}
	public void setProvCode(Integer provCode) {
		this.provCode = provCode;
	}
	@Column(name = "CityCode")
	public Integer getCityCode() {
		return cityCode;
	}
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name = "County", length = 32)
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	@Column(name = "level", length = 0)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name = "expanded")
	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
	@Column(name = "isLeaf")
	public String isLeaf() {
		return isLeaf;
	}

	public void setLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	@Column(name = "OrgClass")
	public Integer getOrgClass() {
		return orgClass;
	}
	public void setOrgClass(Integer orgClass) {
		this.orgClass = orgClass;
	}
	@Column(name = "OrgShortName")
	public String getOrgShortName() {
		return orgShortName;
	}
	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}
	@Column(name = "CountryCode")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	
	
	
	

}