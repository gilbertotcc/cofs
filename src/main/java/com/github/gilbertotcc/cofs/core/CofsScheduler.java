package com.github.gilbertotcc.cofs.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.gilbertotcc.cofs.bean.User;

public class CofsScheduler {
	
	private static class UserComparator implements Comparator<User> {
		
		private static final int LESS_THAN = -1;
		private static final int EQUAL_TO = 0;
		private static final int GREATER_THAN = 1;
		
		@Override
		public int compare(User u1, User u2) {
			if (u1.getCredit() != u2.getCredit()) {
				return u1.getCredit() < u2.getCredit() ? GREATER_THAN : LESS_THAN;
			}
			if (u1.getLastOffer().getTick() != u2.getLastOffer().getTick()) {
				return u1.getLastOffer().getTick() > u2.getLastOffer().getTick() ?
						GREATER_THAN : LESS_THAN;
			}
			return EQUAL_TO;
		}
	}
	
	public CofsScheduler() {
		// ...
	}
	
	public List<User> schedule(List<User> users) {
		if (users.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<User> scheduledUsers = new ArrayList<>(users);
		Collections.sort(scheduledUsers, new UserComparator());
		return scheduledUsers;
	}
}
