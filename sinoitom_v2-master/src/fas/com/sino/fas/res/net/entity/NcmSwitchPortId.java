package com.sino.fas.res.net.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * NcmSwitchPortId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class NcmSwitchPortId implements java.io.Serializable {

	// Fields

	private Long switchId;
	private Long ifIndex;

	// Constructors

	/** default constructor */
	public NcmSwitchPortId() {
	}

	/** full constructor */
	public NcmSwitchPortId(Long switchId, Long ifIndex) {
		this.switchId = switchId;
		this.ifIndex = ifIndex;
	}

	// Property accessors

	@Column(name = "Switch_Id", nullable = false)
	public Long getSwitchId() {
		return this.switchId;
	}

	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}

	@Column(name = "IfIndex", nullable = false)
	public Long getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NcmSwitchPortId))
			return false;
		NcmSwitchPortId castOther = (NcmSwitchPortId) other;

		return ((this.getSwitchId() == castOther.getSwitchId()) || (this
				.getSwitchId() != null && castOther.getSwitchId() != null && this
				.getSwitchId().equals(castOther.getSwitchId())))
				&& ((this.getIfIndex() == castOther.getIfIndex()) || (this
						.getIfIndex() != null && castOther.getIfIndex() != null && this
						.getIfIndex().equals(castOther.getIfIndex())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSwitchId() == null ? 0 : this.getSwitchId().hashCode());
		result = 37 * result
				+ (getIfIndex() == null ? 0 : this.getIfIndex().hashCode());
		return result;
	}

}