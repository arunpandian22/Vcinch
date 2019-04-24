package me.arun.vcinch.localDb;



import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.arun.vcinch.entities.Datum;

public class Converters {
    @TypeConverter
    public static ArrayList<Datum> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Datum>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList(ArrayList<Datum> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}