package com.example.development.mymla;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private ListView listViewDepartment;
    //private ListView listViewTaluka;
    int a=0;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    private String imageDetails = ""; // to get the bitmap image name from the methods and pass it to the object


    //private FirebaseApp app = FirebaseApp.getInstance();
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextProblem = (EditText) findViewById(R.id.editTextProblem);
        editTextPlace = (EditText) findViewById(R.id.editTextPlace);
        editTextDepartment = (EditText) findViewById(R.id.editTextDepartment);
        //imageName = (TextView) findViewById(R.id.imageName);

        //buttonImage = (Button) findViewById(R.id.buttonImage);

        //Creating firebase object
        //Firebase ref = new Firebase(Config.FIREBASE_URL);
        //FirebaseApp app = FirebaseApp.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance(app);
        //FirebaseAuth auth = FirebaseAuth.getInstance(app);
        //Firebase storage = FirebaseStorage.getInstance(app);
        //DatabaseReference databaseRef = database.getReference("issues");


        /*final ArrayList<String> tal = new ArrayList<String>();
        tal.add("AmberPet");
        tal.add("Golconda");
        tal.add("Musheerabad");
        tal.add("Secundabad");
        tal.add("Shaikpet");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tal);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editTextTaluka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.taluka_list_view);
                dialog.setTitle(R.string.lvTaluka);
                listViewTaluka = (ListView) dialog.findViewById(R.id.listViewTaluka);
                dialog.show();

                ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, tal);
                listViewTaluka.setAdapter(dataAdapterDept);
                listViewTaluka.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String itemValue = (String) listViewTaluka
                                .getItemAtPosition(position);

                        editTextTaluka.setText(itemValue);
                        dialog.cancel();
                    }

                });

            }
        });*/

        final ArrayList<String> dept = new ArrayList<String>();
        dept.add("Electricity");
        dept.add("Water");
        dept.add("Municipality");
        dept.add("Fire");
        dept.add("Emergency");

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


       /* buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });*/

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String date = sdf.format(today);

                String status = "open";


                if (name.length() == 0 || mobile.length() == 0 || problem.length() == 0) {
                    Toast.makeText(v.getContext(), "Fields Cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    ReportProblem newIssue = new ReportProblem("User1",name,mobile,place,department,subject,problem,today,status);
                    IMyMLAService apiService =
                            MyMLAServiceApiClient.getClient().create(IMyMLAService.class);

                    Call<ReportProblem> call = apiService.createReport(newIssue);
                    call.enqueue(new Callback<ReportProblem>() {
                        @Override
                        public void onResponse(Call<ReportProblem> call, Response<ReportProblem> response) {
                            int statusCode = response.code();
                            a=statusCode;
                            ReportProblem issue = response.body();
                        }

                        @Override
                        public void onFailure(Call<ReportProblem> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("New Issue Report", t.toString());
                        }
                    });


                    if(a==200)
                    {
                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Report Status")
                                .setMessage("Your Issue has been submitted!!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        editTextName.setText("");
                                        editTextMobile.setText("");
                                        editTextSubject.setText("");
                                        editTextProblem.setText("");
                                        editTextPlace.setText("");
                                        //imageName.setText("");
                                        // imageName.setVisibility(View.GONE);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                }
            }
        });
    }


    /*private void previewStoredFirebaseImage() {
        ref.child("issues").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String base64Image = (String) snapshot.getValue();
                byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );
                System.out.println("Downloaded image with length: " + imageAsBytes.length);
            }

            @Override
            public void onCancelled(FirebaseError error) {}
        });
    }*/



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getApplicationContext());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                imageDetails=onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                imageDetails=onCaptureImageResult(data);
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
        s= storeImageToFirebase(data.getData());
        return  s;
    }

    @SuppressWarnings("deprecation")
    private String onSelectFromGalleryResult(Intent data) {
        String s;

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //List<String> x = data.getData().getPathSegments();
        //String y = data.getData().getLastPathSegment();
        showImageName(data.getData().getLastPathSegment().toString());
        s= storeImageToFirebase(data.getData());
        return s;

    }

    private void showImageName(String name)
    {
        imageName.setVisibility(View.VISIBLE);
        imageName.setText(name);
    }

    private String storeImageToFirebase(Uri path) {
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
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        // we finally have our base64 string version of the image, save it.
        //firebase.child("pic").setValue(base64Image);
        //System.out.println("Stored image with length: " + bytes.length);

        return  base64Image;
    }

}
