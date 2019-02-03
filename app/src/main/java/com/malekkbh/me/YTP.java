package com.malekkbh.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class YTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ytp);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        return true ;
    }
}
