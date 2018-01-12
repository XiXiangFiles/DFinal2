<?php 

$U_Num=$_POST["U_Num"];
$BL_Date=$_POST['BL_Date'];
$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
// SELECT  SUM(`Bi_Price`) as total,`Bi_Item`,`Bi_Price`,`Bi_Info` FROM `Bill` WHERE `UL_Num` = (SELECT `BL_Num` FROM `BList` WHERE `U_Num` =1 AND `BL_Date`='2017-12-24')
$sql="SELECT `Bi_Item`,`Bi_Price`,`Bi_Info`FROM `Bill` WHERE `BL_Num` = (SELECT `BL_Num` FROM `BList` WHERE `U_Num` =".$U_Num." AND `BL_Date`='".$BL_Date."')";
// echo $sql;
$total=0;
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['Bi_Item'].";".$row['Bi_Price'].";".$row['Bi_Info'].";";
        $total+=intval($row['Bi_Price']);
    }
	echo $total;
} else {
    echo "false";
}
$conn->close();


 ?>