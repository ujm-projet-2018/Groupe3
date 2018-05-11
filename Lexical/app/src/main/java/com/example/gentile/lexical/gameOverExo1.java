package com.example.gentile.lexical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gameOverExo1 extends AppCompatActivity {
    String nom;
    String prenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nom = intent.getStringExtra("nom");
        prenom = intent.getStringExtra("prenom");
        setContentView(R.layout.activity_game_over_exo1);
        Button addEleve = (Button) findViewById(R.id.recommencer);
        addEleve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(ConnectionEleve.niveau==1) {
                    Intent appel = new Intent(gameOverExo1.this, exo1.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==2) {
                    Intent appel = new Intent(gameOverExo1.this, exo2.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==3) {
                    Intent appel = new Intent(gameOverExo1.this, exo3.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==4) {
                    Intent appel = new Intent(gameOverExo1.this, exo4.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==5) {
                    Intent appel = new Intent(gameOverExo1.this, exo5.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==6) {
                    Intent appel = new Intent(gameOverExo1.this, exo6.class);
                    appel.putExtra("prenom", prenom);
                    appel.putExtra("nom", nom);
                    startActivity(appel);
                }
            }
        });
    }
}
