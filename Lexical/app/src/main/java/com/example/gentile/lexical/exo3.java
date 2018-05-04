package com.example.gentile.lexical;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/* 5 mots correspondant a 1 champ, 4 nom possible  */
public class exo3 extends AppCompatActivity {
    String scriptExo3 = "http://lexical.hopto.org/lexical/exo3.php";
    LinearLayout drop;
    TextView mot1;
    TextView mot2;
    TextView mot3;
    TextView mot4;
    TextView mot5;
    TextView champ1;
    TextView champ2;
    TextView champ3;
    TextView champ4;
    Button valider;
    int correct;
    boolean reussi;
    public JSONObject jsonObj;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        drop = (LinearLayout) findViewById(R.id.drop);
        mot1 = (TextView) findViewById(R.id.mot1);
        mot2 = (TextView) findViewById(R.id.mot2);
        mot3 = (TextView) findViewById(R.id.mot3);
        mot4 = (TextView) findViewById(R.id.mot4);
        mot5 = (TextView) findViewById(R.id.mot5);

        champ1 = (TextView) findViewById(R.id.champ1);
        champ2 = (TextView) findViewById(R.id.champ2);
        champ3 = (TextView) findViewById(R.id.champ3);
        champ4 = (TextView) findViewById(R.id.champ4);

        valider = (Button) findViewById(R.id.valider);
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
                                if(i == 0) {
                                    correct = place % 8;
                                }
                                place++;
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
                        Button but = (Button) event.getLocalState();
                        if(but.getText().equals(listChamp.get(correct).getText())){
                            TextView champ = findViewById(R.id.champ);
                            champ.setText(but.getText());
                            reussi = true;
                        }
                        else{
                            TextView champ = findViewById(R.id.champ);
                            champ.setText(but.getText());
                            reussi = false;
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
                if(reussi){
                    //gagner
                }
                else{
                    Intent appel = new Intent(exo3.this, gameOverExo3.class);
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
