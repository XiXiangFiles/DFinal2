<?php 
$U_Email=$_POST["U_Email"];
$U_Pwd=$_POST['U_Pwd'];
$U_Name=$_POST['U_Name'];
$U_Gender=$_POST["U_Gender"];
$U_Phone=$_POST["U_Phone"];
$U_Birth=$_POST['U_Birth'];
$U_Date=date("Y/m/d");
$U_Info=$_POST["U_Info"];
// $U_Check=$_POST['U_Check'];
$U_Check="1";

$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";


if (empty($U_Email)||empty($U_Pwd)||empty($U_Name)||empty($U_Gender)||empty($U_Phone)||empty($U_Birth)||empty($U_Date)||empty($U_Date)){
	echo "false";
}else{
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql_check="SELECT * FROM `User` WHERE `U_Email`='".$U_Email."'";
	$result = $conn->query($sql_check);
	if ($result->num_rows > 0) {
		echo "false";
	}else{
		$sql="INSERT INTO `User`(`U_Email`, `U_Pwd`, `U_Name`, `U_Gender`, `U_Phone`, `U_Birth`, `U_Date`, `U_Info`, `U_Check`) VALUES ('".$U_Email."','".$U_Pwd."','".$U_Name."','".$U_Gender."','".$U_Phone."','".$U_Birth."','".$U_Date."','".$U_Info."','".$U_Check."')";
		$conn->query($sql);
		echo "true";
		// echo $sql;
	}
}





 ?>