package com.mymla.development.hsb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Adapters.StatusDetailAdapter;
import Models.Status;
import Models.StatusDetail;

public class StatusDetailActivity extends AppCompatActivity {

    public static final long ONE_MEGABYTE = 480 * 800;

    private Intent intent;
    private String problem;
    private String department;
    private String place, name;
    private String imageRef;
    private ArrayList<StatusDetail> statusDetails;
    private Status status;
    private Button buttonAttachmentImage;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private TextView problemText, departmentText, placeText, nameText;

    private static byte[] imgBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_detail);

        problemText = (TextView) findViewById(R.id.problemDetail);
        departmentText = (TextView) findViewById(R.id.deptDetail);
        placeText = (TextView) findViewById(R.id.placeDetails);
        nameText = (TextView) findViewById(R.id.nameDetail);

        //Getting the intent and the resolving each data item
        intent = getIntent();
        statusDetails = (ArrayList<StatusDetail>) intent.getSerializableExtra("Updates");
        department = intent.getStringExtra("Department");
        problem = intent.getStringExtra("Problem");
        place = intent.getStringExtra("Place");
        name = intent.getStringExtra("Name");

        imageRef = intent.getStringExtra("ImageRef");
        buttonAttachmentImage = (Button) findViewById(R.id.btnImageAttachment);
        buttonAttachmentImage.setOnClickListener(buttonClickListener);
        buttonAttachmentImage.setVisibility(View.GONE);
        if (!imageRef.isEmpty()) {
            getImageFromUrl();
            buttonAttachmentImage.setVisibility(View.VISIBLE);
        }

        if (statusDetails == null || statusDetails.size() == 0) {
            statusDetails = new ArrayList<StatusDetail>();
            StatusDetail statusDetail = new StatusDetail("We will soon look into your problem. Satish Kumar","Open",null);
            statusDetails.add(statusDetail);
        }

        problemText.setText(problem);
        departmentText.setText(department);
        placeText.setText(place);
        nameText.setText(name);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reports2_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StatusDetailAdapter(statusDetails,
                R.layout.list_item_status_child, getApplicationContext()));

    }


    private void getImageFromUrl() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://projecta1-5156a.appspot.com");
        StorageReference previewImg = storageRef.child(imageRef);
        previewImg.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                imgBytes = bytes;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnImageAttachment:
                    previewAttachment(v);
                    break;
            }
        }
    };


    private void previewAttachment(View view) {
        LayoutInflater factory = LayoutInflater.from(view.getContext());
        final View imgView = factory.inflate(R.layout.image_attachment, null);

        ImageView image = (ImageView) imgView.findViewById(R.id.imagePreview);
        if(imgBytes != null && imgBytes.length > 0) {
            image.setImageBitmap(
                    BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length));
        }

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
}
