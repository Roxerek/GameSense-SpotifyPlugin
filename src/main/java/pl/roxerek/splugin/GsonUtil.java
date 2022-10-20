package pl.roxerek.splugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class GsonUtil {

    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(String game, String event, String firstLine, String secondLine){
        JsonObject dataObject = new JsonObject();
        JsonObject frameObject = new JsonObject();
        frameObject.addProperty("first-line", firstLine);
        frameObject.addProperty("second-line", secondLine);
        dataObject.add("frame", frameObject);

        JsonObject mainObject = new JsonObject();
        mainObject.addProperty("game", game);
        mainObject.addProperty("event", event);
        mainObject.add("data", dataObject);

        return mainObject.toString();
    }

}
