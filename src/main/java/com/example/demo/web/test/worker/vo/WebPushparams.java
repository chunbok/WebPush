package com.example.demo.web.test.worker.vo;


public class WebPushparams {
	
	private PayLoadOrigin payLoad;
	private SubscribeInfo subscribeInfo;
	
	public WebPushparams() {
		super();
	}
	
	public WebPushparams(PayLoadOrigin payLoad, SubscribeInfo subscribeInfo) {
		super();
		this.payLoad = payLoad;
		this.subscribeInfo = subscribeInfo;
	}

	public PayLoadOrigin getPayLoad() {
		return payLoad;
	}
	public void setPayLoad(PayLoadOrigin payLoad) {
		this.payLoad = payLoad;
	}
	public SubscribeInfo getSubscribeInfo() {
		return subscribeInfo;
	}
	public void setSubscribeInfo(SubscribeInfo subscribeInfo) {
		this.subscribeInfo = subscribeInfo;
	}
}
