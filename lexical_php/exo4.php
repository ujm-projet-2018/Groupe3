<?php
$response = array();

try{
  $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
}
catch(Exeption $e){
  die('erreur :'.$e->getMessage());
}

$requete_champ = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical ORDER BY rand() LIMIT 3");

$i=0;

$nom_champ = $requete_champ->fetch();
$response["champ0"] = $nom_champ[1];
$id_champ = $intru0 = $nom_champ[0];
$requete_mot = $connexion->query("SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ."') ORDER BY RAND() LIMIT 4");
while($mot = $requete_mot->fetch()){
	$response["mot".$i] = $mot[1];
	$i++;
}

$nom_champ = $requete_champ->fetch();
$response["champ1"] = $nom_champ[1];
$id_champ = $intru1 = $nom_champ[0];
$requete_mot = $connexion->query("SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ."') ORDER BY RAND() LIMIT 4");
while($mot = $requete_mot->fetch()){
	$response["mot".$i] = $mot[1];
	$i++;
}

$nom_champ = $requete_champ->fetch();
$response["champ2"] = $nom_champ[1];
$id_champ = $intru2 = $nom_champ[0];
$requete_mot = $connexion->query("SELECT id_mot,mot FROM Mot WHERE id_mot IN (SELECT id_mot FROM Appartient WHERE id_champ = '".$id_champ."') ORDER BY RAND() LIMIT 4");
while($mot = $requete_mot->fetch()){
	$response["mot".$i] = $mot[1];
	$i++;
}


$requete_intru = $connexion->query( "SELECT id_champ,nom_champ FROM Champ_lexical WHERE id_champ IN (SELECT id_champ FROM Appartient WHERE (id_champ != '".$intru0."' AND id_champ != '".$intru1."' AND id_champ != '".$intru2."')) ORDER BY rand() LIMIT 2");
$intru1 = $requete_intru->fetch();
$response["intru0"] = $intru1[1];
$intru2 = $requete_intru->fetch();
$response["intru1"] = $intru2[1];

echo json_encode($response); 
  
?>