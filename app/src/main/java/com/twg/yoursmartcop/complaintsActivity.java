package com.twg.yoursmartcop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class complaintsActivity extends AppCompatActivity {
    Button btnbrowse, btnupload, btngetlocation;
    EditText txtdata ;
    TextView textLocation,textTime;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    private String[] urls = new String[]{"https://www.thehindu.com/news/cities/Delhi/uxib0e/article25281966.ece/ALTERNATES/LANDSCAPE_1200/21DESTATION","https://www.google.com/url?sa=i&url=https%3A%2F%2Fyourstory.com%2F2019%2F01%2Fvisualising-women-safety-2019-india-do-better&psig=AOvVaw3fz4-BPA8GMlbdcalZ2csB&ust=1606401633437000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKDk5cj2ne0CFQAAAAAdAAAAABAD",
    "data:image/jpeg;base64,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"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        btngetlocation = (Button)findViewById(R.id.btnGetlocation);
        txtdata = (EditText)findViewById(R.id.txtdata);
        textLocation = (TextView) findViewById(R.id.location);
        textTime = (TextView) findViewById(R.id.txttime);
        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(complaintsActivity.this);// context name as per your project name
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String time = simpleDateFormat.format(new Date());
        textTime.setText(time);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });
        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationTrack = new LocationTrack(complaintsActivity.this);


                if (locationTrack.canGetLocation()) {


                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
             //       textLocation.setText("Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude));
                    getCompleteAddressString(latitude,longitude);
                } else {

                    locationTrack.showSettingsAlert();
                }
            }
        });

    }
  ////////////////***********IMAGE UPLOAD*****//////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String TempImageName = txtdata.getText().toString().trim();
                                    String ComLocation = textLocation.getText().toString().trim();
                                    String timeUpload = textTime.getText().toString().trim();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                    @SuppressWarnings("VisibleForTests")
                                    uploadinfo imageUploadInfo = new uploadinfo(TempImageName, task.getResult().toString(),ComLocation,timeUpload);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                }
                            });
//                            String TempImageName = txtdata.getText().toString().trim();
//                            String ComLocation = textLocation.getText().toString().trim();
//                            String timeUpload = textTime.getText().toString().trim();
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                            @SuppressWarnings("VisibleForTests")
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString(),ComLocation,timeUpload);
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(complaintsActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }
    /////////////////************IMAGE UPLOAD END************.////////////

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                textLocation.setText(strAdd);
                Toast.makeText(this, strReturnedAddress.toString(), Toast.LENGTH_LONG).show();
                //Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                // Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(complaintsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

}