package com.fydp.myoralvillage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Level1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
    }

    public void goToLevel1QA(View v) {
        Intent intent = new Intent(this, Level1ActivityGameQA.class);
        startActivity(intent);
    }

    public void goToLevel1Tracing(View v) {
        Intent intent = new Intent(this, Level1ActivityGameTracing.class);
        startActivity(intent);
    }

    public void goToLevel1DualCoding(View v) {
        Intent intent = new Intent(this, Level1ActivityGameDualCoding.class);
        startActivity(intent);
    }

}
