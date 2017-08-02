package com.apps.tuomop.graphcalc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by TuomoP on 26.7.2017.
 */

public class CanvasView extends View {
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private Paint tickPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    private StringBuilder mText;
    private int mXPosit, mYPosit;
    private Vector xValuesVector;
    private Vector yValuesVector;
    private ArrayList<Float> xListPixel;
    private ArrayList<Float> yListPixel;
    private int div = 4;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        xValuesVector = new Vector();
        yValuesVector = new Vector();
        xListPixel = new ArrayList<>();
        yListPixel = new ArrayList<>();
        // we set a new Path
        mPath = new Path();
        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(2f);

        tickPaint = new Paint();
        tickPaint.setAntiAlias(true);
        tickPaint.setTextSize(15);
        tickPaint.setStyle(Paint.Style.FILL);
        tickPaint.setColor(Color.BLUE);
        tickPaint.setTextAlign(Paint.Align.CENTER);

        mText = new StringBuilder("ffffff");
        mXPosit = 110;
        mYPosit = 110;
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);

        if(!xValuesVector.getList().isEmpty() && !yValuesVector.getList().isEmpty()){
            drawTickValues(xValuesVector, yValuesVector, canvas);
        }

    }

    public void setxValuesVector(Vector xvector){
        this.xValuesVector = xvector;
    }

    public void setyValuesVector(Vector yvector){
        this.yValuesVector = yvector;
    }
    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        mText.delete(0,mText.length());
        invalidate();
    }

    public float floatToPixelInvertedY(float input, float min, float max, float pixelArea){
        float margin = 70.0f;
        float maxArea = (max - min);
        float AreaOfPixels = pixelArea - 2.0f * margin;
        float pixel = 0.0f;

        pixel = -1.0f*(input / maxArea) * AreaOfPixels + AreaOfPixels + ((min / maxArea) * AreaOfPixels) + margin;

        return pixel;
    }

    public float floatToPixelNormal(float input, float min, float max, float pixelArea){
        float margin = 70.0f;
        float maxArea = (max - min);
        float AreaOfPixels = pixelArea - 2.0f * margin;
        float pixel = 0.0f;

        //pixel = (input / maxArea) * AreaOfPixels + AreaOfPixels + ((min / maxArea) * AreaOfPixels) + margin;
        pixel = (input / maxArea) * AreaOfPixels -((min / maxArea) * AreaOfPixels)  + margin;
        return pixel;
    }

    public void drawTickValues(Vector x, Vector y, Canvas canvas){

        float xmin = x.getMin();
        float xminPixel = xListPixel.get(x.getMinIndex());
        System.out.println("DrawTickValues -> xminPixel:" + xminPixel);
        System.out.println("DrawTickValues -> xminPixelindex:" + x.getMinIndex());
        float xmax = x.getMax();
        float xmaxPixel = xListPixel.get(x.getMaxIndex());
        System.out.println("DrawTickValues -> xmaxPixel:" + xmaxPixel);
        float ymin = y.getMin();
        float yminPixel = yListPixel.get(y.getMinIndex());
        System.out.println("DrawTickValues -> yminPixel:" + yminPixel);
        float ymax = y.getMax();
        float ymaxPixel = yListPixel.get(y.getMaxIndex());
        System.out.println("DrawTickValues -> ymaxPixel:" + ymaxPixel);

        float xdivision = (0.5f*(xmax - xmin) ) / div;
        System.out.println("DrawTickValues -> Stepsize of x: " + xdivision);
        float ydivision = (0.5f*(ymax - ymin) ) / div;
        System.out.println("DrawTickValues -> Stepsize of y: " + ydivision);
        float xdivPixel = (0.5f*(xmaxPixel - xminPixel)) / div;
        System.out.println("DrawTickValues -> Stepsize of x in pixels: " + xdivPixel);
        float ydivPixel = (0.5f*(yminPixel - ymaxPixel)) / div;
        System.out.println("DrawTickValues -> Stepsize of y in pixels: " + ydivPixel);

        int tmp = x.closestToZeroIndex(x.getList());
        int tmp2 = y.closestToZeroIndex(y.getList());
        System.out.println("DrawTickValues -> Index of x closest to zero: " + tmp);
        System.out.println("DrawTickValues -> Index of y closest to zero: " + tmp2);
        float xpixelinit = xListPixel.get(tmp);
        System.out.println("DrawTickValues -> Initial pixel of x: " + xpixelinit);
        float ypixelinit = yListPixel.get(tmp2);
        System.out.println("DrawTickValues -> Initial pixel of y: " + ypixelinit);
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        // draw ticks to x-axis positive side
        xpixelinit = xListPixel.get(tmp);
        float xvalueinit = x.getList().get(tmp);
        float yvalueinit = y.getList().get(tmp2);

        if(xmin >= 0){
            xpixelinit = xListPixel.get(tmp);
            xvalueinit = x.getList().get(tmp);
            for(int i = 0; i < (2*div); i++){
                xpixelinit += xdivPixel;
                xvalueinit += xdivision;
                System.out.println("DrawTickvalues -> teksti:" + String.valueOf(xvalueinit));
                System.out.println("DrawTickvalues -> xcoord:" + xpixelinit);
                System.out.println("DrawTickvalues -> ycoord:" + yListPixel.get(tmp2));

                canvas.drawText(String.valueOf(df.format(xvalueinit)), xpixelinit, yListPixel.get(tmp2) + 20, tickPaint);
            }
        }else{
            xpixelinit = xListPixel.get(tmp);
            xvalueinit = x.getList().get(tmp);
            for(int i = 0; i < (2*div); i++){
                xpixelinit += xdivPixel;
                xvalueinit += xdivision;
                System.out.println("DrawTickvalues -> teksti:" + String.valueOf(xvalueinit));
                System.out.println("DrawTickvalues -> xcoord:" + xpixelinit);
                System.out.println("DrawTickvalues -> ycoord:" + yListPixel.get(tmp2));

                canvas.drawText(String.valueOf(df.format(xvalueinit)), xpixelinit, yListPixel.get(tmp2) + 20, tickPaint);
            }
            // draw ticks to x-axis negative side
            xpixelinit = xListPixel.get(tmp);
            xvalueinit = x.getList().get(tmp);
            for(int i = 0; i < (2*div); i++){
                xpixelinit -= xdivPixel;
                xvalueinit -= xdivision;
                canvas.drawText(String.valueOf(df.format(xvalueinit)), xpixelinit, yListPixel.get(tmp2)+20, tickPaint);
            }
        }

//        // draw ticks to y-axis positive side
        if(ymin >= 0){
            ypixelinit = yListPixel.get(tmp2);
            yvalueinit = y.getList().get(tmp2);
            for(int i = 0; i < (2*div); i++){
                ypixelinit -= ydivPixel;
                yvalueinit += ydivision;
                canvas.drawText(String.valueOf(df.format(yvalueinit)), xListPixel.get(tmp)+15, ypixelinit+5, tickPaint);
            }
        }else{
            ypixelinit = yListPixel.get(tmp2);
            yvalueinit = y.getList().get(tmp2);
            for(int i = 0; i < (2*div); i++){
                ypixelinit -= ydivPixel;
                yvalueinit += ydivision;
                canvas.drawText(String.valueOf(df.format(yvalueinit)), xListPixel.get(tmp)+15, ypixelinit+5, tickPaint);
            }
            // draw ticks to y-axis negative side
            ypixelinit = yListPixel.get(tmp2);
            yvalueinit = y.getList().get(tmp2);
            for(int i = 0; i < (2*div); i++){
                ypixelinit += ydivPixel;
                yvalueinit -= ydivision;
                canvas.drawText(String.valueOf(df.format(yvalueinit)), xListPixel.get(tmp)+15, ypixelinit+5, tickPaint);
            }
        }

    }

    public void drawFunction(Vector x, Vector y, Spinner spinner){
        mPath.reset();
        invalidate();
        clearData();

        float xmax = x.getMax();
        float xmin = x.getMin();
        float ymin = y.getMin();
        float ymax = y.getMax();

        System.out.println("xmax: " + xmax);
        System.out.println("xmin: " + xmin);

        System.out.println("ymax: " + ymax);
        System.out.println("ymin: " + ymin);

        float xpixelArea = mBitmap.getWidth();
        System.out.println(mBitmap.getWidth());
        System.out.println(mBitmap.getHeight());
        float ypixelArea = mBitmap.getHeight();
        mPath.moveTo(floatToPixelNormal(x.getList().get(0), xmin, xmax, xpixelArea), floatToPixelInvertedY(y.getList().get(0), ymin, ymax, ypixelArea));
        for (int i = 0; i < x.getList().size(); i++){

            float xpixel = floatToPixelNormal(x.getList().get(i), xmin, xmax, xpixelArea);
            float ypixel = floatToPixelInvertedY(y.getList().get(i), ymin, ymax, ypixelArea);
            xListPixel.add(xpixel);
            yListPixel.add(ypixel);
            if(String.valueOf(spinner.getSelectedItem()).equals("Line")){
                mPath.lineTo(xpixel, ypixel);
            }else if(String.valueOf(spinner.getSelectedItem()).equals("Line+circle")){
                mPath.lineTo(xpixel, ypixel);
                mPath.addCircle(xpixel, ypixel, 5.0f, Path.Direction.CCW);
                mPath.moveTo(xpixel, ypixel);
            }else if (String.valueOf(spinner.getSelectedItem()).equals("Circle")){
                mPath.addCircle(xpixel, ypixel, 5.0f, Path.Direction.CCW);
            }
        }

        Axes axes = new Axes(x.getList(), y.getList(), xListPixel, yListPixel);
        axes.drawAxes(mPath, mCanvas);
        axes.drawTicks(mPath, div);

    }

    public void clearData(){
        xListPixel.clear();
        yListPixel.clear();
    }

    public void putText(){
        mPath.reset();
        mText.append("Böö");
        mXPosit = 300;
        mYPosit = 400;
        System.out.println("Canvas width: " + mCanvas.getWidth());
        System.out.println("Canvas height: " + mCanvas.getHeight());
        System.out.println("Bitmap width: " + mBitmap.getWidth());
        System.out.println("Bitmap height: " + mBitmap.getHeight());

        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

}
