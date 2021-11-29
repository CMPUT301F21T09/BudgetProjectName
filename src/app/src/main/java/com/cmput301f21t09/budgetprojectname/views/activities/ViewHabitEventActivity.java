package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.models.LatLngModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Activity that shows the detail of the habit event
 */
public class ViewHabitEventActivity extends AppCompatActivity {
    /* Controllers */

    /**
     * Controller for fetching habit details
     */
    private HabitEventController habitEventController;


    /* Views */

    /**
     * TextView for displaying the habit title
     */
    private TextView habitTitle;

    /**
     * TextView for displaying the habit event location
     */
    private TextView habitEventLocation;

    /**
     * TextView for displaying the habit event description
     */
    private TextView habitEventDescription;

    /**
     * TextView for displaying the habit event date
     */
    private TextView habitEventDate;

    /**
     * ImageView for displaying the habit event image
     */
    private ImageView habitEventImage;

    /* Information about this habit event */

    /**
     * Current user's UserID
     */
    private String currentUserId;

    /**
     * Habit Event ID
     */
    private String habitEventID;

    /**
     * Habit ID associated with this habit event
     */
    private String habitID;

    /**
     * Habit Title
     */
    private String habitTitleStr;

    /**
     * UserID associated with this habit event
     */
    private String habitEventUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);

        // Retrieve the logged in user's userid
        currentUserId = AuthorizationService.getInstance().getCurrentUserId();

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // data received from viewhabitactivity
        Intent intent = getIntent();
        habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        habitID = intent.getStringExtra("HABIT_ID");
        habitTitleStr = intent.getStringExtra("HABIT_TITLE");
        habitEventUID = intent.getStringExtra("HABIT_EVENT_USERID");

        habitTitle = findViewById(R.id.view_habit_event_habit_title);
        habitEventLocation = findViewById(R.id.view_habit_event_habit_event_location);
        habitEventDescription = findViewById(R.id.view_habit_event_habit_event_description);
        habitEventDate = findViewById(R.id.view_habit_event_habit_event_date);
        habitEventImage = findViewById(R.id.view_habit_event_image);

        habitTitle.setText(habitTitleStr);

        ImageButton back = findViewById(R.id.view_habit_event_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // Set the Habit Event fields
        updateHabitEventDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHabitEventDetails();
    }

    /**
     * Update the habit event details
     */
    private void updateHabitEventDetails() {
        habitEventController = new HabitEventController();
        habitEventController.readHabitEvent(habitEventID, retrievedHabitEvent -> {
            System.out.println("habitevent id " + retrievedHabitEvent.getID());

            // Check there is location data or not
            // Set Address as City, Province, Country if address info exist
            // "No Address Found" if address info not exist
            // "No Location" when there is no location data
            if (retrievedHabitEvent.getLocation() != null) {
                LatLngModel latLngModel = retrievedHabitEvent.getLocation();
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> matches = geocoder.getFromLocation(latLngModel.getLatitude(), latLngModel.getLongitude(), 1);
                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                    if (bestMatch != null) {
                        String locality = bestMatch.getLocality();
                        String adminArea = bestMatch.getAdminArea();
                        String countryCode = bestMatch.getCountryCode();

                        if (countryCode != null) {
                            String address;

                            address = (locality != null ? locality + ", " : "") + (adminArea != null ? adminArea + ", " : "") + countryCode;

                            habitEventLocation.setText(address);
                        } else {
                            throw new IOException();
                        }
                    } else {
                        throw new IOException();
                    }
                } catch (IOException ex) {
                    habitEventLocation.setText("No Address Found");
                }
            } else {
                habitEventLocation.setText("No Location");
            }

            habitEventDescription.setText(retrievedHabitEvent.getComment());

            SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
            String strDate = format.format(retrievedHabitEvent.getDate());
            habitEventDate.setText(strDate);

            // Check there is image data or not
            // Decode image and set to the imageView if it exists
            if (retrievedHabitEvent.getImage() != null) {
                byte[] decodedString = Base64.decode(retrievedHabitEvent.getImage(), Base64.DEFAULT);
                habitEventImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            }
        });

        ImageButton editHabitEvent = findViewById(R.id.view_habit_event_habit_event_edit_button);

        // Let the user edit the habit event if the user owns this habit event
        if (habitEventUID.equals(currentUserId)) {
            editHabitEvent.setOnClickListener(v -> {
                Intent editIntent = new Intent(getApplicationContext(), DefineHabitEventActivity.class);
                final String HABIT_EVENT_ID = "HABIT_EVENT_ID";
                final String HABIT_ID = "HABIT_ID";
                editIntent.putExtra(HABIT_ID, habitID);
                editIntent.putExtra("HABIT_NAME", habitTitleStr);
                editIntent.putExtra("HABIT_USERID", habitEventUID);
                System.out.println("habit id " + habitID);
                editIntent.putExtra(HABIT_EVENT_ID, habitEventID);
                startActivity(editIntent);
            });
        } else {
            editHabitEvent.setVisibility(View.INVISIBLE);
        }

        Button deleteHabitEventBtn = findViewById(R.id.view_habit_event_habit_event_delete_button);

        // Let the user delete the habit event if the user owns this habit event
        if (habitEventUID.equals(currentUserId)) {
            deleteHabitEventBtn.setOnClickListener(v -> {
                habitEventController.deleteHabitEvent(habitEventID);
                Intent deleteIntent = new Intent(getApplicationContext(), ViewHabitActivity.class);
                final String HABIT_ID = "HABIT_ID";
                deleteIntent.putExtra(HABIT_ID, habitID);
                deleteIntent.putExtra("HABIT_USERID", habitEventUID);
                deleteIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(deleteIntent);
            });
        } else {
            deleteHabitEventBtn.setVisibility(View.INVISIBLE);
        }
    }
}
