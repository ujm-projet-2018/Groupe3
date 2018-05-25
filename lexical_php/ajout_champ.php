<?php
$response = array();
if(empty($_POST["champ"]) || empty($_POST["mot"]) || empty($_POST["classe"]) ){
  echo "Le champ mot , champ lex ou classe est vide !";
}

else{
  $response["success"] = 0; 
  $champ = $_POST['champ'];
  $mot = $_POST['mot'];
  $classe= $_POST['classe'];
  try{
    $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
  }
  catch(Exeption $e){
    die('erreur :'.$e->getMessage()); 
  }
  $requete = $connexion -> query("SELECT id_mot FROM Mot WHERE mot = '".$mot."' AND classe = '".$classe."'");
  if($id_mot = $requete->fetch()){ // Si mot existe deja
    $requete1 = $connexion->query("INSERT INTO Champ_lexical(nom_champ) VALUES ('".$champ."')");
    $id_champ = $connexion->lastInsertId();
    $requete2 = $connexion->query("INSERT INTO Appartient(id_mot,id_champ) VALUES ('".$id_mot['id_mot']."','".$id_champ."')");
    $response["success"] = 1; 
  }
  else{
    $requete1 = $connexion->query("INSERT INTO Mot(mot,classe) VALUES ('".$mot."','".$classe."')");
    $id_mot = $connexion->lastInsertId();
    $requete2 = $connexion->query("INSERT INTO Champ_lexical(nom_champ) VALUES ('".$champ."')");
    $id_champ = $connexion->lastInsertId();
    $requete3 = $connexion->query("INSERT INTO Appartient(id_mot,id_champ) VALUES ('".$id_mot."','".$id_champ."')");
    $response["success"] = 1; 
  }
}
echo json_encode($response); 

?>
    