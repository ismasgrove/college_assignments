<?php
    include("post.php");
?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>HOTEL DASHBOARD</title>
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
            <a class="navbar-brand" href="#">HOTEL DASHBOARD</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">





            </div>
        </nav>

        <div class="container">

            <form method="POST" enctype="multipart/form-data" style="margin-top:50px;">
                                
                <div class="form-group row col-sm-10 offset-2">

                    <div class="form-group col-sm-2"style="margin-top:5px;">
                        <label>Choose A Hotel :</label>
                    </div>
                                
                    <div class="form-group col-sm-7">
                        <select class="form-control" name="hotel_picked" id="hotel_picked">

                            <?php
                                // dynamic dropdown list
                                $sql_hotel_pick = "SELECT id FROM hotel_room WHERE permission = 'approved'";
                                $run_hotel_pick = mysqli_query($conn,$sql_hotel_pick);
                                if(!mysqli_num_rows($run_hotel_pick)){
                                    //warning no hotels ...
                                }else{

                                    while($row_hotel_pick = mysqli_fetch_assoc($run_hotel_pick)){
                                        echo '<option value = "'.$row_hotel_pick['id'].'">Hotel #'.$row_hotel_pick['id'].'</option>';

                                    }
                                }
                               
                            ?>

                        </select>
                    </div>
                                            
                    
                    <div class="form-group col-sm-2">
                            <button type="submit" class="btn btn-primary" name="pick_hotel" id="pick_hotel">PICK</button>
                    </div>
                                
            </div>
                            
        </form>

        <div class="row">
            <?php echo $hotel_header;?>
        </div>
        <div class="row" style="margin-top:50px; height: 300px;">


            <div class="col-sm" style="overflow:auto;" id="style-7">
                  <?php echo $hotel_search;?>
            </div>

            <div class="col-sm">
            <?php echo $hotel_search;?>
            </div>
            

        </div>

                <div class="row" style="margin-top:50px; height: 400px;">


                    <div class="col-sm" style="overflow:auto;" id="style-7">
                        <?php echo $hotel_reservations;?>
                    </div>


                    <div class="col-sm">
                        <?php echo $hotel_reservations_p;?>
                    </div>

                </div>

                <div class="row" style="margin-top:50px; height: 400px;">


                    <div class="col-sm" style="overflow:auto;" id="style-7">
                        <?php echo $hotel_check_in;?>
                    </div>


                    <div class="col-sm">
                    <?php echo $hotel_check_out;?>
                    </div>

                </div>

             




        
        </div>


    </div>








    <script src="jquery-3.3.1.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    

</body>

</html>



<?php

    if(isset($_GET['decline'])){
        // edit customer notification !
        $request_id = $_GET['decline'];

        $upd_customer_msg = "UPDATE notifications_customer_hotel SET msg='Request denied',status = 'decline' WHERE id='$request_id' LIMIT 1";
            mysqli_query($conn,$upd_customer_msg);
            ?>
           <script>window.location="hotel.php"</script>
        <?php 

    }

    
?>