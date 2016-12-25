<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trends Plot</title>
<%--<link rel="icon" href="favicon.ico" type="image/x-icon"/>--%>
<link class="include" rel="stylesheet" type="text/css" href="js/jqplot/jquery.jqplot.min.css"/>
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
<link href="css/list-import.css" rel="stylesheet"/>
<link href="css/trend-import.css" rel="stylesheet"/>
<%--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>--%>
<style type="text/css">
.jqplot-target {
margin:8px 0;
height: 460px;
width: 900px;
color: #333;
}

.ui-widget-content {
<%--
background: #feffff; /* Old browsers */
background: -moz-linear-gradient(top,  #feffff 0%, #ddf1f9 35%, #a0d8ef 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#feffff), color-stop(35%,#ddf1f9), color-stop(100%,#a0d8ef)); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  #feffff 0%,#ddf1f9 35%,#a0d8ef 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  #feffff 0%,#ddf1f9 35%,#a0d8ef 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  #feffff 0%,#ddf1f9 35%,#a0d8ef 100%); /* IE10+ */
background: linear-gradient(to bottom,  #feffff 0%,#ddf1f9 35%,#a0d8ef 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#feffff', endColorstr='#a0d8ef',GradientType=0 ); /* IE6-9 */
--%>
background: #ffffff;
padding:15px;
border:1px solid #eee;
}

table.jqplot-table-legend {
border: 0px;background-color: rgba(100,100,100, 0.0);
}

.jqplot-highlighter-tooltip {
background-color: rgba(57,57,57, 0.9);
padding: 7px;
color: #fff;
}
.jqplot-yaxis-tick{color:#555;}
#chart1 .jqplot-xaxis-tick{color:#4b627b;}
#chart2 .jqplot-xaxis-tick{color:#4b627b;display:inline-block;padding:4px;border-radius:2px;}
.jqplot-table-legend{color:#333;padding-left:3px;}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>
<body class="trendPage ">
<s:component template="menu.ftl"></s:component>
<%--
<div class="point">
<section class="topmenuArea">
  <ul class="topmenuBox">
    <li><a href="home.action" class="">home</a></li>
    <li><a href="trendsPlot.action" class="now">SpiderMan Trend Plot</a></li>
    <li><a href="list.action" class="">SpiderMan List</a></li>
    <li><a href="report.action" class="">Report</a></li>
    <li><a href="category.action" class="">Category</a></li>
  </ul>
  <div class="topmenuline Shadow1"></div>
  <span class="pullBox Shadow1"></span>
</section>
</div>
--%>
<div class="trendBox Shadow">


<form id="form1" action="trendsPlot!draw.action" method="post" class="pageContent">
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
                <input type="text" name="endDate" id="endDate" value="<s:property value="endDate"/>" placeholder="設定結束日期" >
            </li>
            <li class="oneRow">
                <ul id="radio">
                <s:iterator value="categories" status="s">
                  <s:set name="rid" value="%{'radio' + #s.count}"/>
                  <li><input type="checkbox" id="<s:property value="#rid"/>" name="categoryIds" <s:if test="id in categoryIds">checked="checked"</s:if> value="<s:property value="id"/>"/><label for="<s:property value="#rid"/>"><s:property value="name"/></label></li>
                </s:iterator>
                <%--
                    <li><input type="checkbox" id="radio1" name="categoryIds" value="1"><label for="radio1">hTC</label></li>
                    <li><input type="checkbox" id="radio2" name="categoryIds" checked="checked" value="2"><label for="radio2">Samsung</label></li>
                    <li><input type="checkbox" id="radio3" name="categoryIds" value="3"><label for="radio3">Sony</label></li>
                    <li><input type="checkbox" id="radio4" name="categoryIds" value="4"><label for="radio4">iPhone</label></li>
                    <li><input type="checkbox" id="radio5" name="categoryIds" value="5"><label for="radio5">NEW hTC One</label></li>
                    <li><input type="checkbox" id="radio6" name="categoryIds" checked="checked" value="6"><label for="radio6">hTC Butterfly</label></li>
                    <li><input type="checkbox" id="radio7" name="categoryIds" value="7"><label for="radio7">Samsung Note</label></li>
                    --%>
                </ul>
            </li>
        </ul>
        <input type="button" id="drawBtn" value="走勢圖»">      
    </div>
</div>
</form>
<s:if test="platDataLst != null">
<section class="pageContent">
    <h6 class="item waveicon">走勢圖</h6>
    <div class="ui-widget-content ui-corner-bottom">
        <div id="chart1" class="jqplot-target" style="position: relative;"></div>
    </div>
</section>
<section class="pageContent">
<div class="ui-widget ui-corner-all" style="margin: auto;">
  <h6 class="item percenticon">佔比圖</h6>
    <div class="ui-widget-content ui-corner-bottom">
    <div id="chart2" class="jqplot-target" style="position: relative;"></div>
    </div>
</div>
</section>
<div id="result"></div>
</s:if>
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="js/jqplot/excanvas.js"></script><![endif]-->

<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- Don't touch this! -->
<script class="include" type="text/javascript" src="js/jqplot/jquery.jqplot.min.js"></script>
<!-- End Don't touch this! -->
<!-- Additional plugins go here -->
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.barRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.pointLabels.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.logAxisRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
<script class="include" type="text/javascript" src="js/jqplot/plugins/jqplot.highlighter.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<!-- End additional plugins -->
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
  if ($("input[name=categoryIds]:checked").length == 0) {
    alert("請至少勾選一主題！");
    return;
  }
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
var endMinDate = new Date(absMinDate);
endMinDate.setDate(endMinDate.getDate()+1);

<%--
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
--%>
$bd.datepicker({
	defaultDate: absMinDate,
	minDate: absMinDate,
	maxDate: -1, 
	onClose: function(selectedDate) {
		if (selectedDate != '') {
			var dt = new Date(selectedDate);
			dt.setDate(dt.getDate()+1);
			$ed.datepicker( "option", "minDate", dt );
		}
	}
});
$ed.datepicker({
	minDate: endMinDate,
	maxDate: 0,
	onClose: function(selectedDate) {
		if (selectedDate != '') {
			var dt = new Date(selectedDate);
			dt.setDate(dt.getDate()-1);
			$bd.datepicker( "option", "maxDate", dt );
		}
	}
});

var disableDate = function() {
  $("#yearMonth").prop("disabled", !$("#dateType3").prop("checked"));
  
  var checked = $("#dateType4").prop("checked");
  $ed.prop("disabled", !checked);
  $bd.prop("disabled", !checked);
};

disableDate();
$("[name=dateType]").click(disableDate);


$.jqplot._noToImageButton = true;
var dataAry = [], labelAry = [], colorAry = [], catIdAry = [];<%--//, markerAry = [];--%>

<s:iterator value="platDataLst" status="s1">
var s<s:property value="#s1.index"/> = [<s:iterator value="data" status="s2"><s:if test="#s2.index>0">,</s:if>["<s:property value="formatDateTime(x)"/>",<s:property value="y"/>]</s:iterator>];
dataAry[<s:property value="#s1.index"/>] = s<s:property value="#s1.index"/>;
labelAry[<s:property value="#s1.index"/>] = '<s:property value="label"/>';//{'label':'<s:property value="label"/>', color:'<s:property value="color"/>' };
colorAry[<s:property value="#s1.index"/>] = '#<s:property value="color"/>';
catIdAry[<s:property value="#s1.index"/>] = '<s:property value="category_id"/>';
<%--markerAry[<s:property value="#s1.index"/>] = <s:property value="markerOptions"/>;--%>
</s:iterator>

$('#chart1').bind('jqplotDataClick', function (ev, seriesIndex, pointIndex, data) {
  //alert('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
  //alert('category id: '+catIdAry[seriesIndex]+', date: '+data[0]);
  (function() {
    $("#hiddenDiv").remove();
    var $form = $('<form id="prevForm" method="post"></form>');
    $form.append("<input type='hidden' name='categoryId' value='"+catIdAry[seriesIndex]+"'/>");
    $form.append("<input type='hidden' name='dateType' value='4'/>");
    $form.append("<input type='hidden' name='searchDate' value='"+data[0]+"'/>");
    $form.append("<input type='hidden' name='page' id='page' value='1'/>");
    var $div = $("<div id='hiddenDiv' style='display:none;'></div>").append($form);
    $('body').append($div);
  })();
  ajaxSubmit($("#prevForm").serialize());
});
$(document).on("change", "#pageSel", function() {
  $("#page").val($(this).find("option:selected").val());
  ajaxSubmit($("#prevForm").serialize());
});
var ajaxSubmit = function(datastring) {
  $.blockUI({ css: {
    border: 'none',
    padding: '15px',
    backgroundColor: '#000',
    '-webkit-border-radius': '10px',
    '-moz-border-radius': '10px',
    opacity: .5,
    color: '#fff'
  } });
  $.ajax({
    type: "POST",
    url: "list!queryForPlot.action",
    //data: "categoryId="+catIdAry[seriesIndex]+"&dateType=4&beginDate="+data[0]+"&endDate="+data[0],
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
      scrollTo("result");
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
<s:if test="platDataLst != null">
var plot1 = $.jqplot("chart1", dataAry, {
  seriesColors: colorAry,
  highlighter: {
    show: true,
    sizeAdjust: 1,
    tooltipOffset: 9
  },
  grid: {
    //background: 'rgba(57,57,57,0.0)',
    background: 'rgb(255,255,255)',
    drawBorder: false,
    shadow: false,
    //gridLineColor: '#666666',
    gridLineWidth: 1
  },
  legend: {
    show: true,
    //placement: 'outside',
    labels : labelAry,
    showLabel: true,
    showSwatch : true,
    placement : "outsideGrid",
    location : 'w'
  },
  seriesDefaults: {
	//lineWidth: 1.2,
	rendererOptions: {
      smooth: false,
      animation: {
        show: true
      }
    },
    showMarker: true
  },
  <%--
  series: markerAry
  /*[
    {markerOptions: { style:'filledDimaond', size:10 }},
    {markerOptions: { style:'filledSquare', size:8 }},
    {markerOptions: { style:'filledCircle', size:8 }},
    {markerOptions: { style:'x', size:8 }}
  ]*/
  ,
  --%>
  axesDefaults: {
    rendererOptions: {
      baselineWidth: 1.5,
      baselineColor: '#444444',
      drawBaseline: false
    }
  },
  axes: {
    xaxis: {
      renderer: $.jqplot.DateAxisRenderer,
      tickRenderer: $.jqplot.CanvasAxisTickRenderer,
      tickOptions: {
        formatString: "%m/%e",
        angle: -30
      },
      min: '<s:property value="minDateStr"/>',
      max: '<s:property value="maxDateStr"/>',
      tickInterval: '<s:property value="tickInterval"/>',
      drawMajorGridlines: false
    },
    yaxis: {
      label: "篇數",
      //renderer: $.jqplot.LogAxisRenderer,
      min: 0,
      pad: 0,
      rendererOptions: {
        minorTicks: 1
      },
      tickOptions: {
       // formatString: "%'d則",
        showMark: false
      }
    }
  }
});
</s:if>
$('.jqplot-highlighter-tooltip').addClass('ui-corner-all');
<%-- temporary workaround for showing color for legends--%>
$("div.jqplot-table-legend-swatch").html("&nbsp;&nbsp;")

// bar chart
var barAry = [
<s:iterator value="barPlatDataLst" status="s">
[<s:property value="percent"/>, '<s:property value="label"/>']<s:if test="!#s.last">,</s:if>
</s:iterator>
];
$('#chart2').jqplot([barAry], {
  grid: {
    //background: 'rgba(57,57,57,0.0)',
    background: 'rgb(255,255,255)',
    drawBorder: false,
    shadow: false,
    //gridLineColor: '#666666',
    gridLineWidth: 1
  },
  seriesDefaults: {
    // Provide a custom seriesColors array to override the default colors.
    seriesColors: colorAry,
    renderer: $.jqplot.BarRenderer,
    pointLabels: {
      show: true,
      location: 'w',
      formatString: function(){return '%s%'}()
    },
    rendererOptions: {
      // Set the varyBarColor option to true to use different colors for each bar.
      // The custom series colors are used.
      varyBarColor: true,
      barDirection: 'horizontal'
    }
  },
  axes: {
    xaxis: {
      tickOptions: {
        showGridline: true
      }
    },
    yaxis: {
      renderer: $.jqplot.CategoryAxisRenderer,
      tickOptions: {
        showGridline: false
      }
    }
  }
});

function scrollTo(hash) {
  location.hash = "";
  location.hash = "#" + hash;
}

</script>
</div>
<s:include value="footer.jsp"/>
</body>
</html>