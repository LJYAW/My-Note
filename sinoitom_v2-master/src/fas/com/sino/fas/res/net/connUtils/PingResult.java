package com.sino.fas.res.net.connUtils;

public class PingResult {
	private String ip;
	private Integer result;
	private Integer pingRttMin;
	private Integer pingRttAvg ;
	private Integer pingRttMax ;
	private Integer lossPercent ;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Integer getPingRttMin() {
		return pingRttMin;
	}
	public void setPingRttMin(Integer pingRttMin) {
		this.pingRttMin = pingRttMin;
	}
	public Integer getPingRttAvg() {
		return pingRttAvg;
	}
	public void setPingRttAvg(Integer pingRttAvg) {
		this.pingRttAvg = pingRttAvg;
	}
	public Integer getPingRttMax() {
		return pingRttMax;
	}
	public void setPingRttMax(Integer pingRttMax) {
		this.pingRttMax = pingRttMax;
	}
	public Integer getLossPercent() {
		return lossPercent;
	}
	public void setLossPercent(Integer lossPercent) {
		this.lossPercent = lossPercent;
	}
	@Override
	public String toString() {
		return "PingResult [ip=" + ip + ", result=" + result + ", pingRttMin="
				+ pingRttMin + ", pingRttAvg=" + pingRttAvg + ", pingRttMax="
				+ pingRttMax + ", lossPercent=" + lossPercent + "]";
	}
	
	
}
