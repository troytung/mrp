var cat = {
	init : function(actionName, cats) {
		var 
//		cats = {
//				<s:iterator value="categories" status="s">
//				"<s:property value="id"/>": <s:property value="separatedQuery" escapeHtml="false"/><s:if test="!#s.last">,</s:if>
//				</s:iterator>
//		},
		NOT = "!",
		AND = "&",
		OR = "|",
		bstack = [],
		totalStack = [],
		BHOLDER = {},
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
			if(bstack.length == 0 || isLastOp() || last === "(" || last === NOT) {
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
		
		$(".editCategory").bind("click", function(){
		  var $this = $(this), name = $this.data("catName"), id = $this.data("catId"), isEmail = $this.data("email");
		  $("#resetBtn").triggerHandler("click");
		  $("#categoryId").val(id);
		  $("#name").val(name);
		  totalStack = cats[id];
		  $result.val(totalStack.join(""));
		  toggleBtnState();
		  
		});
		toggleBtnState();
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
			 $("#form1").attr("action", actionName + "!delete.action").submit();
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
	})
};
