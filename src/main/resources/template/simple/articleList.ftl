<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table border="1">
<tr>
<td>序號</td>
<td>標題</td>
<td>發佈時間</td>
<td>主文/回文</td>
<td>討論串總則數</td>
<td>作者</td>
<td>內容</td>
<td>來源網站</td>
<td>點閱數</td>
<td>正面強度</td>
<td>負面強度</td>
</tr>
<#list articles as a>
<tr>
<td>${a_index + 1}</td>
<td>${a.topic}</td>
<td>${a.postDate.toDate()?datetime}</td>
<td><#if a.reply>回文<#else>主文</#if></td>
<td>${a.replyCnt}</td>
<td>${a.poster}</td>
<td>${a.text}</td>
<td>${a.site.siteName}</td>
<td>${a.viewCnt}</td>
<td>${a.positive}</td>
<td>${a.negative}</td>
</tr>
</#list>
</body>
</html>