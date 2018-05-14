package com.example.gentile.lexical;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
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

public class exo1 extends AppCompatActivity {
    public String prenom;
    public String nom;
    String scriptExo1 = "http://lexical.hopto.org/lexical/exo1.php";
   String scriptScore = "http://lexical.hopto.org/lexical/score1.php";
    //String scriptExo1 = "http://applichamplex.000webhostapp.com/lexical/exo1.php";
    //String scriptScore = "http://applichamplex.000webhostapp.com/lexical/score1.php";
    LinearLayout drop;
    TextView nom_champ;
    TextView mot1,mot2,mot3,mot4,mot5,mot6,mot7,mot8;
    Button valider;
    EditText color;
    ImageView etoileON1, etoileON2, etoileON3, etoileON4, etoileON5;
    boolean juste_mot1=false,juste_mot2=false,juste_mot3=false,juste_mot4=false,juste_mot0=false;
    boolean erreur_intru1=false,erreur_intru2=false,erreur_intru3=false;
    public JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        prenom = intent.getStringExtra("prenom");
        nom = intent.getStringExtra("nom");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo1);
        drop = (LinearLayout) findViewById(R.id.drop);
        nom_champ = (TextView) findViewById(R.id.champ1);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);
        mot6 = (TextView) findViewById(R.id.mot6);
        mot7 = (TextView) findViewById(R.id.mot7);
        mot8 = (TextView) findViewById(R.id.mot8);
        valider = (Button) findViewById(R.id.valider);
        color = (EditText) findViewById(R.id.color);
        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        etoileON4 = (ImageView) findViewById(R.id.etoileON4);
        etoileON5 = (ImageView) findViewById(R.id.etoileON5);

        if(ConnectionEleve.NbEtoileN1>0) {
            etoileON1.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN1>1) {
            etoileON2.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN1>2) {
            etoileON3.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN1>3) {
            etoileON4.setVisibility(View.VISIBLE);
        }
        final ArrayList<TextView> listMot = new ArrayList<TextView>();
        listMot.add(mot1);
        listMot.add(mot2);
        listMot.add(mot3);
        listMot.add(mot4);
        listMot.add(mot5);
        listMot.add(mot6);
        listMot.add(mot7);
        listMot.add(mot8);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eleveExo1", "eleveexo1 Wh")
                .build();
        Request request = new Request.Builder()
                .url(scriptExo1)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(exo1.this,
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
                            String champ_lex = jsonObj.getString("champ");
                            nom_champ.setText(champ_lex);
                            int place = (int) (Math.random()*7);
                            //on met les bon mot dans la liste
                            for(int i=0;i<5;i++) {
                                String nom_mot = jsonObj.getString("mot" + i);
                                listMot.get(place).setText(nom_mot);
                                place+=3;
                                place%=8;
                            }
                            for(int i=1;i<4;i++) {
                                String nom_mot = jsonObj.getString("intru"+i);
                                listMot.get(place).setText(nom_mot);
                                place+=3;
                                place%=8;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


        mot1.setOnTouchListener(new MyTouchListener());
        mot2.setOnTouchListener(new MyTouchListener());
        mot3.setOnTouchListener(new MyTouchListener());
        mot4.setOnTouchListener(new MyTouchListener());
        mot5.setOnTouchListener(new MyTouchListener());
        mot6.setOnTouchListener(new MyTouchListener());
        mot7.setOnTouchListener(new MyTouchListener());
        mot8.setOnTouchListener(new MyTouchListener());

        drop.setOnDragListener(new View.OnDragListener() {
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
                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1")) ||
                                    but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3")) ||
                                    but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("intru1")) ||
                                    but.getText().equals(jsonObj.getString("intru2")) || but.getText().equals(jsonObj.getString("intru3"))){
                                 but.setBackgroundColor(Color.GREEN);
                            }
                            if(but.getText().equals(jsonObj.getString("mot0"))){
                                juste_mot0=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot1"))){
                                juste_mot1=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot2"))){
                                juste_mot2=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot3"))){
                                juste_mot3=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot4"))){
                                juste_mot4=true;
                            }
                            if(but.getText().equals(jsonObj.getString("intru1"))){
                                erreur_intru1 = true;
                            }
                            if(but.getText().equals(jsonObj.getString("intru2"))){
                                erreur_intru2 = true;
                            }
                            if(but.getText().equals(jsonObj.getString("intru3"))){
                                erreur_intru3 = true;
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

        color.setOnDragListener(new View.OnDragListener() {
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
                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1")) ||
                                    but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3")) ||
                                    but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("intru1")) ||
                                    but.getText().equals(jsonObj.getString("intru2")) || but.getText().equals(jsonObj.getString("intru3"))){
                                but.setBackgroundColor(Color.GRAY);
                            }
                            if(but.getText().equals(jsonObj.getString("mot0"))){
                                juste_mot0=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot1"))){
                                juste_mot1=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot2"))){
                                juste_mot2=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot3"))){
                                juste_mot3=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot4"))){
                                juste_mot4=false;
                            }
                            if(but.getText().equals(jsonObj.getString("intru1"))){
                                erreur_intru1 = false;
                            }
                            if(but.getText().equals(jsonObj.getString("intru2"))){
                                erreur_intru2 = false;
                            }
                            if(but.getText().equals(jsonObj.getString("intru3"))){
                                erreur_intru3 = false;
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
                if (juste_mot0 == true && juste_mot1 == true && juste_mot2 == true && juste_mot3 == true && juste_mot4 == true
                        && erreur_intru1 == false && erreur_intru2 == false && erreur_intru3 == false) {
                    gagner = "0";
                } else {
                    gagner = "1";
                }

                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("eleveExo1", "eleveexo1 Wh")
                        .add("nom", nom)
                        .add("prenom", prenom)
                        .add("gagne", gagner)
                        .add("exercice", "1")
                        .build();
                Request request = new Request.Builder()
                        .url(scriptScore)
                        .post(formBody)
                        .build();
                client.newCall(request);

                ConnectionEleve.niveau = 1;
                if(juste_mot0==true && juste_mot1==true && juste_mot2==true && juste_mot3==true && juste_mot4==true
                                                                    && erreur_intru1==false && erreur_intru2==false && erreur_intru3==false){
                    //gagner
                    ConnectionEleve.NbEtoileN1+=1;

                    if(ConnectionEleve.NbEtoileN1==5){
                        etoileON4.setVisibility(View.VISIBLE);
                        Intent appel = new Intent(exo1.this, exo2.class);
                        appel.putExtra("prenom", prenom);
                        appel.putExtra("nom", nom);
                        startActivity(appel);
                    }
                    else{
                        Intent appel = new Intent(exo1.this, exo1.class);
                        appel.putExtra("prenom", prenom);
                        appel.putExtra("nom", nom);
                        startActivity(appel);
                    }
                }
                else{
                    ConnectionEleve.NbErreurN1++;
                    if(ConnectionEleve.NbErreurN1<5){
                        Intent appel = new Intent(exo1.this, gameOverExo1.class);
                        appel.putExtra("prenom", prenom);
                        appel.putExtra("nom", nom);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbErreurN1>=5){
                        Intent appel = new Intent(exo1.this, aide.class);
                        appel.putExtra("prenom", prenom);
                        appel.putExtra("nom", nom);
                        startActivity(appel);
                    }
                }
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