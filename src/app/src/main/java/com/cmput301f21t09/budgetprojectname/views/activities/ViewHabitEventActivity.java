package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.views.activities.DefineHabitEventActivity;
import com.cmput301f21t09.budgetprojectname.views.activities.ViewHabitActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

/**
 * Activity that shows the detail of the habit event
 */
public class ViewHabitEventActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ViewHabitEventActivity";
    HabitEventController habitEventController = new HabitEventController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);

        // data received from viewhabitactivity
        Intent intent = getIntent();
        String habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        String habitID = intent.getStringExtra("HABIT_ID");
        String habitTitleStr = intent.getStringExtra("HABIT_TITLE");

        TextView habitTitle = findViewById(R.id.view_habit_event_habit_title);
        TextView habitEventLocation = findViewById(R.id.view_habit_event_habit_event_location);
        TextView habitEventDescription = findViewById(R.id.view_habit_event_habit_event_description);
        TextView habitEventDate = findViewById(R.id.view_habit_event_habit_event_date);

        habitTitle.setText(habitTitleStr);

        // TODO: resolve null error
        /**
         HabitController controller = HabitController.getEditHabitController(habitID);
         if(controller.isTaskComplete(HabitController.HABIT_MODEL_LOAD)){
         IHabitModel model = controller.getModel();
         habitTitle.setText(model.getTitle());
         System.out.println("habit title " + model.getTitle());
         }
         **/
        System.out.println("in viewhabiteventactivity");

        // Set the Habit Event fields
        // TODO: move to a helper function
        HabitEventController habitEventController = new HabitEventController();
        habitEventController.readHabitEvent(habitEventID, retrievedhabitEvent -> {
            System.out.println("habitevent id " + retrievedhabitEvent.getID());
            habitEventLocation.setText(retrievedhabitEvent.getLocation());
            habitEventDescription.setText(retrievedhabitEvent.getComment());
            SimpleDateFormat format = new SimpleDateFormat("MMMM dd,yyyy");
            String strDate = format.format(retrievedhabitEvent.getDate());
            habitEventDate.setText(strDate);
        });

        ImageButton editHabitEvent = findViewById(R.id.view_habit_event_habit_event_edit_button);
        editHabitEvent.setOnClickListener(v -> {
            // TODO: Pass Targeted Habit Event Info to the Intent
            System.out.println("habit event to edit " + habitEventID);
            Intent editIntent = new Intent(getApplicationContext(), DefineHabitEventActivity.class);
            final String HABIT_EVENT_ID = "HABIT_EVENT_ID";
            final String HABIT_ID = "HABIT_ID";
            editIntent.putExtra(HABIT_ID, habitID);
            System.out.println("habit id "+ habitID);
            editIntent.putExtra(HABIT_EVENT_ID, habitEventID);
            startActivity(editIntent);
        });

        ImageView imageHabitEvent = findViewById(R.id.image);
        // TODO: Set Proper Image of Habit Event to the ImageView
        // imageHabitEvent.setImageBitmap();


        Button deleteHabitEventBtn = findViewById(R.id.view_habit_event_habit_event_delete_button);
        deleteHabitEventBtn.setOnClickListener(v -> {
            System.out.println("habit event to delete " + habitEventID);
            habitEventController.deleteHabitEvent(habitEventID);
            Intent deleteIntent = new Intent(getApplicationContext(), ViewHabitActivity.class);
            final String HABIT_ID = "HABIT_ID";
            deleteIntent.putExtra(HABIT_ID, habitID);
            deleteIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(deleteIntent);
        });
    }


}