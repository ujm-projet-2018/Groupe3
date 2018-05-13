<?php
$response = array();

try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}

$requete_champ = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical ORDER BY rand() LIMIT 2");

//On met le champ lex aléatoire dans le tableau.
$nom_champ1 = $requete_champ->fetch();
$response["champ0"] = $nom_champ1[1];

$nom_champ2 = $requete_champ->fetch();
$response["champ1"] = $nom_champ2[1];

$id_champ1 =  $nom_champ1['id_champ'];
$requete_mot1 = $connexion->query( "SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ1."') ORDER BY RAND() LIMIT 4");
$i=0;
while($mot = $requete_mot1->fetch()){ 
  $response["mot".$i] = $mot[1];
  $i++;
}

$id_champ2 =  $nom_champ2['id_champ'];
$requete_mot2 = $connexion->query( "SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ2."') ORDER BY RAND() LIMIT 4");
$j=4;
while($mot = $requete_mot2->fetch()){ 
  $response["mot".$j] = $mot[1];
  $j++;
}

echo json_encode($response); 
  
?>
