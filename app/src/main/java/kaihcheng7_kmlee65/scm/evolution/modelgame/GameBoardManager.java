package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.modelgame.model.SynthesizeResult;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.GameController;


/**
 * Created by xiaotingtang on 09/11/2016.
 */

public class GameBoardManager {
    private GameBoard gameBoard;
    private CraftSynthesizer synthesizer;
    private Context mContext;
    private GameController gameController;
    private int callOnce = 0;
    public GameBoardManager(Context context, GameBoard board, GameController gameController) {
        gameBoard = board;
        synthesizer = new CraftSynthesizer(context);
        mContext = context;
        this.gameController =gameController;
    }

    private ArrayList<String> attemptSynthesize() {
        assert gameBoard.deployedCraftNames.size() == 2;
        String nameA = gameBoard.deployedCraftNames.get(0);
        String nameB = gameBoard.deployedCraftNames.get(1);
        gameBoard.deployedCraftNames.clear();
        ArrayList<String> result = synthesizer.synthesize(nameA, nameB);
        return result;
    }

    public SynthesizeResult addCraftItem(String name) {
        boolean result = gameBoard.deployCraft(name);
        SynthesizeResult synResult = new SynthesizeResult();
        if (gameBoard.deployedCraftNames.size() == 2) {
            final ArrayList<String> product = attemptSynthesize();

            if (product == null) {
                // Reject
                AnimatorSet.AnimatorListener listener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        reset();
                        if(callOnce == 0) {
                            gameController.setCurrentRoundNum(gameController.getCurrentRoundNum() - 1);
                            if (gameController.getCurrentRoundNum() <= 0)
                                gameController.die();
                            else
                                gameController.updateRoundNum();
                        }
                        callOnce++;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
                gameBoard.moveCenterAndOut(listener);
                callOnce = 0;

            } else {
                // Success
                AnimatorSet.AnimatorListener listener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        reset();
                        gameBoard.showNewCraft(product);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
                gameBoard.moveCenterAndShrink(listener);
                synResult.setSuccess(true);
                synResult.setCrafts(product);


            }

        }

        return synResult;
    }

    private void reset() {
        gameBoard.reset();
    }
}
