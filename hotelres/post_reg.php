<?php
session_start();

global $loginerror;
global $logout;
global $login_user_name;
global $login_user_type;
include("connection.php");


//logout session
if(isset($_GET['logout']) && $_SESSION['id']){

    session_destroy();
    /*for(!empty($_GET)){  **** FUCK IT ****
        header("Location:http://localhost:9090/workspace/Form/index.php"); // avoid GET [?logout=1] request in url - instead of trim the url -too lazy sorry :(!-
        die;
    }*/
        $logout = "You have been logged out successfully!<br> HAVE A NICE DAY.";
   ?> <script>  window.location("index.php")</script> <?php
     
}


if (isset($_POST['loginsubmit'])) {
    //echo $_POST['loginname'];
    //echo $_POST['loginpassword'];
    $loginname = mysqli_real_escape_string($conn, strip_tags($_POST['loginname']));
    $loginpassword = md5(md5($_POST['loginname']).$_POST['loginpassword']);
    //echo "<br>";
    //echo $loginpassword;
    $log_query = "SELECT * FROM users WHERE name = '$loginname' AND password = '$loginpassword' LIMIT 1 ";
    $results = mysqli_query($conn,$log_query);
    $row = mysqli_fetch_array($results);
    //print_r($row['id']);


    if ($row){
         $_SESSION['id'] = $row['id'];
         $userid = $row['id'];
         $t_sql = " UPDATE users SET last_login_date = CURRENT_TIMESTAMP WHERE id = '$userid' "; // now()
         $t_result = mysqli_query($conn,$t_sql);
         // redirected to login page
         $type = $row['type'];
         if($type == "owner"){

            $login_user_name = $row['name'];
            $login_user_type = $row['type'];
           
            header("location:owner_view.php");

         }else if($type == "customer"){

            $login_user_name = $row['name'];
            $login_user_type = $row['type'];
            header("location:customer_view.php");

         }
         if($type == "broker"){

            $login_user_name = $row['name'];
            $login_user_type = $row['type'];
            header("location:broker_view.php");

         }

    }else{
        $loginerror .=  "We could not find your Username!,Do you want to signup instead?";
    }


    
}
else if($_POST){

        // check for username if it's already exist
        $name = mysqli_real_escape_string($conn, strip_tags($_POST['username']));
        $query  = "SELECT 'name' FROM users WHERE name = '$name'";
        $run    = mysqli_query($conn,$query);
        $result = mysqli_num_rows($run);

        //echo $result;
        if ($result > 0) echo "ERROR :This Username is already exist Do you want to <a href='' data-toggle='modal' data-target='#elegantModalForm'>login</a> instead?<br />"; else{
            //echo "inside PHP";
            //echo $_POST['username'];
            //echo $_POST['email'];
            //echo $_POST['password1'];

            $name = mysqli_real_escape_string($conn, strip_tags($_POST['username']));
            $email = mysqli_real_escape_string($conn, strip_tags($_POST['email']));
            $type = $_POST['type'];
            $password = md5(md5($_POST['username']).$_POST['password1']);
            $ins_query = "INSERT INTO users (name,email,password,type) VALUES ('$name','$email','$password','$type')";
            $ins_result = mysqli_query($conn,$ins_query);
            
            if ($ins_result){
                //echo "success";
                //$success_db .= "your account has been created successfully! <br />";
                echo "SUCCESS :your account has been created successfully! <br />";

            }else{
                //echo "error";
                //$error_db .= "an error occurred... please try again! <br />";
                 echo "ERROR :an error occurred... please try again! <br />";
            }
        }
        
        }




?>



<?php
/*$imagename=$_FILES["myimage"]["name"]; 

//Get the content of the image and then add slashes to it 
$imagetmp=addslashes (file_get_contents($_FILES['myimage']['tmp_name']));

//Insert the image name and image content in image_table
$insert_image="INSERT INTO image_table VALUES('$imagetmp','$imagename')";

mysql_query($insert_image);*/
?>
