<?php 

$U_Num=$_POST["U_Num"];
$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";
$conn = new mysqli($servername, $username, $password, $dbname);
$sql='DELETE FROM `User_Cards` WHERE `U_Num`='.$U_Num;
$result = $conn->query($sql);
echo "true";
 ?>