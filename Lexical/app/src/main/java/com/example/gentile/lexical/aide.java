package com.example.gentile.lexical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class aide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);
        Button rejouer = (Button) findViewById(R.id.recommencer);
        rejouer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(ConnectionEleve.niveau==1) {
                    Intent appel = new Intent(aide.this, exo1.class);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==2) {
                    Intent appel = new Intent(aide.this, exo2.class);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==3) {
                    Intent appel = new Intent(aide.this, exo3.class);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==4) {
                    Intent appel = new Intent(aide.this, exo4.class);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==5) {
                    Intent appel = new Intent(aide.this, exo5.class);
                    startActivity(appel);
                }
                if(ConnectionEleve.niveau==6) {
                    Intent appel = new Intent(aide.this, exo6.class);
                    startActivity(appel);
                }
            }
        });
    }
}