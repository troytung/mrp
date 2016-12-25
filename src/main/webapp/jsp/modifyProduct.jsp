<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>產品維護</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script>
$(function() {

  $("#suppCode").change(function() {
    $("#matCode").empty();
    $("#unitName").empty();
    $.getJSON( "modifyProduct!ajaxMaterials.action", {"suppCode" : $(this).val()}, function( data ) {
      var items = [];
      items.push( "<option value='' disabled selected>原料名稱</option>" );
      $.each( data, function( key, val ) {
        items.push( "<option value='" + val.mat_code + "' unit='" + val.unit_name + "'>" + val.mat_name + "</option>" );
      });
      $("#matCode").append( items.join( "" ) );
    });
  });
  
  $("#matCode").change(function() {
    $("#unitName").empty().text( $(this).find(":selected").attr("unit") );
  });
  
  $("#addMat").click(function() {
    
    if($("#suppCode").val() == null) {
      alert("請選擇原料廠商");
      return;
    }
    var selectedMatCode = $("#matCode option:selected");
    if(selectedMatCode == null || selectedMatCode.val() == '') {
      alert("請選擇原料名稱");
      return;
    }
    if($("#amount").val() == '') {
      alert("請輸入數量");
      return;
    }
    if(isNaN($("#amount").val())) {
      alert("數量需為數字");
      return;
    }
    if($("#amount").val() <= 0) {
      alert("數量需大於零");
      return;
    }
    var matCodeExist = false;
    $(".matCodes").each(function() {
      if (selectedMatCode.val() == $(this).val()) {
        matCodeExist = true;
        return false;
      }
    });
    if (matCodeExist) {
      alert(selectedMatCode.text() + "已存在，請勿重複輸入");
      return;
    }
    
    var mat = formMat($("#suppCode option:selected").text(), selectedMatCode.val(), selectedMatCode.text(), selectedMatCode.attr("unit"), $("#amount").val());
    $("#matLst").append(mat);
  });
  
  var matCnt = 0;
  var formMat = function(suppName, matCode, matName, matUnit, amount, viewOnly) {
    matCnt++;
    var mat = suppName + " | " + matName + " | " + amount + " " + matUnit;
    mat += "<input type='hidden' name='proMats[" + matCnt + "].mat_code' class='matCodes' value='" + matCode + "'/>";
    mat += "<input type='hidden' name='proMats[" + matCnt + "].amounts' value='" + amount + "'/>";
    var res = "<li id='li" + matCnt + "'>" + mat;
    if (!viewOnly) {
      res += "<span ref='li" + matCnt + "' class='w3-closebtn w3-margin-right w3-medium'>x</span>";
    }
    res += "</li>";
    return res;
  };
  
  $(document).on("click", ".w3-closebtn", function() {
    var ref = $(this).attr("ref");
    $("#" + ref).remove();
  });
  
  $("#saveBtn").click(function(){
    if($("#product_cus_code").val() == null) {
      alert("請選擇客戶");
      return;
    }
    if($("#product_pro_name").val() == null || $("#product_pro_name").val() == '') {
      alert("請輸入品名");
      return;
    }
    if($("input[name^='proMats']").length == 0) {
      alert("請加入配方");
      return;
    }
    $("#form1").submit();
  });
  
  var proMatsJson = <s:property value="proMatsJson" escape="false" default="[]"/>;
  $.each(proMatsJson, function( i, v ) {
    var mat = formMat(v.supp_name, v.mat_code, v.mat_name, v.unit_name, v.amounts, <s:property value="viewOnly"/>);
    $("#matLst").append(mat);
  });
});
</script>
</head>
<body>
<s:component template="menu.ftl"></s:component>
<form method="post" action="modifyProduct!save.action" id="form1">
<s:hidden name="product.pro_code"/>
<div style="margin-left:20%">
<header class="w3-container w3-teal">
  <h1><s:if test="viewOnly">產品明細</s:if><s:else>產品維護</s:else></h1>
</header>
<div class="w3-container">
<br>
<label class="w3-text-grey">客戶</label>
<s:if test="viewOnly || forModify">
<div class="w3-input w3-border"><s:property value="product.cus_name"/></div>
<s:hidden id="product_cus_code" name="product.cus_code"/>
</s:if>
<s:else>
<select class="w3-select w3-border" name="product.cus_code" id="product_cus_code">
  <option value="" disabled selected>選擇客戶</option>
  <s:iterator value="customers" var="c">
  <option value="<s:property value="#c.cus_code"/>"><s:property value="#c.cus_name + ' ' + #c.cus_code"/></option>
  </s:iterator>
</select>
</s:else>
<p/>
<label class="w3-text-grey">品名</label>
<input class="w3-input w3-border" type="text" name="product.pro_name" value="<s:property value="product.pro_name"/>" id="product_pro_name" <s:if test="viewOnly || forModify">readonly="true"</s:if>/>
<p/>
<label class="w3-text-grey">配方描述</label>
<textarea class="w3-input w3-border" style="resize:none" name="product.pro_desc" <s:if test="viewOnly">readonly="true"</s:if>><s:property value="product.pro_desc"/></textarea>
<p/>
<label class="w3-text-grey">配方</label>
<s:if test="!viewOnly">
<div class="w3-row-padding">
<div class="w3-quarter">
<select class="w3-select w3-border" id="suppCode">
  <option value="" disabled selected>原料廠商</option>
  <s:iterator value="suppliers" var="s">
  <option value="<s:property value="#s.supp_code"/>"><s:property value="#s.supp_name"/></option>
  </s:iterator>
</select>
</div>
<div class="w3-quarter">
<select class="w3-select w3-border" id="matCode"></select>
</div>
<div class="w3-quarter">
  <input class="w3-input w3-border w3-light-grey" type="text" style="height:25px" maxlength="5" id="amount"/>
</div>
<div class="w3-quarter">
  <span id="unitName"></span>&nbsp;<input type="button" class="w3-btn w3-tiny w3-red" style="height:25px" id="addMat" value="新增"/>
</div>
</div>
</s:if>
<p/>
<ul class="w3-ul" style="width:50%" id="matLst"></ul>
<br>
<s:if test="!viewOnly">
<p><input id="saveBtn" type="button" class="w3-btn w3-padding w3-teal" style="width:120px" value="儲存"/></p>
</s:if>
</div>
</div>
</form>
</body>
</html>