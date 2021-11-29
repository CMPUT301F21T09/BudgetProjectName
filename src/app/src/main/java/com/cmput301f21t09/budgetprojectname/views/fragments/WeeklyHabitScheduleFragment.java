package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.WeeklyHabitScheduleController;
import com.cmput301f21t09.budgetprojectname.models.IHabitScheduleModel;
import com.cmput301f21t09.budgetprojectname.models.IWeeklyHabitScheduleModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyHabitScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyHabitScheduleFragment extends HabitScheduleFragment {
    /**
     * Model argument
     */
    private static final String MODEL_ARG = "MODEL_ARG";

    /**
     * Editable argument
     */
    private static final String EDITABLE_ARG = "EDITABLE_ARG";

    /**
     * Weekly ahbit schedule controller
     */
    private WeeklyHabitScheduleController controller;

    /**
     * Image view for Sunday
     */
    private ImageView sundayView;

    /**
     * Image view for Monday
     */
    private ImageView mondayView;

    /**
     * Image view for Tuesday
     */
    private ImageView tuesdayView;

    /**
     * Image view for Wednesday
     */
    private ImageView wednesdayView;

    /**
     * Image view for Thursday
     */
    private ImageView thursdayView;

    /**
     * Image view for Friday
     */
    private ImageView fridayView;

    /**
     * Image view for Saturday
     */
    private ImageView saturdayView;

    /**
     * Required empty public constructor
     */
    public WeeklyHabitScheduleFragment() {
    }

    /**
     * Create a new instance of the fragment from the provided model
     *
     * @param model    to use to set initial value
     * @param editable set to true if the values should be editable
     * @return A new instance of WeeklyHabitScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyHabitScheduleFragment newInstance(IWeeklyHabitScheduleModel model, boolean editable) {
        WeeklyHabitScheduleFragment fragment = new WeeklyHabitScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putBooleanArray(MODEL_ARG, model.getAllDays());
        bundle.putBoolean(EDITABLE_ARG, editable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = WeeklyHabitScheduleController.getInstanceFromArray(getArguments().getBooleanArray(MODEL_ARG));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the image views
        sundayView = view.findViewById(R.id.fwch_sunday);
        mondayView = view.findViewById(R.id.fchw_monday);
        tuesdayView = view.findViewById(R.id.fchw_tuesday);
        wednesdayView = view.findViewById(R.id.fchw_wednesday);
        thursdayView = view.findViewById(R.id.fchw_thursday);
        fridayView = view.findViewById(R.id.fchw_friday);
        saturdayView = view.findViewById(R.id.fchw_saturday);

        // Set their onClick listeners if this is editable
        if (getArguments() != null && getArguments().getBoolean(EDITABLE_ARG)) {
            sundayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.SUNDAY));
            mondayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.MONDAY));
            tuesdayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.TUESDAY));
            wednesdayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.WEDNESDAY));
            thursdayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.THURSDAY));
            fridayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.FRIDAY));
            saturdayView.setOnClickListener(clicked -> controller.toggleDay(IWeeklyHabitScheduleModel.SATURDAY));
        }

        // Attach listener to controller and update view
        controller.attachListener(this::updateView);
        this.updateView();
    }

    /**
     * Method to call when view needs to be updated
     */
    private void updateView() {
        IWeeklyHabitScheduleModel model = controller.getModel();
        Context context = getContext();

        if (model.getDay(IWeeklyHabitScheduleModel.SUNDAY))
            sundayView.setImageDrawable(context.getDrawable(R.drawable.ic_sunday_positive));
        else
            sundayView.setImageDrawable(context.getDrawable(R.drawable.ic_sunday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.MONDAY))
            mondayView.setImageDrawable(context.getDrawable(R.drawable.ic_monday_positive));
        else
            mondayView.setImageDrawable(context.getDrawable(R.drawable.ic_monday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.TUESDAY))
            tuesdayView.setImageDrawable(context.getDrawable(R.drawable.ic_tuesday_positive));
        else
            tuesdayView.setImageDrawable(context.getDrawable(R.drawable.ic_tuesday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.WEDNESDAY))
            wednesdayView.setImageDrawable(context.getDrawable(R.drawable.ic_wednesday_positive));
        else
            wednesdayView.setImageDrawable(context.getDrawable(R.drawable.ic_wednesday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.THURSDAY))
            thursdayView.setImageDrawable(context.getDrawable(R.drawable.ic_thursday_positive));
        else
            thursdayView.setImageDrawable(context.getDrawable(R.drawable.ic_thursday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.FRIDAY))
            fridayView.setImageDrawable(context.getDrawable(R.drawable.ic_friday_positive));
        else
            fridayView.setImageDrawable(context.getDrawable(R.drawable.ic_friday_negative));

        if (model.getDay(IWeeklyHabitScheduleModel.SATURDAY))
            saturdayView.setImageDrawable(context.getDrawable(R.drawable.ic_saturday_positive));
        else
            saturdayView.setImageDrawable(context.getDrawable(R.drawable.ic_saturday_negative));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_habit_schedule, container, false);
    }

    @Override
    public IHabitScheduleModel getSchedule() {
        return controller.getModel();
    }
}