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
    <title>CUSTOMER</title>
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>


        body,html,.wrapper {
            height:100%;
            width:100%;
            color:black;
        }
        
        #layer {
            background-image: url("img/background2.jpg");
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
                                            color-stop(0.44, rgb(50,153,217)),
                                            color-stop(0.72, rgb(55,125,189)),
                                            color-stop(0.86, rgb(122,58,148)));
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


            <div class="accordion" id="accordionExample">

                <div class="card">
                        <div class="card-header" id="headingThree">
                        <h5 class="mb-0">
                            <button class="btn btn-warning collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            Notifications
                            </button>
                        </h5>
                        </div>
                        <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionExample">
                        <div class="card-body">
                            <?php
                                $customer_id = $_SESSION['id'];
                                $sql_customer_notifications = "SELECT * FROM notifications_customer_hotel WHERE customer_id='$customer_id' ORDER BY msg_date DESC ";
                                $run_sql_customer_notifications = mysqli_query($conn,$sql_customer_notifications);
                                echo '<ul class="list-group black">';
                                if(!mysqli_num_rows($run_sql_customer_notifications)){
                                    echo '<li class="list-group-item">No Notifications</li>';
                                }

                                while($row_customer_notifications =mysqli_fetch_assoc($run_sql_customer_notifications)){
                                    echo '<li class="list-group-item">
                                        '.$row_customer_notifications['msg'].' For Hotel #'.$row_customer_notifications['hotel_id'].'
                                        ('.$row_customer_notifications['msg_date'].')
                                    </li>';
                                    //echo '<li class="list-group-item disabled">'.$run_sql_owner_notifications['msg'].'</li>';
                                }

                                echo '</ul>';

                            ?>
            
                        </div>
                        </div>
                    </div>
                
                <div class="card">
                    <div class="card-header" id="headingTwo">
                    <h5 class="mb-0">
                        <button class="btn btn-success collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        Current Reservations
                        </button>
                    </h5>
                    </div>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                        <div class="card-body">
                            

                            <?php
                                $customer_id = $_SESSION['id'];
                                $sql_get_customer_notifications = "SELECT * FROM reservations WHERE customer_id = '$customer_id' AND status = 'current'";
                                $run_get_custsomer_notifications = mysqli_query($conn,$sql_get_customer_notifications);
                                echo '
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Hotel</th>
                                        <th scope="col">Start-Date</th>
                                        <th scope="col">End-Date</th>
                                        <th scope="col">Ceack-In</th>
                                        <th scope="col">Ceack-Out</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_custsomer_notifications)){
                                    //echo "<h6 style='text-align:center'>No Previous reservations available.</h6>";
                                    echo "<tbody><tr><td colspan='6'><h6 style='text-align:center'>No current reservations available.</h6></td></tr></tbody>";  

                                }else{

                                    $c = 1;
                                    while($row_get_custsomer_notifications = mysqli_fetch_assoc($run_get_custsomer_notifications)){
                                        echo '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_custsomer_notifications['hotel_id'].'</td>
                                                    <td>'.$row_get_custsomer_notifications['start_date'].'</td>
                                                    <td>'.$row_get_custsomer_notifications['end_date'].'</td>
                                                    <td><a href="customer_view.php?check_in='.$row_get_custsomer_notifications["hotel_id"].'&customer_id='.$customer_id.'" class="btn btn-success btn-sm">Check-In</a></td>
                                                    <td><a href="customer_view.php?check_out='.$row_get_custsomer_notifications["hotel_id"].'&customer_id='.$customer_id.'" class="btn btn-success btn-sm">Check-out</a></td>

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

                <div class="card">
                    <div class="card-header" id="headingFour">
                    <h5 class="mb-0">
                        <button class="btn btn-info collapsed" type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                        Previous Reservations
                        </button>
                    </h5>
                    </div>
                    <div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#accordionExample">
                        <div class="card-body">
                        <?php
                                $customer_id = $_SESSION['id'];
                                $sql_get_customer_notifications = "SELECT * FROM reservations WHERE customer_id = '$customer_id' AND status = 'previous'";
                                $run_get_custsomer_notifications = mysqli_query($conn,$sql_get_customer_notifications);
                                echo '
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Hotel</th>
                                        <th scope="col">Start-Date</th>
                                        <th scope="col">End-Date</th>
                                        <th scope="col">Rate</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_custsomer_notifications)){
                                    //echo "<h6 style='text-align:center'>No current reservations available.</h6>";
                                    echo "<tbody><tr><td colspan='5'><h6 style='text-align:center'>No previous reservations available.</h6></td></tr></tbody>";  
                                }else{

                                    $c = 1;
                                    while($row_get_custsomer_notifications = mysqli_fetch_assoc($run_get_custsomer_notifications)){
                                        echo '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_custsomer_notifications['hotel_id'].'</td>
                                                    <td>'.$row_get_custsomer_notifications['start_date'].'</td>
                                                    <td>'.$row_get_custsomer_notifications['end_date'].'</td>
                                                    
                                                    <td>
                                                        <form method="POST">
                                                            <div class="row">
                                                                <select class="custom-select custom-select-sm col-sm-3" id="rate" name="rate">
                                                                    <option value="0">0</option>
                                                                    <option value="1">1</option>
                                                                    <option value="2">2</option>
                                                                    <option value="3">3</option>
                                                                    <option value="4">4</option>
                                                                    <option value="5">5</option>
                                                                </select>
                                                                <input type="text" class="form-control" name="rate_id" id="rate_id" value = "'.$row_get_custsomer_notifications['hotel_id'].'" hidden>
                                                                <input type="text" class="form-control" name="customer_id" id="customer_id" value = "'.$customer_id.'" hidden>
                                                                <button type="submit" class="btn btn-primary btn-sm col-sm-3" name="rate_button" id="rate_button" style="margin-left:5px;">Rate</button>
                                                            </div>
                                                        </form>
                                                        
                                                    </td>

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
              
                
                <div class="card">
                    <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-danger" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        Search
                        </button>
                    </h5>
                    </div>

                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                        <div class="card-body">
                            <h5 style="color:black; margin-bottom:20px;"class="center">Find Your desired room :)</h5>
                            <div class="col-sm-6 offset-3">
                            
                                <?php

                                    if($customer_error){
                                        echo '<div class="alert alert-danger">' .$customer_error. '</div>';
                                    }
                                    if($customer_success){
                                        echo '<div class="alert alert-success">' .$customer_success. '</div>';
                                    }
                                    
                                    
                                ?>

                            </div>
                            <form method="POST" enctype="multipart/form-data">
                                
                                <div class="form-group row col-sm-10 offset-2">

                                    <div class="form-group col-sm-2">
                                        <label>Sort by :</label>
                                    </div>
                            
                                    <div class="form-group col-sm-7">
                                        <select class="form-control" name="search" id="search">
                                            <option value = 'l_price'>Lowest Price</option>
                                            <option value = 'h_price'>Highest Price</option>
                                            <option value = 'stars'>Stars</option>
                                            <option value = 'location'>Location</option>
                                            <option value = 'rating'>Ratings</option>
                                            <option value = 'type'>Room Type</option>
                                        </select>
                                    </div>
                                        
                
                                    <div class="form-group col-sm-2">
                                        <input type="text" name="customer_id" id="customer_id" value="<?php echo $_SESSION['id'] ;?>" hidden>
                                        <button type="submit" class="btn btn-primary" name="submit_hotel_customer" id="submit_hotel_customer">Search</button>
                                    </div>
                                
                                </div>
                            
                            </form>
                            
                            <div class="row black offset-2"  style="height: 500px;">
                                <div style="overflow:auto;" id="style-7">
                                    
                                    <?php
                                        echo $customer_search;
                                    ?>

                                </div>
                                        
                            </div>

                        </div>


                        </div>
                    </div>
                </div>

        </div>




    </div>








    <script src="jquery-3.3.1.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    

</body>

</html>



<?php
    if( isset($_GET['check_in']) && isset($_GET['customer_id']) ){
        
        //get customer id and hotel id 
        //get the start and end date for this reservation 
        //check the date ?
            //  checked-in before the start date?
                // reject -> throw notification
            // check-in within the 1 day range from the start date
                // check his ass in
                // update the recervation table
        
        $customer_id = $_GET['customer_id'];
        $hotel_id = $_GET['check_in'];
        
        $sql_find_reservation =  "SELECT * FROM reservations WHERE customer_id='$customer_id' AND hotel_id='$hotel_id' ";
        $run_find_reservation = mysqli_fetch_assoc(mysqli_query($conn,$sql_find_reservation));
        $start_date = $run_find_reservation['start_date'];
        $end_date = $run_find_reservation['end_date'];
        $current_date = date('Y-m-d H:i:s');

        

           /* echo $current_date;
            echo "<br>";
            echo $start_date;
            echo "<br>";
            echo $end_date;
            echo "<br>";
            echo $run_find_reservation['check_i_o'];*/
        //echo $current_date;

        /*
        $var = "2018-12-24 12:00:00";
        $var1 = "2018-12-19 13:00:00";
        $var2 = "2018-12-25 13:00:00";

        if((time()-(60*60*24)) == strtotime($var)){
            echo "equal";
        }else if ((time()-(60*60*24)) < strtotime($var)){
            echo "not passed" ;
        }else{
            echo "passed" ;
        }

        if (strtotime($var) <= (time() + 86400)) {
            // current time is 86400 (seconds in 24 hours) or less than stored time
            echo "yessss";
        }else{
            echo "passed";
        }
        */
        
        if($run_find_reservation['check_i_o'] == 'unchecked'){

            if(strtotime($current_date) >= strtotime($start_date) && strtotime($var) <= strtotime($end_date)){

                //reservation time now
                
                $number_of_days = (strtotime($end_date) - strtotime($start_date))/86400;
                $sql_hotel_price = " SELECT * FROM hotel_room WHERE permission = 'approved' AND id = '$hotel_id' ";
                $row_hotel_price = mysqli_fetch_assoc(mysqli_query($conn,$sql_hotel_price));
                //echo $row_hotel_price['price'];
                $fees = $row_hotel_price['price'] * $number_of_days ;
                $broker_fees = (9/100) *$fees;
                $upd_reservation = " UPDATE reservations SET check_i_o = 'check_in', fees = '$fees', broker_fees = '$broker_fees' WHERE customer_id='$customer_id' AND hotel_id='$hotel_id' ";
                if(mysqli_query($conn,$upd_reservation)){
                    ?>
                        <script>alert("Checking-in completed successfully")</script>
                    <?php
                }else{
                    ?>
                        <script>alert("Error while checking-in")</script>
                    <?php 
    
                }
    
                
    
    
            }else if(strtotime($current_date) > strtotime($end_date)){
                //reservation time passed
                //Black list his ass
                ?>
                    <script>alert("Reservation time passed")</script>
                <?php 
    
            }else if(strtotime($current_date) < strtotime($start_date)){
                //reservation time not now
                $reservation_error = "Reservation time is not now";
                ?>
                    <script>alert("Reservation time is not now")</script>
                <?php 
    
                
            }
    
        }else{
                ?>
                    <script>alert("You are already checked-in")</script>
                <?php 
        }

        

        ?>
            <script>window.location="customer_view.php"</script>
        <?php 
        


    }

    if( isset($_GET['check_out']) && isset($_GET['customer_id']) ){

        $customer_id = $_GET['customer_id'];
        $hotel_id = $_GET['check_out'];

        $sql_check_in = " SELECT * FROM reservations WHERE customer_id='$customer_id' AND hotel_id='$hotel_id' LIMIT 1";
        $row_check_in = mysqli_fetch_assoc(mysqli_query($conn,$sql_check_in));

        if($row_check_in['check_i_o'] ==  "check_in"){

            $upd_reservation_o = " UPDATE reservations SET check_i_o = 'check_out',status= 'previous' WHERE customer_id='$customer_id' AND hotel_id='$hotel_id'";
            if(mysqli_query($conn,$upd_reservation_o)){

                $sql_find_hotel= "SELECT * FROM hotel_room WHERE id = '$hotel_id' LIMIT 1";
                $row_find_hotel = mysqli_fetch_assoc (mysqli_query($conn,$sql_find_hotel));
        
                $hotel_count = $row_find_hotel['count'];
                $hotel_count ++;
                $upd_hotel_count = "UPDATE hotel_room SET count = '$hotel_count' WHERE id = '$hotel_id' ";
                mysqli_query($conn,$upd_hotel_count);
            
                ?>
                    <script>alert("Checking-out completed successfully")</script>
                <?php
            }else{
                ?>
                    <script>alert("Error while checking-out")</script>
                <?php 

            }


        }else{
                ?>
                    <script>alert("Error: You have to check in first")</script>
                <?php
        }

        
            ?>
            <script>window.location="customer_view.php"</script>
        <?php 


    }

?>