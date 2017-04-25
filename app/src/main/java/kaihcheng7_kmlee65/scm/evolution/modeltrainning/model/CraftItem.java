package kaihcheng7_kmlee65.scm.evolution.modeltrainning.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import kaihcheng7_kmlee65.scm.evolution.R;


/**
 * Created by TheOska on 10/22/2016.
 */

public class CraftItem extends View {
    String craftName;
    String craftTag;
    String craftProperty;
    Drawable craftDrawable;
    Context context;
    public CraftItem(Context context) {
        super(context);
//        this.setLayoutParams(new ViewGroup.LayoutParams(100,100));
        this.context = context;


    }
    public CraftItem(Context context, AttributeSet attrs){
        super(context, attrs,0);

    }
    public CraftItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CraftItem,
                0, 0);

        try {
            // default value
            craftName = a.getString(R.styleable.CraftItem_craftName);
            craftTag = a.getString(R.styleable.CraftItem_craftTag);
            craftProperty = a.getString(R.styleable.CraftItem_craftProperty);
            craftDrawable = a.getDrawable(R.styleable.CraftItem_craftImageDrawable);
            if(craftDrawable != null)
                setCraftDrawable(craftDrawable);
        } finally {
            a.recycle();
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Do nothing. Do not call the superclass method--that would start a layout pass
        // on this view's children. PieChart lays out its children in onSizeChanged().
    }



    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
        invalidate();
        requestLayout();
    }

    public String getCraftTag() {
        return craftTag;
    }

    public void setCraftTag(String craftTag) {
        this.craftTag = craftTag;
        invalidate();
        requestLayout();
    }

    public String getCraftProperty() {
        return craftProperty;
    }

    public void setCraftProperty(String craftProperty) {
        this.craftProperty = craftProperty;
        invalidate();
        requestLayout();
    }


    public Drawable getCraftDrawable() {
        return craftDrawable;
    }

    public void setCraftDrawable(Drawable craftDrawable) {
        this.craftDrawable = craftDrawable;
        invalidate();
        requestLayout();
    }

//    protected int measureDimension() {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//    }


    @Override
    protected boolean onSetAlpha(int alpha) {
        return super.onSetAlpha(alpha);
    }
}
