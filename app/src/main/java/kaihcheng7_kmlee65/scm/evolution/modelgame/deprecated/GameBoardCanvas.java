package kaihcheng7_kmlee65.scm.evolution.modelgame.deprecated;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class GameBoardCanvas extends View {
    private boolean debug = true;
    private Canvas mCanvas;
    private Context mContext;
    private float width;
    private float height;
    private float[] midPoint;
    private float normalMargin = 30f;
    private float craftBoxW = 250f;
    private float craftBoxH = 350f;
    private float plusImageSize = 80f;
    private Rect leftCraftImgDst;
    private Rect rightCraftImgDst;
    private float[] leftCraftNamePos;
    private float[] rightCraftNamePos;
    public ArrayList<String> deployedCraftNames;

    //TODO: adjust these number proportionally in onDraw()
    public GameBoardCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        width = this.getWidth();
        height = this.getHeight();
        midPoint = new float[]{width/2, height/2};

        leftCraftImgDst = new Rect((int)(midPoint[0]-0.5*plusImageSize-normalMargin-craftBoxW),
                (int)(midPoint[1]-0.5*craftBoxH),
                (int)(midPoint[0]-0.5*plusImageSize-normalMargin),
                (int)(midPoint[1]+craftBoxH-craftBoxW));

        rightCraftImgDst = new Rect((int)(midPoint[0]+0.5*plusImageSize+normalMargin),
                (int)(midPoint[1]-0.5*craftBoxH),
                (int)(midPoint[0]+0.5*plusImageSize+normalMargin+craftBoxW),
                (int)(midPoint[1]+craftBoxH-craftBoxW));

        leftCraftNamePos =  new float[]{(float)(midPoint[0]-0.5*plusImageSize-normalMargin-0.5*craftBoxW), (float)(midPoint[1]+0.45*craftBoxH)};
        rightCraftNamePos = new float[]{(float)(midPoint[0]+0.5*plusImageSize+normalMargin+0.5*craftBoxW), (float)(midPoint[1]+0.45*craftBoxH)};

        deployedCraftNames = new ArrayList<>();

        if (debug) {
            Paint bgPaint = new Paint();
            bgPaint.setColor(Color.GRAY);
            mCanvas.drawRect(0, 0, width, height, bgPaint);

            Paint paint = new Paint();
            paint.setTextSize(60);
            paint.setColor(Color.BLACK);
            mCanvas.drawText("Game Board:" + Float.toString(width) + "," + Float.toString(height), 50, 50, paint);

            Paint syntheContainerP = new Paint();
            syntheContainerP.setStyle(Paint.Style.STROKE);
            syntheContainerP.setStrokeWidth(2);
            syntheContainerP.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));

            mCanvas.drawRect((float)(midPoint[0]-0.5*plusImageSize-normalMargin-craftBoxW),
                    (float)(midPoint[1]-0.5*craftBoxH),
                    (float)(midPoint[0]+0.5*plusImageSize+normalMargin+craftBoxW),
                    (float)(midPoint[1]+0.5*craftBoxH),
                    syntheContainerP);

            Paint craftBoxP = new Paint();
            craftBoxP.setStyle(Paint.Style.STROKE);
            craftBoxP.setStrokeWidth(2);
            // Left box
            mCanvas.drawRect((float)(midPoint[0]-0.5*plusImageSize-normalMargin-craftBoxW),
                    (float)(midPoint[1]-0.5*craftBoxH),
                    (float)(midPoint[0]-0.5*plusImageSize-normalMargin),
                    (float)(midPoint[1]+0.5*craftBoxH),
                    craftBoxP);

            // Right box
            mCanvas.drawRect((float)(midPoint[0]+0.5*plusImageSize+normalMargin),
                    (float)(midPoint[1]-0.5*craftBoxH),
                    (float)(midPoint[0]+0.5*plusImageSize+normalMargin+craftBoxW),
                    (float)(midPoint[1]+0.5*craftBoxH),
                    craftBoxP);
        }

        deployCraft("apple");
        deployCraft("airplane");

    }

    public boolean deployCraft(String name) {
        /*Draw Images of the given craft onto either left or right box
        * Args:
        *   name: the name of the craft
        * Return:
        *   true - successfully deployed
        *   false - failed */
        assert deployedCraftNames.size() < 3;

        Paint p = new Paint();
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(40);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName()));
        int bmH = bitmap.getHeight();
        int bmW = bitmap.getWidth();
        Rect src = new Rect(0, 0, bmW, bmH);

        if (deployedCraftNames.size()==0) {
            mCanvas.drawBitmap(bitmap, src, leftCraftImgDst, p);
            mCanvas.drawText(name,
                    leftCraftNamePos[0],
                    leftCraftNamePos[1],
                    p);
        } else {
            mCanvas.drawBitmap(bitmap, src, rightCraftImgDst, p);
            mCanvas.drawText(name,
                    rightCraftNamePos[0],
                    rightCraftNamePos[1],
                    p);
        }

        deployedCraftNames.add(name);

        return true;
    }

    public void shakeAndRemoveCrafts() {
        /*Shake out the two cratf in boxes
        * Args: Void
        * Return: Void */
        assert deployedCraftNames.size() == 2;




    }



}
