package com.cmput301f21t09.budgetprojectname.services.database.serializers;

import java.util.Map;

/**
 * Interface for mapping between a given model type and a key-value map
 * @param <T> model type
 */
public interface ModelMapParser<T> {
    /**
     * Parse a map to a given model
     * @param map to parse
     * @return model generated from map
     */
    T parseMap(Map<String, Object> map);

    /**
     * Generate a map of key-value pairs from a model
     * @param model to parse
     * @return generated map
     */
    Map<String, Object> parseModel(T model);
}
