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

public class exo4 extends AppCompatActivity {
    String prenom;
    String nom;
    String scriptExo4 = "http://lexical.hopto.org/lexical/exo4.php";
    String scriptScore = "http://lexical.hopto.org/lexical/score1.php";
    EditText rep1,rep2,rep3;
    TextView nom_champ1,nom_champ2,nom_champ3,nom_champ4,nom_champ5;
    TextView mot1, mot2,mot3,mot4,mot5,mot6,mot7,mot8,mot9,mot10,mot11,mot12;
    TextView rep_champ1,rep_champ2,rep_champ3;
    Button valider;
    ImageView etoileON1, etoileON2, etoileON3, etoileON4, etoileON5;
    boolean juste_rep1,juste_rep2,juste_rep3;
    public JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        prenom = intent.getStringExtra("prenom");
        nom = intent.getStringExtra("nom");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo4);
        rep1 = (EditText) findViewById(R.id.rep1);
        rep2 = (EditText) findViewById(R.id.rep2);
        rep3 = (EditText) findViewById(R.id.rep3);
        nom_champ1 = (TextView) findViewById(R.id.champ1);
        nom_champ2 = (TextView) findViewById(R.id.champ2);
        nom_champ3 = (TextView) findViewById(R.id.champ3);
        nom_champ4 = (TextView) findViewById(R.id.champ4);
        nom_champ5 = (TextView) findViewById(R.id.champ5);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);
        mot6 = (TextView) findViewById(R.id.mot6);
        mot7 = (TextView) findViewById(R.id.mot7);
        mot8 = (TextView) findViewById(R.id.mot8);
        mot9 = (TextView) findViewById(R.id.mot9);
        mot10 = (TextView) findViewById(R.id.mot10);
        mot11 = (TextView) findViewById(R.id.mot11);
        mot12 = (TextView) findViewById(R.id.mot12);
        valider = (Button) findViewById(R.id.valider);
        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        etoileON4 = (ImageView) findViewById(R.id.etoileON4);
        etoileON5 = (ImageView) findViewById(R.id.etoileON5);
        if(ConnectionEleve.NbEtoileN4>0) {
            etoileON1.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN4>1) {
            etoileON2.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN4>2) {
            etoileON3.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN4>3) {
            etoileON4.setVisibility(View.VISIBLE);
        }

        rep_champ1 = (TextView) findViewById(R.id.repChamp1);
        rep_champ2 = (TextView) findViewById(R.id.repChamp2);
        rep_champ3 = (TextView) findViewById(R.id.repChamp3);
        final ArrayList<TextView> listMot = new ArrayList<TextView>();
        listMot.add(mot1);
        listMot.add(mot2);
        listMot.add(mot3);
        listMot.add(mot4);
        listMot.add(mot5);
        listMot.add(mot6);
        listMot.add(mot7);
        listMot.add(mot8);
        listMot.add(mot9);
        listMot.add(mot10);
        listMot.add(mot11);
        listMot.add(mot12);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eleveExo4", "eleveexo4 Wh")
                .build();
        Request request = new Request.Builder()
                .url(scriptExo4)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(exo4.this,
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
                            jsonObj = new JSONObject(resp);
                            int alea = (int) (Math.random()*5);
                            String champ_lex, champ_lex2, champ_lex3, champ_lex4, champ_lex5;
                            switch(alea){
                                case 0 :
                                    champ_lex = jsonObj.getString("champ0");
                                    nom_champ1.setText(champ_lex);
                                    champ_lex2 = jsonObj.getString("champ1");
                                    nom_champ2.setText(champ_lex2);
                                    champ_lex3 = jsonObj.getString("champ2");
                                    nom_champ3.setText(champ_lex3);
                                    champ_lex4 = jsonObj.getString("intru0");
                                    nom_champ4.setText(champ_lex4);
                                    champ_lex5 = jsonObj.getString("intru1");
                                    nom_champ5.setText(champ_lex5); break;
                                case 1 :champ_lex = jsonObj.getString("champ0");
                                    nom_champ2.setText(champ_lex);
                                    champ_lex2 = jsonObj.getString("champ1");
                                    nom_champ3.setText(champ_lex2);
                                    champ_lex3 = jsonObj.getString("champ2");
                                    nom_champ1.setText(champ_lex3);
                                    champ_lex4 = jsonObj.getString("intru0");
                                    nom_champ4.setText(champ_lex4);
                                    champ_lex5 = jsonObj.getString("intru1");
                                    nom_champ5.setText(champ_lex5); break;
                                case 2 :champ_lex = jsonObj.getString("champ0");
                                    nom_champ1.setText(champ_lex);
                                    champ_lex2 = jsonObj.getString("champ1");
                                    nom_champ4.setText(champ_lex2);
                                    champ_lex3 = jsonObj.getString("champ2");
                                    nom_champ2.setText(champ_lex3);
                                    champ_lex4 = jsonObj.getString("intru0");
                                    nom_champ3.setText(champ_lex4);
                                    champ_lex5 = jsonObj.getString("intru1");
                                    nom_champ5.setText(champ_lex5); break;
                                case 3 :champ_lex = jsonObj.getString("champ0");
                                    nom_champ5.setText(champ_lex);
                                    champ_lex2 = jsonObj.getString("champ1");
                                    nom_champ2.setText(champ_lex2);
                                    champ_lex3 = jsonObj.getString("champ2");
                                    nom_champ4.setText(champ_lex3);
                                    champ_lex4 = jsonObj.getString("intru0");
                                    nom_champ1.setText(champ_lex4);
                                    champ_lex5 = jsonObj.getString("intru1");
                                    nom_champ3.setText(champ_lex5); break;
                                case 4 :champ_lex = jsonObj.getString("champ0");
                                    nom_champ4.setText(champ_lex);
                                    champ_lex2 = jsonObj.getString("champ1");
                                    nom_champ5.setText(champ_lex2);
                                    champ_lex3 = jsonObj.getString("champ2");
                                    nom_champ2.setText(champ_lex3);
                                    champ_lex4 = jsonObj.getString("intru0");
                                    nom_champ1.setText(champ_lex4);
                                    champ_lex5 = jsonObj.getString("intru1");
                                    nom_champ3.setText(champ_lex5); break;
                            }

                            int place = 0;
                            //on met les bon mot dans la liste
                            for(int i=0;i<12;i++) {
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot.get(place).setText(nom_mot);
                                place++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


        nom_champ1.setOnTouchListener(new MyTouchListener());
        nom_champ2.setOnTouchListener(new MyTouchListener());
        nom_champ3.setOnTouchListener(new MyTouchListener());
        nom_champ4.setOnTouchListener(new MyTouchListener());
        nom_champ5.setOnTouchListener(new MyTouchListener());


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
                            if(but.getText().equals(jsonObj.getString("champ0"))) {
                                rep_champ1.setText(but.getText());
                                rep_champ1.setVisibility(View.VISIBLE);
                                juste_rep1=true;
                            }
                            if(but.getText().equals(jsonObj.getString("champ1")) || but.getText().equals(jsonObj.getString("champ2"))
                                    || but.getText().equals(jsonObj.getString("intru0")) || but.getText().equals(jsonObj.getString("intru1"))){
                                rep_champ1.setText(but.getText());
                                rep_champ1.setVisibility(View.VISIBLE);
                                juste_rep1=false;
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

        rep2.setOnDragListener(new View.OnDragListener() {
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
                            if(but.getText().equals(jsonObj.getString("champ1"))) {
                                rep_champ2.setText(but.getText());
                                rep_champ2.setVisibility(View.VISIBLE);
                                juste_rep2=true;
                            }
                            if(but.getText().equals(jsonObj.getString("champ0")) || but.getText().equals(jsonObj.getString("champ2"))
                                    || but.getText().equals(jsonObj.getString("intru0")) || but.getText().equals(jsonObj.getString("intru1"))){
                                rep_champ2.setText(but.getText());
                                rep_champ2.setVisibility(View.VISIBLE);
                                juste_rep2=false;
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
        rep3.setOnDragListener(new View.OnDragListener() {
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
                            if(but.getText().equals(jsonObj.getString("champ2"))) {
                                rep_champ3.setText(but.getText());
                                rep_champ3.setVisibility(View.VISIBLE);
                                juste_rep3=true;
                            }
                            if(but.getText().equals(jsonObj.getString("champ0")) || but.getText().equals(jsonObj.getString("champ1"))
                                    || but.getText().equals(jsonObj.getString("intru0")) || but.getText().equals(jsonObj.getString("intru1"))){
                                rep_champ3.setText(but.getText());
                                rep_champ3.setVisibility(View.VISIBLE);
                                juste_rep3=false;
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
                if(juste_rep1==true && juste_rep2==true && juste_rep3==true)
                    gagner = "0";
                else
                    gagner = "1";
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("eleveExo4", "eleveexo4 Wh")
                        .add("nom", nom)
                        .add("prenom", prenom)
                        .add("gagne", gagner)
                        .add("exercice", "4")
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
                        ConnectionEleve.niveau=4;
                        if(juste_rep1==true && juste_rep2==true && juste_rep3==true){
                            ConnectionEleve.NbEtoileN4+=1;
                            if(ConnectionEleve.NbEtoileN4>=5){
                                Intent appel = new Intent(exo4.this, exo5.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                            else{
                                Intent appel = new Intent(exo4.this, exo4.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                        }
                        else{
                            ConnectionEleve.NbErreurN4++;
                            if(ConnectionEleve.NbErreurN4<5){
                                Intent appel = new Intent(exo4.this, gameOverExo1.class);
                                appel.putExtra("prenom", prenom);
                                appel.putExtra("nom", nom);
                                startActivity(appel);
                            }
                            if(ConnectionEleve.NbErreurN4>=5){
                                Intent appel = new Intent(exo4.this, aide.class);
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