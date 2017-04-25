package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.GeneralRecyclerTouchListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.Listener.InitBottomCraftListener;
import kaihcheng7_kmlee65.scm.evolution.modelgame.adapter.BottomItemAdapter;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.CraftItem;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.SynthesizeResult;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.CraftUtil;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.GameController;
import kaihcheng7_kmlee65.scm.evolution.modelgame.utils.SoundManger;


public class GamingMainActivity extends AppCompatActivity {
    private LinearLayout workspace;
    private RecyclerView rvItems;
    private LinearLayoutManager  itemManger;
    private BottomItemAdapter bottomItemAdapter;

    private ArrayList<CraftItem> bottomBarCrafts;
    private ArrayList<String> bottomBarCraftNames;

    private SoundManger mSoundManger;
    private GameController gameController;
    private GameBoardManager gbm;
    private GameBoard gameBoard;
    private TextView tvTime;
    private int time;
    private Handler timeHandle = new Handler();
    private TextView tvScore;
    ArrayList<Integer> initialDrawables;
    ArrayList<String> initialNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBeforeShowIntroPage();
        initUtils();
        calCraftSize();
        initViews();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mSoundManger = new SoundManger(getApplication());
        mSoundManger.startBgMusic();
    }

    private void checkBeforeShowIntroPage() {
        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
//                if (isFirstStart) {
                if (true) {

                    //  Launch app intro
                    Intent i = new Intent(GamingMainActivity.this, IntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
    }

    private void initUtils() {
        gameController = new GameController(getApplication(), this);
        gameController.stageStart();
        time = 50;
    }

    private void calCraftSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }


    private void initViews() {
        tvScore = (TextView) findViewById(R.id.score);
        tvScore.setText(gameController.getScore()+"/450");
        workspace = (LinearLayout) findViewById(R.id.workspace);

        initialDrawables = new ArrayList<Integer>();
        initialNames = new ArrayList<String>();

        bottomBarCrafts = new ArrayList<CraftItem>();
        bottomBarCraftNames = new ArrayList<String>();
//
//        if(gameController.getScore() != 5){
//            loadData();
//        }else{
            // initialize data
        initialDrawables = initImageData();
        initialNames = initTextData();
//        }
        initBottomItem(initialDrawables, initialNames);

        gameBoard = new GameBoard(this, (ImageView)findViewById(R.id.craftA), (ImageView)findViewById(R.id.craftB), (FrameLayout)findViewById(R.id.gameboard), gameController);
        gbm = new GameBoardManager(this, gameBoard, gameController);
    }

    private void loadData() {
        gameController.loadBottomBarCrafts(new InitBottomCraftListener() {
            @Override
            public void finish(ArrayList<String> bottomBarCraftName, ArrayList<Integer> bottomBarCraftDrawable) {
                initialDrawables = bottomBarCraftDrawable;
                initialNames = bottomBarCraftName;
            }
        });
    }


    private ArrayList<String> initTextData() {
        ArrayList<String> source = new ArrayList<String>();
        source.add("water");
        source.add("sun");
        source.add("earth");
        source.add("air");
        source.add("fire");
        return source;
    }

    private ArrayList<Integer> initImageData() {
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(R.drawable.water);
        source.add(R.drawable.sun);
        source.add(R.drawable.earth);
        source.add(R.drawable.air);
        source.add(R.drawable.fire);
        return source;
    }

    private void initBottomItem(final ArrayList<Integer> initialDrawables, final ArrayList<String> initialNames) {
        rvItems = (RecyclerView) findViewById(R.id.rv_item);
        itemManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bottomItemAdapter = new BottomItemAdapter(bottomBarCrafts, this);

        rvItems.setLayoutManager(itemManger);
        rvItems.setAdapter(bottomItemAdapter);
        rvItems.addOnItemTouchListener(new GeneralRecyclerTouchListener(this, rvItems, new GeneralRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position, float x, float y) {
                mSoundManger.playSound(SoundManger.SOUND_DRAG_OUT);
                String name = bottomBarCrafts.get(position).getCraftName();
                SynthesizeResult result = gbm.addCraftItem(name);
                if (result.isSuccess()) {
                    for (int i = 0; i < result.getCrafts().size(); i++) {
                        if (!bottomBarCraftNames.contains(result.getCrafts().get(i)) ) {
                            // New craft
                            bottomBarCraftNames.add(result.getCrafts().get(i));
                            bottomBarCrafts.add(CraftUtil.newCraft(getApplicationContext(), result.getCrafts().get(i)));
                            bottomItemAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }
        }));

        assert initialDrawables.size() == initialNames.size();

        for (int i=0; i<initialDrawables.size(); i++) {
            Drawable drawable = getResources().getDrawable(initialDrawables.get(i));
            String name = initialNames.get(i);

            CraftItem item = new CraftItem(this);
            item.setCraftName(name);
            item.setCraftProperty(name);
            item.setCraftDrawable(drawable);
            item.setCraftTag(name);

            bottomBarCrafts.add(item);
            bottomBarCraftNames.add(name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundManger.startBgMusic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        gameController.save(bottomBarCraftNames);

        mSoundManger.stopBgMusic();
//        gameController.save(bottomBarCrafts,instantiatedCraftList,bottomBarCraftNames);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSoundManger.stopBgMusic();

    }


}
