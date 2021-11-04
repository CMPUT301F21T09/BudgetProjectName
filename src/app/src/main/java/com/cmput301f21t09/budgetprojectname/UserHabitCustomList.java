package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserHabitCustomList extends ArrayAdapter<HabitModel> {
    private ArrayList<HabitModel> habits;
    private Context context;


    public UserHabitCustomList(Context context, ArrayList<HabitModel> habits) {
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_habit_custom_list, parent, false);
        }
        HabitModel habit = habits.get(position);

        TextView habitName = view.findViewById(R.id.habit_name);
        TextView habitDescription = view.findViewById(R.id.habit_description);
        TextView streak = view.findViewById(R.id.streak_text);

        habitName.setText(habit.getTitle());
        habitDescription.setText(habit.getReason());
        streak.setText(String.valueOf(habit.getStreak()));

        return view;
    }
}
