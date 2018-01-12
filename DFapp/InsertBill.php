<?php 

	$U_Num=$_POST["U_Num"];
	$BL_Date=$_POST['BL_Date'];
	// -------------------------- BList
	$BL_Num;
	$Bi_Item=$_POST['Bi_Item'];
	$Bi_Price=$_POST['Bi_Price'];
	$Bi_Info=$_POST["Bi_Info"];
	// --------------------------- Bill

	$servername = "127.0.0.1";
	$username = "pi";
	$password = "nccutest";
	$dbname = "DFinal";

	// ---------------------------DB
	if (empty($U_Num)||empty($BL_Date)||empty($Bi_Item)||empty($Bi_Price)||empty($Bi_Info)){
		echo "false";
	}else{
		$conn = new mysqli($servername, $username, $password, $dbname);
		// Check connection
		if ($conn->connect_error) {
		    die("Connection failed: " . $conn->connect_error);
		} 
		$sqlSelectBL="SELECT `BL_Num` FROM `BList` WHERE `U_Num`=".$U_Num."  and `BL_Date`= '".$BL_Date."'";
		// echo "TEST!=\t".$sqlSelectBL;
		$result = $conn->query($sqlSelectBL);
		//-----------------------------------------------------In order to find BL_Num
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$BL_Num=$row['BL_Num'];
				// echo "Select=\t".$BL_Num."<br>";
			}
			// echo "Select=\t".$BL_Num."<br>";
		}else{
			$sqlInsertBL="INSERT INTO `BList`(`U_Num`, `BL_Date`) VALUES (".$U_Num.",'".$BL_Date."')";
			// echo $sqlInsertBL."<br>";
			$resInsert = $conn->query($sqlInsertBL);
	// ---------------------------------------------------------------------insert BList if have no BL_Num
			$sqlSelectBL2="SELECT `BL_Num` FROM `BList` WHERE `U_Num`=".$U_Num."  and `BL_Date`= '".$BL_Date."'";
			$resSelect2 = $conn->query($sqlSelectBL2);
			if ($resSelect2->num_rows > 0) {
				while($row = $resSelect2->fetch_assoc()) {
					$BL_Num=$row['BL_Num'];
				}
			}
		}
	// --------------------------------------------------------------------------------------------------Start to insert bill
		
		if(empty($BL_Num)){
			echo "false";
		}else{
			$sqlInsertB="INSERT INTO `Bill`(`BL_Num`, `Bi_Item`, `Bi_Price`, `Bi_Info`) VALUES (".$BL_Num.",'".$Bi_Item."'".",'".$Bi_Price."'".",'".$Bi_Info."')";
			$conn->query($sqlInsertB);
			echo "true";

		}
		

	}



?>