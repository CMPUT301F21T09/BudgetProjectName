package com.cmput301f21t09.budgetprojectname.services.database;

import com.cmput301f21t09.budgetprojectname.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;

/**
 * Database Collection Specifiers with ids and model types as defined in the database backend
 *
 * @param <T> type of model that the collection references
 */
public class CollectionSpecifier<T> {
    /**
     * Collection Identifier for all habits
     */
    public static CollectionSpecifier<IHabitModel> HABITS = new CollectionSpecifier<>("habits");

    /**
     * Collection Identifier for all habit events
     */
    public static CollectionSpecifier<HabitEventModel> HABIT_EVENTS = new CollectionSpecifier<>("habit_events");

    /**
     * Collection Identifier for all user information not related to authentication
     */
    public static CollectionSpecifier<?> USERS = new CollectionSpecifier<>("users");

    /**
     * The id of the collection as represented in the database
     */
    private final String id;

    /**
     * Constructor for registering a specifier
     *
     * @param id of the collection as defined in the database backend
     */
    private CollectionSpecifier(String id) {
        this.id = id;
    }

    /**
     * The id of the collection as represented in the database
     *
     * @return collection id
     */
    public String getId() {
        return id;
    }
}
