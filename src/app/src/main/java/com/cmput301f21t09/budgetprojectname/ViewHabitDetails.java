package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewHabitDetails extends AppCompatActivity {
    private TextView habitName;
    ListView habitEventList;
    ArrayAdapter<HabitEventModel> habitEventAdapter;
    ArrayList<HabitEventModel> habitEventDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);

        // The specific habitID is fetched from the previous activity
        Intent intent = getIntent();
        int habitID = intent.getIntExtra("HABIT_ID", -1);

        final TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
        final TextView habitDescription = (TextView) findViewById(R.id.habitDescription);
        final TextView habitDate = (TextView) findViewById(R.id.habitDate);

        // Todo: Change the habit title and description to actual data from Firestore using habitID
        habitTitle.setText("Wash Dishes");
        habitDescription.setText("Dirty dishes really suck :(");

        // Todo: Change the frequency of habit to actual data from Firestore using habitID
        ImageView sundayIcon = findViewById(R.id.sunday_icon);
        ImageView mondayIcon = findViewById(R.id.monday_icon);
        ImageView tuesdayIcon = findViewById(R.id.tuesday_icon);
        ImageView wednesdayIcon = findViewById(R.id.wednesday_icon);
        ImageView thursdayIcon = findViewById(R.id.thursday_icon);
        ImageView fridayIcon = findViewById(R.id.friday_icon);
        ImageView saturdayIcon = findViewById(R.id.saturday_icon);

        sundayIcon.setImageResource(R.drawable.ic_sunday);
        mondayIcon.setImageResource(R.drawable.ic_monday);
        tuesdayIcon.setImageResource(R.drawable.ic_tuesday);
        wednesdayIcon.setImageResource(R.drawable.ic_wednesday);
        thursdayIcon.setImageResource(R.drawable.ic_thursday);
        fridayIcon.setImageResource(R.drawable.ic_friday);
        saturdayIcon.setImageResource(R.drawable.ic_saturday);

        // The user chooses not to commit to this habit on the days below (hence the lower visibility)
        sundayIcon.setAlpha(0.1F);
        thursdayIcon.setAlpha(0.1F);
        fridayIcon.setAlpha(0.1F);
        saturdayIcon.setAlpha(0.1F);

        // Todo: Change the habit date to the actual date from Firestore using habitID
        habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        // The code below deals with the past habit events. The HabitEventCustomList is used
        // to arrange and output the details
        // Todo: Change the past habit events' data below to actual data from Firestore
        String[] names = {"Wash dishes", "Wash dishes", "Wash dishes"};
        String[] locations = {"", "", ""};
        Date[] dates = {new Date(), new Date(), new Date()};
        String[] descriptions = {"", "", ""};

        habitEventList = findViewById(R.id.past_habit_event_list);
        habitEventDataList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            habitEventDataList.add(new HabitEventModel(names[i], locations[i], dates[i], descriptions[i]));
        }

        habitEventAdapter = new HabitEventCustomList(this, habitEventDataList);
        habitEventList.setAdapter(habitEventAdapter);
    }

}
