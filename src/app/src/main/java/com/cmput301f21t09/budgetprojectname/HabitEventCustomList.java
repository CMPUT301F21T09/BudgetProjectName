package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HabitEventCustomList extends ArrayAdapter<HabitEventModel> {
    private ArrayList<HabitEventModel> habits;
    private Context context;


    public HabitEventCustomList(Context context, ArrayList<HabitEventModel> habits) {
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_view_habit_event_custom_list, parent, false);
        }

        TextView habitDate = view.findViewById(R.id.date);
        ImageView locationIcon = view.findViewById(R.id.location_icon);
        ImageView descriptionIcon = view.findViewById(R.id.comment_icon);
        ImageView ImageIcon = view.findViewById(R.id.image_icon);

        // Todo: Change the date of past habit events to actual habit event data from Firestore
        DateFormat df = new SimpleDateFormat("MMMM dd, YYYY");
        habitDate.setText(df.format(new Date()));

        // Todo: Change the icons' opacity according to the actual habit events' data from Firestore
        locationIcon.setImageResource(R.drawable.ic_map_marker);
        descriptionIcon.setImageResource(R.drawable.ic_comment);
        descriptionIcon.setAlpha(0.1F);
        ImageIcon.setImageResource(R.drawable.ic_image);

        return view;
    }
}
