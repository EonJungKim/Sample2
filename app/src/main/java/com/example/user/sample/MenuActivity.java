package com.example.user.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2017-11-20.
 */

public class MenuActivity extends AppCompatActivity {

    Button btnCity, btnProgram, btnMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnCity = (Button) findViewById(R.id.btnCity);
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                myIntent.putExtra("REQUEST_CODE", "city");
                startActivity(myIntent);
            }
        });

        btnProgram = (Button) findViewById(R.id.btnProgram);
        btnProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                myIntent.putExtra("REQUEST_CODE", "program");
                startActivity(myIntent);
            }
        });

        btnMargin = (Button) findViewById(R.id.btnMargin);
        btnMargin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MarginActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
