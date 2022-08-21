package org.hostile.rogue.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonChain {

    private final JsonObject jsonObject = new JsonObject();

    /**
     * Adds a property to the JsonObject
     *
     * @param key The property key
     * @param value The String value of the key-value pair
     */
    public JsonChain addProperty(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Adds a property to the JsonObject
     *
     * @param key The property key
     * @param value The boolean value of the key-value pair
     */
    public JsonChain addProperty(String key, boolean value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Adds a property to the JsonObject
     *
     * @param key The property key
     * @param value The boolean value of the key-value pair
     */
    public JsonChain addProperty(String key, Number value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Adds a property to the JsonObject
     *
     * @param key The property key as a number
     * @param value The string value of the key-value pair
     */
    public JsonChain addProperty(Number key, String value) {
        jsonObject.addProperty(String.valueOf(key), value);
        return this;
    }

    /**
     * Adds a property to the JsonObject
     *
     * @param key The property key as a string
     * @param value The JsonElement value of the key-value pair
     */
    public JsonChain addProperty(String key, JsonElement value) {
        jsonObject.add(key, value);
        return this;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}