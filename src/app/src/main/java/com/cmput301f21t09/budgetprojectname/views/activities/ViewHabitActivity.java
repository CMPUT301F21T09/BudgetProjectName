package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.fragments.HabitScheduleViewSelector;
import com.cmput301f21t09.budgetprojectname.views.lists.HabitEventCustomList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Activity that shows the detail of the habit
 */
public class ViewHabitActivity extends AppCompatActivity {

    /* Controllers */

    /**
     * Controller for fetching habit details
     */
    private HabitController controller;

    /**
     * Controller for fetching habit events
     */
    private final HabitEventController habitEventController = new HabitEventController();

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

    /**
     * Button for editing a habit
     */
    private Button editHabitBtn;

    /**
     * ImageButton for removing a habit
     */
    private ImageButton removeHabitBtn;

    /**
     * List View for habit events
     */
    private ListView habitEventList;

    /**
     * Habit event adapter
     */
    ArrayAdapter<HabitEventModel> habitEventAdapter;

    /**
     * Habit event list
     */
    ArrayList<HabitEventModel> habitEventDataList;

    /**
     * Habit's ID
     */
    String habitID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);

        // Retrieve the logged in user's userid
        String currentUserId = AuthorizationService.getInstance().getCurrentUserId();

        // Get the habitID and habitUID from the previous activity
        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABIT_ID");
        String habitUID = intent.getStringExtra("HABIT_USERID");

        /* Habit Details */

        // Retrieve the specific views
        habitTitle = findViewById(R.id.habitTitle);
        habitDescription = findViewById(R.id.habitDescription);
        habitDate = findViewById(R.id.habitDate);
        habitTitleToolbar = findViewById(R.id.toolbar_title);
        editHabitBtn = findViewById(R.id.editHabitButton);
        removeHabitBtn = findViewById(R.id.view_habit_remove_button);

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

        updateHabitEventsList();


        // selection of past habit event item start habit event detail screen
        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                HabitEventModel selectedHEM = habitEventAdapter.getItem(position);
                System.out.println("selected element id " + selectedHEM.getID());

                Intent intent = new Intent(getApplicationContext(), ViewHabitEventActivity.class);
                intent.putExtra("HABIT_EVENT_ID", selectedHEM.getID());
                intent.putExtra("HABIT_ID", selectedHEM.getHabitID());
                intent.putExtra("HABIT_TITLE", habitTitle.getText().toString());
                intent.putExtra("HABIT_EVENT_USERID", habitUID);
                startActivity(intent);
            }

        });

        // Brings the user back to the previous screen
        ImageButton back = findViewById(R.id.view_habit_back_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // Let the user edit the habit if the user owns this habit
        if (currentUserId.equals(habitUID)) {
            editHabitBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DefineHabitActivity.class);
                    final String HABIT_ID = "HABIT_ID";
                    intent.putExtra(HABIT_ID, habitID);
                    startActivity(intent);
                }
            });
        } else {
            editHabitBtn.setVisibility(View.INVISIBLE);
        }

        // Let the user delete the habit if the user owns this habit
        if (currentUserId.equals(habitUID)) {
            removeHabitBtn.setOnClickListener(view -> {
                controller.deleteModel();
            });
        } else {
            removeHabitBtn.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Update the views of the habit details
     */
    private void updateView() {
        // Deletion of a habit
        if (controller.isTaskComplete(HabitController.HABIT_MODEL_DELETE)) {
            if (controller.isTaskSuccessful(HabitController.HABIT_MODEL_DELETE)) {
                Toast t = new Toast(this);
                t.setText("Habit deleted");
                t.show();
                finish();
            } else {
                Toast t = new Toast(this);
                t.setText("Unable to delete");
                t.show();
            }
        }

        // Set the views of the habit details accordingly
        if (controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)) {
            // Set the toolbar title, habit tile, habit reason, and habit date view
            IHabitModel model = controller.getModel();
            habitTitleToolbar.setText(model.getTitle());
            habitTitle.setText(model.getTitle());
            habitDescription.setText(model.getReason());
            habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(model.getStartDate()));

            // Set schedule fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.adh_scheduleFragment,
                            HabitScheduleViewSelector.getFragmentForModel(model.getSchedule(), false)
                    ).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
        updateHabitEventsList();
    }

    /**
     * Fetches the past habit events related to the current habit from Firestore using habitID
     * and update the view
     */
    private void updateHabitEventsList() {
        habitEventController.readHabitEvents(habitID, hbEvtLst -> {
            habitEventDataList.clear();
            for (HabitEventModel hEM : hbEvtLst) {
                System.out.println(hEM.getDate());
            }
            habitEventDataList.addAll(hbEvtLst);
            habitEventAdapter.notifyDataSetChanged();
        });
    }
}
