/**
 * Worker Script
 */

self.addEventListener('push', function(event) {
  console.log('[Service Worker] Push Received.');
  const eventDataJson = event.data.json();
  console.log(`[Service Worker] Push had this title: "${eventDataJson.title}"`);
  console.log(`[Service Worker] Push had this text: "${eventDataJson.text}"`);

  const title = eventDataJson.title;
  const options = {
    body: eventDataJson.text
  };
  

  event.waitUntil(self.registration.showNotification(title, options));
});

self.addEventListener('notificationclick', function(event) {
  console.log('[Service Worker] Notification click Received.');

  event.notification.close();

  event.waitUntil(clients.openWindow('http://www.naver.com'));
});