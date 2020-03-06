package com.example.demo.web.test.worker.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.web.test.worker.vo.PayLoadOrigin;
import com.example.demo.web.test.worker.vo.SubscribeInfo;
import com.example.demo.web.test.worker.vo.WebPushparams;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Subscription.Keys;

@Service
public class WebPushUniqueService {
	
	@Autowired
	private HttpServletRequest request;
	
	// 엔드포인트 정보를 저장하는 스토리지를 임시로 파일로 구성한다.
	@Value("${repository.endpoint.saveFilePath}")
	private String repositoryFile;
	
	@Value("${repository.keyInfo.public}")
	private String publicKey;
	@Value("${repository.keyInfo.private}")
	private String privateKey;
	
	@Value("${repository.pushInfo.title}")
	private String serverTitle;
	@Value("${repository.pushInfo.text}")
	private String serverText;
	
	public void doSaveEndpointInfo(SubscribeInfo subscribe){
		FileWriter writer = null;
		File repositoryFile = this.doInitRepositoryFile();
		try {
			writer = new FileWriter(repositoryFile);
			writer.write(subscribe.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doInitEndPointInfo() {
		this.doInitRepositoryFile();
	}
	
	public void doSendWebPush(WebPushparams params) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
		//public key , private key, subject??
		PushService pushService = new PushService(this.publicKey , this.privateKey, "test");
		SubscribeInfo endpoint = params.getSubscribeInfo();
		
		// subscribe return key, subscribe return author
		Keys keys = new Keys(endpoint.getKeys().getP256dh(), endpoint.getKeys().getAuth());
		// subscribe return endpoint
		Subscription subsc = new Subscription(endpoint.getEndpoint(), keys);
		//payload make
		ObjectMapper oMapper = new ObjectMapper();
		String jsonString = oMapper.writeValueAsString(params.getPayLoad());
		// setting object , context
		Notification noti = new Notification(subsc, jsonString);
		
		pushService.send(noti);
	}
	
	public void doSendWebPush() throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
		PayLoadOrigin payLoad = new PayLoadOrigin(this.serverTitle, this.serverText);
		SubscribeInfo endpoint = SubscribeInfo.getSelf(this.getEndpointInfoFromFile());
		WebPushparams params = new WebPushparams(payLoad, endpoint);
		this.doSendWebPush(params);
	}
	
	private String getEndpointInfoFromFile() throws FileNotFoundException {
		File endpointInfoDir = new File(this.repositoryFile);
		File targetRecognize = new File(endpointInfoDir, this.request.getRemoteAddr());
		FileReader fileReader = new FileReader(targetRecognize);
		BufferedReader reader = new BufferedReader(fileReader);
		String result = null;
		try {
			result = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	} 	
	
	private File doInitRepositoryFile() {
		File endpointInfoDir = new File(this.repositoryFile);
		File targetRecognize = new File(endpointInfoDir, this.request.getRemoteAddr());
		
		if(targetRecognize.exists()) {
			targetRecognize.delete();
			targetRecognize = new File(endpointInfoDir, this.request.getRemoteAddr());
		}
		
		return targetRecognize;
	}
	
}
