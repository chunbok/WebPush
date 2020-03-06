<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/static/js/WorkerObjectmanager.js"></script>
<script type="text/javascript" src="/static/js/test.js"></script>
</head>
<body>
푸쉬 구독 테스트페이지
<div>
	<button disabled name="js-push-btn"></button>
</div>
<div>
	<input type="text" name="title" title="This is the text of the tooltip" />
	<input type="text" name="text" />
	<button name="sendPsuh" onclick="javascript:sendPush();">푸쉬보내기</button>
</div>
<div>
	<button name="takePush" onclick="javascript:takePush()">푸쉬받기</button>
</div>
</body>
</html>