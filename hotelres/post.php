<?php
    include("connection.php");
    global $owner_error;
    global $owner_success;


    global $customer_error;
    global $customer_success;
    global $customer_search;

    global $hotel_header;
    global $hotel_search;
    global $hotel_reservations;
    global $hotel_reservations_p;
    global $hotel_check_in;
    global $hotel_check_out;
    global $hotel_error;
    global $hotel_success;

    if(isset($_POST['submit_hotel_owner'])){
        //validation
        if(empty($_POST['type'])) $owner_error .= "Please enter a valid room type <br />";
        if(empty($_POST['facilities'])) $owner_error .= "Please enter a valid facilities <br />";
        if(empty($_POST['count'])) $owner_error .= "Please enter a valid count <br />";
            else if(!is_numeric($_POST['count'])) $owner_error .= "Pleace enter Interges only [0-^9] in 'Count' <br />";

        if(empty($_POST['price'])) $owner_error .= "Please enter a valid price <br />";
            else if(!is_numeric($_POST['price'])) $owner_error .= "Pleace enter Interges only [0-^9] in 'Price'  <br />";
        
        if($owner_error){
            $owner_error = "<strong>There is Error(s) in your submission</strong> <br />".$owner_error;
        }else{
                
            
                // some mother-fucker should bend this data to the broker some fucking where!
                // code here ..........

                $type = mysqli_real_escape_string($conn,strip_tags($_POST['type']));
                $facilities = mysqli_real_escape_string($conn,strip_tags($_POST['facilities']));
                $count = mysqli_real_escape_string($conn,strip_tags($_POST['count']));
                $price = mysqli_real_escape_string($conn,strip_tags($_POST['price']));
                $filename="";
                $filetype=""; 
                $user_id = $_POST['user_id'];
                //dealing with the image
                if(!empty($_FILES)){
                    $filename=$_FILES['image']['name'];   
                    $filetype=$_FILES['image']['type'];
                    move_uploaded_file($_FILES["image"]["tmp_name"],"images/" . $_FILES["image"]["name"]); 
                }
                $ins_run = "INSERT INTO hotel_room (type,facilities,count,price,image,owner_id) VALUES ('$type','$facilities','$count','$price','$filename','$user_id')";
                if(mysqli_query($conn,$ins_run)){
                    $owner_success = "Your Hotel specifications has been submitted successfully. <br />You should receive a Notification of confirmation or cancelation within 30 seconds.<br />Stay tuned!";
                    $hotel_id = mysqli_insert_id($conn);
                    $msg = "Request is bending";
                    $ins_msg = "INSERT INTO notifications_owner_broker (owner_id,msg,hotel_id) VALUES ('$user_id','$msg','$hotel_id')";
                    mysqli_query($conn,$ins_msg);
                }
                else
                    $owner_error   = "Failed to upload the data .. Try again later <br />";
            
                
            
                }   
        
    }

    if(isset($_POST['submit_hotel_customer'])){

        $customer_id = $_POST['customer_id'];
        $option = $_POST['search'];
        //echo $option;
        $search_query="";
        $search_counter = 0;
        if($option == 'l_price'){

            $search_query = "SELECT * FROM hotel_room WHERE permission='approved' AND suspended='no'  ORDER BY price ASC";

        }else if($option == 'h_price'){

            $search_query = "SELECT * FROM hotel_room WHERE permission='approved'  AND suspended='no' ORDER BY price DESC";

        }else{
            // rating // starts // etc
            $search_query = "SELECT * FROM hotel_room WHERE permission='approved'  AND suspended='no' ORDER BY $option DESC";
        }
        $search_query_run = mysqli_query($conn,$search_query);
        $customer_search .= '<ul class="list-group black col-sm-12">';
        if(!mysqli_num_rows($search_query_run)){
        
            $customer_search .=  '<li class="list-group-item col-sm-12">There no available hotels yet</li>';


        }else{
            
            



while($row_search_query = mysqli_fetch_assoc($search_query_run)){

    $customer_search .=  '

        <li class="list-group-item  col-sm-12">
            <form method="post">
                <div class="row col-sm-12">
                    <label class="form-control-sm col-sm-2">Type:</label>
                    <div class="form-group col-sm-4">
                        <input type="text" class="form-control form-control-sm" id="type" name="type" value=" '.$row_search_query['type'].' " disabled>
                    </div>
                    <label class="form-control-sm col-sm-2">Facilities:</label>
                    <div class="form-group col-sm-4">
                        <input type="text" class="form-control form-control-sm" id="facilities" name="facilities" value=" '.$row_search_query['facilities'].' " disabled>
                    </div>
                
                </div>
                <div class="row col-sm-12">
                    <label class="form-control-sm col-sm-2">Count:</label>
                    <div class="form-group col-sm-4">
                        <input type="text" class="form-control form-control-sm" id="count" name="count" value=" '.$row_search_query['count'].' " disabled>
                    </div>
                    <label class="form-control-sm col-sm-2">Price:</label>
                    <div class="form-group col-sm-4 col-sm-4">
                        <input type="text" class="form-control form-control-sm" id="price" name="price" value=" '.$row_search_query['price'].' $" disabled>
                    </div>
                </div>

                <div class="row col-sm-12">
                    <label class="form-control-sm col-sm-2">Start date::</label>
                    <div class="form-group col-sm-4">
                        <input type="datetime-local" class="form-control form-control-sm" id="start_date" name="start_date">
                    </div>
                    <label class="form-control-sm col-sm-2">End Date</label>
                    <div class="form-group col-sm-4 col-sm-4">
                        <input type="datetime-local" class="form-control form-control-sm" id="end_date" name="end_date">
                    </div>
                </div>

    ';
    $search_image_path = "images/".$row_search_query['image'];
    //echo $search_image_path;
    if(file_exists($search_image_path)){
        $customer_search .=' <div class="row col-sm-12">
                    <label class="form-control-sm col-sm-2">Images:</label>
                    <img src="images/'.$row_search_query["image"].'" class="img-thumbnail rounded mx-auto d-block border border-info" style="margin-bottom:5px;">
                </div>
                <div>
                    <input type="text" name="hotel_id" id="hotel_id" value="'.$row_search_query['id'].'" hidden>
                    <input type="text" name="customer_id" id="customer_id" value="'.$customer_id.'" hidden>
                    <button type="submit" class="btn btn-primary" name="reservation" id="reservation">Make Reservation</button>
                </div>
                
            </form>

        </li>

    ';
    }else{
        $customer_search .=' <div class="row col-sm-12">
                    <label class="form-control-sm col-sm-2">Images:</label>
                    <p>There is no images available</p>
                </div>
                <div>
                    <input type="text" name="hotel_id" id="hotel_id" value="'.$row_search_query['id'].'" hidden>
                    <input type="text" name="customer_id" id="customer_id" value="'.$customer_id.'" hidden>
                    <button type="submit" class="btn btn-primary" name="reservation" id="reservation">Make Reservation</button>
                </div>
                
            </form>

        </li>

    ';
    }
    
    

    $search_counter ++;

}

            $customer_search .=  "</ul>";
        }

        if($customer_search){
            $customer_search = "<h6>There ".$search_counter." Result(s) Found</h6> <br>".$customer_search; 
        }else{
            $customer_error = "Error while retrieving the data, Please try again later";
        }




    }


    if(isset($_POST['reservation'])){

        $customer_id = $_POST['customer_id'];
        $hotel_id = $_POST['hotel_id'];
        $msg = "Request bending";
        if(empty($_POST['start_date'])) $customer_error .= "Please enter a valid date <br />";
        if(empty($_POST['end_date'])) $customer_error .= "Please enter a valid facilities <br />";
        
        if($customer_error){
            $customer_error = "<strong>There were error(s) in your reservation.</strong><br>".$customer_error;
        }else{
            $s_date = $_POST['start_date'];
            $e_date = $_POST['end_date'];
            $ins_customer_notification = "INSERT INTO notifications_customer_hotel (customer_id,msg,hotel_id,start_date,end_date) VALUES ('$customer_id','$msg','$hotel_id','$s_date','$e_date')";
            //sleep(30);
            if(mysqli_query($conn,$ins_customer_notification)){
                $customer_success = "Request has been send successfully.<br>You will recive a notification within 30 seconds.";
                ?>
                    
                    <script>
                        alert("Request has been send successfully, You will recive a notification within 30 seconds.");
                    window.location="customer_view.php";
                    </script>
                <?php 
            }else{
                $customer_error = "Error : Cound't make the quest, Try again later.";
            }

        }

        
        

    }


    if(isset($_POST['pick_hotel'])){
        $hotel_header = '<h2 class="offset-4"><strong>Hotel #'.$_POST['hotel_picked'].' Dashboard</strong></h2>';
        $hotel_search = "";
        $hotel_id = $_POST['hotel_picked'];
        $hotel_search .=  '<ul class="list-group black">';
        $sql_hotel_notifications = "SELECT * FROM notifications_customer_hotel WHERE hotel_id='$hotel_id' AND status = 'pending' ";
        $run_sql_hotel_notifications = mysqli_query($conn,$sql_hotel_notifications);
        $hotel_search .=  '<li class="list-group-item list-group-item-primary affix-top">Notification</li>';
        if(!mysqli_num_rows($run_sql_hotel_notifications)){
            $hotel_search .=  '<li class="list-group-item">No Notifications</li>';
        }else{

            while($row_hotel_notifications =mysqli_fetch_assoc($run_sql_hotel_notifications)){
                // get owner name from owner id
                // get start and end date
                $customer_id = $row_hotel_notifications['customer_id'];
                $sql_customer_info = "SELECT * FROM users WHERE id='$customer_id'";
        
                $row_customer_info = mysqli_fetch_assoc(mysqli_query($conn,$sql_customer_info));
                $start_date = str_replace(' ', 'T', $row_hotel_notifications['start_date']);
                $end_date = str_replace(' ', 'T', $row_hotel_notifications['end_date']);
                
                


                $hotel_search .= '

                    <li class="list-group-item">
                        <form method="post">
                            <div class="row">
                                <label class="form-control-sm col-sm-3">Customer Name:</label>
                                <div class="form-group col-sm-9">
                                    <input type="text" class="form-control form-control-sm" id="type" name="type" value="'.$row_customer_info['name'].'" disabled>
                                </div>
                            
                            </div>
                            
                            <div class="row">
                                <label class="form-control-sm col-sm-3">Start date:</label>
                                <div class="form-group col-sm-9">
                                    <input type="datetime-local" class="form-control form-control-sm" id="s_date" name="s_date" value="'.$start_date.'" disabled>
                                </div>
                            </div>

                            <div class="row">
                                <label class="form-control-sm col-sm-3">End date:</label>
                                <div class="form-group col-sm-9">
                                    <input type="datetime-local" class="form-control form-control-sm" id="e_date" name="e_date" value="'.$end_date.'" disabled>
                                </div>
                            </div>
                            <div>
                            <input type="text" name="customer_notification_id" id="customer_notification_id" value="'.$row_hotel_notifications['id'].'" hidden>

                                <button type="submit" class="btn btn-primary btn-sm" name="approve_reservation" id="approve_reservation">approve</button>
                                <a class="btn btn-danger btn-sm" href="hotel.php?decline='.$row_hotel_notifications["id"].'">Decline</a>
                            </div>
                            
                        </form>

                    </li>
                
                ';
            }
            $hotel_search .=  "</ul>";


        } //end of while

        // current and previous reservations 

        $hotel_reservations = "";
        //echo $hotel_id;
        $sql_get_hotel_notifications = "SELECT * FROM reservations WHERE hotel_id = '$hotel_id' AND status = 'current'";
        $run_get_hotel_notifications = mysqli_query($conn,$sql_get_hotel_notifications);
                                $hotel_reservations .= '
                                    
                                    <h6>Current Reservations</h6>
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Start-Date</th>
                                            <th scope="col">End-Date</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_hotel_notifications)){
                                    $hotel_reservations .=  "<tbody><tr><td colspan='4'><h6 style='text-align:center'>No current reservations available.</h6></td></tr></tbody>";
                                }else{

                                    $c = 1;
                                    while($row_get_hotel_notifications = mysqli_fetch_assoc($run_get_hotel_notifications)){
                                        $hotel_reservations .=  '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_hotel_notifications['customer_id'].'</td>
                                                    <td>'.$row_get_hotel_notifications['start_date'].'</td>
                                                    <td>'.$row_get_hotel_notifications['end_date'].'</td>

                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }   
                                }
                                $hotel_reservations .=  "</table>";
                       
                                $hotel_reservations_p = "";
                                //echo $hotel_id;
                                $sql_get_hotel_notifications = "SELECT * FROM reservations WHERE hotel_id = '$hotel_id' AND status = 'previous'";
                                $run_get_hotel_notifications = mysqli_query($conn,$sql_get_hotel_notifications);
                                $hotel_reservations_p .= '
                                    <h6>Previous Reservations</h6>
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Start-Date</th>
                                            <th scope="col">End-Date</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_hotel_notifications)){
                                    $hotel_reservations_p .=  "<tbody><tr><td colspan='4'><h6 style='text-align:center'>No previous reservations available.</h6></td></tr></tbody>";;
                                }else{

                                    $c = 1;
                                    while($row_get_hotel_notifications = mysqli_fetch_assoc($run_get_hotel_notifications)){
                                        $hotel_reservations_p .=  '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_hotel_notifications['customer_id'].'</td>
                                                    <td>'.$row_get_hotel_notifications['start_date'].'</td>
                                                    <td>'.$row_get_hotel_notifications['end_date'].'</td>

                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }   
                                }
                                $hotel_reservations_p .=  "</table>";


                                $hotel_check_in = "";
                                //echo $hotel_id;
                                $sql_get_hotel_notifications = "SELECT * FROM reservations WHERE hotel_id = '$hotel_id' AND check_i_o = 'check_in'";
                                $run_get_hotel_notifications = mysqli_query($conn,$sql_get_hotel_notifications);
                                $hotel_check_in .= '
                                    <h6>Check-In</h6>
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Start-Date</th>
                                            <th scope="col">End-Date</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_hotel_notifications)){
                                    $hotel_check_in .=  "<tbody><tr><td colspan='4'><h6 style='text-align:center'>No one checked-in yet.</h6></td></tr></tbody>";;
                                }else{

                                    $c = 1;
                                    while($row_get_hotel_notifications = mysqli_fetch_assoc($run_get_hotel_notifications)){
                                        $hotel_check_in .=  '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_hotel_notifications['customer_id'].'</td>
                                                    <td>'.$row_get_hotel_notifications['start_date'].'</td>
                                                    <td>'.$row_get_hotel_notifications['end_date'].'</td>

                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }   
                                }
                                $hotel_check_in .=  "</table>";
                                

                                $hotel_check_out = "";
                                //echo $hotel_id;
                                $sql_get_hotel_notifications = "SELECT * FROM reservations WHERE hotel_id = '$hotel_id' AND check_i_o = 'check_out'";
                                $run_get_hotel_notifications = mysqli_query($conn,$sql_get_hotel_notifications);
                                $hotel_check_out .= '
                                    <h6>Check-Out</h6>
                                    <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Start-Date</th>
                                            <th scope="col">End-Date</th>
                                        </tr>
                                    </thead>
                                ';
                                if(!mysqli_num_rows($run_get_hotel_notifications)){
                                    $hotel_check_out .=  "<tbody><tr><td colspan='4'><h6 style='text-align:center'>No one checked-in yet.</h6></td></tr></tbody>";;
                                }else{

                                    $c = 1;
                                    while($row_get_hotel_notifications = mysqli_fetch_assoc($run_get_hotel_notifications)){
                                        $hotel_check_out .=  '
                                            <tbody>
                                                <tr>
                                                    <th scope="row">'.$c.'</th>
                                                    <td>'.$row_get_hotel_notifications['customer_id'].'</td>
                                                    <td>'.$row_get_hotel_notifications['start_date'].'</td>
                                                    <td>'.$row_get_hotel_notifications['end_date'].'</td>

                                                </tr>
                                            </tbody>
                                        ';
                                        $c++;
                                    }   
                                }
                                $hotel_check_out .=  "</table>";
                                






        



    } // end of request




    if(isset($_POST['approve_reservation'])){
        // check is customer is black-listed -> action
        // if not
        // check count > 0
        // update count --
        // update reservation table
        // update status

        $notification_id = $_POST['customer_notification_id'];
        //echo $notification_id;
        $sql_find_notification = "SELECT * FROM notifications_customer_hotel WHERE id = '$notification_id' LIMIT 1";
        $row_find_notification = mysqli_fetch_assoc (mysqli_query($conn,$sql_find_notification));

        $customer_id = $row_find_notification['customer_id'];
        $hotel_id = $row_find_notification['hotel_id'];
        $start_date = $row_find_notification['start_date'];
        $end_date = $row_find_notification['end_date'];
        $status = "current";
        $sql_find_hotel= "SELECT * FROM hotel_room WHERE id = '$hotel_id' LIMIT 1";
        $row_find_hotel = mysqli_fetch_assoc (mysqli_query($conn,$sql_find_hotel));

        $hotel_count = $row_find_hotel['count'];

        if($hotel_count > 0){
            $hotel_count --;

            $sql_update_hotel_count = "UPDATE hotel_room SET count = '$hotel_count' WHERE id='$hotel_id'";
            mysqli_query($conn,$sql_update_hotel_count);

            $ins_reservation = " INSERT INTO reservations (customer_id,hotel_id,start_date,end_date,status) VALUES ('$customer_id', '$hotel_id', '$start_date', '$end_date', '$status' )";
            mysqli_query($conn,$ins_reservation);

            $hotel_success = "Reservation have been set successfully.<br>The room count has been updated";

            $upd_customer_msg = "UPDATE notifications_customer_hotel SET msg='Reservation completed successfully',status = 'approved' WHERE id='$notification_id' LIMIT 1";
            mysqli_query($conn,$upd_customer_msg);

        }else{
            $hotel_error = "Not enough rooms available";
            $upd_customer_msg = "UPDATE notifications_customer_hotel SET msg='Reservation declined',status = 'decline' WHERE id='$notification_id' LIMIT 1";
            mysqli_query($conn,$upd_customer_msg);
        }








    }


    if(isset($_POST['rate_button'])){
        $rate = $_POST['rate'];
        $hotel_id =  $_POST['rate_id'];
        $customer_id = $_POST['customer_id'];
        $upd_hotel_rate = "UPDATE reservations SET rate='$rate' WHERE customer_id = '$customer_id' AND hotel_id = '$hotel_id' LIMIT 1";
        mysqli_query($conn,$upd_hotel_rate);

        $sql_hotel_rate = "SELECT AVG(rate) AS avg_rate FROM reservations WHERE hotel_id = '$hotel_id' ";
        $result = mysqli_fetch_assoc(mysqli_query($conn,$sql_hotel_rate));
        $avg_rate = $result['avg_rate'];

        $upd_hotel = "UPDATE hotel_room SET rating = '$avg_rate' WHERE id = '$hotel_id' LIMIT 1";
        mysqli_query($conn,$upd_hotel);

        /*?>
            <script> window.location="customer_view.php"</script>
        <?php*/


    }






?>

