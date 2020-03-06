/**
 * Service Worker Object
 */

/*
 * service worker regist closer
 */
var workerRegist = function(targetRegist) {
	var _regist = targetRegist;
	
	const _setRegist = function(regist) {
		_regist = regist;
	}
	
	const _isRegist = function() {
		return !(_regist === null); 
	}
	
	const _isSubscribed = function() {
		return new Promise((resolve, reject) => { 
			_regist.pushManager.getSubscription().then((subscription) => {
				resolve(!(subscription === null));
			});
		});
	}
	
	const _doSubscribe = function(isUserVisibleOnly) {
		var returnPromise = new Promise((resolve, reject) => { 
			_regist.pushManager.subscribe({
				userVisibleOnly: isUserVisibleOnly,
				applicationServerKey: consts.getServerKey()
			}).then((subscription) => {
				console.log('User is subscribed : ', subscription);
				console.log('endpoint text : ', JSON.stringify(subscription));
				var httpRequest = new XMLHttpRequest();
				var params = {subscribeInfo : subscription};
				
				httpRequest.onreadystatechange = function() {
					if (httpRequest.readyState === XMLHttpRequest.DONE) {
						if (httpRequest.status === 200) {
							console.log(httpRequest.responseText);
							
						} else {
							console.log('There was a problem with the request.');
						}
					}
				}
				httpRequest.open('POST', '/subscribe');
				httpRequest.setRequestHeader('Content-Type', 'application/json');
				httpRequest.send(JSON.stringify(params));
				resolve();
			}).catch(function(err) {
				console.log('Failed to subscribe the user: ', err);
				reject(err);
			});
		});
		return returnPromise;
	}
	
	const _doUnSubscribe = function() {
		var returnPromise = new Promise(function(resolve, reject) { 
			_regist.pushManager.getSubscription().then((subscribe) => {
				var httpRequest = new XMLHttpRequest();
				subscribe.unsubscribe().then((flage) => {
					httpRequest.onreadystatechange = function() {
						if (httpRequest.readyState === XMLHttpRequest.DONE) {
							if (httpRequest.status === 200) {
								console.log(httpRequest.responseText);
							} else {
								console.log('There was a problem with the request.');
							}
						}
					}
					httpRequest.open('POST', '/unsubscribe');
					httpRequest.setRequestHeader('Content-Type', 'application/json');
					httpRequest.send();
					resolve();
				}).catch(function(err) {
					console.log('Failed to unsubscribe the user: ', err);
					reject(err);
				});
			});
		});
		return returnPromise;
	}
	
	const _getEndPoint = function() {
		return new Promise((resolve, reject) => { 
			_regist.pushManager.getSubscription().then((subscription) => {
				if(!(subscription === null)) {
					resolve(subscription);
				}else {
					reject("non Subscribe");
				}
			});
		});
	}
		
	return {
		setRegist : _setRegist
		, isRegist : _isRegist
		, isSubscribed : _isSubscribed
		, doSubscribe : _doSubscribe
		, doUnSubscribe : _doUnSubscribe
		, getEndPoint : _getEndPoint
	}
}

const consts = (function() {
	// Have to change this public key to own yours
	const _applicationServerPublicKey 
	= "BC-tL_Z-GuHOUAXq7XFaQSkqX7v2W-trOnfepa6GcPJUbWusfM8QA_Ljy8RVbtYH7WIirOQZw7NWJl9NokJgiug";
	
	const _urlB64ToUint8Array = function(base64String) {
		const padding = '='.repeat((4 - base64String.length % 4) % 4);
		const base64 = (base64String + padding)
		  .replace(/\-/g, '+')
		  .replace(/_/g, '/');

		const rawData = window.atob(base64);
		const outputArray = new Uint8Array(rawData.length);

		for (let i = 0; i < rawData.length; ++i) {
		  outputArray[i] = rawData.charCodeAt(i);
		}
		return outputArray;
	}
	
	const _getServerKey = function() {
		return _urlB64ToUint8Array(_applicationServerPublicKey);
	}
	
	return {
		urlB64ToUint8Array : _urlB64ToUint8Array
		, getServerKey : _getServerKey
	}
	
})();