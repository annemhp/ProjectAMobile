package com.example.development.mymla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import Adapters.DividerItemDecoration;
import Adapters.StatusAdapter;
import Adapters.StatusDetailAdapter;
import Models.Status;
import Models.StatusDetail;
import RestService.IMyMLAService;
import RestService.MyMLAServiceApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDetailActivity extends AppCompatActivity {
    Intent intent;
    String problem;
    String department;
    ArrayList<StatusDetail> statusDetails;
    Status status;
       public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_detail);

        intent = getIntent();
        statusDetails=(ArrayList<StatusDetail>) intent.getSerializableExtra("Updates");
        department = intent.getStringExtra("Department");
        problem = intent.getStringExtra("Problem");

        TextView updateHeader,problemText;

         updateHeader = (TextView) findViewById(R.id.updateHeading);

        if(statusDetails!=null && statusDetails.size()>0){
            updateHeader.setText("Updates");
        }else {
            updateHeader.setText("We will soon look into your problem.");
        }

        problemText =(TextView) findViewById(R.id.problemDetail);
        problemText.setText(problem);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reports2_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StatusDetailAdapter(statusDetails,R.layout.list_item_status_child, getApplicationContext()));
    }
}
