<?php
$response = array();

try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}

$requete_champ = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical ORDER BY rand() LIMIT 1");
$nom_champ = $requete_champ->fetch();
$response["champ0"] = $nom_champ[1];

$id_champ =  $nom_champ['id_champ'];
$requete_mot = $utilisation_mot = $connexion->query( "SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ."') ORDER BY RAND() LIMIT 5");
$i=0;
while($mot = $requete_mot->fetch()){ 
  $response["mot".$i] = $mot[1];
  $i++;
}
$j=0;
while($mot_utilise = $utilisation_mot->fetch()){
	$mot.$j = $mot_utilise[0];
	$j++;
}
$zero = 0;
$un = 1;
$deux = 2;
$trois = 3;
$quatre = 4;

$requete_champ1 = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical WHERE id_champ IN (SELECT id_champ FROM Appartient WHERE (id_champ != '".$id_champ."' AND id_mot != '".$mot.$zero."' AND id_mot != '".$mot.$un."' AND id_mot != '".$mot.$deux."' AND id_mot != '".$mot.$trois."' AND id_mot != '".$mot.$quatre."')) ORDER BY rand() LIMIT 1");
$nom_champ1 = $requete_champ1->fetch();
$response["champ1"] = $nom_champ1[1];
$champ1 = $nom_champ1[0];

$requete_champ2 = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical WHERE id_champ IN (SELECT id_champ FROM Appartient WHERE (id_champ != '".$id_champ."' AND id_mot != '".$mot.$zero."' AND id_mot != '".$mot.$un."' AND id_mot != '".$mot.$deux."' AND id_mot != '".$mot.$trois."' AND id_mot != '".$mot.$quatre."' AND id_champ != '".$champ1."')) ORDER BY rand() LIMIT 1");
$nom_champ2 = $requete_champ2->fetch();
$response["champ2"] = $nom_champ2[1];
$champ2 = $nom_champ2[0];

$requete_champ3 = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical WHERE id_champ IN (SELECT id_champ FROM Appartient WHERE (id_champ != '".$id_champ."' AND id_mot != '".$mot.$zero."' AND id_mot != '".$mot.$un."' AND id_mot != '".$mot.$deux."' AND id_mot != '".$mot.$trois."' AND id_mot != '".$mot.$quatre."' AND id_champ != '".$champ1."' AND id_champ != '".$champ2."')) ORDER BY rand() LIMIT 1");
$nom_champ3 = $requete_champ3->fetch();
$response["champ3"] = $nom_champ3[1];

echo json_encode($response); 
  
?>
