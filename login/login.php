<?php include('server.php') ?>
<!DOCTYPE html>
<html>
<head>
  <title>Login page</title>
  <link rel="stylesheet" type="text/css" href="style.css">
	<script src="main.js"></script>
</head>
<body>
  <div class="header">
  	<h2>Login</h2>
  </div>

  <form method="post" name="login_form" /*action="login.php"*/>
  	<div class="input">
  	  <label>Username</label>
  	  <input type="text" name="username" /*value=*/>
  	</div>
  	<div class="input">
  	  <label>Password</label>
  	  <input type="password" name="password">
  	</div>
  	<div class="input">
  	  <button type="submit" class="btn" onclick=validateLog() name="log_user">Login</button>
  	</div>
      <div  class="switch"><p>Not a member? <a href="registeration.php"> Sign up!
      </p></div>
  </form>
</body>
</html>