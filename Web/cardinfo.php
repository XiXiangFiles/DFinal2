
<?php 
	$latti=$_POST["latti"];
	$longi=$_POST['longi'];
	$U_Num=$_POST['U_Num'];

	$servername = "127.0.0.1";
	$username = "pi";
	$password = "nccutest";
	$dbname = "DFinal";
	$data="";
	$Fdata="";
	function matchrate($strG, $strD) {
		$err=0;
		$right=0;
		for ($i=0; $i <mb_strlen( $strD, 'utf-8' )-3 ; $i++) { 
			
			try {
				$splitstr=mb_substr($strD,$i,3,'utf-8');
  				// echo mb_substr($strD,$i,3,'utf-8')."<br>";
  				// echo $splitstr."<br>";
  				if (preg_match('/'.$splitstr.'/i', $strG)) {
				    // echo "條件符合<br>";
				    $right++;
				} else {
				    // echo "條件不符合";
				    $err++;
				}
			} catch (Exception $e) {}
		}
		if($right>=$err){
			return true;
		}else{
			return false;
		}
	}
	

	if(empty($latti)||empty($longi)||empty($U_Num)){
		echo "false";
	}else{
		$eatScore = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$latti.','.$longi.'&radius=10000&type=restaurant&language=zh-TW&key=AIzaSyDhHnlIZ5W4yjA9NHXTsDmylW_wOp_TSsg';
		$movieScore = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$latti.','.$longi.'&radius=5000&type=movie_theater&language=zh-TW&key=AIzaSyDvs7_RdXMQbu0-G3H0S94UsKiX0AAzDhQ';
		$parkingScore = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$latti.','.$longi.'&radius=5000&type=parking&language=zh-TW&key=AIzaSyDhHnlIZ5W4yjA9NHXTsDmylW_wOp_TSsg';
		$department_store = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$latti.','.$longi.'&radius=10000&type=department_store&language=zh-TW&key=AIzaSyDhHnlIZ5W4yjA9NHXTsDmylW_wOp_TSsg';
		$electronics_store = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='.$latti.','.$longi.'&radius=10000&type=electronics_store&language=zh-TW&key=AIzaSyDhHnlIZ5W4yjA9NHXTsDmylW_wOp_TSsg';
		// echo $movieScore;
		$eat = json_decode(file_get_contents($eatScore), true);
		$movie = json_decode(file_get_contents($movieScore), true);
		// $parking = json_decode(file_get_contents($parkingScore), true);
		// $department = json_decode(file_get_contents($department_store), true);
		// $electronics = json_decode(file_get_contents($electronics_store), true);

		$conn = new mysqli($servername, $username, $password, $dbname);
		$sql  = 'SELECT `C_Num`,`D_Type`,`D_Title`,`D_Content` FROM `Discount` WHERE `C_Num` IN (SELECT `C_Num` FROM `User_Cards` WHERE `U_Num` ='.$U_Num.')';
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
		    while($row = $result->fetch_assoc()) {
		    	// echo $row['C_Num']."\n";
		    	for ($k=0; $k <sizeof($eat['results']) ; $k++) { 
		    		if(matchrate( $eat['results'][$k]['name'],$row['D_Title'])){
		    			$data=$data.$eat['results'][$k]['name'].":".$eat['results'][$k]['geometry']['location']['lat'].",".$eat['results'][$k]['geometry']['location']['lng']."@".$row['C_Num'].":".$row['D_Content'].";";

		    		}
		    	}
		    	for ($k=0; $k <sizeof($movie['results']) ; $k++) { 
		    		if(matchrate( $movie['results'][$k]['name'],$row['D_Title'])){
		    			$data=$data.$movie['results'][$k]['name'].":".$movie['results'][$k]['geometry']['location']['lat'].",".$movie['results'][$k]['geometry']['location']['lng']."@".$row['C_Num'].":".$row['D_Content'].";";
		    			// echo $data;
		    		}
		    	}

		    	
		    	// for ($k=0; $k <sizeof($parking['results']) ; $k++) { 
		    	// 	if(matchrate( $parking['results'][$k]['name'],$row['D_Title'])){
		    	// 		echo $parking['results'][$k]['name'].":".$parking['results'][$k]['geometry']['location']['lat'].",".$parking['results'][$k]['geometry']['location']['lng']."@".$row['C_Num'].":".$row['D_Content'].";";
		    	// 	}
		    	// }
		    	// for ($k=0; $k <sizeof($department['results']) ; $k++) { 
		    	// 	if(matchrate( $department['results'][$k]['name'],$row['D_Title'])){
		    	// 		echo $department['results'][$k]['name'].":".$department['results'][$k]['geometry']['location']['lat'].",".$department['results'][$k]['geometry']['location']['lng']."@".$row['C_Num'].":".$row['D_Content'].";";
		    	// 	}
		    	// }
		    	// for ($k=0; $k <sizeof($electronics['results']) ; $k++) { 
		    	// 	if(matchrate( $electronics['results'][$k]['name'],$row['D_Title'])){
		    	// 		echo $electronics['results'][$k]['name'].":".$electronics['results'][$k]['geometry']['location']['lat'].",".$electronics['results'][$k]['geometry']['location']['lng']."@".$row['C_Num'].":".$row['D_Content'].";";
		    	// 	}
		    	// }

		    }
		    $se_data=explode(";", $data);
		    for ($checknum=0; $checknum <sizeof($se_data) ; $checknum++) { 
		    	$count=0;
		    	$se_data1=explode(":", $se_data[$checknum]);
		    	$se_data2=explode("@", $se_data1[1]);
		    	$se_data12=explode(",", $se_data2[0]);
		    	$se_data121=explode(":", $se_data2[1]);
		    	// echo doubleval($se_data12[0]);
		    	for ($checknum1=0; $checknum1 <sizeof($se_data) ; $checknum1++){
		    		$se_data3=explode(":", $se_data[$checknum1]);
		    		$se_data4=explode("@", $se_data3[1]);
					$se_data34=explode(",", $se_data4[0]);
					$se_data343=explode(",", $se_data4[1]);
		    		if (doubleval($se_data12[0])==doubleval($se_data34[0]) && doubleval($se_data121[0])==doubleval($se_data343[0])) {
		    		// if (doubleval($se_data3[0])==doubleval($se_data1[0])) {
		    			$count++;
		    			if($count>1){
		    				$se_data[$checknum1]="";
		    			}
		    		}
		    	}
		    	if(!empty($se_data[$checknum])){
		    		$Fdata=$Fdata.$se_data[$checknum].";";
		    	}
		    	

		    }
		    echo $Fdata;


		} else {
		    echo "false";
		}
		$conn->close();


	}



?>
