/**
 *  test.jsp binded javascript
 */
var swRegistrationPage = new workerRegist();

window.addEventListener('load', function() {
	if ('serviceWorker' in navigator && 'PushManager' in window) {
		var pushButton = document.querySelector('button[name="js-push-btn"]');
		navigator.serviceWorker.register('/static/js/workerScript.js').then((registration) => {
			// Registration was successful
			console.log('ServiceWorker registration successful with scope: ', registration.scope);
			this.swRegistrationPage.setRegist(registration);
			updateBtn();
		}).catch(function(err) {
			console.log('ServiceWorker registration failed: ', err);
		});
		pushButton.addEventListener('click', () => {
			pushButton.disabled = true;
			this.swRegistrationPage.isSubscribed().then(function(flag) {
				if (flag) {
					this.swRegistrationPage.doUnSubscribe().then(function() {updateBtn()});
				} else {
					this.swRegistrationPage.doSubscribe(true).then(function() {updateBtn()});
				}
			});
		});
	}
});

function updateBtn() {
	var pushButton = document.querySelector('button[name="js-push-btn"]');
	if (Notification.permission === 'denied') {
	  pushButton.textContent = '푸쉬 거부중'; // permission denied from browser
	  pushButton.disabled = true;
	  return;
	}
	
	this.swRegistrationPage.isSubscribed().then(function(flag) {
		if (flag) {
			pushButton.textContent = '푸쉬 활성화중'; //complet subscribed
		} else {
			pushButton.textContent = '푸쉬 비활성화중'; //subscribe not yet
		}
		pushButton.disabled = false;
	})
}

function sendPush() {
	
	
	swRegistrationPage.getEndPoint().then(function(subscribeInfo) {
		
		var title = document.querySelector('input[name="title"]')
		var text = document.querySelector('input[name="text"]');
		var endpointBody = { title: title.value === null ? '' : title.value
				, text: text.value === null ? '' : text.value};
		
		var params = {payLoad : endpointBody
					, subscribeInfo : subscribeInfo};
		
		var httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				if (httpRequest.status === 200) {
					console.log(httpRequest.responseText);
				} else {
					console.log('There was a problem with the request.');
				}
			}
		}
		httpRequest.open('POST', '/clientPush');
		httpRequest.setRequestHeader('Content-Type', 'application/json');
		httpRequest.send(JSON.stringify(params));
	});
	
}


function takePush() {
	const httpRequest = new XMLHttpRequest();
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				console.log(httpRequest.responseText);
			} else {
				console.log('There was a problem with the request.');
			}
		}
	}
	httpRequest.open('POST', '/serverPush');
	httpRequest.setRequestHeader('Content-Type', 'application/json');
	httpRequest.send();
}
