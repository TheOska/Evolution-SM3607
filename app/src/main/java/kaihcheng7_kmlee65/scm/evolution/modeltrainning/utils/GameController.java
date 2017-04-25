package kaihcheng7_kmlee65.scm.evolution.modeltrainning.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


import kaihcheng7_kmlee65.scm.evolution.modeltrainning.Listener.InitBottomCraftListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TheOska on 11/4/2016.
 */

public class GameController  {

    Context context;
    private final String SHARED_PREFS_NAME= "evolution";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int score;

    public GameController(Context context){
        this.context = context;

        prefs= context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();

        if(!haveSharedPref()){
            editor.putBoolean("initialized", true);
            editor.putInt("score", 5);
            score = 5;
            editor.putString("bottomBarCraftNames", "");
            editor.commit();
        }else{
            // set  the score
            score = prefs.getInt("score", -1);
        }
    }

    private boolean haveSharedPref() {
        if(prefs.contains("initialized")) return true;
        else return false;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
       return score;
    }

    public void save(ArrayList<String> bottomBarCraftNames){
        Gson gson = new Gson();
        String jsonBottomBarCraftNames = gson.toJson(bottomBarCraftNames);
        editor.putString("bottomBarCraftNames", jsonBottomBarCraftNames);
        editor.putInt("score", score);
        editor.commit();
    }
    public void loadBottomBarCrafts(InitBottomCraftListener listener){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        String jsonData = prefs.getString("bottomBarCraftNames","");
        ArrayList<String> bottomBarCraftName = gson.fromJson(jsonData, type);
        ArrayList<Integer> bottomBarCraftDrawable= new ArrayList<>();
        for(int i = 0; i< bottomBarCraftName.size(); i++){
            Log.d("gamecontroller", "bottomBarCraftName.get(i)" + bottomBarCraftName.get(i));
            int resID = context.getResources().getIdentifier(bottomBarCraftName.get(i) , "drawable", context.getPackageName());
            bottomBarCraftDrawable.add(i, resID);
        }
        listener.finish(bottomBarCraftName, bottomBarCraftDrawable);
    }

}
