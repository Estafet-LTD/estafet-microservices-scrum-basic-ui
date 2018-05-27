package com.estafet.microservices.scrum.basic.ui.service;

public abstract class WaitUntil {

	private static final int DELAY = 500;
	
	abstract public boolean success();

	private final int timeout;
	
	public WaitUntil() {
		this(5000);
	}
	
	public WaitUntil(int timeout) {
		this.timeout = timeout;
	}

	public void start() {
		try {
			int wait = 0;
			while (!success()) {
				Thread.sleep(DELAY);
				wait+= DELAY;
				if (wait >= timeout) {
					throw new RuntimeException("Timeout after " + wait + "ms");
				} 
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
