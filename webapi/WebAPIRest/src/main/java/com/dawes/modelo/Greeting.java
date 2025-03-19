package com.dawes.modelo;

import java.util.concurrent.atomic.AtomicLong;

public class Greeting {
	private Long counter;
	private String template;
	public Greeting(Long counter, String template) {
		super();
		this.counter = counter;
		this.template = template;
	}
	public Greeting() {
		super();
	}
	public Long getCounter() {
		return counter;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	
}
