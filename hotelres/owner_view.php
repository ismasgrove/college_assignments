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
    <title>OWNER</title>
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>


        body,html,.wrapper {
            height:100%;
            width:100%;
            color:white;
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
    </style>



</head>

<body>
    <div class="wrapper" id="layer">

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
                            $owner_id = $_SESSION['id'];
                            $sql_owner_notifications = "SELECT * FROM notifications_owner_broker WHERE owner_id='$owner_id' ORDER BY msg_date DESC";
                            $run_sql_owner_notifications = mysqli_query($conn,$sql_owner_notifications);
                            echo '<ul class="list-group black">';
                            if(!mysqli_num_rows($run_sql_owner_notifications)){
                                echo '<li class="list-group-item">No Notifications</li>';
                            }

                            while($row_owner_notifications =mysqli_fetch_assoc($run_sql_owner_notifications)){
                                echo '<li class="list-group-item">
                                    '.$row_owner_notifications['msg'].' For Hotel #'.$row_owner_notifications['hotel_id'].'
                                    ('.$row_owner_notifications['msg_date'].')
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
                        View Owned Hotels
                        </button>
                    </h5>
                    </div>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                        <div class="card-body">
                        <table class="table">
                            <thead class="thead-dark">
                                <tr>
                                <th scope="col">#</th>
                                <th scope="col">First</th>
                                <th scope="col">Last</th>
                                <th scope="col">Handle</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                </tr>
                                <tr>
                                <th scope="row">2</th>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                                </tr>
                                <tr>
                                <th scope="row">3</th>
                                <td>Larry</td>
                                <td>the Bird</td>
                                <td>@twitter</td>
                                </tr>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>
                </div>
                <div class="card">
                    <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-danger" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        Add New Hotel
                        </button>
                    </h5>
                    </div>

                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                        <div class="card-body">
                        <h5 style="color:black; margin-bottom:20px;"class="center">Enter the room(s) informations</h5>
                        <div class="col-sm-6 offset-3">
                        
                            <?php

                                if($owner_error){
                                    echo '<div class="alert alert-danger">' .$owner_error. '</div>';
                                }
                                if($owner_success){
                                    echo '<div class="alert alert-success">' .$owner_success. '</div>';
                                }
                                
                                
                            ?>

                        </div>
                        <form method="POST" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="type" class="col-sm-2 col-form-label">Room Type</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="type" id="type" placeholder="Enter Room Type">
                                </div>


                                <label for="price" class="col-sm-2 col-form-label">Room Price</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" name="price" id="price" placeholder="Enter Price">
                                </div>
                                <div class="input-group-append col-sm-1 ">
                                        <span class="input-group-text">$</span>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="facilities" class="col-sm-2 col-form-label">Facilities</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="facilities" id="facilities" placeholder="Enter Facilities">
                                </div>
                                

                                <label for="image" class="col-sm-2">Choose Image</label>
                                <div class="col-sm-2">
                                    <label class="btn btn-primary col-sm-12" for="image">
                                        <input type="file" id="image" name="image" accept="image/gif, image/jpeg, image/png, image/jpg" style="display:none" onchange="$('#image_name').html(this.files[0].name)">
                                        Browse
                                    </label>
                                </div>
                                <span class='label label-info col-sm-2' style="color:black;"id="image_name" name="image_name"></span>

                            </div>
                            <div class="form-group row">
                                <label for="count" class="col-sm-2 col-form-label">Count</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="count" name="count" placeholder="Enter Count">
                                </div>

                                <div class="col-sm-2">
                                <img id='img-upload'/>

                                </div>
                            </div>
                            
                            <input type="text" name="user_id" id="user_id" value="<?php echo $_SESSION['id'] ;?>" hidden>
                            <button type="submit" class="btn btn-primary" name="submit_hotel_owner" id="submit_hotel_owner">Submit</button>
                        </form>
                        </div>
                    </div>
                </div>

        </div>




    </div>








    <script src="jquery-3.3.1.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    

</body>

</html>