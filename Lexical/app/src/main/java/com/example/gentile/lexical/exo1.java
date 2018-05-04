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
    String scriptExo1 = "http://lexical.hopto.org/lexical/exo1.php";
    LinearLayout drop;
    TextView nom_champ;
    TextView mot1;
    TextView mot2;
    TextView mot3;
    TextView mot4;
    TextView mot5;
    TextView mot6;
    TextView mot7;
    TextView mot8;
    Button valider;
    ImageView etoile1, etoile2, etoile3, etoile4, etoile5;
    ImageView etoileON1, etoileON2, etoileON3, etoileON4, etoileON5;
    int nbrMotPlace;
    boolean erreur;
    public JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        etoile1 = (ImageView) findViewById(R.id.etoile1);
        etoile2 = (ImageView) findViewById(R.id.etoile2);
        etoile3 = (ImageView) findViewById(R.id.etoile3);
        etoile4 = (ImageView) findViewById(R.id.etoile4);
        etoile5 = (ImageView) findViewById(R.id.etoile5);
        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        etoileON4 = (ImageView) findViewById(R.id.etoileON4);
        etoileON5 = (ImageView) findViewById(R.id.etoileON5);
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
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot.get(place%8).setText(nom_mot);
                                place++;
                            }
                            for(int i=1;i<4;i++) {
                                String nom_mot = jsonObj.getString("intru"+i);
                                listMot.get(place % 8).setText(nom_mot);
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
                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1")) || but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3")) || but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("mot5")) || but.getText().equals(jsonObj.getString("mot6"))) {
                                but.setVisibility(View.INVISIBLE);
                                nbrMotPlace++;
                            }
                            if(but.getText().equals(jsonObj.getString("intru0")) || but.getText().equals(jsonObj.getString("intru1")) || but.getText().equals(jsonObj.getString("intru2"))){
                                erreur = true;
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
                if(!erreur && nbrMotPlace == 5){
                    //gagner
                    ConnectionEleve.NbEtoileN1+=5;
                    if(ConnectionEleve.NbEtoileN1==1) {
                         etoileON1.setVisibility(View.VISIBLE);
                        Intent appel = new Intent(exo1.this, exo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbEtoileN1==2) {
                        etoileON2.setVisibility(View.VISIBLE);
                        Intent appel = new Intent(exo1.this, exo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbEtoileN1==3) {
                        etoileON3.setVisibility(View.VISIBLE);

                        Intent appel = new Intent(exo1.this, exo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbEtoileN1==4) {
                        etoileON4.setVisibility(View.VISIBLE);
                        Intent appel = new Intent(exo1.this, exo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbEtoileN1==5){
                        Intent appel = new Intent(exo1.this, exo2.class);
                        startActivity(appel);
                    }
                }
                else{
                    Intent appel = new Intent(exo1.this, gameOverExo1.class);
                    startActivity(appel);
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