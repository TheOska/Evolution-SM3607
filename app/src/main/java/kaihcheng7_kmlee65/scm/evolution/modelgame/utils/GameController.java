package kaihcheng7_kmlee65.scm.evolution.modelgame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skyfishjy.library.RippleBackground;

import java.lang.reflect.Type;
import java.util.ArrayList;


import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.AnimationFinishListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.InitBottomCraftListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.ProjectileListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.LoseActivity;
import kaihcheng7_kmlee65.scm.evolution.modelgame.WinActivity;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.Stage;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy.Boss;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy.Enemy;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy.Monster;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by TheOska on 11/4/2016.
 */

public class GameController  {
    private TextView tvRound, tvStage, tvUnknown;
    private int currentRoundNum;
    private RippleBackground rippleLock1, rippleLock2, rippleLock3;
    private TextView tvLock1, tvLock2, tvLock3;
    private LinearLayout monsterField;
    private Context context;
    private Activity activity;
    private final String stagePrefix = "Stage: ";
    private final String SHARED_PREFS_NAME= "evolution";
    private final int TOTAL_STAGES = 3;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int currentStageNum;
    private int FINAL_STAGE = 3;
    private ArrayList<Stage> stageArrayList;
    private final String roundPrefix = "Remaining round: ";
    private int score;
    private ImageView ivBGScene, ivEnemyPos1, ivEnemyPos2, ivEnemyPos3;
    private IconRoundCornerProgressBar hp1,hp2,hp3;
    private final int WAIT_ANIMATION_SEC = 700;
    private final int ANIMATION_INTERVAL = 50;
    private ArrayList<Enemy> onStageEnemies;
    private int onStageEnemy;
    private static final int POSITION_1 = 0;
    private static final int POSITION_2 = 1;
    private static final int POSITION_3 = 2;
    private int attackPos = POSITION_2;
    private LinearLayout linearLayoutPos2;
    private Animation anim, stopAnim;
    public GameController(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
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
        initStage();
        stageStart();
    }

    private void initStage() {
        tvStage = (TextView) activity.findViewById(R.id.stage_hint);
        tvStage.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));

        tvUnknown = (TextView) activity.findViewById(R.id.unKnownEnemy);
        tvUnknown.setVisibility(View.GONE);
        tvUnknown.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));

        currentStageNum = 0;
        stageArrayList = new ArrayList<>();
        initHPbar();
        initLockText();

        initRippleLock();
        initRoundNum();
        setAttackPos(POSITION_2);
        ivBGScene = (ImageView) activity.findViewById(R.id.scene_background);
        ivEnemyPos1 = (ImageView) activity.findViewById(R.id.enemy_pos_1);
        ivEnemyPos2 = (ImageView) activity.findViewById(R.id.enemy_pos_2);
        ivEnemyPos3 = (ImageView) activity.findViewById(R.id.enemy_pos_3);
        linearLayoutPos2 = (LinearLayout) activity.findViewById(R.id.ll_pos_2);

        changeLockListener();

        Stage stage1 = new Stage(1,0, false ,false, false, "scene_1", ivBGScene,1);

        Monster monster_1 = new Monster(context, ivEnemyPos2,"rsz_monster_1",1, "monster 1",0, POSITION_2);
        Monster monster_2 = new Monster(context, ivEnemyPos2,"rsz_monster_2",3, "monster 2",0, POSITION_2);
        Monster monster_3 = new Monster(context, ivEnemyPos1,"rsz_monster_3",1, "monster 3",0, POSITION_1 );
        Monster monster_4 = new Monster(context, ivEnemyPos2,"rsz_monster_4",1, "monster 4",0, POSITION_2 );
        Monster monster_5 = new Monster(context, ivEnemyPos3,"rsz_monster_5",1, "monster 5",0, POSITION_3 );
        Boss boss         = new Boss(context, ivEnemyPos2, "boss",2,"boss",0, POSITION_2);

        ArrayList<Monster> stage1_monster = new ArrayList<>();
        stage1_monster.add(0,monster_1);
        stage1.setMonsters(stage1_monster);

        Stage stage2 = new Stage(1,0, false ,false, false, "scene_2", ivBGScene,4);
        ArrayList<Monster> stage2_monster = new ArrayList<>();
        stage2_monster.add(0,monster_2);
        stage2.setMonsters(stage2_monster);

        Stage stage3 = new Stage(3,0, false ,false, false, "scene_3", ivBGScene, 3);
        ArrayList<Monster> stage3_monster = new ArrayList<>();
        stage3_monster.add(0,monster_3);
        stage3_monster.add(1,monster_4);
        stage3_monster.add(2,monster_5);
        stage3.setMonsters(stage3_monster);

        Stage stage4 = new Stage(1,0, false, false, false, "scene_4", ivBGScene, 4);
        ArrayList<Boss> stage4_boss = new ArrayList<>();
        stage4_boss.add(boss);
        stage4.setBosses(stage4_boss);

        stageArrayList.add(0,stage1);
        stageArrayList.add(1,stage2);
        stageArrayList.add(2,stage3);
        stageArrayList.add(3,stage4);

        monsterField = (LinearLayout) activity.findViewById(R.id.monster_field);
    }

    private void initRoundNum() {

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        stopAnim = new AlphaAnimation(0.0f, 0.0f);
        tvRound = (TextView) activity.findViewById(R.id.round_remain);
        tvRound.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));

    }

    private void initRippleLock() {
        rippleLock1 = (RippleBackground)activity.findViewById(R.id.lock_ripple_1);
        rippleLock2 = (RippleBackground)activity.findViewById(R.id.lock_ripple_2);
        rippleLock3 = (RippleBackground)activity.findViewById(R.id.lock_ripple_3);

        rippleLock2.startRippleAnimation();
    }

    private void initLockText() {
        tvLock1 = (TextView) activity.findViewById(R.id.lock_text_1);
        tvLock2 = (TextView) activity.findViewById(R.id.lock_text_2);
        tvLock3 = (TextView) activity.findViewById(R.id.lock_text_3);

        tvLock1.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));
        tvLock2.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));
        tvLock3.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/lock.ttf"));

        tvLock1.setVisibility(View.INVISIBLE);
        tvLock3.setVisibility(View.INVISIBLE);
    }

    private void initHPbar() {
        hp1 = (IconRoundCornerProgressBar) activity.findViewById(R.id.hp_1);
        hp2 = (IconRoundCornerProgressBar) activity.findViewById(R.id.hp_2);
        hp3 = (IconRoundCornerProgressBar) activity.findViewById(R.id.hp_3);

        hp1.setProgressColor(context.getResources().getColor(R.color.custom_progress_blue_progress));
        hp1.setIconBackgroundColor(context.getResources().getColor(R.color.custom_progress_blue_header));
        hp1.setProgressBackgroundColor(context.getResources().getColor(R.color.custom_progress_background));

        hp2.setProgressColor(context.getResources().getColor(R.color.custom_progress_blue_progress));
        hp2.setIconBackgroundColor(context.getResources().getColor(R.color.custom_progress_blue_header));
        hp2.setProgressBackgroundColor(context.getResources().getColor(R.color.custom_progress_background));

        hp3.setProgressColor(context.getResources().getColor(R.color.custom_progress_blue_progress));
        hp3.setIconBackgroundColor(context.getResources().getColor(R.color.custom_progress_blue_header));
        hp3.setProgressBackgroundColor(context.getResources().getColor(R.color.custom_progress_background));

        hp1.setVisibility(View.INVISIBLE);
        hp2.setVisibility(View.INVISIBLE);
        hp3.setVisibility(View.INVISIBLE);
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
    public void stageStart(){
        startLock();
        onStageEnemies = new ArrayList<>();
        Stage currentStage = stageArrayList.get(currentStageNum);
        currentRoundNum = currentStage.getRoundNum();
        updateRoundNum();
        Log.d("GameController", "current stage" + currentStageNum);
        // load Background
        Glide.with(activity)
                .load("")
                .placeholder(context.getResources().getIdentifier(currentStage.getBgName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                .centerCrop()
                .into(ivBGScene);
        switch (stageArrayList.get(currentStageNum).getNumOfEnemy()){
            case 1:
                if(currentStageNum != FINAL_STAGE) {
                    Monster currentMonster = currentStage.getMonsters().get(0);
                    Glide.with(activity)
                            .load("")
                            .placeholder(context.getResources().getIdentifier(currentMonster.getEnemyImageName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                            .centerCrop()
                            .into(ivEnemyPos2);
                    currentMonster.entry();
                    newHpBar(hp2, currentStage.getMonsters().get(0).getEnemyHP());

                    onStageEnemies.add(POSITION_1, null);
                    onStageEnemies.add(POSITION_2, currentStage.getMonsters().get(0));
                    onStageEnemies.add(POSITION_3, null);
                    onStageEnemy = 1;
                }else{
                    Log.d("GameController","boss stage");
                    setAttackPos(POSITION_2);
                    ivEnemyPos2.setVisibility(View.GONE);
                    tvUnknown.setVisibility(View.VISIBLE);
                    Boss currentBoss = currentStage.getBosses().get(0);
                    linearLayoutPos2.setY(-50f);
                    rippleLock2.setVisibility(View.GONE);
                    Glide.with(activity)
                            .load("")
                            .placeholder(context.getResources().getIdentifier(currentBoss.getEnemyImageName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                            .centerCrop()
                            .into(ivEnemyPos2);

                    newHpBar(hp2, currentStage.getBosses().get(0).getEnemyHP());

                    onStageEnemies.add(POSITION_1, null);
                    onStageEnemies.add(POSITION_2, currentStage.getBosses().get(0));
                    onStageEnemies.add(POSITION_3, null);
                    onStageEnemy = 1;

                }
                break;
            case 3:
                Log.d("GameController", "case 3");
                Monster currentMonster1 = currentStage.getMonsters().get(0);
                Monster currentMonster2 = currentStage.getMonsters().get(1);
                Monster currentMonster3 = currentStage.getMonsters().get(2);

                Glide.with(activity)
                        .load("")
                        .placeholder(context.getResources().getIdentifier(currentStage.getMonsters().get(0).getEnemyImageName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                        .centerCrop()
                        .into(ivEnemyPos1);
                newHpBar(hp1, currentStage.getMonsters().get(0).getEnemyHP()) ;
                currentMonster1.entry();

                Glide.with(activity)
                        .load("")
                        .placeholder(context.getResources().getIdentifier(currentStage.getMonsters().get(1).getEnemyImageName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                        .centerCrop()
                        .into(ivEnemyPos2);
                newHpBar(hp2, currentStage.getMonsters().get(1).getEnemyHP()) ;
                currentMonster2.entry();

                Glide.with(activity)
                        .load("")
                        .placeholder(context.getResources().getIdentifier(currentStage.getMonsters().get(2).getEnemyImageName(), "drawable", "kaihcheng7_kmlee65.scm.evolution"))
                        .centerCrop()
                        .into(ivEnemyPos3);
                newHpBar(hp3, currentStage.getMonsters().get(2).getEnemyHP()) ;
                currentMonster3.entry();

                currentMonster1.entry();
                currentMonster2.entry();
                currentMonster3.entry();

                onStageEnemies.add(POSITION_1,currentStage.getMonsters().get(0));
                onStageEnemies.add(POSITION_2, currentStage.getMonsters().get(1));
                onStageEnemies.add(POSITION_3,currentStage.getMonsters().get(2));
                onStageEnemy = 3;
                break;
        }
    }

    public void updateRoundNum() {
        tvRound.setText(roundPrefix + currentRoundNum);
        if(currentRoundNum <= 1){
            tvRound.startAnimation(anim);
        }else{
            tvRound.startAnimation(stopAnim);
        }
    }

    private void newHpBar(IconRoundCornerProgressBar hpBar, int maxHp) {
        hpBar.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(hpBar);
        hpBar.setMax(maxHp);
        hpBar.setProgress(maxHp);
    }
    private void fadeOutHpBar(IconRoundCornerProgressBar hpBar){
        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .playOn(hpBar);
    }
    public void hitEnemy(int damage){
        if(currentStageNum != FINAL_STAGE) {
            Monster currentMonster = (Monster) onStageEnemies.get(attackPos);
            currentMonster.hit(damage);
            switch (attackPos) {
                case POSITION_1:
                    hp1.setProgress(currentMonster.getCurrentHP());
                    break;
                case POSITION_2:
                    hp2.setProgress(currentMonster.getCurrentHP());
                    break;
                case POSITION_3:
                    hp3.setProgress(currentMonster.getCurrentHP());
                    break;
            }
            if (currentMonster.getCurrentHP() <= 0) {
                onStageEnemy -= 1;
                fadeOutEnemyHpBar();
                currentMonster.killedAnimation(new AnimationFinishListener() {
                    // played kill animation
                    @Override
                    public void finish(boolean played) {
                        if (onStageEnemy == 0) {
//                            animationInterval();
                            clearStage();
                        } else {
                            Log.v("GameController", "in the else finish ");
                            forceNextLock();
                        }
                    }
                });
            }
        }else{

            tvUnknown.setVisibility(View.GONE);
            ivEnemyPos2.setVisibility(View.VISIBLE);
            rippleLock2.setVisibility(View.VISIBLE);
            Boss currentBoss = (Boss) onStageEnemies.get(attackPos);
            currentBoss.hit(damage);
            hp2.setProgress(currentBoss.getCurrentHP());
            if (currentBoss.getCurrentHP() <= 0) {
                onStageEnemy -= 1;
                fadeOutEnemyHpBar();
                currentBoss.killedAnimation(new AnimationFinishListener() {
                    // played kill animation
                    @Override
                    public void finish(boolean played) {
                        if (onStageEnemy == 0) {
//                            animationInterval();
                            winStage();
                        } else {
                            Log.v("GameController", "in the else finish ");
                            forceNextLock();
                        }
                    }
                });
            }
        }

    }

    private void winStage() {
        activity.startActivity(new Intent(activity , WinActivity.class));
    }

    private void fadeOutEnemyHpBar() {
        switch (attackPos){
            case POSITION_1:
                fadeOutHpBar(hp1);
                break;
            case POSITION_2:
                fadeOutHpBar(hp2);
                break;
            case POSITION_3:
                fadeOutHpBar(hp3);
                break;
        }
    }

    private void clearStage() {
        int stageNum = currentStageNum+1;
        tvStage.setText(stagePrefix+ stageNum);
        final Stage currentStage = stageArrayList.get(currentStageNum);
        currentStage.setClear(true);
        currentStage.exitScene(new AnimationFinishListener() {
            @Override
            public void finish(boolean played) {
                currentStageNum += 1;
                stageStart();
            }
        });
        stopLock();
    }
    public void stopLock(){
        switch (attackPos){
            case POSITION_1:
                rippleLock1.stopRippleAnimation();
                tvLock1.setVisibility(View.INVISIBLE);
                break;
            case POSITION_2:
                rippleLock2.stopRippleAnimation();
                tvLock2.setVisibility(View.INVISIBLE);
                break;
            case POSITION_3:
                rippleLock3.stopRippleAnimation();
                tvLock3.setVisibility(View.INVISIBLE);
                break;
        }
    }
    public void startLock(){
        switch (attackPos){
            case POSITION_1:
                rippleLock1.startRippleAnimation();
                tvLock1.setVisibility(View.VISIBLE);
                break;
            case POSITION_2:
                rippleLock2.startRippleAnimation();
                tvLock2.setVisibility(View.VISIBLE);
                break;
            case POSITION_3:
                rippleLock3.startRippleAnimation();
                tvLock3.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void setAttackPos(int newPos){
        stopLock();
        attackPos = newPos;
        startLock();
    }
    public void changeLockListener(){
        ivEnemyPos1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setAttackPos(POSITION_1);
            }
        });
        ivEnemyPos2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setAttackPos(POSITION_2);
            }
        });
        ivEnemyPos3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setAttackPos(POSITION_3);
            }
        });
    }
    public void forceNextLock(){
        for(int i = POSITION_1; i <= POSITION_3; i++){
            int leftPos = attackPos - 1;
            if(leftPos < POSITION_1){
                leftPos = POSITION_3;
            }
            if(!(onStageEnemies.get(leftPos).getEnemyHP() < 0)){
                setAttackPos(leftPos);
                break;
            }
        }
    }

    public int getCurrentRoundNum() {
        return currentRoundNum;
    }

    public void setCurrentRoundNum(int currentRoundNum) {
        this.currentRoundNum = currentRoundNum;
    }
    public void die(){
        Log.d("GameController","die");
        Log.d("GameController","die " + currentRoundNum);
        Toast.makeText(context,"You lose", Toast.LENGTH_LONG);
        LinearLayout workspace = (LinearLayout) activity.findViewById(R.id.workspace);
        activity.startActivity(new Intent(activity , LoseActivity.class));

    }

    public void releaseProjectiles(final ProjectileListener listener) {
        final ImageView p_blue = (ImageView) activity.findViewById(R.id.projectile_blue);
        final ImageView p_green = (ImageView) activity.findViewById(R.id.projectile_green);
        final ImageView p_yellow = (ImageView) activity.findViewById(R.id.projectile_yellow);
        final ImageView p_red = (ImageView) activity.findViewById(R.id.projectile_red);
        final ImageView p_purple = (ImageView) activity.findViewById(R.id.projectile_purple);

        final ArrayList<ImageView> projectiles = new ArrayList<>();
        projectiles.add(p_blue);
        projectiles.add(p_green);
        projectiles.add(p_yellow);
        projectiles.add(p_red);
        projectiles.add(p_purple);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int monster1X = metrics.widthPixels/6;;
        int monster1Y = monsterField.getHeight()/2-100;
        int monster2X = metrics.widthPixels/2;
        int monster2Y = monsterField.getHeight()/2-100;
        int monster3X = metrics.widthPixels*5/6;
        int monster3Y = monsterField.getHeight()/2-100;


        for (int i = 0; i < projectiles.size(); i++) {
            final int step = i;
            int projectileX = 200*i+50;
            int projectileY = 580;

            projectiles.get(i).setVisibility(View.VISIBLE);
            projectiles.get(i).setTranslationX(projectileX);
            projectiles.get(i).setTranslationY(projectileY);

            TranslateAnimation ta = null;
            switch (attackPos){
                case POSITION_1:
                    ta = new TranslateAnimation(0, monster1X-projectileX, 0, monster1Y-projectileY);
                    break;
                case POSITION_2:
                    ta = new TranslateAnimation(0, monster2X-projectileX, 0, monster2Y-projectileY);
                    break;
                case POSITION_3:
                    ta = new TranslateAnimation(0, monster3X-projectileX, 0, monster3Y-projectileY);
                    break;
            }
            if (ta == null){
                return;
            }
            ta.setDuration(1000);
            ta.setFillAfter(false);
            ta.setRepeatCount(0);
            ta.setRepeatMode(Animation.REVERSE);
            ta.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    return input*input*input;
                }
            });

            if (i == projectiles.size()-1){ // The last animation
                ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        listener.doAfterFinish();
                        setCurrentRoundNum(getCurrentRoundNum()-1);
                        if(getCurrentRoundNum() < 0)
                            die();
                        else {
                            Log.d("GameController","updateRoundnum");
                            updateRoundNum();
                        }
                        projectiles.get(step).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        projectiles.get(step).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }


            projectiles.get(i).startAnimation(ta);


        }


    }
}
