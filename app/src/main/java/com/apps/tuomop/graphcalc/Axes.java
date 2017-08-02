package com.apps.tuomop.graphcalc;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by TuomoP on 27.7.2017.
 */

public class Axes {
    private ArrayList<Float> xList;
    private ArrayList<Float> yList;
    private ArrayList<Float> xListPixel;
    private ArrayList<Float> yListPixel;
    private int minIndex;
    private int maxIndex;
    private int closestToZeroIndexOfX;
    private int closestToZeroIndexOfY;

    public Axes(ArrayList<Float> xList, ArrayList<Float> yList, ArrayList<Float> xListPixel, ArrayList<Float> yListPixel){
        this.xList = xList;
        this.yList = yList;
        this.xListPixel = xListPixel;
        this.yListPixel = yListPixel;
        maxIndex = 0;
        minIndex = 0;
    }

    public void drawAxes(Path path, Canvas canvas){

        float xmin = getMinFromArrayList(xList);
        System.out.println("drawAxes -> xmin:" + xmin);
        float xminPixel = xListPixel.get(minIndex);
        System.out.println("drawAxes -> xminPixel:" + xminPixel);
        float xmax = getMaxFromArrayList(xList);
        System.out.println("drawAxes -> xmax:" + xmax);
        float xmaxPixel = xListPixel.get(maxIndex);
        System.out.println("drawAxes -> xmaxPixel:" + xmaxPixel);
        float ymin = getMinFromArrayList(yList);
        System.out.println("drawAxes -> ymin:" + ymin);
        float yminPixel = yListPixel.get(minIndex);
        System.out.println("drawAxes -> yminPixel:" + yminPixel);
        float ymax = getMaxFromArrayList(yList);
        System.out.println("drawAxes -> ymax:" + ymax);
        float ymaxPixel = yListPixel.get(maxIndex);
        System.out.println("drawAxes -> ymaxPixel:" + ymaxPixel);

        int tmp = closestToZeroIndex(xList);
        int tmp2 = closestToZeroIndex(yList);
        System.out.println("drawAxes -> Index of x closest to zero: " + tmp);
        System.out.println("drawAxes -> Index of y closest to zero: " + tmp2);

        // move to the point where zero exists
        path.moveTo(xListPixel.get(tmp), yListPixel.get(tmp2));
        // draw x-axis
        path.lineTo(0,yListPixel.get(tmp2));
        path.lineTo(canvas.getWidth(), yListPixel.get(tmp2));
        // draw y-axis
        path.moveTo(xListPixel.get(tmp), yListPixel.get(tmp2));
        path.lineTo(xListPixel.get(tmp), 0);
        path.lineTo(xListPixel.get(tmp), canvas.getHeight());

    }

    public void drawTicks(Path path, int div){
        int maxIndexOfX = 0;
        int minIndexOfX = 0;
        int maxIndexOfY = 0;
        int minIndexOfY = 0;
        float xmin = getMinFromArrayList(xList);
        float xminPixel = xListPixel.get(minIndex);
        float xmax = getMaxFromArrayList(xList);
        float xmaxPixel = xListPixel.get(maxIndex);
        float ymin = getMinFromArrayList(yList);
        float yminPixel = yListPixel.get(minIndex);
        float ymax = getMaxFromArrayList(yList);
        float ymaxPixel = yListPixel.get(maxIndex);

        float xdivision = (0.5f*(xmax - xmin) ) / div;
        System.out.println("drawTicks -> Stepsize of x: " + xdivision);
        float ydivision = (0.5f*(ymax - ymin) ) / div;
        System.out.println("drawTicks -> Stepsize of y: " + ydivision);
        float xdivPixel = (0.5f*(xmaxPixel - xminPixel)) / div;
        System.out.println("drawTicks -> Stepsize of x in pixels: " + xdivPixel);
        float ydivPixel = (0.5f*(yminPixel - ymaxPixel)) / div;
        System.out.println("drawTicks -> Stepsize of y in pixels: " + ydivPixel);

        int tmp = closestToZeroIndex(xList);
        int tmp2 = closestToZeroIndex(yList);
        System.out.println("drawTicks -> Index of x closest to zero: " + tmp);
        System.out.println("drawTicks -> Index of y closest to zero: " + tmp2);
        float xpixelinit = xListPixel.get(tmp);
        System.out.println("drawTicks -> Initial pixel of x: " + xpixelinit);
        float ypixelinit = yListPixel.get(tmp2);
        System.out.println("drawTicks -> Initial pixel of y: " + ypixelinit);
        // move to the point where zero exists

        path.moveTo(xListPixel.get(tmp), yListPixel.get(tmp2));
        // draw ticks to x-axis positive side
        xpixelinit = xListPixel.get(tmp);
        for(int i = 0; i < div; i++){
            xpixelinit += xdivPixel;
            //System.out.println("(Ticks) xpixel: " + xpixelinit);
            //System.out.println("(Ticks) ypixel: " + ypixelinit);
            path.moveTo(xpixelinit, yListPixel.get(tmp2));
            path.lineTo(xpixelinit, yListPixel.get(tmp2) + 5);
            path.lineTo(xpixelinit, yListPixel.get(tmp2) - 5);
        }
        // draw ticks to x-axis negative side
        xpixelinit = xListPixel.get(tmp);
        for(int i = 0; i < div; i++){
            xpixelinit -= xdivPixel;
            path.moveTo(xpixelinit, yListPixel.get(tmp2));
            path.lineTo(xpixelinit, yListPixel.get(tmp2) + 5);
            path.lineTo(xpixelinit, yListPixel.get(tmp2) - 5);
        }
        path.moveTo(xListPixel.get(tmp), yListPixel.get(tmp2));
        // draw ticks to y-axis positive side
        if(ymin >= 0){
            ypixelinit = yListPixel.get(tmp2);
            for(int i = 0; i < (2*div); i++){
                ypixelinit -= ydivPixel;

                path.moveTo(xListPixel.get(tmp), ypixelinit);
                path.lineTo(xListPixel.get(tmp) + 5, ypixelinit);
                path.lineTo(xListPixel.get(tmp) - 5, ypixelinit);
            }
        }else{
            ypixelinit = yListPixel.get(tmp2);
            for(int i = 0; i < div; i++){
                ypixelinit += ydivPixel;

                path.moveTo(xListPixel.get(tmp), ypixelinit);
                path.lineTo(xListPixel.get(tmp) + 5, ypixelinit);
                path.lineTo(xListPixel.get(tmp) - 5, ypixelinit);
            }
            // draw ticks to y-axis negative side
            ypixelinit = yListPixel.get(tmp2);
            for(int i = 0; i < div; i++){
                ypixelinit -= ydivPixel;
                path.moveTo(xListPixel.get(tmp), ypixelinit);
                path.lineTo(xListPixel.get(tmp) + 5, ypixelinit);
                path.lineTo(xListPixel.get(tmp) - 5, ypixelinit);
            }
        }

    }

    private int closestToZeroIndex(ArrayList<Float> list){
        int closestToZero = 0;
        if(!list.isEmpty()){
            float zero = Math.abs(list.get(0));
            for(int i = 0; i < list.size(); i++){
                if(Math.abs(list.get(i)) < zero){
                    zero = Math.abs(list.get(i));
                    closestToZero = i;
                }
            }
        }

        return closestToZero;
    }

    private float getMaxFromArrayList(ArrayList<Float> list){
        float max = 0;

        if(!list.isEmpty()){
            for(int i = 0; i<list.size(); i++){
                if(list.get(i) > max){
                    max = list.get(i);
                    maxIndex = i;
                }
            }
        }
        return max;

    }

    private float getMinFromArrayList(ArrayList<Float> list){
        float min = 10.0f;
        if(!list.isEmpty()){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) < min){
                    min = list.get(i);
                    minIndex = i;
                }
            }
        }

        return min;
    }
}
