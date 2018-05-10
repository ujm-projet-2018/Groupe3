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

public class exo2 extends AppCompatActivity {

    String scriptExo2 = "http://lexical.hopto.org/lexical/exo2.php";
    String scriptScore = "http://lexical.hopto.org/lexical/score1.php";
    EditText rep1, rep2;
    TextView nom_champ1, nom_champ2;
    TextView mot1,mot2,mot3,mot4,mot5,mot6,mot7,mot8;
    Button valider;
    ImageView etoileON1, etoileON2, etoileON3, etoileON4, etoileON5;
    boolean juste_mot0,juste_mot1,juste_mot2,juste_mot3,juste_mot4,juste_mot5,juste_mot6,juste_mot7;
    public JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getStringExtra("prenom");
        intent.getStringExtra("nom");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo2);
        rep1 = (EditText) findViewById(R.id.rep1);
        rep2 = (EditText) findViewById(R.id.rep2);
        nom_champ1 = (TextView) findViewById(R.id.champ1);
        nom_champ2 = (TextView) findViewById(R.id.champ2);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);
        mot6 = (TextView) findViewById(R.id.mot6);
        mot7 = (TextView) findViewById(R.id.mot7);
        mot8 = (TextView) findViewById(R.id.mot8);
        valider = (Button) findViewById(R.id.valider);

        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        etoileON4 = (ImageView) findViewById(R.id.etoileON4);
        etoileON5 = (ImageView) findViewById(R.id.etoileON5);

        if(ConnectionEleve.NbEtoileN2>0) {
            etoileON1.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN2>1) {
            etoileON2.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN2>2) {
            etoileON3.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN2>3) {
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
                .url(scriptExo2)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(exo2.this,
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
                            String champ_lex1 = jsonObj.getString("champ0");
                            nom_champ1.setText(champ_lex1);
                            String champ_lex2 = jsonObj.getString("champ1");
                            nom_champ2.setText(champ_lex2);
                            int place = (int) (Math.random()*7);
                            //on met les bon mot dans la liste
                            for(int i=0;i<8;i++) {
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot.get(place%8).setText(nom_mot);
                                place++;
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
                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1"))
                                    || but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3"))
                                    || but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("mot5"))
                                    || but.getText().equals(jsonObj.getString("mot6")) || but.getText().equals(jsonObj.getString("mot7" ))){
                                but.setBackgroundColor(Color.BLUE);
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
                                juste_mot4=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot5"))){
                                juste_mot5=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot6"))){
                                juste_mot6=false;
                            }
                            if(but.getText().equals(jsonObj.getString("mot7"))){
                                juste_mot7=false;
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

                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1"))
                                    || but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3"))
                                    || but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("mot5"))
                                    || but.getText().equals(jsonObj.getString("mot6")) || but.getText().equals(jsonObj.getString("mot7" ))){
                                but.setBackgroundColor(Color.GREEN);
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
                                juste_mot4=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot5"))){
                                juste_mot5=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot6"))){
                                juste_mot6=true;
                            }
                            if(but.getText().equals(jsonObj.getString("mot7"))){
                                juste_mot7=true;
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
                ConnectionEleve.niveau=2;
                if(juste_mot0==true && juste_mot1==true && juste_mot2==true && juste_mot3==true
                        && juste_mot4==true && juste_mot5==true && juste_mot6==true && juste_mot7==true ){
                    ConnectionEleve.NbEtoileN2++;
                    if(ConnectionEleve.NbEtoileN2==5){
                        Intent appel = new Intent(exo2.this, exo3.class);
                        startActivity(appel);
                    }
                    else{
                        Intent appel = new Intent(exo2.this, exo2.class);
                        startActivity(appel);
                    }
                }
                else{
                    ConnectionEleve.NbErreurN2++;
                    if(ConnectionEleve.NbErreurN2<5){
                        Intent appel = new Intent(exo2.this, gameOverExo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbErreurN2>=5){
                        Intent appel = new Intent(exo2.this, aide.class);
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

}