package com.example.gentile.lexical;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjoutMot extends AppCompatActivity {
    String urlTestChamp = "http://lexical.hopto.org/lexical/test_champ.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_mot);
        final EditText mot = (EditText) findViewById(R.id.motAjout);
        final EditText champ = (EditText) findViewById(R.id.champAssoc);
        final Button ajouter = (Button) findViewById(R.id.ajouterMot);
        final Spinner classe = (Spinner) findViewById(R.id.spinner);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mot.getText().toString().equals("")){
                    Toast.makeText(AjoutMot.this,
                            "Il faut indiquer le mot à ajouter",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(champ.getText().toString().equals("")){
                    Toast.makeText(AjoutMot.this,
                            "Il faut indiquer le champ associé au mot",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ajouter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(classe.getSelectedItem().toString());
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("champ", champ.getText().toString())
                                .add("mot", mot.getText().toString())
                                .add("classe", classe.getSelectedItem().toString())
                                .build();
                        Request request = new Request.Builder()
                                .url(urlTestChamp)
                                .post(formBody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AjoutMot.this,
                                                "Connection au serveur impossible.",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String resp = response.body().string().toString();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            System.out.println(resp);
                                            JSONObject jsonObj = new JSONObject(resp);
                                            int success = jsonObj.getInt("success");
                                            System.out.println(success);
                                            if (success == 1) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(AjoutMot.this,
                                                                "Mot ajouté",
                                                                Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                });
                                                //le champs lex indiqué existe, si le mot nexiste pas
                                                // il faut ajouter le mot et l'associer au champ dans la la table
                                                //Appartient. sinon on recupre lid du mot et on l'associe au champ
                                            }
                                            if(success == 2) {
                                                //le champ lex n'existe pas on demande a l'user si il veut l'ajouter
                                                //puis on refait l'operation precedente.
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }
                                    }
                                });

                            }
                        });

                        
                       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(AjoutMot.this);
                        alertDialog.setTitle("Créer le champ lexical ?");
                        alertDialog.setMessage("Le champ lexical indiqué n'existe pas voulez vous l'ajouter ?");

                        alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Que faire si il rep oui?
                            }
                        });

                        alertDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Que faire si il rep non ?
                            }
                        });

                        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {

                            }
                        });
                        alertDialog.show();*/
                    }
                });
            }
        });
    }
}
