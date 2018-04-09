package com.ikeeps.pippy.authservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pwds")
public class Pwds {
	private String accountServices;
	private String statisticsServices;
	private String notificationServices;
	public String getAccountServices() {
		return accountServices;
	}
	public void setAccountServices(String accountServices) {
		this.accountServices = accountServices;
	}
	public String getStatisticsServices() {
		return statisticsServices;
	}
	public void setStatisticsServices(String statisticsServices) {
		this.statisticsServices = statisticsServices;
	}
	public String getNotificationServices() {
		return notificationServices;
	}
	public void setNotificationServices(String notificationServices) {
		this.notificationServices = notificationServices;
	}
	
	
}
