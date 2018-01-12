<?php 

header("Content-Type:text/html; charset=utf-8");

	$Bank=$_POST['B_Name'];
	$B_Name=explode(";",$Bank);
	$servername = "127.0.0.1";
	$username = "pi";
	$password = "nccutest";
	$dbname = "DFinal";
	$conn = new mysqli($servername, $username, $password, $dbname);
	$card="";
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

	for ($i=0; $i < sizeof($B_Name)-1; $i++) { 
		$sqlbnak="SELECT `B_Num` FROM `Bank` WHERE `B_Name`like '%".$B_Name[$i]."%'";
		// echo $sqlbnak="SELECT `B_Num` FROM `Bank` WHERE `B_Name`like '%".$B_Name[$i]."%'";;
		$result = $conn->query($sqlbnak);
		while($row = $result->fetch_assoc()) {
			$sql  = 'SELECT `C_Num`,`C_Name` FROM `Card` WHERE `B_Num`="'.$row['B_Num'].'"';
	        //echo $sql;
	        $result1 = $conn->query($sql);
	        while($row1 = $result1->fetch_assoc()) {
	        	echo $row1['C_Num'].",";
				echo $row1['C_Name'].";";
	        }
	    }
	}
 ?>
