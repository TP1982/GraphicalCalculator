package com.apps.tuomop.graphcalc;

import java.util.ArrayList;

/**
 * Created by TuomoP on 28.7.2017.
 */

public class Vector {
    private ArrayList<Float> list;
    private int division;
    private float start;
    private float end;
    private int maxIndex;
    private int minIndex;

    public Vector(){
        this.list = new ArrayList<>();
    }

    public Vector(float start, float end, int division){
        this();
        this.division = division;
        this.start = start;
        this.end = end;
        // create vector of datapoints
        float value = start;
        float h = (end - start) / division;
        for(int i = 0; i < (division+1); i++){
            this.list.add(value);
            value += h;
        }

    }

    public Vector(ArrayList<Float> vector){
        this();
        this.list = vector;
    }

    // return maximum of vector
    public float getMax(){
        float max = 0;
        if(!this.list.isEmpty()){

            for(int i=0; i<this.list.size(); i++){
                float value = this.list.get(i);
                if(value > max){
                    max = value;
                    this.maxIndex = i;
                }
            }
        }
        return max;

    }

    // return Minimum of vector
    public float getMin(){

        if(!this.list.isEmpty()){
            float min = this.list.get(0);
            for(int i=0; i<this.list.size(); i++){
                float value = this.list.get(i);
                if(value < min){
                    min = value;
                    this.minIndex = i;
                }
            }
            return min;
        }else{
            return 12345;
        }
    }

    public int getMaxIndex(){
        return this.maxIndex;
    }
    public int getMinIndex(){
        return this.minIndex;
    }
    @Override
    public String toString(){
        if(!this.list.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("["+this.list.get(0));
            for(int i = 1; i < this.list.size(); i++){
                sb.append(","+this.list.get(i));
            }
            sb.append("]");

            return sb.toString();
        }else{
            return "[]";
        }

    }

    public int closestToZeroIndex(ArrayList<Float> list){
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

    public void setStart(float initPoint){
        this.getList().clear();
        this.start = initPoint;
        float h = (end - initPoint) / division;
        float value = initPoint;
        for(int i = 0; i < (division+1); i++){
            this.list.add(value);
            value += h;
        }
    }
    public void setEnd(float endPoint){
        this.getList().clear();
        this.end = endPoint;
        float h = (endPoint - start) / division;
        float value = this.start;
        for(int i = 0; i < (division+1); i++){
            this.list.add(value);
            value += h;
        }
    }
    public void setDatapoints(int datapoints){
        this.getList().clear();
        this.division = datapoints;
        float h = (end - start) / datapoints;
        float value = this.start;
        for(int i = 0; i < (datapoints+1); i++){
            this.list.add(value);
            value += h;
        }
    }


    public ArrayList<Float> getList(){
        return this.list;
    }

    public void setList(ArrayList<Float> list){
        this.list = list;
    }

    public void setDivision(int division){
        this.division = division;
    }

    public int getDivision(){
        return this.division;
    }

}
