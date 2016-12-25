<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Category</title>
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
<link href="css/trend-import.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/>
<link href="css/spectrum.css" rel="stylesheet"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>
<body class="trendPage">
<s:component template="menu.ftl"></s:component>
<s:set var="isEmail" value="email"/>
<s:set var="actionName">
  <s:if test="#isEmail">emailCategory</s:if>
  <s:else>category</s:else>
</s:set>
<div class="trendBox Shadow"><br/><br/>
<div class="ui-accordion ui-widget ui-helper-reset filterBox">
  <div class="filterContent Shadow1">
  <s:if test="#isEmail">
    <select id="nonEmailCat">
      <s:iterator value="nonEmailCategories">
        <option class="nonEmailCat" data-cat-id="<s:property value="id"/>" data-cat-name="<s:property value="name"/>"><s:property value="name"/></option>
      </s:iterator>
    </select>
    <button id="copyBtn">複製</button>
    <br/>
  </s:if>
  <form name="form1" id="form1" action="<s:property value="#actionName"/>!save.action" method="post" class="pageContent">
  <input type="hidden" id="categoryId" name="category.id" />
  <input type="hidden" id="separatedQuery" name="category.separatedQuery"/>
  Keyword: <input id="word" name="word" type="text"/> <input type="button" id="addBtn" value="加入"> <button id="backBtn">Back</button> <button id="resetBtn">Reset</button>
  <br/>
  <button id="orBtn">OR</button> <button id="andBtn">AND</button> <button id="notBtn">NOT</button>
  <button id="leftBtn">&nbsp;&nbsp;(&nbsp;&nbsp;</button> <button id="rightBtn">&nbsp;&nbsp;)&nbsp;&nbsp;</button>
  <br/>
  <input type="text" id="result" name="category.displayQuery" readonly="readonly" size="100"/>
  <input type="hidden" id="solrResult" name="category.query" />
  <hr>
             類別名稱:<input type="text" name="category.name" id="name" size="20">
   <s:if test="!#isEmail">
                     顏色: <input id="catColor" type="text" >
      <input id="catColorHolder" type="hidden" name="category.color">
   </s:if>
    <br/>
    <input type="button" id="saveBtn" value="儲存">
  </form>
  <s:if test="#isEmail">
      <span style="font-size:60%;">*發送Email的Category最多只能有5個</span>
      <br/>
      <span style="font-size:60%;">*『複製』會建立一個全新的Category</span>
  </s:if>
  <hr>
  <h3 class="ui-widget-header"><s:if test="#isEmail">Email</s:if>類別清單：</h3>
<br/>
<table id="data">
<thead>
<tr>
	<th>名稱</th>
	<th>Query</th>
	<!-- <th>Actual Query</th> -->
  <s:if test="!#isEmail"><th width="5%">顏色</th></s:if>
  <th width="20%"></th>
</tr>
</thead>
<tbody>
<s:iterator value="categories">
<tr align="right">
	<td><s:property value="name"/></td>
	<td><s:property value="displayQuery"/></td>
	<!-- <td><s:property value="query"/></td> -->
  <s:if test="!#isEmail">
  <td><input type="text" class="colorpicker" data-cat-id="<s:property value="id"/>" value="<s:property value="color"/>"/></td>
  </s:if>
  <td>
    <input type="button" class="deleteCategory" data-cat-id="<s:property value="id"/>" data-cat-name="<s:property value="name"/>" value="刪除">
    <input type="button" class="editCategory" data-cat-id="<s:property value="id"/>" data-cat-name="<s:property value="name"/>" value="修改">
  </td>
</tr>
</s:iterator>
</tbody>
</table>
</div>
</div>
<br/>
<div id="errorDiv" title="錯誤" style="display:none;">
  <p class="errorMsg"><s:property value="errorMsg"/></p>
</div>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/spectrum.js"></script>
<script type="text/javascript">
(function() {
    var editFunc,
    cats = {
    		<s:if test="#isEmail">
    	    <s:merge var="allCats">
    	      <s:param value="categories"/>
    	      <s:param value="nonEmailCategories"/>
    	    </s:merge>
    	  </s:if>
    	  <s:else>
    	    <s:set var="allCats" value="categories"/>
    	  </s:else>
    	  <s:iterator value="#allCats" status="s">
    	      "<s:property value="id"/>": <s:property value="separatedQuery" escapeHtml="false"/><s:if test="!#s.last">,</s:if>
    	  </s:iterator>
    },
    
    NOT = "!",
    AND = "&",
    OR = "|",
    bstack = [],
    totalStack = [],
    BHOLDER = {},
    prevAction = OR,
    //LOGIC_OP = {},
    $word = $("#word"),
    $addBtn = $("#addBtn"),
    $orBtn = $("#orBtn"),
    $andBtn = $("#andBtn"),
    $notBtn = $("#notBtn"),
    $leftBtn = $("#leftBtn"),
    $rightBtn = $("#rightBtn"),
    $result = $("#result"),
    $email = $("#email"),
    $errorDialog = $("#errorDiv").dialog({
      modal: true,
      closeOnEscape: true,
      autoOpen: false,
      minHeight: 200,
      minWidth: 350,
      maxHeight: 500,
      maxWidth: 700,
      buttons: {
        OK: function() {
          $( this ).dialog("close");
        }
      }
     
    }),
    $errorText = $errorDialog.find("p.errorMsg"),
    add = function(str, toStack) {
      $result.val($result.val() + str);
      if(toStack !== undefined && toStack !== null) {
        totalStack.push(toStack);
      }
      else {
        totalStack.push(str)
      }
    },
    isEmpty = function() {
      return totalStack.length === 0;
    },
    getLast = function() {
      return isEmpty() ? null : totalStack[totalStack.length - 1];
    }
    isLastOp = function() {
      var last = $.trim(getLast());
      return last === AND || last === OR;
    },
    logicOnClick = function(op) {
      return function() {
        var last = getLast();
        if(isEmpty() || isLastOp() || last === "(" || last === NOT) {
          alert("Syntax錯誤");
          return false;
        }
        else {
          add(" " + op + " ");
          prevAction = op;
        }
        toggleBtnState();
        return false;
      };
    }
    toSolr = function(stack){
      return $.map(stack, function(w){
        w = $.trim(w);
        if(w === AND) {
          w = " AND ";
        }
        else if(w === OR) {
          w = " OR ";
        }
        else if(w === NOT) {
          w = "-";
        }
        return w;
      });
    },
    alert = function(msg) {
      $errorText.text(msg);
      $errorDialog.dialog("open");
    },
    toggleBtnState = function() {
        
      var all = [$addBtn,
                 $orBtn,
                 $andBtn,
                 $notBtn,
                 $leftBtn,
                 $rightBtn],
          last = getLast(), enables;
      
      $.each(all, function(){this.prop("disabled", true);});
      if(last === "(") {
        enables = [$addBtn, $leftBtn, $notBtn];
      }
      else if(last === ")") {
        enables = [$orBtn, $andBtn];
        if(bstack.length > 0) {
          enables.push($rightBtn);
        }
      }
      else if(isLastOp()) {
        enables = [$addBtn, $leftBtn, $notBtn];
      }
      else if(last === NOT) {
        enables = [$addBtn, $leftBtn];
      }
      else if(isEmpty()) {
        enables = [$leftBtn, $addBtn, $notBtn];
      }
      else {//<%-- words --%>
        enables = [$orBtn, $andBtn];
        if(bstack.length > 0) {
          enables.push($rightBtn);
        }
      }
      
      $.each(enables, function(){this.prop("disabled", false);});
  
    };
    
    $leftBtn.bind("click", function() {
      if(!isEmpty() && !isLastOp() && getLast() !== "(" && getLast() !== NOT) {
        alert("Syntax錯誤");
      }
      else {
        add("(");
        bstack.push(BHOLDER);
      }
      toggleBtnState();
      return false;
    });
    $rightBtn.bind("click", function() {
      var last = getLast();
      if(bstack.length === 0 || isLastOp() || last === "(" || last === NOT) {
        alert("Syntax錯誤");
      }
      else {
        add(")");
        bstack.pop();
      }
      toggleBtnState();
      return false;
    });
    
    $orBtn.bind("click", logicOnClick(OR));
    $andBtn.bind("click", logicOnClick(AND));
    $notBtn.bind("click", function() {
      var last = getLast();
      if(!isEmpty() && (last === NOT || !isLastOp())) {
        alert("Syntax錯誤");
      }
      else {
        add(NOT);
      }
      toggleBtnState();
      return false;
    });
    
    $word.on("keypress", function(e) {
    	  if(e.keyCode === 13) {
    		  if(prevAction === OR && !$orBtn.prop("disabled")) {
    			  $orBtn.triggerHandler("click");
    		  }
    		  else if(prevAction === AND && !$andBtn.prop("disabled")) {
    			  $andBtn.triggerHandler("click");
    		  }
    		  $addBtn.triggerHandler("click");
    		  e.preventDefault();
    	  }	
    });
    
    $addBtn.bind("click", function(){
      var w = $.trim($word.val());
      if(w === "") {
        alert("請輸入至少一個字");
      }
      else if(!isEmpty() && !isLastOp() && getLast() !== "(" && getLast() !== NOT) {
        alert("Syntax錯誤，必須有AND或OR。");
      }
      else {
        w = "\"" + w +"\"";
        add(w);
        $word.val("");
      }
      toggleBtnState();
      return false;
    });
    
    $("#resetBtn").bind("click", function() {
      bstack = [];
      totalStack = [];
      $word.val("");
      $result.val("");
      $email.prop("disabled", false);
      $("#name").val("");
      toggleBtnState();
      return false;
    });
      
    $("#backBtn").bind("click", function() {
      
      if(!isEmpty()) {
        var last = totalStack.pop();
        if(last === "(") {
          bstack.pop();
        }
        else if(last === ")") {
          bstack.push(BHOLDER);
        }
        $result.val(totalStack.join(""));
      }
      toggleBtnState();
      return false;
    });
    
    $("#saveBtn").bind("click", function(){
      
      var last = getLast(), solrResult;
      if($.trim($("#name").val()) === "") {
        alert("請輸入 類別名稱");
      }
      else if(bstack.length != 0) {
        alert("括號沒有完整");
      }
      else if(isEmpty()) {
        alert("請輸入關鍵字");
      }
      else if(isLastOp() || last === NOT) {
        alert("結尾不能是邏輯運算子(AND/OR/NOT)")
      }
      else {
        solrResult = toSolr(totalStack).join("");
        $("#solrResult").val(solrResult);
        $("#separatedQuery").val(JSON.stringify(totalStack));
        $(this).prop("disabled", true);
        $("#form1").submit();
      }
      return false;
    });
    
    editFunc = function(){
        var $this = $(this), name = $this.data("catName"), id = $this.data("catId");
        $("#resetBtn").triggerHandler("click");
        $("#categoryId").val(id);
        $("#name").val(name);
        totalStack = cats[id];
        $result.val(totalStack.join(""));
        toggleBtnState();
        
        var $color = $("#catColor");
        if($color.length > 0) {
        	  $color.spectrum("set", $this.closest("tr").find(".colorpicker").spectrum("get"))
        }
        
        
    };
    $(".editCategory").bind("click", editFunc);
    $(".nonEmailCat").bind("edit", editFunc);
    
    $("#copyBtn").click(function(){
    	$(".nonEmailCat:selected").triggerHandler("edit")
    	$("#categoryId").val("");
    	$("#name").val("");
    });
    toggleBtnState();
    
    if($errorText.text() !== "") {
    	$errorDialog.dialog("open");
    }
})();
  
$('#data').dataTable({
  "bFilter": false,
  "bInfo": false,
  "bPaginate": false,
  "aaSorting": [[ 0, "desc" ]]
});

if($("#data1 tbody tr").length === 5) {
  $("#email").prop("disabled", true);
}

$(".deleteCategory").bind("click", function() {
  
  var $this = $(this), del = confirm("確定刪除類別『" + $this.data("catName") + "』？"), id;
  if(del) {
     id = $this.prop("disabled", true).data("catId");
     $("#categoryId").val(id);
     $("#form1").attr("action", "<s:property value="#actionName"/>!delete.action").submit();
  }
  
});

$("#catColor").spectrum({
  preferredFormat: "hex6",
  change: function(color) {
    $("#catColorHolder").val(color.toHexString());
  }
});

$(".colorpicker").spectrum({
  preferredFormat: "hex6",
  change: function(color) {
    var cs = color.toHexString(), //<%-- #ff0000--%>
        $this = $(this);
        
    $this.spectrum("disable");
    
    $.ajax("category!updateColor.action", {
      type: "post",
      cache: false,
      data: {
        "category.id": $this.data("catId"),
        "category.color": cs
      }})
      .done(function() {
          $this.spectrum("enable");
      });
  }
});


</script>
</div>
<s:include value="footer.jsp"/>
</body>
</html>