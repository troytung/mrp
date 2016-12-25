<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta charset="utf-8">
<title></title>
<style>
body, div {
  vertical-align: baseline;
}
</style>
<%--<link rel="icon" href="favicon.ico" type="image/x-icon"/>--%>
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
<link href="css/list-import.css" rel="stylesheet"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script>
$(function() {
	
	var $filenames = $("#filenames"),
	blockUI = function() {
		$.blockUI({ css: {
		      border: 'none',
		      padding: '15px',
		      backgroundColor: '#000',
		      '-webkit-border-radius': '10px',
		      '-moz-border-radius': '10px',
		      opacity: .5,
		      color: '#fff'
		    } });
	},
	ajaxSubmit = function(datastring) {
		  blockUI();
	    $.ajax({
	      type: "POST",
	      url: "list!query.action",
	      data: datastring,
	      dataType: "html",
	      success: function(data) {
	        $("#result").empty().html(data);
	        $("p.text-content").each(function(){
	    		var text = $(this).text();
	    		if(text.length > 250) {
	    			text = text.substring(0, 250) + "...";
	    			$(this).text(text);
	    		}
	    	});
	        $.unblockUI();
	      },
	      error: function(jqXHR) {
	        $.unblockUI();
	        if (jqXHR.status === 0) {
              alert('未連線，請檢查網路');
            } else if (jqXHR.status == 401) {
              window.location = 'login.action';
            } else {
              alert("發生錯誤，請稍後再試");
            }
	      }
	    });
	};
	
  //$("#radio").buttonset();
  $("#sortRadio").buttonset();
  <%--
  $("#searchBtn")
    .button()
    .click(function() {
      this.disabled = true;
      $("#form1").submit();
    });
  $("#pageSel").change(function(){
    
    $("#page").val($(this).find("option:selected").val());
    $("#prevForm").submit();
  });
  --%>
  $("#searchBtn").click(function() {
    (function() {
      $("#hiddenDiv").remove();
      var $form = copyForm("list!query").attr("id", "prevForm");
      var $div = $("<div id='hiddenDiv' style='display:none;'></div>").append($form);
      $form.append("<input type='hidden' name='page' id='page' value='1'/>");
      $('body').append($div);
    })();
    ajaxSubmit($("#form1").serialize());
    $filenames.empty();
  });
  
  $(document).on("change", "#pageSel", function() {
    $("#page").val($(this).find("option:selected").val());
    ajaxSubmit($("#prevForm").serialize());
  });
  
  
  $.datepicker.setDefaults({
      dateFormat : 'yy/mm/dd',
      changeMonth : true,
      changeYear : true,
      //showOn : 'button',
      showOtherMonths : true,
      firstDay : 0
  });
  
  var $bd = $("#beginDate"), $ed = $("#endDate"),
  absMinDate = $.datepicker.parseDate('yy/mm/dd', '<s:property value="searchMinDate"/>');
 
  
  var dateRange = function(input) {
    
    var obj, minDate = absMinDate;

    if(input.id === "beginDate") {
      
      obj = {
          defaultDate: minDate,
          minDate: minDate,
          maxDate: $ed.datepicker("getDate") || new Date()
      };
    }
    else {
      obj = {
          minDate: $bd.datepicker("getDate") || minDate,
          maxDate: new Date()
      };
    }
    return obj;
  };
  
  var config = {
    constrainInput: true,
    beforeShow: dateRange
  };
  
  $bd.datepicker(config);
  $ed.datepicker(config);
  
  
  var disableDate = function() {
    $("#yearMonth").prop("disabled", !$("#dateType3").prop("checked"));
    
    var checked = $("#dateType4").prop("checked");
    $ed.prop("disabled", !checked);
    $bd.prop("disabled", !checked);
  };
  
  disableDate();
  $("[name=dateType]").click(disableDate);

  var copyForm = function(action) {
    var $form = $('<form action="' + action + '.action" method="post"></form>');
      $("#form1").find(":input").clone().appendTo($form);
      return $form;
  };
  
  $("#excel").click(function(){
	  
	  blockUI();
	  $.ajax({
	    type: "POST",
	    dataType: "json",
		  url: "list!generateExcel.action",
		  data: $("#form1").serialize(),
		  success: function(data) {
			  $filenames.empty();
			  $filenames.html($.map(data, function(filename){
				  return "<li><a href='list!download.action?filename="+filename+"' class='download-anchor' target='_blank'>"+filename+"</a></li>";
			  }).join(""));
        $.unblockUI();
      },
      error: function() {
        $.unblockUI();
        alert("發生錯誤，請稍後再試");
      }
	  });
	  
	  /*
    var $form = copyForm("list!generateExcel");
    $('body').append($form);
    $form.submit();
    $form.remove();
    */
  });
  
});
</script>
</head>
<body class="listPage">
<s:component template="menu.ftl"></s:component>
<%--
<div class="point">
<section class="topmenuArea">
  <ul class="topmenuBox">
    <li><a href="home.action" class="">home</a></li>
    <li><a href="trendsPlot.action" class="">SpiderMan Trend Plot</a></li>
    <li><a href="list.action" class="now">SpiderMan List</a></li>
    <li><a href="report.action" class="">Report</a></li>
    <li><a href="category.action" class="">Category</a></li>
  </ul>
  <div class="topmenuline Shadow1"></div>
  <span class="pullBox Shadow1"></span>
</section>
</div>
<script>
$(function() {
  $(".topmenuBox li a").click(function() {
    $(".topmenuBox li a").removeClass("now");
    $(this).addClass("now");
  });
  var showText='+';
  var hideText='-';
  var is_visible = false;
  $(".pullBox").html(showText);
  $('.pullBox').click(function() {
    is_visible = !is_visible;
    $(".pullBox").html( (!is_visible) ? showText : hideText);
    if(is_visible==false){
      $('.topmenuBox').slideUp();
    }else {
      $('.topmenuBox').slideDown();
    }
  });
});
</script>
--%>
<div class="listBox Shadow ">
<form id="form1" action="list!query.action" method="post" class="pageContent">
<div class="ui-accordion ui-widget ui-helper-reset filterBox">
  <div class="filterContent Shadow1">
      <ul>
          <li><input type="radio" id="dateType1" name="dateType" value="1" <s:if test="dateType == 1">checked="checked"</s:if>><label for="dateType1">近15天</label></li>
            <li><input type="radio" id="dateType2" name="dateType" value="2" <s:if test="dateType == 2">checked="checked"</s:if>><label for="dateType2">近30天</label></li>
            <li><input type="radio" id="dateType3" name="dateType" value="3" <s:if test="dateType == 3">checked="checked"</s:if>>
                <select id="yearMonth" name="yearMonth">
                    <s:iterator value="yearMonths" var="ym">
                      <option value="<s:property />" <s:if test="#ym == yearMonth">selected="selected"</s:if>><s:property/></option>
                    </s:iterator>
                </select>
            </li>
            <li class="oneRow">
                <input type="radio" id="dateType4" name="dateType" value="4" <s:if test="dateType == 4">checked="checked"</s:if>>
                <input type="text" name="beginDate" id="beginDate" value="<s:property value="beginDate"/>" placeholder="設定開始日期" ><%--class="hasDatepicker" --%>
                <input type="text" name="endDate" id="endDate" value="<s:property value="endDate"/>" placeholder="設定結束日期" ><%--class="hasDatepicker" --%>
            </li>
            <li class="oneRow" id="radio">
              <ul>
              <s:iterator value="categories" status="s">
                <s:set name="rid" value="%{'radio' + #s.count}"/>
                <li><input type="radio" id="<s:property value="#rid"/>" name="categoryId" value="<s:property value="id"/>" <s:if test="categoryId == id">checked="checked"</s:if>/><label for="<s:property value="#rid"/>"><s:property value="name"/></label></li>
              </s:iterator>
              <%--
                  <li><input type="radio" id="radio1" name="categoryId" value="1" checked="checked"><label for="radio1">hTC</label></li>
                    <li><input type="radio" id="radio2" name="categoryId" value="2"><label for="radio2">Samsung</label></li>
                    <li><input type="radio" id="radio3" name="categoryId" value="3"><label for="radio3">Sony</label></li>
                    <li><input type="radio" id="radio4" name="categoryId" value="4"><label for="radio4">iPhone</label></li>
                    <li><input type="radio" id="radio5" name="categoryId" value="5"><label for="radio5">NEW hTC One</label></li>
                    <li><input type="radio" id="radio6" name="categoryId" value="6"><label for="radio6">hTC Butterfly</label></li>
                    <li><input type="radio" id="radio7" name="categoryId" value="7"><label for="radio7">Samsung Note</label></li>
--%>
                </ul>
            </li>
            <li>
              <input type="radio" id="orradio1" name="useOr" value="true" <s:if test="useOr == true">checked="checked"</s:if>><label for="orradio1">OR</label>
                <input type="radio" id="orradio2" name="useOr" value="false" <s:if test="useOr == false">checked="checked"</s:if>><label for="orradio2">AND</label>
                <input type="text" name="query" value="<s:property value="query"/>" size="40">
            </li>
            <li class="oneRow ui-buttonset" id="sortRadio">
              <ul>
                  <li><input type="radio" id="sradio1" name="sort" value="DATE" <s:if test="@com.petfood.mrp.web.action.SortColumn@DATE == sort">checked="checked"</s:if> ><label for="sradio1">Date</label></li>
                    <li><input type="radio" id="sradio2" name="sort" value="POSITIVE" <s:if test="@com.petfood.mrp.web.action.SortColumn@POSITIVE == sort">checked="checked"</s:if> ><label for="sradio2">Positive</label></li>
                    <li><input type="radio" id="sradio3" name="sort" value="NEGATIVE" <s:if test="@com.petfood.mrp.web.action.SortColumn@NEGATIVE == sort">checked="checked"</s:if> ><label for="sradio3">Negative</label></li>
                </ul>
            </li>
            <li class="oneRow">
              Exclude: <input type="text" name="exclude" value="<s:property value="getExclude()"/>" size="40">
            </li>
        </ul>        
        <input type="button" id="searchBtn" value="Search»" role="button" aria-disabled="false">
        <input type="button" id="excel" value="Generate Excel»">
    </div>
</div>
</form>
<br>
<ol id="filenames">
</ol>
<div id="result"></div>
</div>
<s:include value="footer.jsp"/>
</body>
</html>