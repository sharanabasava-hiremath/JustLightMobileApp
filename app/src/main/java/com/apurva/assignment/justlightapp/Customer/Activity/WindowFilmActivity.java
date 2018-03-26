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

public class WindowFilmActivity extends Activity {
    Button window_film;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_film);
        window_film = (Button) findViewById(R.id.window_film);
        window_film.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(WindowFilmActivity.this,WindowFilmPatnersActivity.class);
                startActivity(i);
            }
        });
    }
}

