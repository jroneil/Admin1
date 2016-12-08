package com.admin1.admin;

public class Check {
	private Long confirmCounter;
	private boolean userExist=false;
	public Long getConfirmCounter() {
		return confirmCounter;
	}
	public void setConfirmCounter(Long confirmCounter) {
		this.confirmCounter = confirmCounter;
	}
	public boolean isUserExist() {
		return userExist;
	}
	public void setUserExist(boolean userExist) {
		this.userExist = userExist;
	}
}
