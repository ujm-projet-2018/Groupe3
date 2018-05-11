package com.example.gentile.lexical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ConnectionEleve extends AppCompatActivity {
    public String prenom;
    public String nom;
    public static int NbEtoileN1 = 0;
    public static int NbEtoileN2=0;
    public static int NbEtoileN3=0;
    public static int NbEtoileN4=0;
    public static int NbEtoileN5=0;
    public static int NbEtoileN6=0;
    public static int NbErreurN1 = 0;
    public static int NbErreurN2 = 0;
    public static int NbErreurN3 = 0;
    public static int NbErreurN4 = 0;
    public static int NbErreurN5 = 0;
    public static int NbErreurN6 = 0;
    public static int niveau = 0;

    String urlCoEleve = "http://lexical.hopto.org/lexical/connect.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_eleve);

        //Recuperation des boutons.
        Button connect = (Button) findViewById(R.id.seConnecter);
        Button inscr = (Button) findViewById(R.id.sinscrire);
        //Recuperation des Edit text login et pass.


        connect.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                final EditText identifiant = (EditText) findViewById(R.id.identifiantEleve);
                final EditText pass = (EditText) findViewById(R.id.passEleve);
                String strId = identifiant.toString();
                String strPass = pass.toString();
                if (identifiant.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(ConnectionEleve.this,
                            "Identifiant ou mot de passe non indiqué.",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    prenom = identifiant.getText().toString();
                    nom = pass.getText().toString();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("identifiant", prenom)
                            .add("pass", nom)
                            .add("type", "eleve")
                            .build();
                    Request request = new Request.Builder()
                            .url(urlCoEleve)
                            .post(formBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ConnectionEleve.this,
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
                                            Intent intent = new Intent(ConnectionEleve.this, exo1.class);
                                            intent.putExtra("prenom",prenom);
                                            intent.putExtra("nom",nom);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(ConnectionEleve.this,
                                                    "Identifiant ou mot de passe incorrects.",
                                                    Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}


