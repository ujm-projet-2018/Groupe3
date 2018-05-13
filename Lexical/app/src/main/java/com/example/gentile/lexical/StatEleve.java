package com.example.gentile.lexical;

import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatEleve extends AppCompatActivity {

    String urlProgres = "http://lexical.hopto.org/lexical/res_prof.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stat_eleve);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(urlProgres)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StatEleve.this,
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
                            JSONObject jsonObj = new JSONObject(resp);
                            int nombre = jsonObj.getInt("nb_eleves");
                            TableLayout tableLayout = (TableLayout) findViewById(R.id.Table);
                            for (int i = 0; i < nombre; i++) {

                                TableRow tableRow = new TableRow(StatEleve.this);
                                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                if (i % 2 == 0)
                                    tableRow.setBackgroundColor(Color.GREEN);
                                else
                                    tableRow.setBackgroundColor(Color.GRAY);

                                TextView nom = new TextView(StatEleve.this);
                                TextView prenom = new TextView(StatEleve.this);
                                TextView niv1 = new TextView(StatEleve.this);
                                TextView niv2 = new TextView(StatEleve.this);
                                TextView niv3 = new TextView(StatEleve.this);
                                TextView niv4 = new TextView(StatEleve.this);
                                TextView niv5 = new TextView(StatEleve.this);
                                TextView niv6 = new TextView(StatEleve.this);

                                int nivEnCours = jsonObj.getInt(i + "niveau en cours : ");

                                nom.setText(jsonObj.getString("nom" + i));
                                nom.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                prenom.setText(jsonObj.getString("prenom" + i));
                                prenom.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv1.setText(jsonObj.getString(i + "essai1"));
                                niv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv2.setText(jsonObj.getString(i + "essai2"));
                                niv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv3.setText(jsonObj.getString(i + "essai3"));
                                niv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv4.setText(jsonObj.getString(i + "essai4"));
                                niv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv5.setText(jsonObj.getString(i + "essai5"));
                                niv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                niv6.setText(jsonObj.getString(i + "essai6"));
                                niv6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                switch(nivEnCours){
                                    case 1 : niv1.setHighlightColor(Color.BLUE); break;
                                    case 2 : niv2.setHighlightColor(Color.BLUE); break;
                                    case 3 : niv3.setHighlightColor(Color.BLUE); break;
                                    case 4 : niv4.setHighlightColor(Color.BLUE); break;
                                    case 5 : niv5.setHighlightColor(Color.BLUE); break;
                                    case 6 : niv6.setHighlightColor(Color.BLUE); break;
                                }

                                tableRow.addView(nom);
                                tableRow.addView(prenom);
                                tableRow.addView(niv1);
                                tableRow.addView(niv2);
                                tableRow.addView(niv3);
                                tableRow.addView(niv4);
                                tableRow.addView(niv5);
                                tableRow.addView(niv6);
                                tableLayout.addView(tableRow);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}