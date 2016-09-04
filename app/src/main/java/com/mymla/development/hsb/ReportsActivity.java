package com.mymla.development.hsb;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import Models.ReportProblem;
import Models.Utility;
import RestService.IMyMLAService;
import RestService.MyMLAServiceApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextMobile;

    private EditText editTextProblem;
    private EditText editTextSubject;
    private Button buttonSubmit;
    private EditText editTextPlace;
    private EditText editTextDepartment;
    private Button buttonImage;
    private TextView imageName;
    private Button buttonAttachmentImage;
    private ImageView image;
    private String base64Image;
    private TextInputLayout nameTextLayout, mobileTextLayout, deptTextLayout, placeTextLayout, subjectTextLayout, problemTextLayout;
    private FrameLayout ff_report;

    private ListView listViewDepartment;

    int a = 0;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    private String imageDetails = ""; // to get the bitmap image name from the methods and pass it to the object


    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsernameId;
    private String mUsername;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private StorageReference imagesRef;

    private Uri downloadUrl;

    private Uri imageAttachmentUri;
    private String imageRef;
    private LinearLayout llReports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        llReports = (LinearLayout) findViewById(R.id.ll_report);

        // Create a storage reference from our app
        storageRef = storage.getReferenceFromUrl("gs://projecta1-5156a.appspot.com");

        // Create a child reference
        // imagesRef now points to "images"

        downloadUrl = null;
        imageAttachmentUri = null;
        imageRef = null;


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextProblem = (EditText) findViewById(R.id.editTextProblem);
        editTextPlace = (EditText) findViewById(R.id.editTextPlace);
        editTextDepartment = (EditText) findViewById(R.id.editTextDepartment);
        imageName = (TextView) findViewById(R.id.imageName);

        ff_report = (FrameLayout)findViewById(R.id.frame_report);

        buttonImage = (Button) findViewById(R.id.buttonImage);
        buttonAttachmentImage = (Button) findViewById(R.id.btnImageAttachment);


        nameTextLayout = (TextInputLayout) findViewById(R.id.name_layout);
        mobileTextLayout = (TextInputLayout) findViewById(R.id.mobile_layout);
        deptTextLayout = (TextInputLayout) findViewById(R.id.dept_layout);
        placeTextLayout = (TextInputLayout) findViewById(R.id.place_layout);
        subjectTextLayout = (TextInputLayout) findViewById(R.id.subject_layout);
        problemTextLayout = (TextInputLayout) findViewById(R.id.problem_layout);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsernameId = mFirebaseUser.getEmail();
        mUsername = mFirebaseUser.getDisplayName();

        editTextName.setText(mUsername);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        final ArrayList<String> dept = new ArrayList<String>();
        dept.addAll(Departments.departmants);


        editTextDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.department_list_view);
                dialog.setTitle(R.string.lvDepartment);
                listViewDepartment = (ListView) dialog.findViewById(R.id.listViewDepartment);
                dialog.show();

                ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, dept);
                listViewDepartment.setAdapter(dataAdapterDept);
                listViewDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String itemValue = (String) listViewDepartment
                                .getItemAtPosition(position);

                        editTextDepartment.setText(itemValue);
                        dialog.cancel();
                    }

                });

            }
        });


        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateReport()) {
                    Log.i("Transaction", "Fishing ");
                    //Getting values to store
                    String name = editTextName.getText().toString();
                /*if (name.length() == 0)
                    editTextName.setError("Name is Required!");*/

                    String mobile = editTextMobile.getText().toString().trim();
                /*if (number.length() == 0)
                    editTextNumber.setError("Contact Number is Required!");*/

                    String problem = editTextProblem.getText().toString();
                /*if (problem.length() == 0)
                    editTextProblem.setError("Problem you are facing is Required!");*/

                    String place = editTextPlace.getText().toString();
                    String department = editTextDepartment.getText().toString();
                    String subject = editTextSubject.getText().toString();

                    Date today = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String date = sdf.format(today);
                    String status = "open";


                    if (name.length() == 0 || mobile.length() == 0 || problem.length() == 0) {
                        Toast.makeText(v.getContext(), "Fields Cannot be empty", Toast.LENGTH_LONG).show();
                        return;
                    } else {

                        final ReportProblem newIssue = new ReportProblem(mUsernameId, name, mobile, place, department, subject, problem, date, status, imageRef);

                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Report Status")
                                .setMessage("Your Issue has been submitted!! " +
                                        "We will respond with in 15 days. " +
                                        "Sathish Kumar .V")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        editTextName.setText("");
                                        editTextMobile.setText("");
                                        editTextSubject.setText("");
                                        editTextProblem.setText("");
                                        editTextPlace.setText("");
                                        editTextDepartment.setText("");
                                        ff_report.setBackgroundColor(Color.TRANSPARENT);
                                        buttonAttachmentImage.setVisibility(View.GONE);
                                        imageName.setVisibility(View.GONE);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        DatabaseReference postRef = mFirebaseDatabaseReference.child("sequence_num");


                        final DatabaseReference issuesRef = mFirebaseDatabaseReference.child("issues");


                        postRef.runTransaction(new Transaction.Handler() {


                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Long curr = mutableData.getValue() != null ? (Long) mutableData.getValue() : 0;
                                curr = curr + 1;
                                mutableData.setValue(curr);
                                return Transaction.success(mutableData);

                            }


                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b,
                                                   DataSnapshot dataSnapshot) {

                                Log.i("Transaction", dataSnapshot.getValue().toString());

                                newIssue.setComplaintNo((Long) dataSnapshot.getValue());

                                submitReport(newIssue);
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean validateReport() {

        boolean isValid = true;
        if (editTextName.getText().toString().trim().isEmpty()) {
            nameTextLayout.setError("Please Enter Name.");
            editTextName.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            nameTextLayout.setErrorEnabled(false);
            editTextName.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));

        }

        if (editTextMobile.getText().toString().trim().isEmpty()) {
            mobileTextLayout.setError("Please Enter Mobile No.");
            editTextMobile.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            mobileTextLayout.setErrorEnabled(false);
            editTextMobile.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));

        }

        if (editTextDepartment.getText().toString().trim().isEmpty()) {
            deptTextLayout.setError("Please Enter Department.");
            editTextDepartment.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            deptTextLayout.setErrorEnabled(false);
            editTextDepartment.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));
        }

        if (editTextPlace.getText().toString().trim().isEmpty()) {
            placeTextLayout.setError("Please Enter Place.");
            editTextPlace.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            placeTextLayout.setErrorEnabled(false);
            editTextPlace.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));
        }

        if (editTextSubject.getText().toString().trim().isEmpty()) {
            subjectTextLayout.setError("Please Enter Subject.");
            editTextSubject.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            subjectTextLayout.setErrorEnabled(false);
            editTextSubject.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));
        }

        if (editTextProblem.getText().toString().trim().isEmpty()) {
            problemTextLayout.setError("Please Enter Problem.");
            editTextProblem.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_round_valid_info));
            isValid = false;
        } else {
            problemTextLayout.setErrorEnabled(false);
            editTextProblem.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));
        }
        if(!isValid){
            ff_report.setBackgroundColor(Color.parseColor("#3f3f3f"));
        }

        return isValid;
    }

    private void submitReport(ReportProblem newIssue) {
        IMyMLAService apiService =
                MyMLAServiceApiClient.getClient().create(IMyMLAService.class);

        Call<ReportProblem> call = apiService.createReport(newIssue);
        call.enqueue(new Callback<ReportProblem>()

                     {
                         @Override
                         public void onResponse(Call<ReportProblem> call, Response<ReportProblem> response) {
                             a = response.code();

                             ReportProblem issue = response.body();
                         }

                         @Override
                         public void onFailure(Call<ReportProblem> call, Throwable t) {
                             // Log error here since request failed
                             Log.e("New Issue Report", t.toString());
                         }
                     }

        );

        if (a != 200) {
            Log.e("New Issue Report", "Some Problem");
        }

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getApplicationContext());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                imageDetails = onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                imageDetails = onCaptureImageResult(data);
        }
    }

    private String onCaptureImageResult(Intent data) {
        String s;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showImageName(System.currentTimeMillis() + ".jpg");


        s = storeImageToFirebase(data.getData());
        return s;
    }

    @SuppressWarnings("deprecation")
    private String onSelectFromGalleryResult(Intent data) {
        String s;

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //List<String> x = data.getData().getPathSegments();
        //String y = data.getData().getLastPathSegment();
        // showImageName(data.getData().getLastPathSegment().toString());
        showImageName("Upload in Progress");
        s = storeImageToFirebase(data.getData());
        return s;

    }

    private void showImageName(String name) {
        imageName.setVisibility(View.VISIBLE);
        buttonAttachmentImage.setVisibility(View.GONE);
        buttonSubmit.setVisibility(View.GONE);
        imageName.setText(name);
    }

    private String storeImageToFirebase(Uri path) {

        imageRef = "Images/" + mUsernameId + "/" + UUID.randomUUID().toString();
        imagesRef = storageRef.child(imageRef);


        imageAttachmentUri = path;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6; // shrink it down otherwise we will use stupid amounts of memory
        Bitmap bitmap = null;

        //Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(path), options);
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(path), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);


        UploadTask uploadTask = imagesRef.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                imageName.setVisibility(View.GONE);
                buttonAttachmentImage.setVisibility(View.VISIBLE);
                buttonSubmit.setVisibility(View.VISIBLE);
                previewAttachment();
            }
        });

        return base64Image;
    }


    private void previewAttachment() {
        buttonAttachmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory = LayoutInflater.from(view.getContext());
                final View imgView = factory.inflate(R.layout.image_attachment, null);

                image = (ImageView) imgView.findViewById(R.id.imagePreview);
                image.setImageURI(imageAttachmentUri);

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
