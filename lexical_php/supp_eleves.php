<?php
$response = array();

try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');   
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}
  
$requete = $connexion -> query("DELETE FROM Eleves");

echo json_encode($response); 
?>
