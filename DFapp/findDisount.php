<?php 

$U_Num=$_POST["U_Num"];


$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";
$conn = new mysqli($servername, $username, $password, $dbname);
$sql  = 'SELECT `C_Num`,`D_Type`,`D_Title`,`D_Content` FROM `Discount` WHERE `C_Num` IN (SELECT `C_Num` FROM `User_Cards` WHERE `U_Num` ='.$U_Num.')';

$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['C_Num'].",".$row['D_Type'].",".$row['D_Title'].",".$row['D_Content'].";";
    }

} else {
    echo "false";
}
$conn->close();


?>