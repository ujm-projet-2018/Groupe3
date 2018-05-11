package com.example.gentile.lexical;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/* 5 mots correspondant a 1 champ, 4 nom possible  */
public class exo3 extends AppCompatActivity {
    String prenom;
    String nom;
    String scriptExo3 = "http://lexical.hopto.org/lexical/exo3.php";
    String scriptScore = "http://lexical.hopto.org/lexical/score1.php";
    EditText rep1;
    TextView mot1,mot2,mot3,mot4,mot5;
    TextView champ1,champ2,champ3,champ4;
    TextView champ;
    Button valider;
    ImageView etoileON1, etoileON2, etoileON3, etoileON4, etoileON5;
    boolean reussi;
    public JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        prenom = intent.getStringExtra("prenom");
        nom = intent.getStringExtra("nom");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        rep1 = (EditText) findViewById(R.id.repintrus);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);

        champ = (TextView) findViewById(R.id.champ);
        champ1 = (TextView) findViewById(R.id.champ1);
        champ2 = (TextView) findViewById(R.id.champ2);
        champ3 = (TextView) findViewById(R.id.champ3);
        champ4 = (TextView) findViewById(R.id.champ4);

        valider = (Button) findViewById(R.id.valider);
        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        etoileON4 = (ImageView) findViewById(R.id.etoileON4);
        etoileON5 = (ImageView) findViewById(R.id.etoileON5);

        if (ConnectionEleve.NbEtoileN3 > 0) {
            etoileON1.setVisibility(View.VISIBLE);
        }
        if (ConnectionEleve.NbEtoileN3 > 1) {
            etoileON2.setVisibility(View.VISIBLE);
        }
        if (ConnectionEleve.NbEtoileN3 > 2) {
            etoileON3.setVisibility(View.VISIBLE);
        }
        if (ConnectionEleve.NbEtoileN3 > 3) {
            etoileON4.setVisibility(View.VISIBLE);
        }

        final ArrayList<TextView> listMot = new ArrayList<TextView>();
        final ArrayList<TextView> listChamp = new ArrayList<TextView>();
        listMot.add(mot1);
        listMot.add(mot2);
        listMot.add(mot3);
        listMot.add(mot4);
        listMot.add(mot5);
        listChamp.add(champ1);
        listChamp.add(champ2);
        listChamp.add(champ3);
        listChamp.add(champ4);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eleveExo3", "eleveexo3 Wh")
                .build();
        Request request = new Request.Builder()
                .url(scriptExo3)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(exo3.this,
                                "Connection au serveur impossible.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String resp = response.body().string().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonObj = new JSONObject(resp);
                            for(int i=0; i < 5; i++){
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot.get(i).setText(nom_mot);
                            }
                            int place = (int) (Math.random()*4);
                            for(int i=0;i<4;i++) {
                                String nom_mot = jsonObj.getString("champ"+i);
                                listChamp.get(place%8).setText(nom_mot);
                                place++;
                                if(place==4)
                                    place=0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        champ1.setOnTouchListener(new exo3.MyTouchListener());
        champ2.setOnTouchListener(new exo3.MyTouchListener());
        champ3.setOnTouchListener(new exo3.MyTouchListener());
        champ4.setOnTouchListener(new exo3.MyTouchListener());

        rep1.setOnDragListener(new View.OnDragListener() {
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Executed after the Drag Shadow enters the drop area
                        break;
                    case DragEvent.ACTION_DROP:
                        //Executed when user drops the data
                        TextView but = (TextView) event.getLocalState();
                        try {
                            if (but.getText().equals(jsonObj.getString("champ0"))) {
                                champ.setText(but.getText());
                                champ.setVisibility(View.VISIBLE);
                                reussi = true;
                            }
                            else{
                                champ.setText(but.getText());
                                champ.setVisibility(View.VISIBLE);
                                reussi = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return (true);

                    case DragEvent.ACTION_DRAG_ENDED:
                        return (true);
                    default:
                        break;
                }
                return true;
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String gagner;
                if(reussi){
                    gagner = "0";
                }
                else{
                    gagner = "1";
                }
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("eleveExo3", "eleveexo3 Wh")
                        .add("nom", nom)
                        .add("prenom", prenom)
                        .add("gagne", gagner)
                        .add("exercice", "3")
                        .build();
                Request request = new Request.Builder()
                        .url(scriptScore)
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ConnectionEleve.niveau = 3;
                        if (reussi) {
                            ConnectionEleve.niveau = 3;
                            ConnectionEleve.NbEtoileN3 += 1;

                            if (ConnectionEleve.NbEtoileN3 == 5) {
                                etoileON5.setVisibility(View.VISIBLE);
                                Intent appel = new Intent(exo3.this, exo4.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            } else {
                                Intent appel = new Intent(exo3.this, exo3.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                        } else {
                            ConnectionEleve.NbErreurN3++;
                            if (ConnectionEleve.NbErreurN3 < 5) {
                                Intent appel = new Intent(exo3.this, gameOverExo1.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                            if (ConnectionEleve.NbErreurN3 >= 5) {
                                Intent appel = new Intent(exo3.this, exo4.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                        }
                    }
                });
            }
        });
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}

