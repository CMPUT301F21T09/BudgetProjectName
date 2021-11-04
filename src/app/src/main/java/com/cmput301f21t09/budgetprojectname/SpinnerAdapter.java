package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * CustomSpinnerAdapter for DailyHabitFragment to select to view whether today's habit or all habits of user
 */
public class SpinnerAdapter extends BaseAdapter {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public SpinnerAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data != null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_spinner_view, parent, false);
        }

        if (data != null) {
            String text = data.get(position);
            ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_spinner_dropdown_view, parent, false);
        }

        String text = data.get(position);
        ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}