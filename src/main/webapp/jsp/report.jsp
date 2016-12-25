<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Report</title>
<%--<link rel="icon" href="favicon.ico" type="image/x-icon"/>--%>
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
<link href="css/trend-import.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>
<body class="trendPage">
<s:component template="menu.ftl"></s:component>
<%--
<div class="point">
<section class="topmenuArea">
  <ul class="topmenuBox">
    <li><a href="home.action" class="">home</a></li>
    <li><a href="trendsPlot.action" class="">SpiderMan Trend Plot</a></li>
    <li><a href="list.action" class="">SpiderMan List</a></li>
    <li><a href="report.action" class="now">Report</a></li>
    <li><a href="category.action" class="">Category</a></li>
  </ul>
  <div class="topmenuline Shadow1"></div>
  <span class="pullBox Shadow1"></span>
</section>
</div>
 --%>
<div class="trendBox Shadow">
<form id="form1" action="report!genReport.action" method="post" class="pageContent">
<div class="ui-accordion ui-widget ui-helper-reset filterBox">
    <div class="filterContent Shadow1">
        <ul>
            <li><input type="radio" id="dateType1" name="dateType" value="1" <s:if test="dateType == 1">checked="checked"</s:if>><label for="dateType1">近15天</label></li>
            <li><input type="radio" id="dateType2" name="dateType" value="2" <s:if test="dateType == 2">checked="checked"</s:if>><label for="dateType2">近30天</label></li>
            <li>
                <input type="radio" id="dateType3" name="dateType" value="3" <s:if test="dateType == 3">checked="checked"</s:if>>
                <select id="yearMonth" name="yearMonth" >
                    <s:iterator value="yearMonths" var="ym">
                      <option value="<s:property />" <s:if test="#ym == yearMonth">selected="selected"</s:if>><s:property/></option>
                    </s:iterator>
                </select>
            </li>
            <li class="oneRow">
                <input type="radio" id="dateType4" name="dateType" value="4" <s:if test="dateType == 4">checked="checked"</s:if>>
                <input type="text" name="beginDate" id="beginDate" value="<s:property value="beginDate"/>" placeholder="設定開始日期" >
                <input type="text" name="endDate" id="endDate" value="<s:property value="endDate"/>" placeholder="設定結束日期">
            </li>
        </ul>
        <input type="button" id="drawBtn" value="產生報表»">      
    </div>
</div>
</form>
<s:if test="postDateCntMap != null">
<h3>每日文章數量表：</h3><br/>
<table id="data">
<thead>
<tr>
	<th>日期</th>
	<th>PhoneHK</th>
	<th>Uwants</th>
	<th>discuss</th>
	<th>unwire</th>
	<th>ePrice</th>
	<th>DCFever</th>
</tr>
</thead>
<tbody>
<s:iterator value="postDateCntMap" var="v">
<tr align="right">
	<td><s:property value="#v.key"/><s:date name="#v.key"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@PHONE_HK)" default="0"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@UWANTS)" default="0"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@DISCUSS)" default="0"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@UNWIRE)" default="0"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@EPRICE)" default="0"/></td>
	<td><s:property value="#v.value.get(@com.petfood.mrp.model.site.Site@DCFEVER)" default="0"/></td>
</tr>
</s:iterator>
</tbody>
</table>
<br/>
</s:if>

<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
<%--
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
--%>
<script type="text/javascript">
<%-- menu start
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
 menu end--%>
$("#drawBtn").click(function() {
  this.diabled = true;
  $("#form1").submit();
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

$('#data').dataTable({
	   "bFilter": false,
	   "bInfo": false,
	   "bPaginate": false,
	   "aaSorting": [[ 0, "desc" ]]
	});
</script>
</div>
<s:include value="footer.jsp"/>
</body>
</html>