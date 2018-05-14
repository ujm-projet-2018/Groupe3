<?php
$response = array();
if(empty($_POST["nom"]) || empty($_POST["prenom"])){
  echo "Nom ou prenom vide!";
}

else{
  $nom = $_POST['nom'];
  $prenom = $_POST['prenom'];
  
  try{
    $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
  }
  catch(Exeption $e){
    die('erreur :'.$e->getMessage());
  }
  try{
  $requete=$connexion->query("INSERT INTO Eleve(nom_eleve,prenom_eleve) VALUE ('".$nom."','".$prenom."')");
}catch (PDOException $e) {
    if ($e->getCode() == 1062) {
        $response["success"]=0;
    } else {
        throw $e;
    }
}
if($requete)
	$response["success"]=1;
 }
 
?>
