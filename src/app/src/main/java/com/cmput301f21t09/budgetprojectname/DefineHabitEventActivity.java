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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * Activity that makes the user to add/edit a habit event
 */
public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView habitEventName;
    private EditText location;
    private EditText comment;
    private ImageView imageView;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";
    private boolean isNewHabitEvent;
    private final HabitEventController habitEventController = new HabitEventController();
    private String habitID;
    private String habitEventID;

    private ActivityResultLauncher<Intent> GalleryResultLauncher;
    private ActivityResultLauncher<Intent> CameraResultLauncher;
    String mPhotoPath;
    Bitmap image;
    String imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        habitID = intent.getStringExtra("HABIT_ID");
        System.out.println("*****habitID " + habitID);
        System.out.println("****HE id" + habitEventID);
        isNewHabitEvent = (habitEventID == null);
        String modeStr;

        if (isNewHabitEvent) {
            modeStr = getString(R.string.createHabitEventMode);
        } else {
            modeStr = getString(R.string.editHabitEventMode);
            // sets existing habitEvent fields
            setHabitEventFields(habitEventID);
        }

        // update title according to mode selected: "add" or "edit"
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tbTitle = findViewById(R.id.toolbar_title);
        tbTitle.setText(modeStr);

        habitEventName = findViewById(R.id.habitName);
        location = findViewById(R.id.location);
        comment = findViewById(R.id.comment);
        imageView = findViewById(R.id.image);

        ImageButton back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            finish();
        });

        // Get Image from Launcher
        GalleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            image = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(image);
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

        // Add, Replace or Remove Image by Clicking ImageView area
        // TODO: Detect orientation of photo, 1:1 Crop function
        imageView.setOnClickListener(v -> new MaterialAlertDialogBuilder(this, R.style.Dialog)
                .setTitle("Select Image From")
                .setItems((imageData == null && image == null) ? new String[]{"Gallery", "Take a Photo"} : new String[]{"Gallery", "Take a Photo", "Delete"}, (dialog, which) -> {
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
                    } else if (which == 2) {
                        imageView.setImageDrawable(null);
                        imageData = null;
                        image = null;
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

    /**
     * Encode Image to base64 string
     * @return encoded image string
     */
    private String encodeImage() {
        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imageData = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        }

        return imageData;
    }

    /**
     * Run camera
     */
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
        habitEventRef.update("description", modifiedHabitEvent.getComment(),
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
                        // TODO: Move to HabitEventController once async request handling is solved

                        // set fields in view
                        location.setText(document.getString("location"));
                        comment.setText(document.getString("comment"));
                        imageData = document.getString("image");
                        if (imageData != null) {
                            byte[] byteArray = Base64.decode(imageData, Base64.DEFAULT);
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_define_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Brings the user back to the previous activity if the back button on the app bar is pressed
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_commit_changes:
                String locationStr = location.getText().toString();
                String commentStr = comment.getText().toString();
                // error checking/handling for adding optional comment of up to 30 chars
                if (commentStr.length() > 20) {
                    comment.setError(getString(R.string.errorHabitEventComment));
                    comment.requestFocus();
                    Toast.makeText(getApplicationContext(), "ERROR: could not save habit event",
                            Toast.LENGTH_SHORT).show();
                } else {

                    String descriptionStr = comment.getText().toString();

                    HabitEventModel habitEvent = new HabitEventModel(null, locationStr, new Date(),
                            descriptionStr, encodeImage(), habitID);

                    if (isNewHabitEvent) {
                        habitEventController.createHabitEvent(habitEvent, new HabitEventController.HabitEventIDCallback() {
                            @Override
                            public void onCallback(String habitEventID) {
                                // TODO: figure out what to add here
                                System.out.println("habitevent id " + habitEventID);
                                // return back to main habit list
                                finish();

                            }
                        });
                    } else {
                        habitEventController.updateHabitEvent(habitEventID, habitEvent);
                        // return back to habit detail page
                        Intent i = new Intent(getApplicationContext(), ViewHabitActivity.class);
                        i.putExtra("HABIT_ID", habitID);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}