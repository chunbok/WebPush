package com.example.demo.web.test.worker.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content=Include.NON_NULL)
public class WorkerKeys {

	private String p256dh="";
	private String auth="";
	
	
	public String getP256dh() {
		return p256dh;
	}
	public void setP256dh(String p256dh) {
		this.p256dh = p256dh;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
}
