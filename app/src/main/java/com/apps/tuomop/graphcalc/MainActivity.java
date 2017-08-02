package com.apps.tuomop.graphcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private CanvasView customCanvas;
    private EditText edit;
    private EditText minx;
    private EditText maxx;
    private EditText datapoints;
    private Vector x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        Button draw = (Button) findViewById(R.id.Draw);
        edit = (EditText) findViewById(R.id.editText);
        minx = (EditText) findViewById(R.id.xmin);
        maxx = (EditText) findViewById(R.id.xmax);
        datapoints = (EditText) findViewById((R.id.datapoints));
        x = new Vector(-1, 1, 20);
    }

    public void clearCanvas(View v){
        customCanvas.clearCanvas();
    }

    public void drawFunction(View v){
        StringToFunction function = new StringToFunction(edit.getText().toString());
        //customCanvas.setyList(function.getStringToList(customCanvas.getxList()));

        if(!minx.getText().toString().isEmpty()
                && !maxx.getText().toString().isEmpty()
                && !datapoints.getText().toString().isEmpty()){
            float xmin = Float.parseFloat(minx.getText().toString());
            float xmax = Float.parseFloat(maxx.getText().toString());
            int dp = Integer.parseInt(datapoints.getText().toString());
            x.setStart(xmin);
            x.setEnd(xmax);
            x.setDatapoints(dp);
        }

        Vector y = function.getStringToVector(x);
        customCanvas.setxValuesVector(x);
        customCanvas.setyValuesVector(y);
        System.out.println("MainActivity: vector x:" + x);
        System.out.println("MainActivity: vector y:" + y);
        customCanvas.drawFunction(x, y);
    }

    public void putText(View v){
        customCanvas.putText();
    }
}
