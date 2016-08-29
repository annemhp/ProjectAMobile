package com.mymla.development.hsb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mymla.development.hsb.R;

import java.util.ArrayList;

import Adapters.DividerItemDecoration;
import Adapters.StatusDetailAdapter;
import Models.Status;
import Models.StatusDetail;

public class StatusDetailActivity extends AppCompatActivity {
    Intent intent;
    String problem;
    String department;
    String place;
    String imageRef;
    ArrayList<StatusDetail> statusDetails;
    Status status;
    Button buttonAttachmentImage;
    private StorageReference storageRef;
    private FirebaseStorage storage ;
    public static final String TAG = MainActivity.class.getSimpleName();
    private  static   byte[] imgBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_detail);

        intent = getIntent();
        statusDetails=(ArrayList<StatusDetail>) intent.getSerializableExtra("Updates");
        department = intent.getStringExtra("Department");
        problem = intent.getStringExtra("Problem");
        place = intent.getStringExtra("Place");

        imageRef = intent.getStringExtra("ImageRef");
        buttonAttachmentImage =(Button) findViewById(R.id.btnImageAttachment);
        if (!imageRef.isEmpty()){
            Log.d("Image ref", imageRef);
            storage = FirebaseStorage.getInstance();
            storageRef= storage.getReferenceFromUrl("gs://projecta1-5156a.appspot.com");

            Log.d(TAG, "onCreate: "+storageRef.toString());

            StorageReference previewImg = storageRef.child(imageRef);


            final long ONE_MEGABYTE = 480 * 800;
            previewImg.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Data for "images/island.jpg" is returns, use this as needed
                    imgBytes =bytes;
                    previewAttachment();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            buttonAttachmentImage.setVisibility(View.VISIBLE);
        }else {
            buttonAttachmentImage.setVisibility(View.GONE);
        }

        TextView updateHeader,problemText, departmentText,placeText;

         updateHeader = (TextView) findViewById(R.id.updateHeading);

        if(statusDetails!=null && statusDetails.size()>0){
            updateHeader.setText("Updates");
        }else {
            updateHeader.setText("We will soon look into your problem.");
        }

        problemText =(TextView) findViewById(R.id.problemDetail);
        problemText.setText(problem);

        departmentText = (TextView) findViewById(R.id.deptDetail);
        departmentText.setText(department);

        placeText = (TextView) findViewById(R.id.placeDetails);
        placeText.setText(place);



        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reports2_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StatusDetailAdapter(statusDetails,
                R.layout.list_item_status_child, getApplicationContext()));
        previewAttachment();
    }


    private void previewAttachment(){
        buttonAttachmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory = LayoutInflater.from(view.getContext());
                final View imgView = factory.inflate(R.layout.image_attachment, null);

                ImageView image = (ImageView) imgView.findViewById(R.id.imagePreview);
                image.setImageBitmap(
                        BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length));

                new AlertDialog.Builder(view.getContext())
                        .setTitle("Attached Image")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setView(imgView)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }
}
