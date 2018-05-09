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

public class exo6 extends AppCompatActivity {

    String scriptExo6 = "http://lexical.hopto.org/lexical/exo6.php";
    EditText rep_champ,rep_intru;
    TextView nom_champ,nom_champ0,nom_champ1,nom_champ2,nom_champ3,nom_champ4;
    TextView mot1;
    TextView mot2;
    TextView mot3;
    TextView mot4;
    TextView mot5;
    TextView mot6;
    TextView intru;
    Button valider;
    ImageView etoileON1, etoileON2, etoileON3;
    boolean juste_champ,juste_intru;
    public JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo6);
        rep_champ = (EditText) findViewById(R.id.nom_champ);
        rep_intru= (EditText) findViewById(R.id.repintrus);
        nom_champ = (TextView) findViewById(R.id.rep1);
        nom_champ0 = (TextView) findViewById(R.id.champ0);
        nom_champ1 = (TextView) findViewById(R.id.champ1);
        nom_champ2 = (TextView) findViewById(R.id.champ2);
        nom_champ3 = (TextView) findViewById(R.id.champ3);
        nom_champ4 = (TextView) findViewById(R.id.champ4);
        intru = (TextView) findViewById(R.id.rep2);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);
        mot6 = (TextView) findViewById(R.id.mot6);
        valider = (Button) findViewById(R.id.valider);
        etoileON1 = (ImageView) findViewById(R.id.etoileON1);
        etoileON2 = (ImageView) findViewById(R.id.etoileON2);
        etoileON3 = (ImageView) findViewById(R.id.etoileON3);
        if(ConnectionEleve.NbEtoileN6>0) {
            etoileON1.setVisibility(View.VISIBLE);
        }
        if(ConnectionEleve.NbEtoileN6>1) {
            etoileON2.setVisibility(View.VISIBLE);
        }
        final ArrayList<TextView> listMot = new ArrayList<TextView>();
        final ArrayList<TextView> listChamp = new ArrayList<TextView>();
        listMot.add(mot1);
        listMot.add(mot2);
        listMot.add(mot3);
        listMot.add(mot4);
        listMot.add(mot5);
        listMot.add(mot6);
        listChamp.add(nom_champ0);
        listChamp.add(nom_champ1);
        listChamp.add(nom_champ2);
        listChamp.add(nom_champ3);
        listChamp.add(nom_champ4);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eleveExo6", "eleveexo6 Wh")
                .build();
        Request request = new Request.Builder()
                .url(scriptExo6)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(exo6.this,
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
                            int place = (int) (Math.random()*6);
                            //on met les bon mot dans la liste
                            for(int i=0;i<5;i++) {
                                String nom_mot = jsonObj.getString("mot"+i);
                                listMot.get(place).setText(nom_mot);
                                place++;
                                if(place==6)
                                    place=0;
                            }
                            String nom_mot = jsonObj.getString("intru");
                            listMot.get(place).setText(nom_mot);
                            int place2 = (int) (Math.random()*4);
                            for(int i=0;i<5;i++) {
                                String nom_champ = jsonObj.getString("champ"+i);
                                listChamp.get(place2).setText(nom_champ);
                                place2++;
                                if(place2==5)
                                    place2=0;
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
        nom_champ0.setOnTouchListener(new MyTouchListener());
        nom_champ1.setOnTouchListener(new MyTouchListener());
        nom_champ2.setOnTouchListener(new MyTouchListener());
        nom_champ3.setOnTouchListener(new MyTouchListener());
        nom_champ4.setOnTouchListener(new MyTouchListener());

        rep_intru.setOnDragListener(new View.OnDragListener() {
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
                            if(but.getText().equals(jsonObj.getString("intru"))) {
                                intru.setText(but.getText());
                                intru.setVisibility(View.VISIBLE);
                                juste_intru=true;
                            }
                            if( but.getText().equals(jsonObj.getString("mot0")) || but.getText().equals(jsonObj.getString("mot1"))
                                    || but.getText().equals(jsonObj.getString("mot2")) || but.getText().equals(jsonObj.getString("mot3")) ||
                                    but.getText().equals(jsonObj.getString("mot4"))){
                                intru.setText(but.getText());
                                intru.setVisibility(View.VISIBLE);
                                juste_intru=false;
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

        rep_champ.setOnDragListener(new View.OnDragListener() {
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
                                nom_champ.setText(but.getText());
                                nom_champ.setVisibility(View.VISIBLE);
                                juste_champ = true;
                            }
                            if(but.getText().equals(jsonObj.getString("champ1")) || but.getText().equals(jsonObj.getString("champ2"))
                                    || but.getText().equals(jsonObj.getString("champ3")) || but.getText().equals(jsonObj.getString("champ4"))){
                                nom_champ.setText(but.getText());
                                nom_champ.setVisibility(View.VISIBLE);
                                juste_champ = false;
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
                ConnectionEleve.niveau=6;
                if(juste_champ==true && juste_intru==true){
                    ConnectionEleve.NbEtoileN6++;
                    if(ConnectionEleve.NbEtoileN6>2) {
                        etoileON3.setVisibility(View.VISIBLE);
                        Intent appel = new Intent(exo6.this, reussi.class);
                        startActivity(appel);
                    }
                    else{
                        Intent appel = new Intent(exo6.this, exo6.class);
                        startActivity(appel);
                    }
                }
                else{
                    ConnectionEleve.NbErreurN6++;
                    if(ConnectionEleve.NbErreurN6<5){
                        Intent appel = new Intent(exo6.this, gameOverExo1.class);
                        startActivity(appel);
                    }
                    if(ConnectionEleve.NbErreurN6>=5){
                        Intent appel = new Intent(exo6.this, aide.class);
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