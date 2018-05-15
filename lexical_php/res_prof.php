<?php
$response = array();
  
try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}

try{
  //nombre d'eleves
  $req_nb_eleve = $connexion->query(" SELECT COUNT(*) FROM Eleve");
  $nb_eleve = $req_nb_eleve->fetch();
  $nbe = $nb_eleve[0];
  $response["nb_eleves"] = $nbe;

  //affichage de l'élève et ses parties gagnées
  $req_eleve = $connexion->query("SELECT * FROM Eleve");
  $i=0;
  while($eleve = $req_eleve->fetch()){
    $response["nom".$i] = $eleve[1];
    $response["prenom".$i] = $eleve[2];

    $req_nbe1 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '1' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe1->fetch(); // valeur a renvoyer
    if($essais != 0) $response[$i."niveau en cours : "] = '1';
    $response[$i."essai1"] = $essais[0];

    $req_nbe2 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '2' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe2->fetch();
    if($essais[0]!=0) $response[$i."niveau en cours : "] = '2';
    $response[$i."essai2"] = $essais[0];

    $req_nbe3 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '3' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe3->fetch();
    if($essais[0]!=0) $response[$i."niveau en cours : "] = '3';
    $response[$i."essai3"] = $essais[0];

    $req_nbe4 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '4' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe4->fetch();
    if($essais[0]!=0) $response[$i."niveau en cours : "] = '4';
    $response[$i."essai4"] = $essais[0];

    $req_nbe5 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '5' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe5->fetch();
    $response[$i."essai5"] = $essais[0];
    if($essais[0]!=0) $response[$i."niveau en cours : "] = '5';

    $req_nbe6 = $connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '6' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$eleve['id_eleve']."')) ");
    $essais = $req_nbe6->fetch();
    if($essais[0]!=0) $response[$i."niveau en cours : "] = '6';
    $response[$i."essai6"] = $essais[0];
              
    $i++;
  }
  
}catch (PDOException $e) {
  if ($e->getCode() == 1062) {
      $response["success"]=0;
  } else {
      throw $e;
  }
}

echo json_encode($response);
 
?>  