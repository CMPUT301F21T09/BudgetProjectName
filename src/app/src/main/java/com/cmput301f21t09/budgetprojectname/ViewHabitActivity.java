package com.cmput301f21t09.budgetprojectname;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class ViewHabitActivity extends AppCompatActivity {
    private TextView habitName;
    private ListView habitEventList;
    ArrayAdapter<HabitEventModel> habitEventAdapter;
    ArrayList<HabitEventModel> habitEventDataList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ViewHabitActivity";
    private HabitEventController habitEventController = new HabitEventController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);

        // The specific habitID is fetched from the previous activity
        Intent intent = getIntent();
        String habitID = intent.getStringExtra("HABIT_ID");

        // Todo: Change the habitID below to the actual habitID passed into this activity
        String testHabitID = "zViEJpRvJ01aleO1d0K3";

        // Todo: Change the mock habitID below to the habitID from intent
        showHabitDetail(testHabitID);

        // Todo: Implement edit Habit function
        final Button editHabitBtn = findViewById(R.id.editHabitButton);
        editHabitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        // The code below deals with the past habit events. The HabitEventCustomList is used
        // to arrange and output the details
        habitEventList = findViewById(R.id.past_habit_event_list);
        habitEventDataList = new ArrayList<>();
        habitEventAdapter = new HabitEventCustomList(this, habitEventDataList);
        habitEventList.setAdapter(habitEventAdapter);

        // Fetches the past habit events related to the current habit from Firestore using habitID
        habitEventController.readHabitEvent(testHabitID, new HabitEventController.HabitEventListCallback() {
            @Override
            public void onCallback(ArrayList<HabitEventModel> hbEvtLst) {
                habitEventDataList.clear();

                for (HabitEventModel hEM: habitEventDataList) {
                    System.out.println(hEM.getDate());
                }

                habitEventDataList.addAll(hbEvtLst);
                habitEventAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Brings the user back to the previous activity if the back button on the app bar is pressed
            case android.R.id.home:
                finish();
                return true;
            // Todo: Implement delete habit function
            case R.id.actionDeleteHabit:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add the delete button to the app bar
        getMenuInflater().inflate(R.menu.menu_view_habit_details_screen, menu);
        return true;
    }

    // Todo: Refactor this into the habit controller
    /**
     * Fetch the habit details from the habits collection
     * @param habitID
     */
    private void showHabitDetail(String habitID) {
        final TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
        final TextView habitDescription = (TextView) findViewById(R.id.habitDescription);
        final TextView habitDate = (TextView) findViewById(R.id.habitDate);

        HashMap<Integer, ImageView> hmIcon = new HashMap<>();
        hmIcon.put(0, findViewById(R.id.sunday_icon));
        hmIcon.put(1, findViewById(R.id.monday_icon));
        hmIcon.put(2, findViewById(R.id.tuesday_icon));
        hmIcon.put(3, findViewById(R.id.wednesday_icon));
        hmIcon.put(4, findViewById(R.id.thursday_icon));
        hmIcon.put(5, findViewById(R.id.friday_icon));
        hmIcon.put(6, findViewById(R.id.saturday_icon));

        ArrayList<Integer> positiveIcons = new ArrayList<Integer>();
        positiveIcons.add(R.drawable.ic_sunday_positive);
        positiveIcons.add(R.drawable.ic_monday_positive);
        positiveIcons.add(R.drawable.ic_tuesday_positive);
        positiveIcons.add(R.drawable.ic_wednesday_positive);
        positiveIcons.add(R.drawable.ic_thursday_positive);
        positiveIcons.add(R.drawable.ic_friday_positive);
        positiveIcons.add(R.drawable.ic_saturday_positive);

        ArrayList<Integer> negativeIcons = new ArrayList<Integer>();
        negativeIcons.add(R.drawable.ic_sunday_negative);
        negativeIcons.add(R.drawable.ic_monday_negative);
        negativeIcons.add(R.drawable.ic_tuesday_negative);
        negativeIcons.add(R.drawable.ic_wednesday_negative);
        negativeIcons.add(R.drawable.ic_thursday_negative);
        negativeIcons.add(R.drawable.ic_friday_negative);
        negativeIcons.add(R.drawable.ic_saturday_negative);

        System.out.println("************ Viewing habit");

        DocumentReference docRef = db.collection("habits").document(habitID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        habitTitle.setText(String.valueOf(document.getData().get("title")));
                        habitDescription.setText(String.valueOf(document.getData().get("reason")));
                        habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(((Timestamp)document.getData().get("startDate")).toDate()));
                        ArrayList<Boolean> fireStoreFrequency = (ArrayList<Boolean>) document.getData().get("frequency");

                        for (int i = 0; i < fireStoreFrequency.size(); i++) {
                            if (fireStoreFrequency.get(i)) {
                                hmIcon.get(i).setImageResource(positiveIcons.get(i));
                            }
                            else {
                                hmIcon.get(i).setImageResource(negativeIcons.get(i));
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error ", task.getException());
                }
            }
        });
    }
}
