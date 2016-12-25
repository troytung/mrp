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
  $("#cusCode").change(function() {
    //$("#matCode").empty();
    //$("#unitName").empty();
    $.getJSON( "mrp!ajaxProducts.action", {"cusCode" : $(this).val()}, function( data ) {
      var items = [];
      items.push( "<option value='' disabled selected>產品名稱</option>" );
      $.each( data, function( key, val ) {
        items.push( "<option value='" + val.pro_code + "'>" + val.pro_name + "</option>" );
      });
      $("#proCode").append( items.join( "" ) );
    });
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<form method="post" action="mrp!saveMrp.action" id="form1">
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1>排單維護</h1>
</header>
<div class="w3-container">
<br>
<p>
<label class="w3-text-grey">排單日期：<s:property value="scheduleDt"/></label>
</p>
<p></p>
<div class="w3-row-padding">
<div class="w3-quarter">
<select class="w3-select w3-border" name="product.cus_code" id="product_cus_code">
  <option value="" disabled selected>選擇客戶</option>
  <s:iterator value="customers" var="c">
  <option value="<s:property value="#c.cus_code"/>"><s:property value="#c.cus_name + ' ' + #c.cus_code"/></option>
  </s:iterator>
</select>
</div>
<div class="w3-quarter">
<select class="w3-select w3-border" id="proCode"></select>
</div>
<div class="w3-quarter">
  <input class="w3-input w3-border w3-light-grey" type="text" style="height:25px" placeholder="件數"/>
</div>
<div class="w3-quarter">
  <button class="w3-btn w3-tiny w3-red" style="height:25px" onclick="document.getElementById('li003').style.display=''">新增</button>
</div>
</div>
<p></p>
<ul class="w3-ul w3-border" style="width:50%">
  <li>客戶#001 產品#001 100件<span onclick="this.parentElement.style.display='none'" class="w3-closebtn w3-margin-right w3-medium">x</span></li>
  <li>客戶#002 產品#002 200件<span onclick="this.parentElement.style.display='none'" class="w3-closebtn w3-margin-right w3-medium">x</span></li>
  <li id="li003" style="display:none">客戶#003 產品#003 300件<span onclick="this.parentElement.style.display='none'" class="w3-closebtn w3-margin-right w3-medium">x</span></li>
</ul>
<br>
<p><button class="w3-btn w3-padding w3-teal" style="width:120px" onclick="window.location='mrp.html'">儲存</button></p>
</div>
</div>
</form>
</body>
</html>