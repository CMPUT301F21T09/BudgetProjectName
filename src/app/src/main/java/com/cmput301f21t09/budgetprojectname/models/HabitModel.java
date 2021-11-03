package com.cmput301f21t09.budgetprojectname.models;
import com.cmput301f21t09.budgetprojectname.services.ServiceTask;
import com.cmput301f21t09.budgetprojectname.services.ServiceTaskManager;
import com.cmput301f21t09.budgetprojectname.services.database.CollectionSpecifier;
import com.cmput301f21t09.budgetprojectname.services.database.DatabaseService;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.DocumentModelSerializer;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.ModelMapParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//TODO: Add Frequency
public class HabitModel implements IHabitModel {
    private String title;
    private String ID;
    private String reason;
    private int streak;
    private Date startDate;
    private Date lastCompleted;

    private HabitModel(String ID, String title, String reason, Date startDate, Date lastCompleted, int streak) {
        setID(ID);
        setTitle(title);
        setReason(reason);
        setStartDate(startDate);
        setLastCompleted(lastCompleted);
        setStreak(streak);
    }

    public static ServiceTask<HabitModel> getNewInstance() {
        ServiceTaskManager<HabitModel> taskman = new ServiceTaskManager<>();
        taskman.setSuccess(new HabitModel(null, "", "", new Date(), null, 0));
        return taskman.getTask();
    }

    public static ServiceTask<HabitModel> getInstanceById(String ID) {
        return DatabaseService.getInstance().getCollection(CollectionSpecifier.HABITS)
                .getDocument(ID)
                .retrieve(DocumentModelSerializer.getInstance(new HabitModelMapParser()));
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStreak() {
        return this.streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getLastCompleted() {
        return this.lastCompleted;
    }

    public void setLastCompleted(Date date) {
        this.lastCompleted = date;
    }

    public ServiceTask<Void> commit() {
        // TODO: prevent multiple commits at the same time through the same model
        DocumentModelSerializer<HabitModel> serializer = DocumentModelSerializer.getInstance(new HabitModelMapParser());
        if (this.ID == null) {
            ServiceTask<String> saveTask = DatabaseService.getInstance().getCollection(CollectionSpecifier.HABITS).push(this, serializer);
            saveTask.addTaskCompleteListener(task -> {
                if (task.isSuccessful()) {
                    this.ID = task.getResult();
                }
            });
            return ServiceTaskManager.fromTask(saveTask, s -> null, e -> e);
        }
        return DatabaseService.getInstance().getCollection(CollectionSpecifier.HABITS)
                .getDocument(this.ID)
                .save(this, serializer);
    }

    private static class HabitModelMapParser implements ModelMapParser<HabitModel> {

        @Override
        public HabitModel parseMap(Map<String, Object> map) {
            return new HabitModel(
                    (String) map.get("id"),
                    (String) map.get("title"),
                    (String) map.get("reason"),
                    (Date) map.get("start_date"),
                    (Date) map.get("last_completed"),
                    map.get("streak") != null ? (Integer) map.get("streak") : 0
            );
        }

        @Override
        public Map<String, Object> parseModel(HabitModel model) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", model.getID());
            map.put("title", model.getTitle());
            map.put("reason", model.getReason());
            map.put("start_date", model.getStartDate());
            map.put("last_completed", model.getStartDate());
            map.put("streak", model.getStreak());
            return map;
        }
    }
}
