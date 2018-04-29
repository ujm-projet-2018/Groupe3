package com.example.gentile.lexical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainProf extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prof);
        Button addEleve = (Button) findViewById(R.id.AjoutEleve);
        Button addMot = (Button) findViewById(R.id.AjoutMot);
        Button resultEleve = (Button) findViewById(R.id.StatEleve);

        addEleve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(MainProf.this, AjoutEleve.class);
                startActivity(appel);
            }
        });

        addMot.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(MainProf.this, AjoutMot.class);
                startActivity(appel);
            }
        });


        resultEleve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(MainProf.this, StatEleve.class);
                startActivity(appel);
            }
        });

    }
}
