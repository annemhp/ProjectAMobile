package com.mymla.development.hsb;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mymla.development.hsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import Adapters.DividerItemDecoration;
import Adapters.StatusAdapter;
import Models.Status;
import RestService.IMyMLAService;
import RestService.MyMLAServiceApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusMasterActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsernameId;
    private String mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_master);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reports_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        IMyMLAService apiService =
                MyMLAServiceApiClient.getClient().create(IMyMLAService.class);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsernameId = mFirebaseUser.getEmail();
        mUsernameId = "\""+mUsernameId+"\"";





        Call<LinkedHashMap<String,Status>> call = apiService.getAllStatus(mUsernameId);
        call.enqueue(new Callback<LinkedHashMap<String,Status>>() {
            @Override
            public void onResponse(Call<LinkedHashMap<String,Status>> call, Response<LinkedHashMap<String,Status>> response) {
                int statusCode = response.code();
                LinkedHashMap<String,Status> status = response.body()!=null ?response.body() : new LinkedHashMap<String, Status>() ;
                List<Status> statusvalues = new ArrayList<Status>(status.values());
                List<String> keys = new ArrayList<String>(status.keySet());
//                for(int i=0;i<statusvalues.size();i++)
//                {
//                    statusvalues.get(i).complaintNo = statusvalues.get(i).getComplaintNo();
//                }

                if(status.size()==0 ){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(recyclerView.getRootView().getContext());
                    builder1.setMessage("Sorry, Currently there are no reports to show. Please come back later.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Back",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }else{
                    recyclerView.setAdapter(new StatusAdapter(statusvalues, R.layout.list_item, getApplicationContext()));
                }

            }

            @Override
            public void onFailure(Call<LinkedHashMap<String,Status>> call, Throwable t) {

                Log.e("Error in Get", t.toString());
            }
        });
    }
}
