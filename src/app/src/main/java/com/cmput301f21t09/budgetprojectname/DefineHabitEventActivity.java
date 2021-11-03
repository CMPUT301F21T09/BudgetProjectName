package com.cmput301f21t09.budgetprojectname;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView habitName;
    private EditText location;
    private EditText description;
    private ImageView imageView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";

    private ActivityResultLauncher<Intent> GalleryResultLauncher;
    private ActivityResultLauncher<Intent> CameraResultLauncher;
    String mPhotoPath;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        String habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        boolean isNewHabitEvent = (habitEventID == null);
        String modeStr;

        if (isNewHabitEvent) {
            modeStr = getString(R.string.createHabitEventMode);
        } else {
            modeStr = getString(R.string.editHabitEventMode);
            // sets existing habitEvent fields
            setHabitEventFields(habitEventID);
        }

        // update title according to mode selected: "add" or "edit"
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(modeStr);

        habitName = findViewById(R.id.habitName);

        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.image);

        ImageButton doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String locationStr = location.getText().toString();
                String descriptionStr = description.getText().toString();
                HabitEventModel habitEvent = new HabitEventModel(locationStr, new Date(), descriptionStr);

                if (isNewHabitEvent) {
                    storeNewHabitEvent(habitEvent);
                } else {
                    storeEditedHabitEvent(habitEventID, habitEvent);
                    setHabitEventFields(habitEventID);
                }
            }
        });

        // Get Image from Launcher
        GalleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            image = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                            imageView.setImageBitmap(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

        // Get Image from Camera
        CameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        File file = new File(mPhotoPath);
                        try {
                            image = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(image);
                    }
                });


        // Add or Replace Image by Clicking ImageView area
        // TODO: Detect orientation of photo, 1:1 Crop function
        imageView.setOnClickListener(v -> new MaterialAlertDialogBuilder(this, R.style.MyDialogTheme)
                .setTitle("Select Image From")
                .setItems(new String[]{"Gallery", "Take a Photo"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
                        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                            GalleryResultLauncher.launch(galleryIntent);
                        }
                    } else if (which == 1) {
                        // Check if Corresponding Permissions are granted
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                        } else {
                            runCameraIntent();
                        }
                    }
                }).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            runCameraIntent();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "This action requires\ncamera permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Grant Permission", v1 -> {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                        }).show();
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "This action requires\ncamera permission.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Go to Settings", v1 -> {
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri));
                        }).show();
            }
        }
    }

    private void runCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File imageFile = null;
            try {
                // Try to Create Temporary File
                imageFile = File.createTempFile("temp", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            mPhotoPath = imageFile.getAbsolutePath();

            Uri photoURI = FileProvider.getUriForFile(this, "com.cmput301f21t09.budgetprojectname.provider", imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            CameraResultLauncher.launch(cameraIntent);
        }
    }

    /**
     * Stores newly created habitEvent document in the habit_events collection
     *
     * @param habitEvent habitEvent to be added
     */
    private void storeNewHabitEvent(HabitEventModel habitEvent) {
        System.out.println("store new habit");
        db.collection("habit_events")
                .add(habitEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String docID = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + docID);
                        System.out.println(docID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Stores modified habitEvent document in the habit_events collection
     *
     * @param habitEventID       ID of habitEvent to be updated
     * @param modifiedHabitEvent habitEvent to be updated
     */
    private void storeEditedHabitEvent(String habitEventID, HabitEventModel modifiedHabitEvent) {
        DocumentReference habitEventRef = db.collection("habit_events")
                .document(habitEventID);
        habitEventRef.update("description", modifiedHabitEvent.getDescription(),
                "location", modifiedHabitEvent.getLocation(),
                "image", modifiedHabitEvent.getImage())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    /**
     * Sets the fields with existing values from Firestore
     *
     * @param habitEventID ID of habitEvent to be retrieved
     */
    private void setHabitEventFields(String habitEventID) {
        DocumentReference docRef = db.collection("habit_events").document(habitEventID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        // TODO: Possible refactoring where document retrieval is
                        //  separate from setting fields

                        // set fields in view
                        location.setText(document.getString("location"));
                        description.setText(document.getString("description"));
                        // TODO: set image

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}