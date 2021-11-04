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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);

        // Get the habitID from the previous activity
        Intent intent = getIntent();
        String habitID = intent.getStringExtra("HABIT_ID");

        /* Habit Details */

        // Retrieve the specific views
        habitTitle = (TextView) findViewById(R.id.habitTitle);
        habitDescription = (TextView) findViewById(R.id.habitDescription);
        habitDate = (TextView) findViewById(R.id.habitDate);
        habitTitleToolbar = findViewById(R.id.toolbar_title);

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

        // Fetches the past habit events related to the current habit from Firestore using habitID
        // and update the view
        habitEventController.readHabitEvents(habitID, new HabitEventController.HabitEventListCallback() {
            @Override
            public void onCallback(ArrayList<HabitEventModel> hbEvtLst) {
                habitEventDataList.clear();

                for (HabitEventModel hEM : habitEventDataList) {
                    System.out.println(hEM.getDate());
                }

                habitEventDataList.addAll(hbEvtLst);
                habitEventAdapter.notifyDataSetChanged();
            }
        });


        // selection of past habit event item start habit event detail screen
        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                HabitEventModel selectedHEM = habitEventAdapter.getItem(position);
                System.out.println("selected element id " + selectedHEM.getID());

                Intent intent = new Intent(getApplicationContext(), ViewHabitEventActivity.class);
                intent.putExtra("HABIT_EVENT_ID", selectedHEM.getID());
                intent.putExtra("HABIT_ID", selectedHEM.getHabitID());
                startActivity(intent);
            }

        });
    }

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



