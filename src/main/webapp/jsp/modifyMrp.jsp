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
  
  $("#addPro").click(function() {
    
    if($("#cusCode").val() == null) {
      alert("請選擇客戶");
      return;
    }
    var selectedProCode = $("#proCode option:selected");
    if(selectedProCode == null || selectedProCode.val() == '') {
      alert("請選擇產品名稱");
      return;
    }
    if($("#amount").val() == '') {
      alert("請輸入件數");
      return;
    }
    if(isNaN($("#amount").val())) {
      alert("件數需為數字");
      return;
    }
    if($("#amount").val() <= 0) {
      alert("件數需大於零");
      return;
    }
    var proCodeExist = false;
    $(".proCodes").each(function() {
      if (selectedProCode.val() == $(this).val()) {
        proCodeExist = true;
        return false;
      }
    });
    if (proCodeExist) {
      alert(selectedProCode.text() + "已存在，請勿重複輸入");
      return;
    }
    
    var pro = formPro($("#cusCode option:selected").text(), selectedProCode.val(), selectedProCode.text(), $("#amount").val());
    $("#proLst").append(pro);
  });
  
  var proCnt = 0;
  var formPro = function(cusName, proCode, proName, amount, viewOnly) {
    proCnt++;
    var pro = cusName + " | " + proName + " | " + amount + "件";
    pro += "<input type='hidden' name='scheduleLst[" + proCnt + "].pro_code' class='proCodes' value='" + proCode + "'/>";
    pro += "<input type='hidden' name='scheduleLst[" + proCnt + "].target_amt' value='" + amount + "'/>";
    var res = "<li id='li" + proCnt + "'>" + pro;
    if (!viewOnly) {
      res += "<span ref='li" + proCnt + "' class='w3-closebtn w3-margin-right w3-medium'>x</span>";
    }
    res += "</li>";
    return res;
  };
  
  $(document).on("click", ".w3-closebtn", function() {
    var ref = $(this).attr("ref");
    $("#" + ref).remove();
  });
  
  $("#saveBtn").click(function(){
    if($("input[name^='scheduleLst']").length == 0) {
      alert("請加入產品");
      return;
    }
    $("#form1").submit();
  });
  
  var scheduleJson = <s:property value="scheduleJson" escape="false" default="[]"/>;
  $.each(scheduleJson, function( i, v ) {
    var pro = formPro(v.cus_name, v.pro_code, v.pro_name, v.target_amt);
    $("#proLst").append(pro);
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<form method="post" action="mrp!saveMrp.action" id="form1">
<s:hidden name="scheduleDt"/>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1>排單維護</h1>
</header>
<div class="w3-container">
<br>
<p>
<label class="w3-text-grey">排單日期：<s:property value="scheduleDt"/></label>
</p>
<p/>
<div class="w3-row-padding">
<div class="w3-quarter">
<select class="w3-select w3-border" id="cusCode">
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
  <input class="w3-input w3-border w3-light-grey" type="text" style="height:25px" maxlength="5" id="amount" placeholder="件數"/>
</div>
<div class="w3-quarter">
  <input type="button" class="w3-btn w3-tiny w3-red" style="height:25px" id="addPro" value="新增"/>
</div>
</div>
<p/>
<ul class="w3-ul" style="width:50%" id="proLst"></ul>
<br>
<s:if test="!viewOnly">
<p><input id="saveBtn" type="button" class="w3-btn w3-padding w3-teal" style="width:120px" value="儲存"/></p>
</s:if>
</div>
</div>
</form>
</body>
</html>