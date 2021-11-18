package com.cmput301f21t09.budgetprojectname;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.cmput301f21t09.budgetprojectname.models.LatLngModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

/**
 * Activity that makes the user to add/edit a habit event
 */
public class DefineHabitEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView habitEventName;
    private EditText comment;
    private ImageView image;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";
    private boolean isNewHabitEvent;
    private final HabitEventController habitEventController = new HabitEventController();
    private String habitID;
    private String habitEventID;

    SwitchMaterial locationSwitch;

    GoogleMap map;
    LatLngModel marker;

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

        habitEventName = (TextView) findViewById(R.id.habitName);
        comment = (EditText) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);

        // Let User Add/Change their habit event image as click ImageView area
        image.setOnClickListener(v -> {
            // TODO: Let User Choose Image from Gallery or Take a Photo
        });

        ImageButton back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            finish();
        });

        // Show/Hide Map
        ConstraintLayout locationContainer = findViewById(R.id.location_container);
        locationSwitch = findViewById(R.id.location_switch);
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                locationContainer.setVisibility(View.VISIBLE);
            } else {
                locationContainer.setVisibility(View.GONE);
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                        comment.setText(document.getString("comment"));

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
                String commentStr = comment.getText().toString();
                // error checking/handling for adding optional comment of up to 30 chars
                if (commentStr.length() > 20) {
                    comment.setError(getString(R.string.errorHabitEventComment));
                    comment.requestFocus();
                    Toast.makeText(getApplicationContext(), "ERROR: could not save habit event",
                            Toast.LENGTH_SHORT).show();
                } else {

                    String descriptionStr = comment.getText().toString();

                    HabitEventModel habitEvent = new HabitEventModel(null, locationSwitch.isChecked() ? marker : null, new Date(),
                            descriptionStr, null, habitID);

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        marker = new LatLngModel();
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(205.0f));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            googleMap.setMyLocationEnabled(true);
            Location location = getLastKnownLocation();
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            googleMap.addMarker(markerOptions.position(userLocation));
            marker.setLatitude(userLocation.latitude);
            marker.setLongitude(userLocation.longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
        }

        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(markerOptions.position(latLng));
            marker.setLatitude(latLng.latitude);
            marker.setLongitude(latLng.longitude);
        });

        //googleMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}