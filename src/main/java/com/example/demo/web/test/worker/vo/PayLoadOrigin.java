package com.example.demo.web.test.worker.vo;

public class PayLoadOrigin {
	private String title;
	private String text;
	
	
	public PayLoadOrigin() {
		super();
	}
	
	public PayLoadOrigin(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
