package com.sino.base.system.entity;

/**
 * 参数树对象
 *
 * @author .
 * @date 2017年5月11日 上午10:57:19
 */
public class ParamTree {
	private String id;	//本节点id

	private String pid;	//父节点id

	private String text;	//显示的文本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
