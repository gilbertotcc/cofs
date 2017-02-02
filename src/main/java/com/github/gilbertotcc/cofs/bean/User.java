package com.github.gilbertotcc.cofs.bean;

public class User {

	private final String userId;
	private int credit;
	private TimeTick lastOffer;

	public User(String userId, int credit) {
		this.userId = userId;
		this.credit = credit;
		this.lastOffer = TimeTick.getNext();
	}

	public String getUserId() {
		return userId;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public TimeTick getLastOffer() {
		return lastOffer;
	}

	public void setLastOffer(TimeTick time) {
		this.lastOffer = time;
	}
}
