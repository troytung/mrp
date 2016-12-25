<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>產品查詢</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script>
$(function() {
  $("#searchBtn").click(function(){
    if($("#cusCode").val() == null) {
      alert("請選擇客戶");
      return;
    }
    $("#form1").submit();
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1>產品查詢</h1>
</header>
<p>
<div class="w3-container">
<form method="post" action="searchProduct!search.action" id="form1">
<label class="w3-text-grey">客戶</label>
<select class="w3-select w3-border" name="cusCode" id="cusCode">
  <option value="" disabled selected>選擇客戶</option>
  <s:iterator value="customers" var="c">
  <option value="<s:property value="#c.cus_code"/>" <s:if test="#c.cus_code == cusCode">selected</s:if>><s:property value="#c.cus_name + ' ' + #c.cus_code"/></option>
  </s:iterator>
</select>
<p>      
<label class="w3-text-grey">品名</label>
<s:textfield name="proName" class="w3-input w3-border"/>
</p>
<p><button class="w3-btn w3-padding w3-teal" style="width:120px">查詢</button></p>
</form>
<br>
<s:if test="products.size() > 0">
<table id="tb001" class="w3-table w3-striped w3-border">
<thead>
<tr class="w3-green">
  <th>客戶</th>
  <th>品名</th>
  <th></th>
</tr>
</thead>
<s:iterator value="products" var="p">
<tr>
  <td><s:property value="#p.cus_name"/></td>
  <td><s:property value="#p.pro_name"/></td>
  <td><span onclick="window.location='modifyProduct!modify.action?pro_code=<s:property value="#p.pro_code"/>'" class="w3-closebtn w3-margin-right w3-medium">編輯</span>
    <span onclick="window.location='modifyProduct!view.action?pro_code=<s:property value="#p.pro_code"/>'" class="w3-closebtn w3-margin-right w3-medium">查看</span></td>
</tr>
</s:iterator>
</table>
</s:if>
<br>
</div>
</div>
</body>
</html>