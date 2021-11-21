package com.cmput301f21t09.budgetprojectname.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

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

    SupportMapFragment mapFragment;
    SwitchMaterial locationSwitch;
    ConstraintLayout locationContainer;

    GoogleMap map;
    LatLngModel marker;
    MarkerOptions markerOptions;

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

            if (marker != null) {
                LatLng markerLocation = new LatLng(marker.getLatitude(), marker.getLongitude());
                map.addMarker(markerOptions.position(markerLocation));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 10));
            }
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

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        marker = new LatLngModel();
        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(205.0f));

        // Show/Hide Map
        locationContainer = findViewById(R.id.location_container);
        locationSwitch = findViewById(R.id.location_switch);
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                } else {
                    if (isNewHabitEvent) {
                        mapMarkCurrentLocation();
                    }
                    locationContainer.setVisibility(View.VISIBLE);
                }
            } else {
                locationContainer.setVisibility(View.GONE);
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

                        HabitEventModel habitEventModel = document.toObject(HabitEventModel.class);

                        // set fields in view
                        if (habitEventModel.getLocation() != null) {
                            locationSwitch.setChecked(true);
                            marker = habitEventModel.getLocation();
                            mapFragment.getMapAsync(googleMap -> {
                                LatLng markerLocation = new LatLng(marker.getLatitude(), marker.getLongitude());
                                googleMap.addMarker(markerOptions.position(markerLocation));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 13));
                            });
                        }
                        comment.setText(habitEventModel.getComment());

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
                    HabitEventModel habitEvent = new HabitEventModel(null, locationSwitch.isChecked() ? marker : null, new Date(),
                            commentStr, null, habitID);

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                mapMarkCurrentLocation();
            } else {
                map.setMyLocationEnabled(false);
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "This action requires\nlocation permission", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Grant Permission", v1 -> {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                            }).show();
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "This action requires\nlocation permission.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Go to Settings", v1 -> {
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri));
                            }).show();
                }
            }
            locationContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(markerOptions.position(latLng));
            marker.setLatitude(latLng.latitude);
            marker.setLongitude(latLng.longitude);
        });
    }

    private void mapMarkCurrentLocation() {
        map.setMyLocationEnabled(true);

        Location location = getLocation();
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        map.addMarker(markerOptions.position(userLocation));
        marker.setLatitude(userLocation.latitude);
        marker.setLongitude(userLocation.longitude);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
    }

    private final LocationListener mLocationListener = location -> {
    };

    private Location getLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        Location bestLocation = null;
        for (String provider : mLocationManager.getProviders(true)) {
            mLocationManager.requestLocationUpdates(provider, 1000L, 0, mLocationListener);
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }

        return bestLocation;
    }
}