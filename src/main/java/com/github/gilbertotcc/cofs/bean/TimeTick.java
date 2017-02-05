package com.github.gilbertotcc.cofs.bean;

import java.util.concurrent.atomic.AtomicInteger;

public class TimeTick {
	
	private static final AtomicInteger globalTime = new AtomicInteger(0);
	
	private final int tick;
	
	private TimeTick(int tick) {
		this.tick = tick;
	}
	
	public static TimeTick getNext() {
		int currentTime = globalTime.getAndIncrement();
		return new TimeTick(currentTime);
	}
	
	public int getTick() {
		return tick;
	}
}
