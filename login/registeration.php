<?php include('server.php') ?>
<!DOCTYPE html>
<html>
<head>
  <title>Registration page</title>
  <link rel="stylesheet" type="text/css" href="style.css">
	<script src="main.js"></script>
</head>
<body>
  <div class="header">
  	<h2>Register</h2>
  </div>

  <form name="reg_form" method="post" /*action="registeration.php"*/>
	<div class="input">
  	  <label>Email</label>
  	  <input type="email" name="email" /*value=*/>
  	</div>
  	<div class="input">
  	  <label>Username</label>
  	  <input type="text" name="username" /*value=*/>
  	</div>
  	<div class="input">
  	  <label>Password</label>
  	  <input type="password" name="password">
  	</div>
  	<div class="input">
  	  <button type="submit" class="btn" onclick=validateReg() name="reg_user">Register</button>
		</div>
		<div  class="switch"><p>
  		Already a member? <a href="login.php">Sign in!</a>
		</p></div>
  </form>
</body>
</html>