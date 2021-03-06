package com.github.gilbertotcc.cofs.bean;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	
	private final User offeror;
	private final List<User> recipients;
	private final TimeTick time;
	
	public Transaction(User offeror, List<User> recipients) {
		this.offeror = offeror;
		this.recipients = recipients;
		this.time = TimeTick.getNext();
	}
	
	public List<User> commit() {
		
		List<User> updatedUsers = new ArrayList<>(recipients.size() + 1);
		
		recipients.forEach(recipient -> {
			recipient.setCredit(recipient.getCredit() + 1);
			updatedUsers.add(recipient);
		});
		
		int spentCredits = recipients.size();
		offeror.setCredit(offeror.getCredit() - spentCredits);
		offeror.setLastOffer(this.getTime());
		updatedUsers.add(offeror);
		
		return updatedUsers;
	}

	public User getOfferor() {
		return offeror;
	}

	public List<User> getRecipients() {
		return recipients;
	}

	public TimeTick getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		final String pattern = "@%d: %s offers to %s";
		return String.format(pattern, time.getTick(), offeror, userListToString(recipients));
	}
	
	private static String userListToString(List<User> userList) {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		for (User u : userList) {
			sb.append(separator).append(u.getUserId());
			separator = ",";
		}
		return sb.toString();
	}
}
