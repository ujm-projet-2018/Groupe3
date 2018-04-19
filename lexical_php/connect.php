<?php
$response = array();
if(empty($_POST["type"]) || empty($_POST["identifiant"]) || empty($_POST["pass"])){
  echo "Le champ pseudo , mot de passe ou type est vide !";
}
else{
  $identifiant = $_POST['identifiant'];
  $pass = $_POST['pass'];
  $type= $_POST['type'];
  try{
    $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');
    
  }
  catch(Exeption $e){
    die('erreur :'.$e->getMessage());
  }
if($type == "prof"){
  $requete = $connexion -> query("SELECT * FROM Professeur WHERE pseudo_prof = '".$identifiant."' AND mot_passe = '".$pass."'");
}else if($type=="eleve"){
$requete=$connexion->query("SELECT * FROM Eleve WHERE prenom_eleve ='".$identifiant."' AND nom_eleve='".$pass."'");
}else if($type=="insc"){
try{
$requete=$connexion->query("INSERT INTO Professeur(pseudo_prof,mot_passe) VALUES ('".$identifiant."','".$pass."')");
}catch (PDOException $e) {
    if ($e->getCode() == 1062) {
        $response["success"]=0;
    } else {
        throw $e;
    }
}
if($requete)
	$response["success"]=1;
else
	$response["success"]=0;

echo json_encode($response);
return;
}
if( $tab=$requete->fetch())
  $response["success"] = 1;
  else
  $response["success"]=0;

  echo json_encode($response);
  
$requete->closeCursor();
} 
 
?>
