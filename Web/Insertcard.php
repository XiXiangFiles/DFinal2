<?php 
$U_Num=$_POST['U_Num'];

$C_Num=$_POST['C_Num'];

$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";


if (empty($U_Num)||empty($C_Num)){
	echo "false";
}else{

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 
	$C_Num_arr=explode(";",$C_Num);

	for ($i=0; $i < sizeof($C_Num_arr)-1; $i++) { 
		$sqlNum="SELECT `B_Num` FROM `Card` WHERE `C_Num`=".$C_Num_arr[$i];
		// echo $sqlNum;
		// echo "<br>";
		// echo $sqlbnak="SELECT `B_Num` FROM `Bank` WHERE `B_Name`like '%".$B_Name[$i]."%'";;
		$result = $conn->query($sqlNum);
		while($row = $result->fetch_assoc()) {
			$sql_check='SELECT * FROM `User_Cards` WHERE `U_Num` ='.$U_Num .' and `B_Num` = '.$row['B_Num'] .' and `C_Num`='.$C_Num_arr[$i];
	        // echo $sql_check;
	        // echo "<br>";
	        $result1 = $conn->query($sql_check);
			if ($result1->num_rows > 0) {
				echo "false";
				// echo "<br>";
			}else{
				$sqlins="INSERT INTO `User_Cards`( `U_Num`, `B_Num`, `C_Num`) VALUES ('".$U_Num."','".$row['B_Num']."','".$C_Num_arr[$i]."')";
				$conn->query($sqlins);
				echo "true";
				// echo $sqlins;
				// echo "<br>";
			}
	    }
	}
}









 ?>