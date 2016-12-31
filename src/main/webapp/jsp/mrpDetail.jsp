<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="mrpParam.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script>
$(function() {
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1><s:property value="mrpParam.title"/></h1>
</header>
<div class="w3-container">
<br>
<p>
<label class="w3-text-grey">排單日期：<s:property value="scheduleDt"/></label>
</p>
<s:if test="scheduleLst.size > 0">
<table class="w3-table w3-striped w3-border">
<thead>
<tr class="w3-green">
  <th width="20%">客戶</th>
  <th width="20%">品名</th>
  <th width="20%">目標生產數量</th>
  <th width="20%">已生產數量</th>
  <th width="20%">已包裝數量</th>
</tr>
</thead>
<s:iterator value="scheduleLst" var="s">
<tr>
  <td><s:property value="cus_name"/></td>
  <td><s:property value="pro_name"/></td>
  <td><s:property value="target_amt"/></td>
  <td><input class="w3-input w3-border w3-sand" type="text" style="height:25px;" value="<s:property value="produced_amt"/>"/></td>
  <td><input class="w3-input w3-border w3-sand" type="text" style="height:25px;" value="<s:property value="packed_amt"/>"/></td>
</tr>
</s:iterator>
</table>
<br>
<p>
<button id="bt001" class="w3-btn w3-padding w3-green" style="width:120px" onclick="document.getElementById('bt001').style.display='none';document.getElementById('sp001').style.display='';">儲存</button>
<span id="sp001" class="w3-red" style="display:none">資料已儲存</span>
</p>
</s:if>
<s:else>尚無排單資料！</s:else>
</div>
</div>
</body>
</html>