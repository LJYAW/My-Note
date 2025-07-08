/**
 * 
 */
package com.sino.fas.res.host.entity;

/**
 * @author Mr.LP
 * @date 2019-12-3上午10:51:15
 * @className ParamPerformance
 * @version V1.0
 * @Description TODO
 *
 */
public class ParamPerformance {
	
	private Long memorySize;
	private Long freeMemorySize;
	private Long usedMemorySize;
	private String usedMem;

	private String userUsed;
	private String sysUsed;
	
	
	public Long getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Long memorySize) {
		this.memorySize = memorySize;
	}
	public Long getFreeMemorySize() {
		return freeMemorySize;
	}
	public void setFreeMemorySize(Long freeMemorySize) {
		this.freeMemorySize = freeMemorySize;
	}
	public Long getUsedMemorySize() {
		return usedMemorySize;
	}
	public void setUsedMemorySize(Long usedMemorySize) {
		this.usedMemorySize = usedMemorySize;
	}
	public String getUsedMem() {
		return usedMem;
	}
	public void setUsedMem(String usedMem) {
		this.usedMem = usedMem;
	}
	public String getUserUsed() {
		return userUsed;
	}
	public void setUserUsed(String userUsed) {
		this.userUsed = userUsed;
	}
	public String getSysUsed() {
		return sysUsed;
	}
	public void setSysUsed(String sysUsed) {
		this.sysUsed = sysUsed;
	}
	
	
}
