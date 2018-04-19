package com.example.gentile.lexical;

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

public class AjoutEleve extends AppCompatActivity {
    String urlAjoutEleve = "http://lexical.hopto.org/lexical/ajout_eleve.php";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_eleve);
        final EditText nom = (EditText) findViewById(R.id.nomEleve);
        final EditText prenom = (EditText) findViewById(R.id.prenomEleve);
        final Button ajouter = (Button) findViewById(R.id.AjouterEleve);


        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nom.getText().toString().equals("")){
                    Toast.makeText(AjoutEleve.this,
                            "Il faut indiquer le prenom de l'eleve à ajouter",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(prenom.getText().toString().equals("")){
                    Toast.makeText(AjoutEleve.this,
                            "Il faut indiquer le nom de l'eleve a ajouter",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ajouter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("nom", nom.getText().toString())
                                .add("prenom", prenom.getText().toString())
                                .build();
                        Request request = new Request.Builder()
                                .url(urlAjoutEleve)
                                .post(formBody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AjoutEleve.this,
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
                                                        Toast.makeText(AjoutEleve.this,
                                                                "Eleve ajouté",
                                                                Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }
                                    }
                                });

                            }
                        });



                    }
                });
            }
        });
    }
}
