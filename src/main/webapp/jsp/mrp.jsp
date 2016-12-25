<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>排單維護</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script>
$(function() {
  $("li").click(function(){
    window.location = "mrp!modifyMrp.action?scheduleDt=" + $(this).attr("ref");
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1>排單</h1>
</header>
<div class="w3-container">
  <p>點選日期可編輯排單</p>
  <ul class="w3-ul w3-hoverable w3-border">
  <s:iterator value="scheduleDtLst">
    <li ref="<s:property/>"><s:property/></li>
  </s:iterator>
  </ul>
  <br/>
</div>
</div>
</body>
</html>