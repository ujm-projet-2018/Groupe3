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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

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
    Button mot1;
    Button mot2;
    Button mot3;
    Button mot4;
    Button mot5;
    Button mot6;
    Button mot7;
    Button mot8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo1);
        drop = (LinearLayout) findViewById(R.id.drop);
        nom_champ = (TextView) findViewById(R.id.champ);
        mot1 = (Button) findViewById(R.id.mot1);
        mot2 = (Button) findViewById(R.id.mot2);
        mot3 = (Button) findViewById(R.id.mot3);
        mot4 = (Button) findViewById(R.id.mot4);
        mot5 = (Button) findViewById(R.id.mot5);
        mot6 = (Button) findViewById(R.id.mot6);
        mot7 = (Button) findViewById(R.id.mot7);
        mot8 = (Button) findViewById(R.id.mot8);

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
                            JSONObject jsonObj = new JSONObject(resp);
                            JSONObject champ_lex = jsonObj.getJSONObject("champ");
                            for(int i=0;i<1;i++) {
                                JSONObject Mot = jsonObj.getJSONObject("mot"+i);
                                String nom_mot = Mot.getString("mot");
                                System.out.println(nom_mot);
                            }
                            int idChamp = champ_lex.getInt("id_champ");
                            String nomChamp = champ_lex.getString("nom_champ");
                            nom_champ.setText(nomChamp);



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
                    case DragEvent.ACTION_DROP: {
                        //Executed when user drops the data
                        return (true);
                    }
                    case DragEvent.ACTION_DRAG_ENDED: {
                        return (true);
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


}
