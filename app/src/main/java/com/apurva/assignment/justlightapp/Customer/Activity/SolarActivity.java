package com.apurva.assignment.justlightapp.Customer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apurva.assignment.justlightapp.R;

/**
 * Created by priya on 12/3/2017.
 */

public class SolarActivity extends Activity {
    Button solar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar);
        solar = (Button) findViewById(R.id.solar);
        solar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(SolarActivity.this,SolarPatnersActivity.class);
                startActivity(i);
            }
        });
    }
}

