<?php 

$U_Num=$_POST["U_Num"];


$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";
$conn = new mysqli($servername, $username, $password, $dbname);
// $sql  = 'SELECT `C_Num`,`D_Type`,`D_Title`,`D_Content` FROM `Discount` WHERE `C_Num` IN (SELECT `C_Num` FROM `User_Cards` WHERE `U_Num` ='.$U_Num.')';
$sql= 'SELECT `C_Num` ,`C_Name` FROM `Card` WHERE `C_Num` IN (SELECT `C_Num` FROM `User_Cards` WHERE `U_Num` = '.$U_Num.')';
//echo $sql;
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['C_Num'].",".$row['C_Name'].";";
    }

} else {
    echo "false";
}
$conn->close();


?>