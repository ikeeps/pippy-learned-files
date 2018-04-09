package com.ikee.pippy.notification.domain;

import java.util.Date;

public class NotificationSettings {
	
	private boolean active;
	
	private Frequency frequency;
	
	private Date lastNotified;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public Date getLastNotified() {
		return lastNotified;
	}

	public void setLastNotified(Date lastNotified) {
		this.lastNotified = lastNotified;
	}
	
	
}
