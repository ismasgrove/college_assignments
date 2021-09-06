<?php include("post_reg.php");?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>HOTEL</title>
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>


        body,html,.wrapper {
            height:100%;
            width:100%;
            color:white;
        }
        
        #layer {
            background-image: url("img/background4.jpg");
            width:100%;
            height:100%;
            background-size: cover;
            background-position: center;
            
        }

        .bold {
            font-weight: bold;
        }
        
        .font {
            font-size: 90%;
        }

        .paddingTop {
            padding-top: 10px;
        }
        
        .marginTop {
            margin-top: 10px;
        }

        #content h1 {
            font-size: 300%;
            font-weight: bold;
        }

        #content     {
            margin-top: 70px;
        }

        .center {
            text-align: center;

        }

        .paddingBottom {
            padding-bottom : 25px;
        }
        
        #emailHelp{
            color: white;
        }
        
        .popup{
            color:black;
        }
        
        
        
    </style>



</head>

<body>
    <script src="jquery-3.3.1.min.js"></script>

    <div class="wrapper" id="layer">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="#">HOTEL RESERVATIO</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                </ul>


                <form class="form-inline my-2 my-lg-0" id="loginForm" method="POST" action="">

                    <div class="form-group">
                        <input type="text" name="loginname" class="form-control mr-sm-2" placeholder="write your username" />
                    </div>
                    <div class="form-group">
                        <input type="password" name="loginpassword" class="form-control mr-sm-2" placeholder="password" />
                    </div>
                    <!--  <div class="form-group checkbox">
                                    <input  type="checkbox" name="inputcheckbox"><label for="inputcheckbox">Remember me</label>
                                </div> -->
                    <input type="submit" class="btn btn-success my-2 my-sm-0" name="loginsubmit" value="login" />

                </form>

            </div>
        </nav>

        <div class="container">

            <div class="row" id=content>

                <div class="col-md-4 offset-md-4">
                    <h5 class="center">Are you not a member yet ?<br>sign-up now.</h5>

                    <form class="font form-horizon" id="regform" action="" method="">

      
                        <div class="alert alert-danger" id="error"></div>

                        <div class="alert alert-success" id="success"></div>
                        
                        <?php
             
                            if($loginerror){
                                ?> 
                                    <script>
                                        $(document).ready(function(){
                                            $('#popuplogin').modal('show');
                                        });
                                        $('#popuplogin').delay(4500).fadeOut('slow');
                                        setTimeout(function() {
                                                $("#popuplogin").modal('hide');
                                        }, 5000);
                                    </script>
                                
                                <?php

                                //echo '<div class="alert alert-warning" id="login">' .$loginerror. '</div>';
                                echo '
                                    <div class="modal fade popup" id="popuplogin" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content alert alert-danger">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">LOGIN ERROR</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            '.$loginerror.'
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">SIGN UP</button>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                                ';
                            }
                            if($logout){
                                ?> 
                                    <script>
                                        $(document).ready(function(){
                                            $('#popuplogout').modal('show');
                                            $('#popuplogout').delay(3500).fadeOut('slow');
                                            setTimeout(function() {
                                                $("#popuplogout").modal('hide');
                                            }, 4000);
                                        });
                                    </script>
                                
                                <?php

                                echo '
                                    <div class="modal fade popup" id="popuplogout" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content alert alert-success">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">BYE BYE</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                '.$logout.'
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                ';
                                //echo '<div class="alert alert-success"  id="logout">' .$logout. '</div>';
                            }
                                        
                        ?>
              

<!-- TEST -->


<!-- END -->

                        
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" class="form-control" name="username"  id="username" aria-describedby="emailHelp"
                                placeholder="Enter username" >
                        </div>
                        <div class="form-group">
                            <label>Email address</label>
                            <input type="email" class="form-control" name="email"  id="email" aria-describedby="emailHelp"
                                placeholder="Enter email">
                            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                                else.</small>
                        </div>

                        <label>Password</label>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <!-- <label for="name">password</label>-->
                                <input type="password" name="password1" id="password1" class="form-control" placeholder="Password" />
                            </div>
                            <div class="form-group col-md-6 ">
                                <!-- <label for="name">Re-type</label>-->
                                <input type="password" name="password2" id="password2" class="form-control" placeholder="Re-type password" />
                            </div>

                        </div>
                        <div class="form-group">
                            <label>Account type</label>
                            <select class="form-control col-md-6" name="type" id="type">
                                <option value = 'customer'>Customer</option>
                                <option value = 'owner'>Hotel Owner</option>
                            </select>
                        </div>

                        <input type="submit" class="btn btn-success" name="regsubmit" id="regsubmit" value="submit" />
                    </form>



                </div>




            </div>


        </div>




    </div>








    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function(){

           /*    $(window).on('load',function(){
                $('#popup').modal('show');
            }); */

            if(typeof window.history.pushState == 'function') {
                window.history.pushState({}, "Hide", "http://localhost:9090/workspace/hotel_r/index.php");
            }

            if($("#logout").length) {
                $("#logout").fadeOut(5000);
            }

            $("#error").hide();
            $("#success").hide();

            var error;
            var success;
            $("#regform").submit(function(e){
                e.preventDefault();

                $("#login").hide();
                $("#logout").hide();
                $("#error").hide();
                $("#success").hide();

                error = "";

                var username = $("#username").val();
                var email = $("#email").val();
                var password1 = $("#password1").val();
                
                //alert(username);
                //alert(password1);
                //alert(email);
                
                username_validation();
                email_validation();
                password_vlidation();
                
                if(error != ""){
                    error ="<strong>There were error(s) in your signup details.</strong> <br />" + error; 
                    $("#error").fadeIn();
                    $("#error").html(error);

                }else{
                    $("#error").fadeOut();
                    var that = $(this);
                    //var dataString = 'username='+ username + '&email='+ email + '&password1='+ password1;

                    $.ajax({

                        url : "post_reg.php",
                        type : "post",
                        data :{
                            'username':$("#username").val(),
                            'email':$("#email").val(),
                            'password1':$("#password1").val(),
                            'type':$("#type").val()
                        },
                        success : function(res){

                            console.log(res);
                            //alert(res);
                            //var success = "Your form has been submitted successfully."
                            var check  = res.split(" ",1);
                            if(check == "SUCCESS"){
                                //$("#regform").reset();
                                $("#success").html(res);
                                $("#success").fadeIn();
                                $("#success").fadeOut(5000);
                            }else if(check == "ERROR"){
                                $("#error").html(res);
                                $("#error").fadeIn();
                                $('#error').fadeOut(10000);

                                /*setTimeout(function(){
                                    $('#error').fadeOut();
                                }, 10000 );*/

                            }

                            //

                        
                        }
                        



                    })
                }
                

            })
            

            function username_validation () {

                var userName = $("#username").val();
                var nameTest = /^[0-9a-zA-Z_.-]+$/;

                if (userName.match(nameTest) == null || userName.length > 16 ){
                    error += "please Enter a valid name <br />" ;
                }

            } //END-USERNAME

            function email_validation () {

                var email = $("#email").val();
                var emailTest = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i

                if (email.match(emailTest) == null){
                    error += "please Enter a valid email <br />" ;
                }
            } //END-EMAIL

            function password_vlidation(){

                var pass1 = $("#password1").val();
                var pass2 = $("#password2").val();
                
                if(!pass1 || !pass2){
                    if(pass1.length < 6 || pass2.length < 6)
                        error += "please Enter a valid password <br />" ;
                }else{
                    if(pass1 != pass2){
                        error += "please Enter a match passowrd <br />";
                    }
                }
                

            } //END-PASSWORD

        })
    </script>

</body>

</html>