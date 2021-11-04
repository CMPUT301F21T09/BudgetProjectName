package com.cmput301f21t09.budgetprojectname;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;
import com.cmput301f21t09.budgetprojectname.models.IWeeklyHabitScheduleModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ViewHabitActivity extends AppCompatActivity {

    /* Controllers */

    /**
     * Controller for fetching habit details
     */
    private HabitController controller;

    /**
     * Controller for fetching habit events
     */
    private HabitEventController habitEventController = new HabitEventController();

    /* Views */

    /**
     * TextView for displaying title of habit on Toolbar
     */
    private TextView habitTitleToolbar;

    /**
     * Text View for displaying habit title
     */
    private TextView habitTitle;


    /**
     * Text View for displaying habit Description
     */
    private TextView habitDescription;

    /**
     * Text View for displaying habit Date
     */
    private TextView habitDate;

    // Image View for each of the icon
    private ImageView sundayIcon;
    private ImageView mondayIcon;
    private ImageView tuesdayIcon;
    private ImageView wednesdayIcon;
    private ImageView thursdayIcon;
    private ImageView fridayIcon;
    private ImageView saturdayIcon;

    /**
     * List View for habit events
     */
    private ListView habitEventList;

    ArrayAdapter<HabitEventModel> habitEventAdapter;
    ArrayList<HabitEventModel> habitEventDataList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ViewHabitActivity";
    private HabitEventController habitEventController = new HabitEventController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);


        // Display the back button
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // The specific habitID is fetched from the previous activity
        Intent intent = getIntent();
        String habitID = intent.getStringExtra("HABIT_ID");

        // Todo: Change the mock habitID below to the habitID from intent
        showHabitDetail("zViEJpRvJ01aleO1d0K3");
      
        /* Habit Details */

        // Retrieve the specific views
        habitTitle = (TextView) findViewById(R.id.habitTitle);
        habitDescription = (TextView) findViewById(R.id.habitDescription);
        habitDate = (TextView) findViewById(R.id.habitDate);
        habitTitleToolbar = findViewById(R.id.toolbar_title);

        // The code below deals with the past habit events. The HabitEventCustomList is used
        // to arrange and output the details

        habitEventList = findViewById(R.id.past_habit_event_list);
        habitEventDataList = new ArrayList<>();
        habitEventAdapter = new HabitEventCustomList(this, habitEventDataList);
        habitEventList.setAdapter(habitEventAdapter);

        // Todo: Change the habitID below to the actual habitID passed into this activity
        String testHabitID = "zViEJpRvJ01aleO1d0K3";

        habitEventController.readHabitEvents(testHabitID, new HabitEventController.HabitEventListCallback() {
            @Override
            public void onCallback(ArrayList<HabitEventModel> hbEvtLst) {
                habitEventDataList.clear();

                for (HabitEventModel hEM: habitEventDataList) {
                    System.out.println(hEM.getDate());
                }

                habitEventDataList.addAll(hbEvtLst);
                habitEventAdapter.notifyDataSetChanged();
            }
        });


        // Todo: Change the past habit events' data below to actual data from Firestore using HabitID and HabitEventID

        String[] locations = {"", "", "", "", "", "", "", "", "", "", "", ""};
        Date[] dates = {new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date()};
        String[] descriptions = {"", "", "", "", "", "", "", "", "", "", "", ""};
        String[] habitIDs = {"", "", "", "", "", "", "", "", "", "", "", ""};

        // Set up the controller and set the views accordingly
        controller = HabitController.getEditHabitController(habitID);
        controller.attachListener(this::updateView);
        updateView();

        /* Past Habit Events */

        // Retrieve the specific view
        habitEventList = findViewById(R.id.past_habit_event_list);

        // Set up the list and send an empty list to the view
        habitEventDataList = new ArrayList<>();
        habitEventAdapter = new HabitEventCustomList(this, habitEventDataList);
        habitEventList.setAdapter(habitEventAdapter);

        // selection of past habit event item start habit event detail screen
        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3)
            {
                HabitEventModel selectedHEM = habitEventAdapter.getItem(position);
                System.out.println("selected element id " + selectedHEM.getID());

                Intent intent = new Intent(getApplicationContext(), ViewHabitEventActivity.class);
                intent.putExtra("HABIT_EVENT_ID", selectedHEM.getID());
                intent.putExtra("HABIT_ID", selectedHEM.getHabitID());
                startActivity(intent);

        // Fetches the past habit events related to the current habit from Firestore using habitID
        // and update the view
        habitEventController.readHabitEvent(habitID, new HabitEventController.HabitEventListCallback() {
            @Override
            public void onCallback(ArrayList<HabitEventModel> hbEvtLst) {
                habitEventDataList.clear();
                habitEventDataList.addAll(hbEvtLst);
                habitEventAdapter.notifyDataSetChanged();
            }
        });

        // Go Back
        ImageButton back = findViewById(R.id.view_habit_back_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // Edit habit
        // Todo: Implement edit Habit function
        final Button editHabitBtn = findViewById(R.id.editHabitButton);
        editHabitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        // Delete Habit
        ImageButton remove = findViewById(R.id.view_habit_remove_button);
        remove.setOnClickListener(v -> {
            // TODO: Remove Targeted Habit. Dialog To Confirm?
        });
    }


    // Todo: Refactor this into the habit controller
    /**
     * Fetch the habit details from the habits collection
     * @param habitID
     */
    private void showHabitDetail(String habitID) {
        final TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
        final TextView habitDescription = (TextView) findViewById(R.id.habitDescription);
        final TextView habitDate = (TextView) findViewById(R.id.habitDate);

        HashMap<Integer, ImageView> hmIcon = new HashMap<>();
        hmIcon.put(0, findViewById(R.id.sunday_icon));
        hmIcon.put(1, findViewById(R.id.monday_icon));
        hmIcon.put(2, findViewById(R.id.tuesday_icon));
        hmIcon.put(3, findViewById(R.id.wednesday_icon));
        hmIcon.put(4, findViewById(R.id.thursday_icon));
        hmIcon.put(5, findViewById(R.id.friday_icon));
        hmIcon.put(6, findViewById(R.id.saturday_icon));

        ArrayList<Integer> positiveIcons = new ArrayList<Integer>();
        positiveIcons.add(R.drawable.ic_sunday_positive);
        positiveIcons.add(R.drawable.ic_monday_positive);
        positiveIcons.add(R.drawable.ic_tuesday_positive);
        positiveIcons.add(R.drawable.ic_wednesday_positive);
        positiveIcons.add(R.drawable.ic_thursday_positive);
        positiveIcons.add(R.drawable.ic_friday_positive);
        positiveIcons.add(R.drawable.ic_saturday_positive);

        ArrayList<Integer> negativeIcons = new ArrayList<Integer>();
        negativeIcons.add(R.drawable.ic_sunday_negative);
        negativeIcons.add(R.drawable.ic_monday_negative);
        negativeIcons.add(R.drawable.ic_tuesday_negative);
        negativeIcons.add(R.drawable.ic_wednesday_negative);
        negativeIcons.add(R.drawable.ic_thursday_negative);
        negativeIcons.add(R.drawable.ic_friday_negative);
        negativeIcons.add(R.drawable.ic_saturday_negative);

        System.out.println("************ Viewing habit");

        DocumentReference docRef = db.collection("habits").document(habitID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        habitTitle.setText(String.valueOf(document.getData().get("title")));
                        habitDescription.setText(String.valueOf(document.getData().get("reason")));
                        habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(((Timestamp)document.getData().get("startDate")).toDate()));
                        ArrayList<Boolean> fireStoreFrequency = (ArrayList<Boolean>) document.getData().get("frequency");

                        for (int i = 0; i < fireStoreFrequency.size(); i++) {
                            if (fireStoreFrequency.get(i)) {
                                hmIcon.get(i).setImageResource(positiveIcons.get(i));
                            }
                            else {
                                hmIcon.get(i).setImageResource(negativeIcons.get(i));
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error ", task.getException());
                }
            }
        });

    /**
     * Update the views of the habit details
     */
    private void updateView() {
        /**
         * Get the image views
         */
        sundayIcon = findViewById(R.id.sunday_icon);
        mondayIcon = findViewById(R.id.monday_icon);
        tuesdayIcon = findViewById(R.id.tuesday_icon);
        wednesdayIcon = findViewById(R.id.wednesday_icon);
        thursdayIcon = findViewById(R.id.thursday_icon);
        fridayIcon = findViewById(R.id.friday_icon);
        saturdayIcon = findViewById(R.id.saturday_icon);

        // Set the views of the habit details accordingly
        if(controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)){
            // Set the toolbar title, habit tile, habit reason, and habit date view
            IHabitModel model = controller.getModel();
            habitTitleToolbar.setText(model.getTitle());
            habitTitle.setText(model.getTitle());
            habitDescription.setText(model.getReason());
            habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(model.getStartDate()));

            // Set the frequency of habit accordingly
            IWeeklyHabitScheduleModel schedule = (IWeeklyHabitScheduleModel) model.getSchedule();
            if (schedule.getDay(IWeeklyHabitScheduleModel.SUNDAY))
                sundayIcon.setImageResource(R.drawable.ic_sunday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.MONDAY))
                mondayIcon.setImageResource(R.drawable.ic_monday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.TUESDAY))
                tuesdayIcon.setImageResource(R.drawable.ic_tuesday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.WEDNESDAY))
                wednesdayIcon.setImageResource(R.drawable.ic_wednesday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.THURSDAY))
                thursdayIcon.setImageResource(R.drawable.ic_thursday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.FRIDAY))
                fridayIcon.setImageResource(R.drawable.ic_friday_positive);

            if (schedule.getDay(IWeeklyHabitScheduleModel.SATURDAY))
                saturdayIcon.setImageResource(R.drawable.ic_saturday_positive);
        }

    }
}
