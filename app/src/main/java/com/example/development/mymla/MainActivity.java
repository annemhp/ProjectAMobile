package com.example.development.mymla;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import RestService.MyMLAServiceApiClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String a ="hello";
        String b = "&quot;World&quot;";
        String c = "pore";
        String d = MyMLAServiceApiClient.BASE_URL;
        ImageView img1 = (ImageView) findViewById(R.id.imageView9);
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), ReportsActivity.class);
                    startActivity(i);
                }
                else {
                    Snackbar.make(v, "No Internet connetion.Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                // your code here
            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.imageView10);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), StatusMasterActivity.class);
                    startActivity(i);
                }
                else {
                    Snackbar.make(v, "No Internet connetion.Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                // your code here
            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.imageView11);
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
                    startActivity(i);
                }
                else {
                    Snackbar.make(v, "No Internet connetion.Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                // your code here
            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.imageView6);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), NewsActivity.class);
                    startActivity(i);
                }
                else {
                    Snackbar.make(v, "No Internet connetion.Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
