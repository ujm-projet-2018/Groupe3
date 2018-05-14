<?php
$response = array();
if(empty($_POST["champ"]) || empty($_POST["mot"]) || empty($_POST["classe"]) ){
  echo "Le champ mot , champ lex ou classe est vide !";
}

else{
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
    $requete1 = $connexion->query("SELECT id_champ FROM Champ_lexical WHERE nom_champ = '".$champ."'");
    if($id_champ = $requete1->fetch()){ // Si champ existe aussi
echo $id_champ[0]+$id_mot[0];
      $requete3=$connexion->query("INSERT INTO Appartient(id_mot,id_champ) VALUES ('".$id_mot[0]."','".$id_champ[0]."')"); //On associe le champ au mot
$response["success"] = 1; 

    }

    else{
      $response["success"] = 0; // Le champ nexiste pas il faut demander a l'user si il veut l'ajouter
      echo json_encode($response);
    }
  }
  else{ //Le mot n'existe pas il faut l'ajouter avant de faire le lien avec le champ
    $requete1 = $connexion->query("SELECT id_champ FROM Champ_lexical WHERE nom_champ = '".$champ."'");
    if($id_champ = $requete1->fetch()){ // Si champ existe
      $requete4=$connexion->query("INSERT INTO Mot(mot,classe) VALUES ('".$mot."','".$classe."')");
      $id_mot = $connexion->lastInsertId();
      echo $id_mot;
      $requete3=$connexion->query("INSERT INTO Appartient(id_mot,id_champ) VALUES ('".$id_mot[0]."','".$id_champ[0]."')"); //On associe le champ au mot
$response["success"] = 1; 

    }

    else{
      $response["success"] = 0; // Le champ nexiste pas il faut demander a l'user si il veut l'ajouter
      echo json_encode($response);
    }   
  }
}

 
?>
