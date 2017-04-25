package kaihcheng7_kmlee65.scm.evolution.modeltrainning.Listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TheOska on 10/21/2016.
 */

public class GeneralRecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private ClickListener clickListener;

    public GeneralRecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return clickListener.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
//        float x = e.getX();
//        float y = e.getY();
//        View child = rv.findChildViewUnder(e.getX(), e.getY());
//        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//            clickListener.onClick(child, rv.getChildPosition(child), e.getX(), e.getY());
//        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position, float x, float y);

        void onLongClick(View view, int position);

        boolean onScroll(MotionEvent e1, MotionEvent e2, float dX, float dY);

    }
}