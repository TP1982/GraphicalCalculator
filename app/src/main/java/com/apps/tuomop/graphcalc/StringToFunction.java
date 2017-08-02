package com.apps.tuomop.graphcalc;

import java.util.ArrayList;

/**
 * Created by TuomoP on 27.7.2017.
 */

public class StringToFunction {
    private StringBuilder inputText;
    private int xsize;
    private Vector vector;

    public StringToFunction(String inputText){
        this.inputText = new StringBuilder(inputText);
        this.xsize = xsize;
        this.vector = new Vector();
    }

    public Vector getStringToVector(Vector xvector){
        ArrayList<Float> functionList = new ArrayList<>();
        String functionString = inputText.toString();
        System.out.println("syötetty funktio: " + functionString);
        System.out.println("syötetyssä funktiossa merkkejä: " + functionString.length());
        float value = 0;
        float xvar = 0;

        // NORMAL x^a ,where a is real number
        //------------------------------------------
        if(functionString.contains("x^") && functionString.length() == 3){
            String str = functionString.replaceAll("\\D+","");
            double power = (double) Double.parseDouble(str);
            double valueToPlot = 0;
            if("x^0".equals(functionString)){
                for(int i = 0; i < xvector.getList().size(); i++){
                    functionList.add(1.0f);
                }
                this.vector.setList(functionList);
            }else{
                for(int i = 0; i < xvector.getList().size(); i++){
                    xvar = xvector.getList().get(i);
                    valueToPlot = Math.pow(xvar, power);
                    functionList.add((float) valueToPlot);
                }
                this.vector.setList(functionList);
            }

        }else if("x".equals(functionString)){
            for(int i = 0; i < xvector.getList().size(); i++){
                xvar = xvector.getList().get(i);
                value = xvar;
                functionList.add(value);
            }
            this.vector.setList(functionList);
        }
        //------------  sin(x)
        else if ("sin(x)".equals(functionString) || "sin x".equals(functionString)){
            double valueToPlot = 0;
            for(int i = 0; i < xvector.getList().size(); i++){
                xvar = xvector.getList().get(i);
                valueToPlot = Math.sin(xvar);
                functionList.add((float) valueToPlot);
                System.out.println(valueToPlot);
            }
            this.vector.setList(functionList);
        }else if ("cos(x)".equals(functionString) || "cos x".equals(functionString)){
            double valueToPlot = 0;
            for(int i = 0; i < xvector.getList().size(); i++){
                xvar = xvector.getList().get(i);
                valueToPlot = Math.cos(xvar);
                functionList.add((float) valueToPlot);
                System.out.println(valueToPlot);
            }
            this.vector.setList(functionList);
        }else if ("tan(x)".equals(functionString) || "tan x".equals(functionString)){
            double valueToPlot = 0;
            for(int i = 0; i < xvector.getList().size(); i++){
                xvar = xvector.getList().get(i);
                valueToPlot = Math.tan(xvar);
                functionList.add((float) valueToPlot);
                System.out.println(valueToPlot);
            }
            this.vector.setList(functionList);
        }else if ("e^x".equals(functionString)){
            double valueToPlot = 0;
            for(int i = 0; i < xvector.getList().size(); i++){
                xvar = xvector.getList().get(i);
                valueToPlot = Math.exp(xvar);
                functionList.add((float) valueToPlot);
                System.out.println(valueToPlot);
            }
            this.vector.setList(functionList);
        }
        else{
            for(int i = 0; i < xvector.getList().size(); i++){
                value = 0;
                functionList.add(value);
            }
            this.vector.setList(functionList);
        }

        //return functionList;
        return this.vector;
    }
}
