package com.example.demo.web.test.worker.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.web.test.worker.service.WebPushUniqueService;
import com.example.demo.web.test.worker.vo.SubscribeInfo;
import com.example.demo.web.test.worker.vo.WebPushparams;

@Controller
public class ServiceWorkerController {
	
	public ServiceWorkerController() {
		//RSA 처리를 위한 암/복호화 provider 초기화
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@Autowired
	private WebPushUniqueService webPushService;
	
	@RequestMapping(value="/testPage")
	public ModelAndView toTest() {
		ModelAndView result = new ModelAndView();
		result.setViewName("/test/worker/testWorker");
		
		return result;
	}
	
	@RequestMapping(value="/clientPush")
	@ResponseBody
	public String toWebPush(@RequestBody(required=false) WebPushparams params) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
		this.webPushService.doSendWebPush(params);
		
		return "success";
	}
	
	@RequestMapping(value="/serverPush")
	@ResponseBody
	public String toWebPushFromServer() throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
		this.webPushService.doSendWebPush();
		
		return "success";
	}
	
	@RequestMapping(value="/subscribe")
	@ResponseBody
	public String doTakeSubscription(@RequestBody(required=false) WebPushparams params) {
		String result = "success";
		
		SubscribeInfo subscribe = params.getSubscribeInfo();
		if(null !=  subscribe) {
			//this feature is saved endpoint information in file be named remote ip
			//if you want save in repository(e.g Database), pushmanager endpoint is good for using key. that's why because, can get endpoint in frontside
			this.webPushService.doSaveEndpointInfo(subscribe);
		}else {
			result = "no Data taken";
		}
			
		return result;
	}
	
	@RequestMapping(value="/unsubscribe")
	@ResponseBody
	public String doTakeUnSubscription() {
		String result = "success";
		this.webPushService.doInitEndPointInfo();
		return result;
	}
}
