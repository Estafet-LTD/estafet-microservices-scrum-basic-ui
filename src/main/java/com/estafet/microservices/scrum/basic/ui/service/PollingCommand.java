package com.estafet.microservices.scrum.basic.ui.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class PollingCommand<T> {

	private final Future<T> future;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final Integer timeout;
	
	abstract public boolean isReady(T result);
	
	abstract public T result();
		
	public PollingCommand() {
		this(10);
	}
	
	public PollingCommand(Integer timeout) {
		this.timeout = timeout;
		future = executor.submit(new Callable<T>() {
		    public T call() throws Exception {
		    	T result = result();
		    	while (!isReady(result)) {
					Thread.sleep(333);
				}
		        return result;
		    }
		});
	}
	
	public T execute() {
		try {
			return future.get(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}
	
}
