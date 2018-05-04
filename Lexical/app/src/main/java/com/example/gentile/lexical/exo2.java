package com.example.gentile.lexical;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    TextView nom_champ1;
    TextView nom_champ2;
    Button mot1;
    Button mot2;
    Button mot3;
    Button mot4;
    Button mot5;
    Button mot6;
    Button mot7;
    Button mot8;
    Button valider;
    int nbrMotPlace;
    boolean erreur;
    public JSONObject jsonObj;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        erreur = false;
        nbrMotPlace = 0;
        setContentView(R.layout.activity_exo2);
        nom_champ1 = (TextView) findViewById(R.id.champ1);
        nom_champ2 = (TextView) findViewById(R.id.champ2);
        mot1 = (Button) findViewById(R.id.mot1);
        mot2 = (Button) findViewById(R.id.mot2);
        mot3 = (Button) findViewById(R.id.mot3);
        mot4 = (Button) findViewById(R.id.mot4);
        mot5 = (Button) findViewById(R.id.mot5);
        mot6 = (Button) findViewById(R.id.mot6);
        mot7 = (Button) findViewById(R.id.mot7);
        mot8 = (Button) findViewById(R.id.mot8);
        valider = (Button) findViewById(R.id.valider);
        final ArrayList<Button> listMot1 = new ArrayList<Button>();
        final ArrayList<Button> listMot2 = new ArrayList<Button>();
        listMot1.add(mot1);
        listMot1.add(mot2);
        listMot1.add(mot3);
        listMot1.add(mot4);
        listMot2.add(mot5);
        listMot2.add(mot6);
        listMot2.add(mot7);
        listMot2.add(mot8);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eleveExo2", "eleveexo2 Wh")
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
            public void onResponse(Call call, Response response) throws IOException {
                final String resp = response.body().string().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(resp);
                            jsonObj = new JSONObject(resp);
                            String champ_lex1 = jsonObj.getString("champ1");
                            nom_champ1.setText(champ_lex1);
                            String champ_lex2 = jsonObj.getString("champ2");
                            nom_champ2.setText(champ_lex2);
                            int place = (int) (Math.random()*8);
                            //on met les bon mot dans la liste
                            for(int i=0;i<4;i++) {
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot1.get(place%8).setText(nom_mot);
                                place++;
                            }
                            for(int i=0;i<4;i++) {
                                String nom_mot = jsonObj.getString("mot"+i+4);
                                listMot2.get(place%8).setText(nom_mot);
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

        nom_champ1.setOnDragListener(new View.OnDragListener() {
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
                        Button but = (Button) event.getLocalState();
                        try {
                            if(but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1")) || but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3"))) {

                                but.setVisibility(View.INVISIBLE);
                                nbrMotPlace++;
                            }
                            else{
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

        nom_champ2.setOnDragListener(new View.OnDragListener() {
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
                        Button but = (Button) event.getLocalState();
                        try {
                            if(but.getText().equals(jsonObj.getString("mot4")) || but.getText().equals(jsonObj.getString("mot5")) || but.getText().equals(jsonObj.getString("mot6")) || but.getText().equals(jsonObj.getString("mot7"))) {

                                but.setVisibility(View.INVISIBLE);
                                nbrMotPlace++;
                            }
                            else{
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
                if(!erreur && nbrMotPlace == 8){
                    //gagner
                }
                else{
                    Intent appel = new Intent(exo2.this, gameOverExo2.class);
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
