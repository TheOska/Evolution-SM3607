package kaihcheng7_kmlee65.scm.evolution.modelgame.model.enemy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.AnimationFinishListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.CraftItem;

public class Boss extends Enemy {
    CraftItem[] requirement;
    private int currentHP;

    public Boss(Context context){
        super(context);
    }

    public Boss(Context context, ImageView ivEnemy, String enemyImageName, int hp, String name, int craftLevel, int position) {
        super(context);
        setEnemyImageName(enemyImageName);
        setEnemyHP(hp);
        setEnemyName(name);
        setIvEnemy(ivEnemy);
        setCraftLevel(craftLevel);
        this.currentHP = hp;
    }
    @Override
    public int getCraftLevel() {
        return super.getCraftLevel();
    }

    @Override
    public void setCraftLevel(int craftLevel) {
        super.setCraftLevel(craftLevel);
    }

    @Override
    protected void hitAnimation() {
        super.hitAnimation();
    }

    @Override
    protected void setEnemyName(String enemyName) {
        super.setEnemyName(enemyName);
    }

    @Override
    protected void setEnemyHP(int enemyHP) {
        super.setEnemyHP(enemyHP);
    }

    @Override
    protected void setEnemyDrawable(Drawable enemyDrawable) {
        super.setEnemyDrawable(enemyDrawable);
    }

    @Override
    public String getEnemyName() {
        return super.getEnemyName();
    }

    @Override
    public int getEnemyHP() {
        return super.getEnemyHP();
    }

    @Override
    public ImageView getIvEnemy() {
        return super.getIvEnemy();
    }

    @Override
    public void setIvEnemy(ImageView ivEnemy) {
        super.setIvEnemy(ivEnemy);
    }

    @Override
    public Drawable getEnemyDrawable() {
        return super.getEnemyDrawable();
    }

    @Override
    public void killedAnimation(final AnimationFinishListener animationFinishListener) {
        super.killedAnimation(animationFinishListener);
    }

    @Override
    public String getEnemyImageName() {
        return super.getEnemyImageName();
    }

    @Override
    public void setEnemyImageName(String enemyImageName) {
        super.setEnemyImageName(enemyImageName);
    }
    public void hit(int damage){
        currentHP -= damage;
        super.hitAnimation();
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    @Override
    public void entry() {
        super.entry();
    }
}
