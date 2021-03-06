package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;
import com.cmput301f21t09.budgetprojectname.views.fragments.HabitScheduleFragment;
import com.cmput301f21t09.budgetprojectname.views.fragments.HabitScheduleViewSelector;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

/**
 * Activity that allows the user to add/edit a habit
 */
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

    /**
     * Switch view for selecting whether the habit is private
     */
    private SwitchMaterial privateSwitch;

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
        TextView tbTitle = findViewById(R.id.toolbar_title);
        tbTitle.setText(isNewHabitEvent ? "Create Habit" : "Edit Habit");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        // Retrieve the specific views
        habitTitle = findViewById(R.id.adh_editHabitTitle);
        habitReason = findViewById(R.id.adh_editHabitReason);
        loadingBar = findViewById(R.id.adh_loading_bar);
        habitStartDate = findViewById(R.id.adh_editStartDate);
        privateSwitch = findViewById(R.id.adh_privateSwitch);

        // Confirm button pressed
        ImageButton confirm = findViewById(R.id.habit_confirm);
        confirm.setOnClickListener(v -> confirmButtonPressed());

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
            privateSwitch.setEnabled(false);
        } else if (controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)) {
            // Enable editing fields and hide loading bar
            loadingBar.setVisibility(View.GONE);
            habitTitle.setEnabled(true);
            habitReason.setEnabled(true);
            habitStartDate.setEnabled(controller.isStartDateEditable());
            privateSwitch.setEnabled(true);

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
            privateSwitch.setChecked(model.getIsPrivate());

            // Set schedule fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.adh_scheduleFragment,
                            HabitScheduleViewSelector.getFragmentForModel(model.getSchedule(), true)
                    ).commit();
        }
    }

    /**
     * Fired when checkmark is pressed
     */
    private void confirmButtonPressed() {
        IHabitModel model = controller.getModel();
        String titleError = controller.getTitleError(habitTitle.getText().toString());
        if (titleError != null) {
            habitTitle.setError(titleError);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(habitStartDate.getYear(), habitStartDate.getMonth(), habitStartDate.getDayOfMonth());
            HabitScheduleFragment hsv = ((HabitScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.adh_scheduleFragment));
            controller.updateModel(habitTitle.getText().toString(), habitReason.getText().toString(),
                    calendar.getTime(), privateSwitch.isChecked(),
                    model.getStreak(), hsv.getSchedule(), model.getLastCompleted());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Brings the user back to the previous activity if the back button on the app bar is pressed
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}