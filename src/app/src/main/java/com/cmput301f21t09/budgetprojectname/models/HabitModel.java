package com.cmput301f21t09.budgetprojectname.models;

import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.services.ServiceTask;
import com.cmput301f21t09.budgetprojectname.services.ServiceTaskManager;
import com.cmput301f21t09.budgetprojectname.services.database.CollectionSpecifier;
import com.cmput301f21t09.budgetprojectname.services.database.DatabaseService;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.DocumentModelSerializer;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.ModelMapParser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for user habits
 */
public class HabitModel implements IHabitModel {

    /**
     * Collection ID for habits as represented in backend
     */
    public static final String HABIT_COLLECTION_ID = "habits";

    /**
     * Habit title
     */
    private String title;
    /**
     * Habit database entry id
     */
    private String id;
    /**
     * Habit reason
     */
    private String reason;
    /**
     * Habit streak score
     */
    private long streak;
    /**
     * Habit start date
     */
    private Date startDate;
    /**
     * Habit last completed date
     */
    private final Date lastCompleted;
    /**
     * Habit schedule
     */
    private IHabitScheduleModel schedule;

    /**
     * Private constructor for creating a habit model with the given data
     *
     * @param id            habit id
     * @param title         habit title string
     * @param reason        habit reason
     * @param startDate     habit start date
     * @param lastCompleted habit last time completed date
     * @param streak        habit streak score
     */
    private HabitModel(String id, String title, String reason, Date startDate, Date lastCompleted, long streak, IHabitScheduleModel schedule) {
        this.id = id;
        this.title = sanitizeStringFromDatabase(title, IHabitModel.MAX_TITLE_LENGTH);
        this.reason = sanitizeStringFromDatabase(reason, IHabitModel.MAX_REASON_LENGTH);
        this.startDate = startDate != null ? startDate : new Date();
        this.lastCompleted = lastCompleted;
        this.schedule = schedule;
        this.streak = streak;
    }

    /**
     * Get an instance of a habit that does not exist in the database
     *
     * @return completed load task for habit
     */
    public static ServiceTask<HabitModel> getNewInstance() {
        HabitScheduleModelFactory scheduleFactory = new HabitScheduleModelFactory();
        ServiceTaskManager<HabitModel> taskman = new ServiceTaskManager<>();
        taskman.setSuccess(new HabitModel(null, null, null, new Date(), null, 0, scheduleFactory.getNewModelInstance()));
        return taskman.getTask();
    }

    /**
     * Get an instance of a habit that exists in the database as specified by the given id
     *
     * @param id of habit
     * @return load task for habit
     */
    public static ServiceTask<HabitModel> getInstanceById(String id) {
        ServiceTaskManager<HabitModel> taskManager = new ServiceTaskManager<>();
        FirebaseFirestore.getInstance().collection(HABIT_COLLECTION_ID).document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HabitModel data = new HabitModelMapParser().parseMap(task.getResult().getData(), id);
                taskManager.setSuccess(data);
            } else {
                taskManager.setFailure(task.getException());
            }
        });
        return taskManager.getTask();
    }

    /**
     * Get all the current user's habits
     *
     * @return load task for all user's habits
     */
    public static ServiceTask<List<HabitModel>> getAllForCurrentUser() {
        ServiceTaskManager<List<HabitModel>> taskManager = new ServiceTaskManager<>();
        FirebaseFirestore.getInstance().collection(HABIT_COLLECTION_ID)
                .whereEqualTo("uid", AuthorizationService.getInstance().getCurrentUserId())
                .get()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<HabitModel> models = new ArrayList<>();
                HabitModelMapParser parser = new HabitModelMapParser();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    models.add(parser.parseMap(document.getData(), document.getId()));
                }
                taskManager.setSuccess(models);
            } else {
                taskManager.setFailure(task.getException());
            }
        });
        return taskManager.getTask();
    }

    /**
     * Get all habits that the current user needs to do for the day
     *
     * @return load task for all user's habits they need to do
     */
    public static ServiceTask<List<HabitModel>> getTodoForCurrentUser() {
        ServiceTaskManager<List<HabitModel>> taskManager = new ServiceTaskManager<>();
        FirebaseFirestore.getInstance().collection(HABIT_COLLECTION_ID)
                .whereEqualTo("uid", AuthorizationService.getInstance().getCurrentUserId())
                .whereArrayContainsAny("schedule", new HabitScheduleModelFactory().getQueryForToday())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<HabitModel> models = new ArrayList<>();
                HabitModelMapParser parser = new HabitModelMapParser();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    models.add(parser.parseMap(document.getData(), document.getId()));
                }
                taskManager.setSuccess(models);
            } else {
                taskManager.setFailure(task.getException());
            }
        });
        return taskManager.getTask();
    }

    /**
     * Sanitize a string from the database
     * <p>
     * If the string is null, provide an empty string
     * If the string is greater than the max length truncate it
     *
     * @param s         string to sanitize
     * @param maxLength max length of the string
     * @return sanitized string
     */
    private static String sanitizeStringFromDatabase(String s, int maxLength) {
        if (s == null) return "";
        else if (s.length() > maxLength) return s.substring(0, maxLength);
        else return s;
    }

    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Set the id of the habit
     *
     * @param id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title of the habit
     * <p>
     * Must comply with the following rules:
     * String length is less than IHabitModel.MAX_TITLE_LENGTH
     *
     * @param title to set
     */
    public void setTitle(String title) {
        if (title.length() > IHabitModel.MAX_TITLE_LENGTH)
            throw new IllegalArgumentException("Cannot set title to String of length greater than " + IHabitModel.MAX_TITLE_LENGTH);
        else if (title.trim().length() == 0)
            throw new IllegalArgumentException("Cannot set title to empty string");

        this.title = title;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    /**
     * Set the reason of the habit
     * <p>
     * Must comply with the following rules:
     * String length is less than IHabitModel.MAX_REASON_LENGTH
     *
     * @param reason to set
     */
    public void setReason(String reason) {
        if (reason.length() > IHabitModel.MAX_REASON_LENGTH) {
            throw new IllegalArgumentException("Cannot set reason to String of length greater than " + IHabitModel.MAX_REASON_LENGTH);
        }
        this.reason = reason;
    }

    @Override
    public long getStreak() {
        return this.streak;
    }

    /**
     * Set the streak
     *
     * @param streak Set the streak count
     */
    public void setStreak(long streak) {
        this.streak = streak;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Set the start date of the habit
     *
     * @param date to set
     */
    public void setStartDate(Date date) {
        this.startDate = date;
    }

    @Override
    public Date getLastCompleted() {
        return this.lastCompleted;
    }

    @Override
    public IHabitScheduleModel getSchedule() {
        return this.schedule;
    }

    /**
     * Set the schedule of the habit
     *
     * @param schedule to set
     */
    public void setSchedule(IHabitScheduleModel schedule) {
        this.schedule = schedule;
    }

    /**
     * Commit the habit model to the database, creating a new entry if necessary
     *
     * @return task representing status of save task
     */
    public ServiceTask<Void> commit() {
        // TODO: prevent multiple commits at the same time through the same model
        // TODO: throw if user is not logged in
        ServiceTaskManager<Void> tman = new ServiceTaskManager<>();
        // If this was a new habit model we push a new entry to the database
        if (this.id == null) {
            FirebaseFirestore.getInstance().collection(HABIT_COLLECTION_ID).add(
                    new HabitModelMapParser().parseModel(this)
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    this.id = task.getResult().getId();
                    tman.setSuccess(null);
                } else tman.setFailure(task.getException());
            });
        } else {
            // Otherwise we just set the new model info
            FirebaseFirestore.getInstance().collection(HABIT_COLLECTION_ID)
                    .document(this.id).set(new HabitModelMapParser().parseModel(this)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    tman.setSuccess(null);
                } else tman.setFailure(task.getException());
            });
        }
        return tman.getTask();
    }

    /**
     * Delete the habit model from the database
     *
     * @return task representing status of delete
     */
    public ServiceTask<Void> delete() {
        // TODO: prevent multiple deletes at the same time through the same model
        // TODO: throw if user is not logged in
        ServiceTaskManager<Void> tman = new ServiceTaskManager<>();
        if (this.id != null) {
            // Only delete if it is in the database,
            // this would be better invoked as a cloud function
            FirebaseFirestore.getInstance()
                    .collection("habits")
                    .document(this.id)
                    .delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            tman.setSuccess(null);
                            // Delete all the habit events
                            FirebaseFirestore.getInstance()
                                    .collection("habit_events")
                                    .whereEqualTo("habitID", this.id)
                                    .get()
                                    .addOnSuccessListener(snapshot -> {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : snapshot) {
                                            queryDocumentSnapshot.getReference().delete();
                                        }
                            });
                        }
                        else
                            tman.setFailure(task.getException());
                    }

            );
        }
        return tman.getTask();
    }

    /**
     * Parser for mapping between a map and a habit model
     */
    private static class HabitModelMapParser implements ModelMapParser<HabitModel> {

        @Override
        public HabitModel parseMap(Map<String, Object> map, String id) {
            HabitScheduleModelFactory scheduleFactory = new HabitScheduleModelFactory();
            return new HabitModel(
                    id,
                    (String) map.get("title"),
                    (String) map.get("reason"),
                    DocumentModelSerializer.parseAsDate(map.get("start_date")),
                    DocumentModelSerializer.parseAsDate(map.get("last_completed")),
                    map.get("streak") != null ? (Long) map.get("streak") : 0,
                    scheduleFactory.getModelInstanceFromData((List<String>) map.get("schedule"))
            );
        }

        @Override
        public Map<String, Object> parseModel(HabitModel model) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", model.getTitle());
            map.put("reason", model.getReason());
            map.put("start_date", model.getStartDate());
            map.put("last_completed", model.getLastCompleted());
            map.put("streak", model.getStreak());
            map.put("schedule", model.getSchedule().toList());
            map.put("uid", AuthorizationService.getInstance().getCurrentUserId());
            return map;
        }
    }
}
