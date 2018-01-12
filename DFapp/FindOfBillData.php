<?php 
$U_Num=$_POST["U_Num"];
$BL_Year=$_POST['BL_Year'];
$BL_Mon=$_POST['BL_Mon'];

$servername = "127.0.0.1";
$username = "pi";
$password = "nccutest";
$dbname = "DFinal";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql  = 'SELECT * FROM `Bill` WHERE`BL_Num` IN (SELECT `BL_Num` FROM `BList` WHERE YEAR(`BL_Date`)='.$BL_Year.' and MONTH(`BL_Date`)='.$BL_Mon.' and `U_Num` = '.$U_Num.')';

$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['Bi_Item'].";".$row['Bi_Price'].";".$row['Bi_Info'].";";
    }
} else {
    echo "false";
}
$conn->close();

?>