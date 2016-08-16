package com.example.development.mymla;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView img1 = (ImageView) findViewById(R.id.imageView9);
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.imageView10);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),StatusMasterActivity.class);
                startActivity(i);
                // your code here
            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.imageView11);
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.imageView6);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
            }
        });


        ImageView img5 = (ImageView) findViewById(R.id.imageView8);
        img5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
