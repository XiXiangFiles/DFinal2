<?php 
$ID=$_POST["ID"];
$PWD=$_POST['PWD'];
$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";


// echo $ID.$PWD;
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql  = 'SELECT * FROM `User` WHERE `U_Email`="'.$ID .'"and `U_Pwd`="'.$PWD.'"';
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['U_Name'].";".$row['U_Num'];
        // $sqlcheck="SELECT * FROM `User_Cards` WHERE 'U_Num' = '".$row['U_Num']."'";
        $sqlcheck  = 'SELECT * FROM `User_Cards` WHERE `U_Num`='.$row['U_Num'];
        // echo ";sql=".$sqlcheck;
        $result1 = $conn->query($sqlcheck);
        if($result1->num_rows> 0){
        	echo ";havecard";
        	continue;
        }else{
        	echo ";nocard";
        	continue;
        }
    }

} else {
    echo "false";
}
$conn->close();
 ?>