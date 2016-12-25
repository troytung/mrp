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
<link rel="icon" href="favicon.ico" type="image/x-icon"/>
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
<link href="css/list-import.css" rel="stylesheet"/>
<link href="css/trend-import.css" rel="stylesheet"/>
<link href="css/jquery.jqplot.min.css" rel="stylesheet"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
$(function() {
	
  $("#searchBtn").click(function() {
	  $("form").submit();
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

});
</script>
</head>
<body class="listPage">
<s:component template="menu.ftl"></s:component>
<div class="listBox Shadow ">
<form action="channel!query.action" method="post" class="pageContent">
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
                </ul>
            </li>
            <li>
                關鍵字篩選：<input type="text" name="query" value="<s:property value="query"/>" size="40">
            </li>
        </ul>
        <input type="button" id="searchBtn" value="Search»" role="button" aria-disabled="false"/>
    </div>
</div>
</form>
<br/>
<s:if test="siteCnts != null">
<section class="pageContent">
<div style="margin: auto;">
  <h6 class="item percenticon">頻道列表圖</h6>
  <div id="chart1" class="jqplot-target" style="position: relative;"></div>
</div>
</section>
<section class="pageContent">
<div style="margin: auto;">
  <h6 class="item percenticon">頻道佔比圖</h6>
  <div id="chart2" class="jqplot-target" style="position: relative;"></div>
</div>
</section>
</s:if>
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="js/jqplot/excanvas.js"></script><![endif]-->
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/jquery.jqplot.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.barRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.pointLabels.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript">
<%--
var mainCnt = [], replyCnt = [], ticks = [], labels = [];
<s:iterator value="siteCnts" status="s">
labels[<s:property value="#s.index"/>] = <s:property value="totalCnt"/>;
mainCnt[<s:property value="#s.index"/>] = <s:property value="mainCnt"/>;
replyCnt[<s:property value="#s.index"/>] = <s:property value="replyCnt"/>;
ticks[<s:property value="#s.index"/>] = '<s:property value="site.siteName"/>';
</s:iterator>
plot1 = $.jqplot('chart1', [mainCnt, replyCnt], {
  // Tell the plot to stack the bars.
  stackSeries: true,
  //captureRightClick: true,
  seriesDefaults:{
    shadow: false,
    renderer:$.jqplot.BarRenderer,
    rendererOptions: {
        // Put a 30 pixel margin between bars.
        barMargin: 30,
        barDirection: 'horizontal'
    },
    pointLabels: {
      show: true,
      stackedValue: true,
      //location: 'e',
      //ypadding: 26,
      //xpadding: 26,
      //labels: labels
    }
  },
  axes: {
    yaxis: {
        renderer: $.jqplot.CategoryAxisRenderer,
        ticks: ticks
    },
    xaxis: {
      // Don't pad out the bottom of the data range.  By default,
      // axes scaled as if data extended 10% above and below the
      // actual range to prevent data points right on grid boundaries.
      // Don't want to do that here.
      padMin: 0
    }
  }
});
--%>
var mainCnt = [], replyCnt = [], ticks = [];
<s:iterator value="siteCnts" status="s">
mainCnt[<s:property value="#s.index"/>] = [<s:property value="mainCnt"/>,<s:property value="#s.count"/>];
replyCnt[<s:property value="#s.index"/>] = [<s:property value="replyCnt"/>,<s:property value="#s.count"/>];
ticks[<s:property value="#s.index"/>] = '<s:property value="site.siteName"/>';
</s:iterator>
var plot1 = $.jqplot('chart1', [replyCnt, mainCnt], {
    seriesDefaults: {
    	shadow: false,
        renderer:$.jqplot.BarRenderer,
        pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
        rendererOptions: {
            barDirection: 'horizontal'
        }
    },
    axes: {
        yaxis: {
            renderer: $.jqplot.CategoryAxisRenderer,
            ticks: ticks
        }
    },
    series:[ 
      {label:'回文'},
      {label:'主文'}
    ], 
    legend: { show:true, location:'e' }
});
// pie chart
var data = [];
<s:iterator value="siteCnts" status="s">data[<s:property value="#s.index"/>] = ['<s:property value="site.siteName"/>', <s:property value="totalCnt"/>];</s:iterator>
var plot2 = jQuery.jqplot ('chart2', [data], {
	seriesDefaults: {
		shadow: false,
		// Make this a pie chart.
		renderer: jQuery.jqplot.PieRenderer, 
		rendererOptions: {
			// Put data labels on the pie slices.
			// By default, labels show the percentage of the slice.
			showDataLabels: true
		}
	}, 
	legend: { show:true, location: 'e' }
});
</script>
</div>
<s:include value="footer.jsp"/>
</body>
</html>