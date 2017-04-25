package kaihcheng7_kmlee65.scm.evolution.modelgame.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.AnimationFinishListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy.Boss;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy.Monster;


/**
 * Created by TheOska on 11/17/2016.
 */

public class Stage {
    private int numOfEnemy;
    private int craftRequirementLevel;
    private boolean haveLimitedTime;
    private boolean haveCraftRequirement;
    private boolean isClear;
    private ArrayList<Monster> monsters;
    private ArrayList<Boss> bosses;
    private Drawable drawableBg;
    private String bgName;
    private ImageView ivBG;
    private int roundNum;
    public Stage(int numOfEnemy, int craftRequirementLevel, boolean haveLimitedTime , boolean haveCraftRequirement, boolean isClear, Drawable drawableBg, ImageView ivBG){
        this.numOfEnemy = numOfEnemy;
        this.craftRequirementLevel = craftRequirementLevel;
        this.haveLimitedTime = haveLimitedTime;
        this.haveCraftRequirement = haveCraftRequirement;
        this.isClear = isClear;
        this.drawableBg = drawableBg;
        this.ivBG = ivBG;
    }

    public Stage(int numOfEnemy, int craftRequirementLevel, boolean haveLimitedTime , boolean haveCraftRequirement, boolean isClear, String bgName, ImageView ivBG, int roundNum){
        this.numOfEnemy = numOfEnemy;
        this.craftRequirementLevel = craftRequirementLevel;
        this.haveLimitedTime = haveLimitedTime;
        this.haveCraftRequirement = haveCraftRequirement;
        this.isClear = isClear;
        this.bgName = bgName;
        this.ivBG = ivBG;
        this.roundNum = roundNum;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    public int getNumOfEnemy() {
        return numOfEnemy;
    }

    public void setNumOfEnemy(int numOfEnemy) {
        this.numOfEnemy = numOfEnemy;
    }

    public int getCraftRequirementLevel() {
        return craftRequirementLevel;
    }

    public void setCraftRequirementLevel(int craftRequirementLevel) {
        this.craftRequirementLevel = craftRequirementLevel;
    }

    public boolean isHaveLimitedTime() {
        return haveLimitedTime;
    }

    public void setHaveLimitedTime(boolean haveLimitedTime) {
        this.haveLimitedTime = haveLimitedTime;
    }

    public boolean isHaveCraftRequirement() {
        return haveCraftRequirement;
    }

    public void setHaveCraftRequirement(boolean haveCraftRequirement) {
        this.haveCraftRequirement = haveCraftRequirement;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public Drawable getDrawableBg() {
        return drawableBg;
    }

    public void setDrawableBg(Drawable drawableBg) {
        this.drawableBg = drawableBg;
    }

    public ImageView getIvBG() {
        return ivBG;
    }

    public void setIvBG(ImageView ivBG) {
        this.ivBG = ivBG;
    }

    public void setBosses(ArrayList<Boss> bosses) {
        this.bosses = bosses;
    }

    public String getBgName() {
        return bgName;
    }

    public void setBgName(String bgName) {
        this.bgName = bgName;
    }

    public void exitScene(final AnimationFinishListener animationFinishListener){
        YoYo.with(Techniques.TakingOff).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                animationFinishListener.finish(true);
                enterScene();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
        .duration(700)
        .playOn(ivBG);
    }
    public void exitScene(){
        YoYo.with(Techniques.TakingOff)
                .duration(700)
                .playOn(ivBG);
    }
    public void enterScene(){
        YoYo.with(Techniques.Landing)
                .duration(700)
                .playOn(ivBG);
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }
}
