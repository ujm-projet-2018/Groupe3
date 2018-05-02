package com.example.gentile.lexical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gameOverExo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_exo1);
        Button addEleve = (Button) findViewById(R.id.recommencer);
        addEleve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(gameOverExo1.this, exo1.class);
                startActivity(appel);
            }
        });
    }
}
