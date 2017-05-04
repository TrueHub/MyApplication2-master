package com.utang.vervel.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.utang.vervel.R;
import com.utang.vervel.beans.AngV;
import com.utang.vervel.beans.GravA;
import com.utang.vervel.beans.Mag;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.view.LinechartView;

import java.util.ArrayList;
import java.util.Collections;

public class LineChartPage extends AppCompatActivity {

    private LinechartView linechartview;
    private String instruct;
    private long[] times;
    private int[] z;
    private int[] x;
    private int[] y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_view);

        instruct = getIntent().getStringExtra("instruct");

        initView();

        initData();

    }

    private void initData() {
        switch (instruct) {
            case "PulseHis":
                ArrayList<Pulse> pulseArrayList = getIntent().getParcelableArrayListExtra("pulseArrayList");
                Collections.reverse(pulseArrayList);
                int[] pulseValue = new int[pulseArrayList.size()];
                times = new long[pulseArrayList.size()];
                for (int i = 0; i < pulseArrayList.size(); i++) {
                    Pulse pulse = pulseArrayList.get(i);
                    times[i] = pulse.getTime();
                    pulseValue[i] = pulse.getPulse();
                }

                linechartview.setTimes(times);
                linechartview.setType("pulse");
                linechartview.setValuesX(pulseValue);

                break;
            case "AOGHis":
                ArrayList<GravA> gravAArrayList = getIntent().getParcelableArrayListExtra("gravAArrayList");
                Collections.reverse(gravAArrayList);
                x = new int[gravAArrayList.size()];
                y = new int[gravAArrayList.size()];
                z = new int[gravAArrayList.size()];
                times = new long[gravAArrayList.size()];
                for (int i = 0; i < gravAArrayList.size(); i++) {
                    GravA gravA = gravAArrayList.get(i);
                    x[i] = gravA.getVelX();
                    y[i] = gravA.getVelY();
                    z[i] = gravA.getVelZ();
                    times[i] = gravA.getTime();
                }

                linechartview.setValueName("GravA");
                linechartview.setTimes(times);
                linechartview.setValuesX(x);
                linechartview.setValuesY(y);
                linechartview.setValuesZ(z);
                break;

            case "Mag":
                ArrayList<Mag> magList = getIntent().getParcelableArrayListExtra("magArrayList");
                Collections.reverse(magList);
                x = new int[magList.size()];
                y = new int[magList.size()];
                z = new int[magList.size()];
                times = new long[magList.size()];
                for (int i = 0; i < magList.size(); i++) {
                    Mag mag = magList.get(i);
                    x[i] = mag.getStrengthX();
                    y[i] = mag.getStrengthY();
                    z[i] = mag.getStrengthZ();
                    times[i] = mag.getTime();
                }

                linechartview.setValueName("Mag");
                linechartview.setTimes(times);
                linechartview.setValuesX(x);
                linechartview.setValuesY(y);
                linechartview.setValuesZ(z);
                break;
            case "AngV":
                ArrayList<AngV> angVList = getIntent().getParcelableArrayListExtra("angVArrayList");
                Collections.reverse(angVList);
                x = new int[angVList.size()];
                y = new int[angVList.size()];
                z = new int[angVList.size()];
                times = new long[angVList.size()];
                for (int i = 0; i < angVList.size(); i++) {
                    AngV angV = angVList.get(i);
                    x[i] = angV.getVelX();
                    y[i] = angV.getVelY();
                    z[i] = angV.getVelZ();
                    times[i] = angV.getTime();
                }

                linechartview.setValueName("AngV");
                linechartview.setTimes(times);
                linechartview.setValuesX(x);
                linechartview.setValuesY(y);
                linechartview.setValuesZ(z);
                break;
            case "Pressure":
                ArrayList<Pressure> pressureList = getIntent().getParcelableArrayListExtra("pressureArrayList");
                                Collections.reverse(pressureList);
                long[] pressureValue = new long[pressureList.size()];
                times = new long[pressureList.size()];
                for (int i = 0; i < pressureList.size(); i++) {
                    Pressure pressure = pressureList.get(i);
                    pressureValue[i] = pressure.getIntensityOfPressure();
                    times[i] = pressure.getTime();
                }

                linechartview.setValueName("Pressure");
                linechartview.setValuesL(pressureValue);
                linechartview.setTimes(times);
                linechartview.setType("pressure");
                break;
        }
    }

    private void initView() {
        linechartview = (LinechartView) findViewById(R.id.linechartview);
    }
}
