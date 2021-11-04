package com.cmput301f21t09.budgetprojectname;

import static java.util.Objects.isNull;

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
    private ArrayList<HabitEventModel> habitEvents;
    private Context context;


    public HabitEventCustomList(Context context, ArrayList<HabitEventModel> habitEvents) {
        super(context, 0, habitEvents);
        this.habitEvents = habitEvents;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_view_habit_event_custom_list, parent, false);
        }

        HabitEventModel habitEvent = habitEvents.get(position);

        TextView habitDate = view.findViewById(R.id.date);
        ImageView locationIcon = view.findViewById(R.id.location_icon);
        ImageView descriptionIcon = view.findViewById(R.id.comment_icon);
        ImageView ImageIcon = view.findViewById(R.id.image_icon);

        // Todo: Change the date of past habit events to actual habit event data from Firestore
        DateFormat df = new SimpleDateFormat("MMMM dd, YYYY");
        habitDate.setText(df.format(habitEvent.getDate()));

        // Todo: Change the icons' opacity according to the actual habit events' data from Firestore
        if (isNull(habitEvent.getLocation())) {
            locationIcon.setImageResource(R.drawable.ic_map_marker_negative);
        } else {
            locationIcon.setImageResource(R.drawable.ic_map_marker_positive);
        }
        if (isNull(habitEvent.getComment())) {
            descriptionIcon.setImageResource(R.drawable.ic_comment_negative);
        } else {
            descriptionIcon.setImageResource(R.drawable.ic_comment_positive);
        }
        if (isNull(habitEvent.getImage())) {
            ImageIcon.setImageResource(R.drawable.ic_image_negative);
        } else {
            ImageIcon.setImageResource(R.drawable.ic_image_positive);
        }
//        locationIcon.setImageResource(R.drawable.ic_map_marker_positive);
//        descriptionIcon.setImageResource(R.drawable.ic_comment_negative);
//        ImageIcon.setImageResource(R.drawable.ic_image_positive);

        return view;
    }
}
