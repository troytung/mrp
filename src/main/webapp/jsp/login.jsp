<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/core/css/w3.css"/>
</head>
<body>
  <header class="w3-container w3-teal">
    <h1>Login</h1>
  </header>
  <div class="w3-container w3-half w3-margin-top">
    <article>
      <span style="color:red"><s:property value="errorMessage"/></span>
    </article>
    <form action="login!login.action" method="post" class="w3-container w3-card-4">
      <p>
        <input class="w3-input" type="text" name="account" style="width: 90%" required="required"/><label class="w3-label">使用者代碼</label>
      </p>
      <p>
        <input class="w3-input" type="password" name="password" style="width: 90%" required="required"/><label class="w3-label">密碼</label>
      </p>
      <p>
        <!-- <button class="w3-btn w3-section w3-teal w3-ripple">登入</button> -->
        <input type="submit" name="submit" value="Log in" class="w3-btn w3-section w3-teal w3-ripple"/>
      </p>
    </form>
  </div>
</body>
</html>