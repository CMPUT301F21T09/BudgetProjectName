package com.cmput301f21t09.budgetprojectname;


import static java.util.Objects.nonNull;

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

/**
 * CustomList for ViewHabitActivity
 * Show the habit event's date and whether or not the user has added a location, comment, and/or photograph
 */
public class HabitEventCustomList extends ArrayAdapter<HabitEventModel> {
    private final ArrayList<HabitEventModel> habitEvents;
    private final Context context;

    /**
     * Constructor for HabitEventCustomList
     *
     * @param context
     * @param habitEvents
     */
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


        /* Views */

        /**
         * Text View for habit date
         */

        TextView habitDate = view.findViewById(R.id.date);

        /**
         * Image View for location icon
         */
        ImageView locationIcon = view.findViewById(R.id.location_icon);

        /**
         * Image View for description icon
         */
        ImageView descriptionIcon = view.findViewById(R.id.comment_icon);

        /**
         * Image View for Image icon
         */
        ImageView ImageIcon = view.findViewById(R.id.image_icon);

        // Set the date when the habit event occured
        DateFormat df = new SimpleDateFormat("MMMM dd, YYYY");
        habitDate.setText(df.format(habitEvent.getDate()));

        // Set the location, comment, and image icons accordingly
        // location and comment are empty string by default
        // image is null by default
        if (habitEvent.getLocation().length() > 0) {
            locationIcon.setImageResource(R.drawable.ic_map_marker_positive);
        }
        if (habitEvent.getComment() != null && habitEvent.getComment().length() > 0) {
            descriptionIcon.setImageResource(R.drawable.ic_comment_positive);
        }
        if (nonNull(habitEvent.getImage())) {
            ImageIcon.setImageResource(R.drawable.ic_image_positive);
        }

        return view;
    }
}