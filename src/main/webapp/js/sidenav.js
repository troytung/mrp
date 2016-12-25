function sidenav() {
  var output = '<nav class="w3-sidenav w3-white w3-card-2" style="width:20%">';
  output += '<br>';
  output += '<a href="mrp.html">排單</a>';
  output += '<a href="mrpProduce.html">排單生產進度維護</a>';
  output += '<a href="mrpPack.html">排單包裝進度維護</a>';
  output += '<a href="searchProduct.html">產品查詢與維護</a>';
  output += '<a href="modifyProduct.html">新增產品</a>';
  output += '<a href="viewMrp.html">打料</a>';
  output += '<a href="moveInMaterial.html">原料入庫</a>';
  output += '<a href="moveOutMaterial.html">原料出庫</a>';
  output += '<br>';
  output += '<a href="index.html">登出</a>';
  output += '</nav>';
  self.document.write(output);
}