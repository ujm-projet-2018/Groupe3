package com.example.gentile.lexical;

import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatEleve extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_eleve);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.Table);
        for (int i = 0; i < 25; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            if(i%2==0)
                tableRow.setBackgroundColor(Color.GREEN);
            else
                tableRow.setBackgroundColor(Color.GRAY);

            TextView text1 = new TextView(this);
            TextView text2 = new TextView(this);
            TextView text3 = new TextView(this);
            TextView text4 = new TextView(this);
            TextView text5 = new TextView(this);
            TextView text6 = new TextView(this);
            TextView text7 = new TextView(this);
            TextView text8 = new TextView(this);
            text1.setText("Test" + i);
            text1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text2.setText("Test" + i);
            text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text3.setText("Test" + i);
            text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text4.setText("Test" + i);
            text4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text5.setText("Test" + i);
            text5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text6.setText("Test" + i);
            text6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text7.setText("Test" + i);
            text7.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text8.setText("Test" + i);
            text8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation button
          /*  final Button button = new Button(this);
            button.setText("Delete");
            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final TableRow parent = (TableRow) v.getParent();
                    tableLayout.removeView(parent);
                }
            });*/
            tableRow.addView(text1);
            tableRow.addView(text2);
            tableRow.addView(text3);
            tableRow.addView(text4);
            tableRow.addView(text5);
            tableRow.addView(text6);
            tableRow.addView(text7);
            tableRow.addView(text8);
         //   tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }
}
