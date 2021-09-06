<?php
    session_start();
    include("post.php"); 
?>

<?php
        // getting the user data
        $user_id = $_SESSION['id'];
        $user_query = "SELECT * FROM users WHERE id = '$user_id'  LIMIT 1 ";
        $user_results = mysqli_query($conn,$user_query);
        $user_row = mysqli_fetch_array($user_results);
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>BROKER</title>
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>


        body,html,.wrapper {
            height:100%;
            width:100%;
            color:black;
        }
        
        #layer {
            background-image: url("img/background3.jpg");
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

        .marginbottom {
            margin-bottom: 50px;
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
        
        #user_style{
            font-weight:bold;
            margin-right:100px;
        }
        
        #accordionExample{
            margin-top:50px;
        }
        label{
            color:black;
        }

        .btn-file {
        position: relative;
        overflow: hidden;
        }
        .btn-file input[type=file] {
            position: absolute;
            top: 0;
            right: 0;
            min-width: 100%;
            min-height: 100%;
            font-size: 100px;
            text-align: right;
            filter: alpha(opacity=0);
            opacity: 0;
            outline: none;
            background: white;
            cursor: inherit;
            display: block;
        }

        #img-upload{
            width: 100%;
        }
        .black{
            color:black;
        }


        .fixed_height{
            max-height:200px;
            background-color:red;
        }

        /*
        *  STYLE 8
        */

        #style-8::-webkit-scrollbar-track
        {
            border: 1px solid black;
            background-color: #F5F5F5;
        }

        #style-8::-webkit-scrollbar
        {
            width: 10px;
            background-color: #F5F5F5;
        }

        #style-8::-webkit-scrollbar-thumb
        {
            background-color: #000000;	
        }
        
        /*
        *  STYLE 7
        */

        #style-7::-webkit-scrollbar-track
        {
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
            background-color: #F5F5F5;
            border-radius: 10px;
        }

        #style-7::-webkit-scrollbar
        {
            width: 10px;
            background-color: #F5F5F5;
        }

        #style-7::-webkit-scrollbar-thumb
        {
            border-radius: 10px;
            background-image: -webkit-gradient(linear,
                                            left bottom,
                                            left top,
                                            color-stop(0.44, rgb(122,153,217)),
                                            color-stop(0.72, rgb(73,125,189)),
                                            color-stop(0.86, rgb(28,58,148)));
        }


    </style>



</head>

<body>
    <div class="wrapper">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="#">HOTEL RESERVATION</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <div class="mr-auto"></div>
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#" id="user_style"><?php echo strtoupper($user_row['name']);echo " [".$user_row['type']."]"; ?></a>
                    </li>
                </ul>

                <ul class="navbar-nav mr">
                    <li class="nav-item active">
                        <a class="nav-link" href="index.php?logout=1">Logout</a>
                    </li>
                </ul>



            </div>
        </nav>

        <div class="container">
        <div class="row" style="margin-top:50px; height: 500px;">


            <div class="col-sm" style="overflow:auto;" id="style-7">
                    <?php
                    echo '<ul class="list-group black">';
                    $sql_broker_notifications = "SELECT * FROM hotel_room WHERE permission = 'denied' ";
                    $run_sql_broker_notifications = mysqli_query($conn,$sql_broker_notifications);
                    echo '<li class="list-group-item list-group-item-primary affix-top">Notification</li>';
                    if(!mysqli_num_rows($run_sql_broker_notifications)){
                        echo '<li class="list-group-item">No Notifications</li>';
                    }

                    while($row_broker_notifications =mysqli_fetch_assoc($run_sql_broker_notifications)){
                        echo '

                            <li class="list-group-item">
                                <form method="post">
                                    <div class="row">
                                        <label class="form-control-sm col-sm-2">Type:</label>
                                        <div class="form-group col-sm-4">
                                            <input type="text" class="form-control form-control-sm" id="type" name="type" value=" '.$row_broker_notifications['type'].' " disabled>
                                        </div>
                                        <label class="form-control-sm col-sm-2">Facilities:</label>
                                        <div class="form-group col-sm-4">
                                            <input type="text" class="form-control form-control-sm" id="facilities" name="facilities" value=" '.$row_broker_notifications['facilities'].' " disabled>
                                    </div>
                                    
                                    </div>
                                    <div class="row">
                                        <label class="form-control-sm col-sm-2">Count:</label>
                                        <div class="form-group col-sm-4">
                                            <input type="text" class="form-control form-control-sm" id="count" name="count" value=" '.$row_broker_notifications['count'].' " disabled>
                                        </div>
                                        <label class="form-control-sm col-sm-2">Price:</label>
                                        <div class="form-group col-sm-4 col-sm-4">
                                            <input type="text" class="form-control form-control-sm" id="price" name="price" value=" '.$row_broker_notifications['price'].' $" disabled>
                                    </div>
                                    
                                    </div>
                                    <div>
                                        <a class="btn btn-primary btn-sm" href="broker_view.php?approve='.$row_broker_notifications["id"].'&owner_id='.$row_broker_notifications['owner_id'].'   ">Approve</a>
                                        <a class="btn btn-danger btn-sm" href="broker_view.php?cancel='.$row_broker_notifications["id"].'&owner_id='.$row_broker_notifications['owner_id'].'    ">Cancel</a>
                                    </div>
                                    
                                </form>

                            </li>
                        
                        ';
                    }
                    echo "</ul>";
                    ?>
                    
            </div>

            <div class="col-sm">
                <div class="row" style="height: 500px;">

                    <div class="col-sm" style="overflow:auto;" id="style-7">
                            <?php
                                $sql_get_hotels = "SELECT * FROM hotel_room WHERE permission = 'approved'";
                                $run_get_hotels = mysqli_query($conn,$sql_get_hotels);
                                echo '
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Hotel</th>
                                        <th scope="col">Suspend</th>
                                        <th scope="col">Unsuspend</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_hotels)){
                                    //echo "<h6 style='text-align:center'>No Previous reservations available.</h6>";
                                    echo "<tbody><tr><td colspan='6'><h6 style='text-align:center'>No current reservations available.</h6></td></tr></tbody>";  

                                }else{

                                    $c = 1;
                                    while($row_get_hotels = mysqli_fetch_assoc($run_get_hotels)){
                                        echo '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_hotels['id'].'</td>
                                                    <td><a href="broker_view.php?suspend='.$row_get_hotels["id"].'" class="btn btn-danger btn-sm">Suspend</a></td>
                                                    <td><a href="broker_view.php?unsuspend='.$row_get_hotels["id"].'" class="btn btn-success btn-sm">Unsuspend</a></td>

                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }
                                }
                                echo "</table>";
                            ?>

                        </div>
                    </div>
            </div>

            <div class="col-sm">
                <div class="row" style="height: 500px;">

                    <div class="col-sm" style="overflow:auto;" id="style-7">
                            <?php
                                $sql_get_fees = "SELECT * FROM reservations WHERE fees > 0";
                                $run_get_fees = mysqli_query($conn,$sql_get_fees);
                                echo '
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Hotel</th>
                                        <th scope="col">Fees</th>
                                        <th scope="col">status</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_fees)){
                                    //echo "<h6 style='text-align:center'>No Previous reservations available.</h6>";
                                    echo "<tbody><tr><td colspan='6'><h6 style='text-align:center'>No current reservations available.</h6></td></tr></tbody>";  

                                }else{

                                    $c = 1;
                                    while($row_get_fees = mysqli_fetch_assoc($run_get_fees)){
                                        echo '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_fees['hotel_id'].'</td>
                                                    <td>'.$row_get_fees['broker_fees'].'</td>
                                                    <td>Unpaid</td>


                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }
                                }
                                echo "</table>";
                            ?>

                        </div>
                </div>
            </div>
        </div>


        </div>








    <script src="jquery-3.3.1.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    <script>

    
    
    </script>

</body>

</html>



<?php

    //managing the approve and cancel

    // cancel ---> delete hotel
    // approve --> change permission
    if(isset($_GET['cancel']) && isset($_GET['owner_id'])){
        $owner_id = $_GET['owner_id'];
        $hotel_id = $_GET['cancel'];

        $query_hotel_image = "SELECT * FROM hotel_room WHERE id='$hotel_id' LIMIT 1";
        $hotel_image_name = mysqli_fetch_array(mysqli_query($conn,$query_hotel_image));
        if($hotel_image_name['image']!="") $image_name = $hotel_image_name['image'];
        else $image_name = "";
        $del_hotel_sql = "DELETE FROM hotel_room WHERE id='$hotel_id'";
        
        if(mysqli_query($conn,$del_hotel_sql)){
            echo "deleted";
            // delete the img from the folder
            if($image_name){ // if there's a pic --> delete it
                if(file_exists("images/".$image_name)){
                    unlink("images/".$image_name);
                }else{
                }
            }

            // update notifications
            $upd_owner_msg = "UPDATE notifications_owner_broker SET msg='Request denied' WHERE owner_id='$owner_id' AND hotel_id='$hotel_id' LIMIT 1";
            mysqli_query($conn,$upd_owner_msg);
            ?>
           <script>window.location="broker_view.php"</script>
        <?php 
        }

    }

    if(isset($_GET['approve']) && isset($_GET['owner_id']) ){
        echo "dfdfddfd";
        $owner_id = $_GET['owner_id'];
        $hotel_id = $_GET['approve'];

        $approve_hotel_sql = "UPDATE hotel_room SET permission='approved' WHERE id='$_GET[approve]'";
        if(mysqli_query($conn,$approve_hotel_sql)){
            $upd_owner_msg_approve = "UPDATE notifications_owner_broker SET msg='Request Approved' WHERE owner_id='$owner_id' AND hotel_id='$hotel_id' LIMIT 1";
            mysqli_query($conn,$upd_owner_msg_approve);
            ?>
           <script>window.location="broker_view.php"</script>
        <?php 
        }
    }


    
    if(isset($_GET['suspend'])){
        //$hotel_id = $_GET['suspend'];
        $sql_check_hotel = "SELECT * FROM hotel_room WHERE id='$_GET[suspend]'";
        $row_check_hotel = mysqli_fetch_assoc(mysqli_query($conn,$sql_check_hotel));

        if($row_check_hotel['suspended'] == "no"){

            $suspend_hotel_sql = "UPDATE hotel_room SET suspended='yes' WHERE id='$_GET[suspend]'";
            if(mysqli_query($conn,$suspend_hotel_sql)){
                ?>
                    <script>alert("The hotel has been suspended successfully");</script>
                <?php
                
            }else{
                ?>
                    <script>alert("Error: can't suspend the Hotel, Try again.");</script>
                <?php
            }
        }else{
            ?>
                <script>alert("The hotel is already suspended ^^ ");</script>
            <?php

        }

        ?>
           <script>window.location="broker_view.php"</script>
        <?php 
    }



    if(isset($_GET['unsuspend'])){
        //$hotel_id = $_GET['suspend'];
        $sql_check_hotel_u = "SELECT * FROM hotel_room WHERE id='$_GET[unsuspend]'";
        $row_check_hotel_u = mysqli_fetch_assoc(mysqli_query($conn,$sql_check_hotel_u));

        if($row_check_hotel_u['suspended'] == "yes"){
            
            $unsuspend_hotel_sql = "UPDATE hotel_room SET suspended='no' WHERE id='$_GET[unsuspend]'";
            if(mysqli_query($conn,$unsuspend_hotel_sql)){
                ?>
                    <script>alert("The hotel has been unsuspended successfully");</script>
                <?php
                
            }else{
                ?>
                    <script>alert("Error: can't unsuspend the Hotel, Try again.");</script>
                <?php
            }
        }else{
            ?>
                <script>alert("The hotel is not suspended ");</script>
            <?php

        }

        ?>
           <script>window.location="broker_view.php"</script>
        <?php 
    }


?>