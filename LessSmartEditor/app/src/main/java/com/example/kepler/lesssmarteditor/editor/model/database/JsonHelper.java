package com.example.kepler.lesssmarteditor.editor.model.database;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kepler on 2017-05-28.
 */

public class JsonHelper{
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(BaseComponent.class, new ItemDeserializer())
            .create();

    public static String List2Json(List<BaseComponent> list){
        return gson.toJson(list);
    }

    public static List<BaseComponent> Json2List(String json){
        JsonElement element = new JsonParser().parse(json);
        JsonArray arr = element.getAsJsonArray();

        List<BaseComponent> list = new ArrayList<>();
        for(int i=0; i<arr.size(); i++){
            JsonElement current = arr.get(i);
            BaseComponent baseComponent = gson.fromJson(current,BaseComponent.class);
            list.add(baseComponent);
        }
        return list;
    }


    static class ItemDeserializer implements JsonDeserializer<BaseComponent> {
        @Override
        public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if(json==null)
                return null;
            else{
                JsonObject obj = json.getAsJsonObject();
                JsonElement ele = obj.get("type");
                int t = ele.getAsInt();

                switch(t){
                    case 0:
                        return context.deserialize(obj, TextComponent.class);
                    case 1:
                        return context.deserialize(obj, ImageComponent.class);
                    case 2:
                        return context.deserialize(obj, MapComponent.class);
                    case 3:
                        return context.deserialize(obj, TitleComponent.class);
                    default:
                        return null;
                }
            }
        }
    }
}