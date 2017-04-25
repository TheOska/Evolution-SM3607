package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;

import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.ProjectileListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.GameController;

public class GameBoard {

    private ImageView craftItemA;
    private ImageView craftItemB;
    private FrameLayout board;
    public ArrayList<String> deployedCraftNames;
    private Context mContext;
    private Timer timer;
    private GameController gameController;
    public static int callOnce = 0;
    private ArrayList<String> createdCrafts;
    public GameBoard(Context context, ImageView A, ImageView B, FrameLayout aBoard, GameController gameController) {
        craftItemA= A;
        craftItemB = B;
        deployedCraftNames = new ArrayList<>();
        mContext = context;
        board = aBoard;
        this.gameController = gameController;
        createdCrafts = new ArrayList<>();
    }

    public boolean deployCraft(String name) {
        /*Draw Images of the given craft onto either left or right box
        * Args:
        *   name: the name of the craft
        * Return:
        *   true - successfully deployed
        *   false - failed */
        assert deployedCraftNames.size() < 3;
        if (deployedCraftNames.size() == 0) {
            craftItemA.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(name, "drawable", "kaihcheng7_kmlee65.scm.evolution")));
            deployedCraftNames.add(name);
        } else if (deployedCraftNames.size() == 1) {
            craftItemB.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(name, "drawable", "kaihcheng7_kmlee65.scm.evolution")));
            deployedCraftNames.add(name);
        }

        return true;
    }

    public void moveCenterAndOut(AnimatorSet.AnimatorListener listener) {
        AnimatorSet mergeFailAnimatorA = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.merge_failed_a);
        mergeFailAnimatorA.setTarget(craftItemA);
        mergeFailAnimatorA.addListener(listener);
        mergeFailAnimatorA.start();
        AnimatorSet mergeFailAnimatorB = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.merge_failed_b);
        mergeFailAnimatorB.setTarget(craftItemB);
        mergeFailAnimatorB.addListener(listener);
        mergeFailAnimatorB.start();
    }

    public void moveCenterAndShrink(AnimatorSet.AnimatorListener listener) {
        AnimatorSet mergeSuccAnimatorA = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.merge_success_a);
        mergeSuccAnimatorA.setTarget(craftItemA);
        mergeSuccAnimatorA.addListener(listener);
        mergeSuccAnimatorA.start();
        AnimatorSet mergeSuccAnimatorB = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.merge_success_b);
        mergeSuccAnimatorB.setTarget(craftItemB);
        mergeSuccAnimatorB.start();
        mergeSuccAnimatorB.addListener(listener);
    }

    private void showNextCraft(final int pos, final ArrayList<String> crafts) {
        if (crafts.size() == pos) {
            return;
        }
        final ImageView newCraft = new ImageView(mContext);
        newCraft.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(crafts.get(pos), "drawable", "kaihcheng7_kmlee65.scm.evolution")));
        FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) craftItemA.getLayoutParams();
        newCraft.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        board.addView(newCraft);
        newCraft.setTranslationX(500);  //TODO: automatically calculate the center position
        newCraft.setTranslationY(300);

        if (!createdCrafts.contains(crafts.get(pos))) {
            AnimatorSet birthAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.birth);
            birthAnimator.setTarget(newCraft);
            birthAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d("GameBoard" ,"onAnimationStart" );

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeAllListeners();
                    board.removeView(newCraft);
                    showNextCraft(pos+1, crafts);
                    // I DON'T KNOW WHY ALWAYS CALL TWICE...
                    if(callOnce == 0) {
                        Log.d("GameBoard", "onAnimationEnd");
                        gameController.releaseProjectiles(new ProjectileListener() {
                            @Override
                            public void doAfterFinish() {
                                gameController.hitEnemy(1);
                            }
                        });
                    }
//                    gameController.hitEnemy(1);

                    callOnce++;
//                    gameController.setCurrentRoundNum(gameController.getCurrentRoundNum()-1);
//                    if(gameController.getCurrentRoundNum()== 0)
//                        gameController.die();
//                    else {
//                        gameController.updateRoundNum();
//                    }
                }


                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            birthAnimator.start();
            callOnce = 0;
        } else if (createdCrafts.contains(crafts.get(pos))){
            AnimatorSet failBirthAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.fail_birth);
            failBirthAnimator.setTarget(newCraft);
            failBirthAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation.removeAllListeners();
                    board.removeView(newCraft);
                    showNextCraft(pos+1, crafts);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            failBirthAnimator.start();
        }
        createdCrafts.add(crafts.get(pos));

    }

    public void showNewCraft(ArrayList<String> crafts) {
        showNextCraft(0, crafts);
    }

    public void reset() {
        craftItemA.setImageDrawable(null);
        craftItemA.setTranslationX(0);
        craftItemA.setRotation(0);
        craftItemA.setScaleX(1);
        craftItemA.setScaleY(1);
        craftItemB.setImageDrawable(null);
        craftItemB.setTranslationX(0);
        craftItemB.setRotation(0);
        craftItemB.setScaleX(1);
        craftItemB.setScaleY(1);
        deployedCraftNames.clear();
    }


}
