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
  $("#btnSave").click(function() {
    var _btnSave = $(this);
    _btnSave.attr("disabled", true);
    var checkOk = true;
    $(".schTr").each(function() {
      var _schTr = $(this);
      var ref = _schTr.attr("ref");
      var targetAmt = _schTr.attr("targetAmt");
      var producedAmt = $("#scheduleLst_"+ref+"__produced_amt");
      var packedAmt = $("#scheduleLst_"+ref+"__packed_amt");
      if (typeof producedAmt.val() != 'undefined') {
        if (producedAmt.val() == '') {
          alert("請輸入已生產數量");checkOk=false;return false;
        }
        var producedAmtVal = parseInt(producedAmt.val());
        if(isNaN(producedAmtVal)) {
          alert("已生產數量需為數字");checkOk=false;return false;
        }
        if(producedAmtVal < 0) {
          alert("已生產數量需大於0");checkOk=false;return false;
        }
        if (producedAmtVal > parseInt(targetAmt)) {
          alert("已生產數量不可大於目標生產數量");checkOk=false;return false;
        }
      }
      if (typeof packedAmt.val() != 'undefined') {
        if (packedAmt.val() == '') {
          alert("請輸入已包裝數量");checkOk=false;return false;
        }
        var packedAmtVal = parseInt(packedAmt.val());
        if(isNaN(packedAmtVal)) {
          alert("已包裝數量需為數字");checkOk=false;return false;
        }
        if(packedAmtVal < 0) {
          alert("已包裝數量需大於0");checkOk=false;return false;
        }
        if (packedAmtVal > parseInt(producedAmt.val())) {
          alert("已包裝數量不可大於已生產數量");checkOk=false;return false;
        }
      }
    });
    if (checkOk) {
      $("#form1").submit();
    } else {
      _btnSave.attr("disabled", false);
      return false;
    }
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<form method="post" action="<s:property value="actionName"/>!saveMrpDetail.action" id="form1">
<s:hidden name="scheduleDt"/>
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
  <th>客戶</th>
  <th>品名</th>
  <th>目標生產數量</th>
  <s:if test='actionName=="mrpProduce" || actionName=="mrpPack"'><th>已生產數量</th></s:if>
  <s:if test='actionName=="mrpPack"'><th>已包裝數量</th></s:if>
</tr>
</thead>
<s:iterator value="scheduleLst" status="s">
<tr class="schTr" ref="<s:property value="#s.index"/>" targetAmt="<s:property value="target_amt"/>">
  <td><s:property value="cus_name"/></td>
  <td><a href="modifyProduct!view.action?pro_code=<s:property value="pro_code"/>" target="_blank"><s:property value="pro_name"/></a><s:hidden name="scheduleLst[%{#s.index}].pro_code"/></td>
  <td><s:property value="target_amt"/></td>
  <s:if test='actionName=="mrpProduce" || actionName=="mrpPack"'><td><s:textfield name="scheduleLst[%{#s.index}].produced_amt" cssClass="w3-input w3-border w3-sand" maxlength="5"/></td></s:if>
  <s:if test='actionName=="mrpPack"'><td><s:textfield name="scheduleLst[%{#s.index}].packed_amt" cssClass="w3-input w3-border w3-sand" maxlength="5"/></td></s:if>
</tr>
</s:iterator>
</table>
<br>
<s:if test='actionName=="mrpProduce" || actionName=="mrpPack"'>
<p><button id="btnSave" class="w3-btn w3-padding w3-green" style="width:120px">儲存</button></p>
</s:if>
</s:if>
<s:else>尚無排單資料！</s:else>
</div>
</div>
</form>
</body>
</html>