package com.apurva.assignment.justlightapp.Customer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apurva.assignment.justlightapp.R;

/**
 * Created by Apurva on 11/21/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
