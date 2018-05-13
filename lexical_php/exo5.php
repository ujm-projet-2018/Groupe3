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
$response["champ"] = $nom_champ[1];

  
$id_champ =  $nom_champ['id_champ'];
$requete_mot = $utilisation_mot = $connexion->query( "SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ."') ORDER BY RAND() LIMIT 5");
$i=0;
while($mot = $requete_mot->fetch()){ 
  $response["mot".$i] = $mot[1];
  $i++;
}
$intru=0;
$j=0;
while($mot_utilise = $utilisation_mot->fetch()){
	$intru.$j = $mot_utilise[0];
	$j++;
}

$zero = 0;
$un = 1;
$deux = 2;
$trois = 3;
$quatre = 4;

$requete_intru = $connexion->query( "SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ != '".$id_champ."' AND id_mot != '".$intru.$zero."' AND id_mot != '".$intru.$un."' AND id_mot != '".$intru.$deux."' AND id_mot != '".$intru.$trois."' AND id_mot != '".$intru.$quatre."') ORDER BY RAND() LIMIT 1");
$mot_intru = $requete_intru->fetch();
$response["intru0"] = $mot_intru[1];

echo json_encode($response); 

?>