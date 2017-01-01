[#ftl]
<nav class="w3-sidenav w3-white w3-card-2" style="width:20%">
<br>
<a href="home.action">首頁</a>
<br>
<a href="mrp.action">排單</a>
<a href="mrpProduce.action">排單生產進度維護</a>
<a href="mrpPack.action">排單包裝進度維護</a>
<a href="searchProduct.action">產品查詢與維護</a>
<a href="modifyProduct.action">新增產品</a>
<a href="viewMrp.action">打料</a>
<a href="moveInMaterial.action">原料入庫 <span class="w3-red">尚未開發，待確認</span></a>
<a href="moveOutMaterial.action">原料出庫 <span class="w3-red">尚未開發，待確認</span></a>
[@s.if test="login.admin"]
<a href="account.action">使用者帳號管理</a>
[/@s.if]
<br>
<a href="login!logout.action">登出</a>
</nav>