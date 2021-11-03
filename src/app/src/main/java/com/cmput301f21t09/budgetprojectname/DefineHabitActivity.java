package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;

public class DefineHabitActivity extends AppCompatActivity {

    private HabitController controller;

    private EditText habitTitle;
    private EditText habitReason;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit);

        Intent intent = getIntent();
        String habitId = intent.getStringExtra("HABIT_EVENT_ID");
        boolean isNewHabitEvent = habitId == null;

        if (isNewHabitEvent) controller = HabitController.getCreateHabitController();
        else controller = HabitController.getEditHabitController(habitId);

        Toolbar toolbar = findViewById(R.id.adh_toolbar);
        toolbar.setTitle(isNewHabitEvent ? "Create Habit" : "Edit Habit");
        setSupportActionBar(toolbar);

        habitTitle = findViewById(R.id.adh_editHabitTitle);
        habitReason = findViewById(R.id.adh_editHabitDescription);
        loadingBar = findViewById(R.id.adh_loading_bar);

        controller.attachListener(this::updateView);
        updateView();
    }

    private void updateView() {
        if (!controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)) {
            loadingBar.setVisibility(View.VISIBLE);
            habitTitle.setEnabled(false);
            habitReason.setEnabled(false);
        } else if (controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)) {
            loadingBar.setVisibility(View.GONE);
            habitTitle.setEnabled(true);
            habitReason.setEnabled(true);

            IHabitModel model = controller.getModel();
            habitTitle.setText(model.getTitle());
            habitReason.setText(model.getReason());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_define_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("DefineHabitActivity" , "Options Item Selected");
        switch (item.getItemId()) {
            // Brings the user back to the previous activity if the back button on the app bar is pressed
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_commit_changes:
                controller.updateModel(habitTitle.getText().toString(), habitReason.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}