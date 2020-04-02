
package com.getscriba.sampleapp.android.Game.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.getscriba.sampleapp.android.Game.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ChartActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Context context = getApplicationContext();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        SharedPreferences scorePrefs = this.getSharedPreferences("scorePrefs", Context.MODE_PRIVATE);
        String savedGraphMotorPlanning = scorePrefs.getString("gameGraphMotorPlanning", "0");
        String savedGraphSizePrecision = scorePrefs.getString("gameGraphSizePrecision", "0");
        String savedGraphEngagement = scorePrefs.getString("gameGraphEngagement", "0");
        String savedGraphSpeedOfMotorPlanning = scorePrefs.getString("gameGraphSpeedOfMotorPlanning", "0");
        String savedGraphPositionPrecision = scorePrefs.getString("gameGraphPositionPrecision", "0");
        String savedGraphRoundScore = scorePrefs.getString("gameGraphTotalScore", "0");
//        int maxHeight = scorePrefs.getInt("maxHeight", 100);
        int maxHeight = 100;


        Log.v("ChartActivity: ", "savedGraphMotorPlanning: " + savedGraphMotorPlanning);
        Log.v("ChartActivity: ", "savedGraphSizePrecision: " + savedGraphSizePrecision);
        Log.v("ChartActivity: ", "savedGraphEngagement: " + savedGraphEngagement);
        Log.v("ChartActivity: ", "savedGraphSpeedOfMotorPlanning: " + savedGraphSpeedOfMotorPlanning);
        Log.v("ChartActivity: ", "savedGraphPositionPrecision: " + savedGraphPositionPrecision);
        Log.v("ChartActivity: ", "savedGraphRoundScore: " + savedGraphRoundScore);

        buttonFunctions();



        int[] motorPlanningData = Arrays.stream(savedGraphMotorPlanning.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] sizePrecisionData = Arrays.stream(savedGraphSizePrecision.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] engagementData = Arrays.stream(savedGraphEngagement.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] speedOfMotorPlanningData= Arrays.stream(savedGraphSpeedOfMotorPlanning.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] positionPrecisionData = Arrays.stream(savedGraphPositionPrecision.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] highScoreData = Arrays.stream(savedGraphRoundScore.split(",")).mapToInt(Integer::parseInt).toArray();
        int TimeData = motorPlanningData.length;

        int maxValue=Integer.MAX_VALUE;
        int minValue=0;


        int maxMotorPlanning=minValue;
        int minMotorPlanning=maxValue;
        int maxSizePrecision=minValue;
        int minSizePrecision=maxValue;
        int maxEngagement=minValue;
        int minEngagement=maxValue;
        int maxSpeedOfMotorPlanning=minValue;
        int minSpeedOfMotorPlanning=maxValue;
        int maxPositionPrecision=minValue;
        int minPositionPrecision=maxValue;
        int maxHighScore=minValue;
        int minHighScore=maxValue;

//        motorPlanningData
        for (int motorPlanningDatum : motorPlanningData) {
            if (motorPlanningDatum < minMotorPlanning) {
                minMotorPlanning = motorPlanningDatum;
            }
            if (motorPlanningDatum > maxMotorPlanning) {
                maxMotorPlanning = motorPlanningDatum;
            }
        }

//        sizePrecisionData
        for (int sizePrecisionDatum : sizePrecisionData) {
            if (sizePrecisionDatum < minSizePrecision) {
                minSizePrecision = sizePrecisionDatum;
            }
            if (sizePrecisionDatum > maxSizePrecision) {
                maxSizePrecision = sizePrecisionDatum;
            }
        }

//        engagementData
        for (int engagementDatum : engagementData) {
            if (engagementDatum < minEngagement) {
                minEngagement = engagementDatum;
            }
            if (engagementDatum > maxEngagement) {
                maxEngagement = engagementDatum;
            }
        }

//        speedOfMotorPlanningData
        for (int speedOfMotorPlanningDatum : speedOfMotorPlanningData) {
            if (speedOfMotorPlanningDatum < minSpeedOfMotorPlanning) {
                minSpeedOfMotorPlanning = speedOfMotorPlanningDatum;
            }
            if (speedOfMotorPlanningDatum > maxSpeedOfMotorPlanning) {
                maxSpeedOfMotorPlanning = speedOfMotorPlanningDatum;
            }
        }

//        positionPrecisionData
        for (int positionPrecisionDatum : positionPrecisionData) {
            if (positionPrecisionDatum < minPositionPrecision) {
                minPositionPrecision = positionPrecisionDatum;
            }
            if (positionPrecisionDatum > maxPositionPrecision) {
                maxPositionPrecision = positionPrecisionDatum;
            }
        }

//        highScoreData
        for (int highScoreDatum : highScoreData) {
            if (highScoreDatum < minHighScore) {
                minHighScore = highScoreDatum;
            }
            if (highScoreDatum > maxHighScore) {
                maxHighScore = highScoreDatum;
            }
        }

//        Log.v("ChartActivity: ", "minHighScore: " + minHighScore+", and maxHighScore: "+maxHighScore);

        LineChartView ALineChartView = findViewById(R.id.chart1);
        LineChartView BLineChartView = findViewById(R.id.chart2);
        LineChartView CLineChartView = findViewById(R.id.chart3);
        LineChartView FLineChartView = findViewById(R.id.chart6);
        List<PointValue> AValues = new ArrayList<>();
        List<AxisValue> TimeValues = new ArrayList<>();




        Line ALine = new Line(AValues).setColor(Color.parseColor("#FF0000"));

        for (int i = 0; i < TimeData; i++) {
            TimeValues.add(i, new AxisValue(i).setLabel(String.valueOf(i+1)));
        }

        for (int i = 0; i < motorPlanningData.length; i++) {
            AValues.add(new PointValue(i, normalize(motorPlanningData[i],maxMotorPlanning)));
//            AValues.add(new PointValue(i, motorPlanningData[i]));
        }

        List<Line> ALines = new ArrayList<>();
        ALines.add(ALine);

        LineChartData AData = new LineChartData();
        AData.setLines(ALines);

        Axis Time = new Axis();
        Time.setValues(TimeValues);
        Time.setTextSize(16);
        Time.setTextColor(Color.parseColor("#03A9F4"));
        AData.setAxisXBottom(Time);

        Axis A = new Axis();
//        A.setName("Sales in millions");
        A.setTextColor(Color.parseColor("#03A9F4"));
        A.setTextSize(16);
        AData.setAxisYLeft(A);

        ALineChartView.setLineChartData(AData);
        Viewport AViewport = new Viewport(ALineChartView.getMaximumViewport());
        AViewport.top = maxHeight;
        AViewport.bottom = 0;
        ALineChartView.setMaximumViewport(AViewport);
        ALineChartView.setCurrentViewport(AViewport);


        List<PointValue> BValues = new ArrayList<>();
        Line BLine = new Line(BValues).setColor(Color.parseColor("#FFA500"));

        for (int i = 0; i < sizePrecisionData.length; i++) {
            BValues.add(new PointValue(i, normalize(sizePrecisionData[i],maxSizePrecision)));
//            BValues.add(new PointValue(i, sizePrecisionData[i]));
        }

        List<Line> BLines = new ArrayList<>();
        BLines.add(BLine);

        LineChartData BData = new LineChartData();
        BData.setLines(BLines);
        BData.setAxisXBottom(Time);

        Axis B = new Axis();
//        B.setName("Sales in millions");
        B.setTextColor(ContextCompat.getColor(context, R.color.invisible));
        B.setTextSize(16);
        BData.setAxisYLeft(B);

        BLineChartView.setLineChartData(BData);
        Viewport BViewport = new Viewport(BLineChartView.getMaximumViewport());
        BViewport.top = maxHeight;
        BViewport.bottom = 0;
        BLineChartView.setMaximumViewport(BViewport);
        BLineChartView.setCurrentViewport(BViewport);

        //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

        List<PointValue> CValues = new ArrayList<>();
        Line CLine = new Line(CValues).setColor(Color.parseColor("#FFFF00"));
        for (int i = 0; i < engagementData.length; i++) {
            CValues.add(new PointValue(i, normalize(engagementData[i],maxEngagement)));
//            CValues.add(new PointValue(i, engagementData[i]));
        }
        List<Line> CLines = new ArrayList<>();
        CLines.add(CLine);
        LineChartData CData = new LineChartData();
        CData.setLines(CLines);
        CData.setAxisXBottom(Time);
        Axis C = new Axis();
        C.setTextColor(ContextCompat.getColor(context, R.color.invisible));
        C.setTextSize(16);
        CData.setAxisYLeft(C);
        CLineChartView.setLineChartData(CData);
        Viewport CViewport = new Viewport(CLineChartView.getMaximumViewport());
        CViewport.top = maxHeight;
        CViewport.bottom = 0;
        CLineChartView.setMaximumViewport(CViewport);
        CLineChartView.setCurrentViewport(CViewport);

        //DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD


//        List<PointValue> DValues = new ArrayList<>();
//        Line DLine = new Line(DValues).setColor(Color.parseColor("#008000"));
//        for (int i = 0; i < speedOfMotorPlanningData.length; i++) {
//            DValues.add(new PointValue(i, normalize(speedOfMotorPlanningData[i],maxSpeedOfMotorPlanning)));
////            DValues.add(new PointValue(i, speedOfMotorPlanningData[i]));
//        }
//        List<Line> DLines = new ArrayList<>();
//        DLines.add(DLine);
//        LineChartData DData = new LineChartData();
//        DData.setLines(DLines);
//        DData.setAxisXBottom(Time);
//        Axis D = new Axis();
//        D.setTextColor(ContextCompat.getColor(context, R.color.invisible));
//        D.setTextSize(16);
//        DData.setAxisYLeft(D);
//        DLineChartView.setLineChartData(DData);
//        Viewport DViewport = new Viewport(DLineChartView.getMaximumViewport());
//        DViewport.top = maxHeight;
//        DViewport.bottom = 0;
//        DLineChartView.setMaximumViewport(DViewport);
//        DLineChartView.setCurrentViewport(DViewport);


        //EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

//        List<PointValue> EValues = new ArrayList<>();
//        Line ELine = new Line(EValues).setColor(Color.parseColor("#0000FF"));
//        for (int i = 0; i < positionPrecisionData.length; i++) {
//            EValues.add(new PointValue(i, normalize(positionPrecisionData[i],maxPositionPrecision)));
////            EValues.add(new PointValue(i, positionPrecisionData[i]));
//        }
//        List<Line> ELines = new ArrayList<>();
//        ELines.add(ELine);
//        LineChartData EData = new LineChartData();
//        EData.setLines(ELines);
//        EData.setAxisXBottom(Time);
//        Axis E = new Axis();
//        E.setTextColor(ContextCompat.getColor(context, R.color.invisible));
//        E.setTextSize(16);
//        EData.setAxisYLeft(E);
//        ELineChartView.setLineChartData(EData);
//        Viewport EViewport = new Viewport(ELineChartView.getMaximumViewport());
//        EViewport.top = maxHeight;
//        EViewport.bottom = 0;



        //FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
        List<PointValue> FValues = new ArrayList<>();
        Line FLine = new Line(FValues).setColor(Color.parseColor("#4B0082"));
        for (int i = 0; i < highScoreData.length; i++) {
            FValues.add(new PointValue(i, normalize(highScoreData[i],maxHighScore)));

            Log.v("ChartActivity: ", "normalize(highScoreData[i]): " + normalize(highScoreData[i],maxHighScore));

        }
        List<Line> FLines = new ArrayList<>();
        FLines.add(FLine);
        LineChartData FData = new LineChartData();
        FData.setLines(FLines);
        FData.setAxisXBottom(Time);
        Axis F = new Axis();
        F.setTextColor(ContextCompat.getColor(context, R.color.invisible));
        F.setTextSize(16);
        FData.setAxisYLeft(F);
        FLineChartView.setLineChartData(FData);
        Viewport FViewport = new Viewport(CLineChartView.getMaximumViewport());        ///Was ELineChart
        FViewport.top = maxHeight;
        FViewport.bottom = 0;
        FLineChartView.setMaximumViewport(FViewport);
        FLineChartView.setCurrentViewport(FViewport);


        ALineChartView.setInteractive(false);
        BLineChartView.setInteractive(false);
        CLineChartView.setInteractive(false);
//        DLineChartView.setInteractive(false);
//        ELineChartView.setInteractive(false);
        FLineChartView.setInteractive(false);



    }

    public static float normalize(float size, float normalizedHigh) {
        if(normalizedHigh==0){
            normalizedHigh=1;
        }
        return Math.abs(size/normalizedHigh*100);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void buttonFunctions() {
        float bigger = 2;
        Button button1 = findViewById(R.id.textView1);

        button1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {

                button1.setScaleX(bigger);
                button1.setScaleY(bigger);
                button1.setTranslationX(110);
                button1.setTranslationY(-30);
//                button1.setBackgroundResource(R.drawable.blue_220);
            }

            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                float x = 1;
                float y = 1;

                button1.setScaleX(x);
                button1.setScaleY(y);
                button1.setTranslationX(0);
                button1.setTranslationY(0);
//                button1.setBackgroundResource(R.drawable.blue_206);

            }
            return false;
        });

        Button button2 = findViewById(R.id.textView2);

        button2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {

                button2.setScaleX(bigger);
                button2.setScaleY(bigger);
                button2.setTranslationY(-30);
//                button1.setBackgroundResource(R.drawable.blue_220);
            }

            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                float x = 1;
                float y = 1;

                button2.setScaleX(x);
                button2.setScaleY(y);
                button2.setTranslationY(0);
//                button1.setBackgroundResource(R.drawable.blue_206);

            }
            return false;
        });

        Button button3 = findViewById(R.id.textView3);

        button3.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {

                button3.setScaleX(bigger);
                button3.setScaleY(bigger);
                button3.setTranslationY(-30);
//                button1.setBackgroundResource(R.drawable.blue_220);
            }

            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                float x = 1;
                float y = 1;

                button3.setScaleX(x);
                button3.setScaleY(y);
                button3.setTranslationY(0);
//                button1.setBackgroundResource(R.drawable.blue_206);

            }
            return false;
        });
//
//        Button button4 = findViewById(R.id.textView4);
//
//        button4.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_DOWN)
//            {
//
//                button4.setScaleX(bigger);
//                button4.setScaleY(bigger);
//                button4.setTranslationY(-30);
////                button1.setBackgroundResource(R.drawable.blue_220);
//            }
//
//            else if(event.getAction() == MotionEvent.ACTION_UP)
//            {
//                float x = 1;
//                float y = 1;
//
//                button4.setScaleX(x);
//                button4.setScaleY(y);
//                button4.setTranslationY(0);
////                button1.setBackgroundResource(R.drawable.blue_206);
//
//            }
//            return false;
//        });

//        Button button5 = findViewById(R.id.textView5);

//        button5.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_DOWN)
//            {
//
//                button5.setScaleX(bigger);
//                button5.setScaleY(bigger);
//                button5.setTranslationY(-30);
//                button1.setBackgroundResource(R.drawable.blue_220);
//            }
//
//            else if(event.getAction() == MotionEvent.ACTION_UP)
//            {
//                float x = 1;
//                float y = 1;
//
//                button5.setScaleX(x);
//                button5.setScaleY(y);
//                button5.setTranslationY(0);
////                button1.setBackgroundResource(R.drawable.blue_206);
//
//            }
//            return false;
//        });

        Button button6 = findViewById(R.id.textView6);

        button6.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {

                button6.setScaleX(bigger);
                button6.setScaleY(bigger);
                button6.setTranslationX(-110);
                button6.setTranslationY(-30);
//                button1.setBackgroundResource(R.drawable.blue_220);
            }

            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                float x = 1;
                float y = 1;

                button6.setScaleX(x);
                button6.setScaleY(y);
                button6.setTranslationX(0);
                button6.setTranslationY(0);
//                button1.setBackgroundResource(R.drawable.blue_206);

            }
            return false;
        });


    }

}
