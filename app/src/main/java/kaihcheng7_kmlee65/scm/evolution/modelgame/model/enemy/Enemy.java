package kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.AnimationFinishListener;


public abstract class Enemy extends View {
    String enemyName;
    int enemyHP;
    ImageView ivEnemy;
    Drawable enemyDrawable;
    int craftLevel;
    String enemyImageName;
    boolean killedAnimationFinish;

    public int getCraftLevel() {
        return craftLevel;
    }

    public void setCraftLevel(int craftLevel) {
        this.craftLevel = craftLevel;
    }

    Context context;

    public Enemy(Context context) {
        super(context);
        this.context = context;
        killedAnimationFinish =false;
    }
    protected void entry(){
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(ivEnemy);
    }
    protected void hitAnimation(){
        Log.d("Enemy" , "played hit animtaion");
        YoYo.with(Techniques.Shake).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d("Enemy" , "played animtaion");
                    animation.removeListener(this);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            })
            .duration(700)
            .playOn(ivEnemy);
    }

    protected void killedAnimation(final AnimationFinishListener animationFinishListener){
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d("Enemy" , "played animtaion");
                        animation.removeListener(this);
                        animationFinishListener.finish(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(700)
                .playOn(ivEnemy);
    }
    protected void setEnemyName(String enemyName){
        this.enemyName = enemyName;
    }
    protected void setEnemyHP(int enemyHP){
        this.enemyHP= enemyHP;
    }
    protected void setEnemyDrawable(Drawable enemyDrawable){
        this.enemyDrawable = enemyDrawable;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getEnemyHP() {
        return enemyHP;
    }

    public ImageView getIvEnemy() {
        return ivEnemy;
    }

    public void setIvEnemy(ImageView ivEnemy) {
        this.ivEnemy = ivEnemy;
    }

    public Drawable getEnemyDrawable() {
        return enemyDrawable;
    }

    public String getEnemyImageName() {
        return enemyImageName;
    }

    public void setEnemyImageName(String enemyImageName) {
        this.enemyImageName = enemyImageName;
    }
}
