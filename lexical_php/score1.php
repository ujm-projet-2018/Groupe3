<?php
$response = array();
/*
if(empty($_POST["nom"]) || empty($_POST["prenom"]) || empty($_POST["gagne"]) || empty($_POST["exercice"]) ){
  echo "Nom, prenom, score ou exercice vide!";
}

else{*/
  $nom = $_POST['nom'];
  $prenom = $_POST['prenom'];
  $score = $_POST['gagne'];
  $exercice = intval($_POST['exercice']);
/*
  $response["nom"] = $nom;
  $response["prenom"] = $prenom;
  $response["gagne"] = $score;
  $response["num_exercice"] = $exercice;
*/
  try{
    $connexion=new PDO('mysql:host=lexical.hopto.org;dbname=projet_tut;charset=utf8','root','devweb4242');    
  }
  catch(Exeption $e){
    die('erreur :'.$e->getMessage());
  }

  try{
// On ajoute le résultat à la BDD
    $ajout_res=$connexion->query("INSERT INTO Resultat(niveau,resultat) VALUES ('".$exercice."','".$score."') ");
    if($ajout_res)
      //$response["ajout du resultat"]=1;
   
// On en récupère l'id
    $req_id_res=$connexion->query("SELECT * FROM Resultat ORDER BY id_resultat DESC LIMIT 1");
    $idr = $req_id_res->fetch();
    //$response["id_resultat"] = $idr[0];

// On récupère l'id de l'élève
    $req_id_eleve=$connexion->query("SELECT id_eleve FROM Eleve WHERE (nom_eleve = '".$nom."' AND prenom_eleve = '".$prenom."') ");
    $ide = $req_id_eleve->fetch();
    //$response["id_eleve"] = $ide[0];
    
// On compte le nombre d'essais totals de l'élève
    $essais=$connexion->query("SELECT COUNT(*) FROM Resultat WHERE (niveau = '".$exercice."' AND id_resultat IN (SELECT id_resultat FROM Obtient WHERE id_eleve = '".$ide[0]."')) ");
    $pessayees = $essais->fetch();
    $essai = $pessayees[0];
    $response["nb_essais"] = $essai;

// On compte a présent le nombre d'essais réussis
    $gagnes=$connexion->query("SELECT COUNT(*) FROM Obtient WHERE (id_eleve = '".$ide[0]."' AND id_resultat IN (SELECT id_resultat FROM Resultat WHERE (niveau = '".$exercice."' AND resultat = '0')) )");
    $pgagnees = $gagnes->fetch();
    $response["victoires"] = $pgagnees[0];

// Si on a toujours pas 5 victoires
    if($pgagnees[0] < 5){
      $ajout_obt=$connexion->query("INSERT INTO Obtient(id_eleve,id_resultat, num_essai) VALUES ('".$ide[0]."', '".$idr[0]."', '".$essai."')");
    }

  }catch (PDOException $e) {
    if ($e->getCode() == 1062) {
        $response["success"]=0;
    } else {
        throw $e;
    }
  }
//}

echo json_encode($response);
 
?>  