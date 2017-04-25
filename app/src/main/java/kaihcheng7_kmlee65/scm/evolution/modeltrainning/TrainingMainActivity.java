package kaihcheng7_kmlee65.scm.evolution.modeltrainning;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.Listener.GeneralRecyclerTouchListener;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.Listener.InitBottomCraftListener;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.adapter.BottomItemAdapter;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.model.CraftItem;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.utils.GameController;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.utils.SoundManger;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class TrainingMainActivity extends AppCompatActivity implements ViewGroup.OnTouchListener {
    private FrameLayout flWorkspace;
    private RecyclerView rvItems;
    private LinearLayoutManager  itemManger;
    private BottomItemAdapter bottomItemAdapter;
    private CraftSynthesizer synthesizer;
    private CraftItem dispatchingItem;

    private int craftItemSize;
    private float dX;
    private float dY;
    private int lastAction;
    private boolean dispatching = false;

    private ArrayList<CraftItem> bottomBarCrafts;
    private ArrayList<CraftItem> collidingItems;
    private ArrayList<CraftItem> instantiatedCraftList;
    private ArrayList<String> bottomBarCraftNames;

    private SoundManger mSoundManger;
    private GameController gameController;
    private Rect outRect = new Rect();
    private int[] location = new int[2];
    private TextView tvScore;
    ArrayList<Integer> initialDrawables;
    ArrayList<String> initialNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_trainning);
        initUtils();
        calCraftSize();
        initViews();
        synthesizer = new CraftSynthesizer(this);

    }
    private void initUtils() {
        gameController = new GameController(getApplication());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mSoundManger = new SoundManger(getApplication());
        mSoundManger.startBgMusic();
    }

    private void calCraftSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        craftItemSize = size.x/5;
    }


    private void initViews() {
        tvScore = (TextView) findViewById(R.id.score);
        tvScore.setText(gameController.getScore()+"/450");
        flWorkspace = (FrameLayout) findViewById(R.id.fl_workspace);

        initialDrawables = new ArrayList<Integer>();
        initialNames = new ArrayList<String>();

        instantiatedCraftList = new ArrayList<CraftItem>();
        bottomBarCrafts = new ArrayList<CraftItem>();
        collidingItems = new ArrayList<CraftItem>();
        bottomBarCraftNames = new ArrayList<String>();

        if(gameController.getScore() != 5){
            loadData();
        }else{
            // initialize data
            initialDrawables = initImageData();
            initialNames = initTextData();
        }
        initBottomItem(initialDrawables, initialNames);

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
            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY) {
                if (e1!=null && e2!=null){
                    float x = e1.getX();
                    float y = e1.getY();
                    View child = rvItems.findChildViewUnder(x, y);
                    boolean verticalSwipe = false;
                    if (Math.abs(dX) < Math.abs(0.05*dY))
                        verticalSwipe = true;
                    if (child!=null && verticalSwipe) {
                        int position = rvItems.getChildPosition(child);
                        dispatchingItem = instantiateCraftItem(rvItems.getChildPosition(child), e2, x-craftItemSize/2, y-craftItemSize/2+flWorkspace.getHeight()-rvItems.getHeight());
                        dispatching = true;
                        mSoundManger.playSound(SoundManger.SOUND_DRAG_OUT);
                    }

                    return true;
                }
                return false;
            }
        }));
        rvItems.hasFixedSize();

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

    private CraftItem instantiateCraftItem(int position, MotionEvent e, float x, float y) {

        CraftItem newCraftItemView = new CraftItem(getApplication());
        newCraftItemView.setLayoutParams(new ViewGroup.LayoutParams(craftItemSize, craftItemSize));
        Log.v("TrainingMainActivity","renderCraftItem" + bottomBarCrafts.get(position).getCraftName());
        newCraftItemView.setBackground(bottomBarCrafts.get(position).getCraftDrawable());
        newCraftItemView.setCraftName(bottomBarCrafts.get(position).getCraftName());
        newCraftItemView.setCraftTag(bottomBarCrafts.get(position).getCraftTag());
        newCraftItemView.setCraftProperty(bottomBarCrafts.get(position).getCraftProperty());
        newCraftItemView.setOnTouchListener(this);

        flWorkspace.addView(newCraftItemView);
        newCraftItemView.setX(x);
        newCraftItemView.setY(y);
        instantiatedCraftList.add(newCraftItemView);

        return newCraftItemView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = false;
        float x = ev.getX();
        float y = ev.getY();
        if (!dispatching) {
            result = super.dispatchTouchEvent(ev);

        } else {
            if (ev.getAction() == ACTION_UP) {
                dispatching = false;
            }
            dispatchingItem.setX(x-craftItemSize/2);
            dispatchingItem.setY(y-craftItemSize/2);
            this.onTouch(dispatchingItem, ev);
        }
        int a = 1;
        return result;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        /* Receive touches on workspace's craftItems.
         * Action:
         *   Check the touched item's position, and
         *   move it accordingly.
         *   When intersect, turn ou transparency, and
         *   when part, turn off transparency.
         *   When user release finger while intersect,
         *   perform a synthesize query. If the query
         *   result is valid, distribute the items from
         *   the result onto workspace */
        switch (event.getActionMasked()) {
            case ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;

                int x = (int)event.getRawX();
                int y = (int)event.getRawY();
                if (checkItemCollided(view, x, y)) {
                    CraftItem A = collidingItems.get(0);
                    CraftItem B = collidingItems.get(1);
                    A.setAlpha((float) 0.5);
                    B.setAlpha((float) 0.5);
                } else {
//                    mSoundManger.playSound(SoundManger.SOUND_SYN_THE_FAIL);
                    clearTransparency();
                }
                break;

            case ACTION_UP:
                x = (int)event.getRawX();
                y = (int)event.getRawY();

                if (lastAction == ACTION_DOWN)
                    Toast.makeText(TrainingMainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();

                if (checkItemCollided(view, x, y)) {
                    CraftItem A = collidingItems.get(0);
                    CraftItem B = collidingItems.get(1);
                    String nameA = A.getCraftName();
                    String nameB = B.getCraftName();

                /* Synthesize new craft and add them to workspace */
                    ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(craftItemSize, craftItemSize);
                    ArrayList<CraftItem> result = synthesizer.synthesize(nameA, nameB, lparams);
                    if (result != null && result.size() > 0) {
                        flWorkspace.removeView(A);
                        flWorkspace.removeView(B);
                        for (CraftItem item : result) {
                            if (!bottomBarCraftNames.contains(item.getCraftName())) {
                                bottomBarCrafts.add(item);
                                bottomBarCraftNames.add(item.getCraftName());
                                bottomItemAdapter.notifyItemInserted(bottomBarCrafts.size() - 1);
                                mSoundManger.playSound(SoundManger.SOUND_SYN_THE_NEW);
                                gameController.setScore(gameController.getScore()+1);
                                tvScore.setText(gameController.getScore()+"/450");
                            }

                            item.setOnTouchListener(this);
                            item.setX(x-130);
                            item.setY(y-130);
                            flWorkspace.addView(item);
                            instantiatedCraftList.add(item);
                        }
                    } else {
                        mSoundManger.playSound(SoundManger.SOUND_SYN_THE_FAIL);
                        clearTransparency();
                    }
                }
                break;
            default:
                return false;
        }


        return true;
    }

    private boolean checkItemCollided(View view, int x, int y) {
        if (collidingItems != null) {
            collidingItems.clear();
        }
        for (int i = 0;i< instantiatedCraftList.size();i++){
            if(view != instantiatedCraftList.get(i)){
                CraftItem target = instantiatedCraftList.get(i);
                if(isViewInBounds(target, x, y)) {
                    Log.d("mainactivity", "Collision detected");
                    collidingItems.add((CraftItem)view);
                    collidingItems.add(target);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isViewInBounds(View view, int x, int y){
//        Log.v("mainactivity","run isviewinbounds");
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    private void clearTransparency() {
        for (int i = 0;i< instantiatedCraftList.size();i++){
            CraftItem item = instantiatedCraftList.get(i);
            item.setAlpha((float) 1.0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundManger.startBgMusic();
        Log.d("oska","onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("oska","onPause");
        gameController.save(bottomBarCraftNames);

        mSoundManger.stopBgMusic();
//        gameController.save(bottomBarCrafts,instantiatedCraftList,bottomBarCraftNames);

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("oska","onStop");
        mSoundManger.stopBgMusic();

    }
}
