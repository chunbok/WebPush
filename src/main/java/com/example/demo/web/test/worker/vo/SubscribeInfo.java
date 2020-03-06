package com.example.demo.web.test.worker.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content=Include.NON_NULL)
public class SubscribeInfo {
	private static String separator = "<!!!>";

	private String endpoint="";
	private String expirationTime="";
	private WorkerKeys keys;
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public WorkerKeys getKeys() {
		return keys;
	}
	public void setKeys(WorkerKeys keys) {
		this.keys = keys;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.doReplaceJsonNulltoBlank(this.endpoint)).append(SubscribeInfo.separator);
		builder.append(this.doReplaceJsonNulltoBlank(this.expirationTime)).append(SubscribeInfo.separator);
		builder.append(this.doReplaceJsonNulltoBlank(this.keys.getP256dh())).append(SubscribeInfo.separator);
		builder.append(this.doReplaceJsonNulltoBlank(this.keys.getAuth()));
		return builder.toString();
	}
	
	public static SubscribeInfo getSelf(String getString) {
		String[] getStringArray = getString.split(SubscribeInfo.separator);
		SubscribeInfo returnObject = new SubscribeInfo();
		WorkerKeys returnInnerObject = new WorkerKeys();
		returnObject.setEndpoint(getStringArray[0]);
		returnObject.setExpirationTime(getStringArray[1]);
		returnInnerObject.setP256dh(getStringArray[2]);
		returnInnerObject.setAuth(getStringArray[3]);
		returnObject.setKeys(returnInnerObject);
		return returnObject;
	}
	
	private String doReplaceJsonNulltoBlank(String check) {
		if(null == check) {
			check = "";
		}
		return check;
	}
}
