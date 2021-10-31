package com.cmput301f21t09.budgetprojectname;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private ImageView image;

    String mPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        int habitEventID = intent.getIntExtra("HABIT_EVENT_ID", -1);

        String modeStr;
        // if intent not have habit event ID, then create a new habit event
        if (habitEventID == -1) {
            modeStr = getString(R.string.createHabitEventMode);
        } else {
            modeStr = getString(R.string.editHabitEventMode);
        }

        // update title according to mode selected: "add" or "edit"
        //  toolbarTitle = findViewById(R.id.toolbar_title);
        //  toolbarTitle.setText(modeStr);

        // TODO: query habitID in Firestore and populate this field with corresponding habitName
        habitEventName = findViewById(R.id.habitName);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);

        ImageButton doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nameStr = habitEventName.getText().toString();
                String locationStr = location.getText().toString();
                String descriptionStr = description.getText().toString();

                HabitEventModel habitEvent = new
                        HabitEventModel(nameStr, locationStr, new Date(), descriptionStr);
                // TODO: save HabitEvent in Firestore DB
            }
        });

        //Get Image from Launcher
        ActivityResultLauncher<Intent> GalleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getBaseContext().getContentResolver().openInputStream(selectedImage));
                            image.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

        //Get Image from Camera
        ActivityResultLauncher<Intent> CameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        File file = new File(mPhotoPath);
                        Bitmap selectedImage = null;
                        try {
                            selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        image.setImageBitmap(selectedImage);
                    }
                });

        String[] actionList = new String[]{"Gallery", "Take a Photo"};

        image.setOnClickListener(v -> new MaterialAlertDialogBuilder(this, R.style.MyDialogTheme)
                .setTitle("Select Image From")
                .setItems(actionList, (dialog, which) -> {
                    if (which == 0) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
                        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                            GalleryResultLauncher.launch(galleryIntent);
                        }
                    } else if (which == 1) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                            }
                            // Continue only if the File created successfully
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(this,
                                        "com.cmput301f21t09.budgetprojectname.provider",
                                        photoFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                CameraResultLauncher.launch(cameraIntent);
                            }
                        }
                    }
                }).show());
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "temp";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mPhotoPath = image.getAbsolutePath();
        return image;
    }

}