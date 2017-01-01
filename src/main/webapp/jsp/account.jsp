<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>註冊使用者帳號</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script>
$(function() {
  $("#btnCreate").click(function() {
    $("form").attr("action", "account!create.action").submit();
  });
  $(".edit").click(function() {
    $("input[name='user.userCode']").val($(this).attr("ref"));
    $("form").attr("action", "account.action").submit();
  });
  $("#btnSave").click(function() {
    $("form").attr("action", "account!save.action").submit();
  });
  $("#btnCancel").click(function() {
    window.location = "account.action";
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1>註冊使用者帳號</h1>
</header>
<div class="w3-container">
	<form method="post">
		<span style="color:teal"><s:property value="message"/></span><br/>
		<input type="text" name="user.userCode" value="<s:property value="user.userCode"/>" size="46" placeholder="帳號(建立後不可更改)" autocomplete="off" maxlength="20" <s:if test="edit">readonly="readonly"</s:if>/><span style="color:red"><s:property value="user.userCodeErr"/></span><br/>
		<s:textfield name="user.userName" size="46" placeholder="名稱"/><span style="color:red"><s:property value="user.userNameErr"/></span><br/>
		<s:textfield name="user.email" size="46" placeholder="Email"/><span style="color:red"><s:property value="user.emailErr"/></span><br/>
		<s:password name="user.password" size="46" placeholder="密碼" autocomplete="off" maxlength="30"/><span style="color:red"><s:property value="user.passwordErr"/></span><br/>
		<s:password name="user.confirmPassword" size="46" placeholder="確認密碼" cssClass="showpassword" autocomplete="off" maxlength="30"/><span style="color:red"><s:property value="user.confirmPasswordErr"/></span><br/><br/>
        系統角色：<s:select list="@com.petfood.mrp.model.Role@values()" listValue="roleName" name="user.roleCode"></s:select><br/>
		管理權限：<s:radio list="#{'true':'有', 'false':'無'}" name="user.admin"/><br/>
		是否啟用：<s:radio list="#{'true':'是', 'false':'否'}" name="user.active"/><br/>
		<s:if test="edit">
			<input type="button" id="btnSave" class="w3-btn w3-padding w3-green" value="儲存"/>
			<input type="button" id="btnCancel" class="w3-btn w3-padding w3-green" value="取消"/>
		</s:if>
		<s:else>
			<input type="button" id="btnCreate" class="w3-btn w3-padding w3-green" value="建立新帳號"/>
		</s:else>
	</form><br/>
	<table class="w3-table w3-striped w3-border">
		<thead>
		<tr>
			<th>帳號</th>
			<th>名稱</th>
			<th>系統角色</th>
			<th>Email</th>
			<th>管理權限</th>
			<th>是否啟用</th>
			<th></th>
		</tr>
		</thead>
	<tbody>
	<s:iterator value="users">
		<tr>
			<td><s:property value="userCode"/></td>
			<td><s:property value="userName"/></td>
            <td><s:property value="role.roleName"/></td>
			<td><s:property value="email"/></td>
			<td><s:if test="admin">有</s:if><s:else>無</s:else></td>
			<td><s:if test="active">是</s:if><s:else>否</s:else></td>
			<td><a href="javascript:void(0)" class="edit" ref="<s:property value="userCode"/>">編輯</a></td>
		</tr>
	</s:iterator>
	</tbody>
	</table><br/>
</div>
</div>
</body>
</html>