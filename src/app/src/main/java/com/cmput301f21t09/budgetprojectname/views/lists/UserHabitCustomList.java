package com.cmput301f21t09.budgetprojectname.views.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.activities.ViewHabitActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

/**
 * CustomList for CurrentUserProfileFragment and OtherUserProfileActivity
 * Show the habit's name, reason, and streak
 */
public class UserHabitCustomList extends ArrayAdapter<HabitModel> {
    private final ArrayList<HabitModel> habits;
    private final Context context;


    /**
     * Constructor for UserHabitCustomList
     *
     * @param context a current context of application
     * @param habits  a arrayList of habits
     */
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

        // Get the specific habit being interacted with
        HabitModel habit = habits.get(position);

        // Get the views
        TextView habitName = view.findViewById(R.id.habit_name);
        TextView habitDescription = view.findViewById(R.id.habit_description);
        TextView streak = view.findViewById(R.id.streak_text);

        // Set the views accordingly
        habitName.setText(habit.getTitle());
        habitDescription.setText(habit.getReason());
        streak.setText(String.valueOf(habit.getStreak()));

        // Brings the user to the View Habit Details screen
        ShapeableImageView habitBackground = view.findViewById(R.id.habit_lists_background);
            habitBackground.setOnClickListener(v -> {
                // Pass habit id to view the habit details for targeted habit
                Intent intent = new Intent(context, ViewHabitActivity.class);
                intent.putExtra("HABIT_ID", habit.getId());
                intent.putExtra("HABIT_USERID", habit.getUID());
                context.startActivity(intent);
            });
        return view;
    }
}
