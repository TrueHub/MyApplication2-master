package com.utang.vervel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.utang.vervel.R;
import com.utang.vervel.adapter.MyAOGDataAdapter;
import com.utang.vervel.adapter.MyMagnetismDataAdapter;
import com.utang.vervel.adapter.MyPalstanceDataAdapter;
import com.utang.vervel.adapter.MyPressureDataAdapter;
import com.utang.vervel.adapter.MyPulseDataAdapter;
import com.utang.vervel.beans.AngV;
import com.utang.vervel.beans.GravA;
import com.utang.vervel.beans.Mag;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView list_result;
    private String instruct;
    private MyAOGDataAdapter adapterAOG;
    private MyMagnetismDataAdapter adapterMagnetism;
    private MyPalstanceDataAdapter adapterPalstance;
    private MyPressureDataAdapter adapterPressure;
    private MyPulseDataAdapter adapterPulse;
    private TextView tv_x;
    private TextView tv_y;
    private TextView tv_z;
    private Button btn_linechart;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
        instruct = getIntent().getStringExtra("instruct");


        showData();


    }

    private void showData() {
        switch (instruct) {
            case "PulseHis":
                tv_x.setText("心率");
                tv_y.setText("可信任等级");
                tv_z.setVisibility(View.GONE);
                final ArrayList<Pulse> pulseArrayList = getIntent().getParcelableArrayListExtra("pulseArrayList");
                Collections.reverse(pulseArrayList);
                adapterPulse = new MyPulseDataAdapter(pulseArrayList, this);
                list_result.setAdapter(adapterPulse);

                intent = new Intent(SearchResultActivity.this, LineChartPage.class);
                intent.putExtra("instruct", instruct);
                intent.putParcelableArrayListExtra("pulseArrayList", pulseArrayList);

                break;
            case "AOGHis":
                final ArrayList<GravA> gravAArrayList = getIntent().getParcelableArrayListExtra("gravAArrayList");
                Collections.reverse(gravAArrayList);
                adapterAOG = new MyAOGDataAdapter(gravAArrayList, this);
                list_result.setAdapter(adapterAOG);

                intent = new Intent(SearchResultActivity.this, LineChartPage.class);
                intent.putExtra("instruct", instruct);
                intent.putParcelableArrayListExtra("gravAArrayList", gravAArrayList);


                break;
            case "Mag":
                final ArrayList<Mag> magArrayList = getIntent().getParcelableArrayListExtra("magArrayList");
                Collections.reverse(magArrayList);
                adapterMagnetism = new MyMagnetismDataAdapter(magArrayList, this);
                list_result.setAdapter(adapterMagnetism);

                intent = new Intent(SearchResultActivity.this, LineChartPage.class);
                intent.putExtra("instruct", instruct);
                intent.putParcelableArrayListExtra("magArrayList", magArrayList);

                break;
            case "AngV":
                final ArrayList<AngV> angVArrayList = getIntent().getParcelableArrayListExtra("angVArrayList");
                Collections.reverse(angVArrayList);
                adapterPalstance = new MyPalstanceDataAdapter(angVArrayList, this);
                list_result.setAdapter(adapterPalstance);

                intent = new Intent(SearchResultActivity.this, LineChartPage.class);
                intent.putExtra("instruct", instruct);
                intent.putParcelableArrayListExtra("angVArrayList", angVArrayList);

                break;
            case "Pressure":
                tv_x.setText("大气压强");
                tv_y.setVisibility(View.GONE);
                tv_z.setVisibility(View.GONE);
                final ArrayList<Pressure> pressureArrayList = getIntent().getParcelableArrayListExtra("pressureArrayList");
                Collections.reverse(pressureArrayList);
                adapterPressure = new MyPressureDataAdapter(pressureArrayList, this);
                list_result.setAdapter(adapterPressure);

                intent = new Intent(SearchResultActivity.this, LineChartPage.class);
                intent.putExtra("instruct", instruct);
                intent.putParcelableArrayListExtra("pressureArrayList", pressureArrayList);

                break;

        }
    }

    private void initView() {
        list_result = (ListView) findViewById(R.id.list_result);
        tv_x = (TextView) findViewById(R.id.tv_x);
        tv_y = (TextView) findViewById(R.id.tv_y);
        tv_z = (TextView) findViewById(R.id.tv_z);
        btn_linechart = (Button) findViewById(R.id.btn_linechart);
        btn_linechart.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linechart:
                startActivity(intent);
                break;
        }
    }
}
