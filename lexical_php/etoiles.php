<?php
$response = array();

$nom = $_POST['nom'];
$prenom = $_POST['prenom'];
  
try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}

$etoiles=0;
$succes=0;

try{
  $req_id_eleve = $connexion->query("SELECT id_eleve FROM Eleve WHERE (nom_eleve = '".$nom."'AND prenom_eleve = '".$prenom."') ");
  $id_eleve = $req_id_eleve->fetch();
  $ide = $id_eleve[0];
  //$response["id_eleve"] = $ide;

  $ex=1;
  $req_nb_etoiles_e1 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e1->fetch();
  $response["etoiles1"] = $etoiles[0];
  
  $ex=2;
  $req_nb_etoiles_e2 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e2->fetch();
  $response["etoiles2"] = $etoiles[0];
  

  $ex=3;
  $req_nb_etoiles_e3 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e3->fetch();
  $response["etoiles3"] = $etoiles[0];
  

  $ex=4;
  $req_nb_etoiles_e4 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e4->fetch();
  $response["etoiles4"] = $etoiles[0];
  

  $ex=5;
  $req_nb_etoiles_e5 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e5->fetch();
  $response["etoiles5"] = $etoiles[0];
  

  $ex=6;
  $req_nb_etoiles_e6 = $connexion->query( " SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$ex."' AND resultat = '".$succes."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve ='".$ide."')) ");
  
  $etoiles = $req_nb_etoiles_e6->fetch();
  $response["etoiles6"] = $etoiles[0];
  
}catch (PDOException $e) {
  if ($e->getCode() == 1062) {
      $response["success"]=0;
  } else {
      throw $e;
  }
}

echo json_encode($response);
 
?>  