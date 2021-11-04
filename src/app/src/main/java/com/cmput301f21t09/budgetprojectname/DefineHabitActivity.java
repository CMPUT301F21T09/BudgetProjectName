package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;

import java.util.Calendar;

import com.cmput301f21t09.budgetprojectname.views.fragments.HabitScheduleFragment;
import com.cmput301f21t09.budgetprojectname.views.fragments.HabitScheduleViewSelector;

public class DefineHabitActivity extends AppCompatActivity {

    /* Controllers */
    private HabitController controller;

    /* Views */

    /**
     * Edit text view for editing habit title
     */
    private EditText habitTitle;

    /**
     * Edit text view for editing habit reason
     */
    private EditText habitReason;

    /**
     * Progress Bar view for showing when the habit is loading
     */
    private ProgressBar loadingBar;


    /**
     * Date Picker view for editing the start date
     */
    private DatePicker habitStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit);

        // Get any passed in data
        Intent intent = getIntent();
        String habitId = intent.getStringExtra("HABIT_ID");
        boolean isNewHabitEvent = habitId == null;

        // Load the appropriate controller
        if (isNewHabitEvent) controller = HabitController.getCreateHabitController();
        else controller = HabitController.getEditHabitController(habitId);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.adh_toolbar);
        toolbar.setTitle(isNewHabitEvent ? "Create Habit" : "Edit Habit");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the specific views
        habitTitle = findViewById(R.id.adh_editHabitTitle);
        habitReason = findViewById(R.id.adh_editHabitReason);
        loadingBar = findViewById(R.id.adh_loading_bar);
        habitStartDate = findViewById(R.id.adh_editStartDate);

        // Set up the controller and set views
        controller.attachListener(this::updateView);
        updateView();
    }

    /**
     * Update the views with the current controller data
     */
    private void updateView() {
        // TODO: maybe refactor methods like these to attach to specific "streams" of updated data?
        if (controller.isSaved()) finish();

        // Update view based on habit model load state
        if (!controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD) || controller.isSaving()) {
            // Disable editing fields and show loading bar
            loadingBar.setVisibility(View.VISIBLE);
            habitTitle.setEnabled(false);
            habitReason.setEnabled(false);
            habitStartDate.setEnabled(false);
        } else if (controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)) {
            // Enable editing fields and hide loading bar
            loadingBar.setVisibility(View.GONE);
            habitTitle.setEnabled(true);
            habitReason.setEnabled(true);
            habitStartDate.setEnabled(true);

            // Update fields
            IHabitModel model = controller.getModel();
            habitTitle.setText(model.getTitle());
            habitReason.setText(model.getReason());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(model.getStartDate());
            habitStartDate.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            // Set schedule fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.adh_scheduleFragment,
                            HabitScheduleViewSelector.getFragmentForModel(model.getSchedule(), true)
                    ).commit();
        }
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
                Calendar calendar = Calendar.getInstance();
                calendar.set(habitStartDate.getYear(), habitStartDate.getMonth(), habitStartDate.getDayOfMonth());
                HabitScheduleFragment hsv = ((HabitScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.adh_scheduleFragment));
                controller.updateModel(habitTitle.getText().toString(), habitReason.getText().toString(), calendar.getTime(), hsv.getSchedule() );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}