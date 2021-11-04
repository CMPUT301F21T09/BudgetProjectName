package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * CustomList for DailyHabitFragment
 * Show the habit's name, reason, and streak with button that create a habit event for the habit
 */
public class DailyHabitCustomList extends ArrayAdapter<HabitModel> {
    private ArrayList<HabitModel> habits;
    private Context context;

    /**
     * Constructor for DailyHabitCustomList
     *
     * @param context a current context of application
     * @param habits  a arrayList of habits
     */
    public DailyHabitCustomList(Context context, ArrayList<HabitModel> habits) {
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.daily_habit_custom_list, parent, false);
        }
        HabitModel habit = habits.get(position);

        TextView habitName = view.findViewById(R.id.habit_name);
        TextView habitDescription = view.findViewById(R.id.habit_description);
        TextView streak = view.findViewById(R.id.streak_text);

        habitName.setText(habit.getTitle());
        habitDescription.setText(habit.getReason());
        streak.setText(String.valueOf(habit.getStreak()));

        ImageButton done = view.findViewById(R.id.done_button);
        done.setOnClickListener(v -> {
            // pass habit id to create habit event for targeted habit
            Intent intent = new Intent(context, DefineHabitEventActivity.class);
            intent.putExtra("HABIT_EVENT_ID", habit.getID());
            context.startActivity(intent);
        });

        return view;
    }
}
