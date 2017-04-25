package kaihcheng7_kmlee65.scm.evolution.modeltrainning.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import kaihcheng7_kmlee65.scm.evolution.modeltrainning.model.CraftItem;


/**
 * Created by TheOska on 11/6/2016.
 */

public class CraftItemSerialiser implements JsonSerializer {

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        ((CraftItem) src).getCraftName();
        jsonObject.addProperty("craftName", ((CraftItem) src).getCraftName());
        jsonObject.addProperty("craftTag", ((CraftItem) src).getCraftTag());
        jsonObject.addProperty("craftProperty", ((CraftItem) src).getCraftProperty());
        jsonObject.addProperty("craftName", ((CraftItem) src).getCraftName());

        return null;
    }


}
