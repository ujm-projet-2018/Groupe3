package com.example.gentile.lexical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button butEleve;
    Button butProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butProf = (Button) findViewById(R.id.butProf);
        butEleve = (Button) findViewById(R.id.butElev);

        butProf.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(MainActivity.this, ConnectionProf.class);
                startActivity(appel);
            }
        });

        butEleve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent appel = new Intent(MainActivity.this, ConnectionEleve.class);
                startActivity(appel);
            }
        });

    }
}
