package com.utang.vervel.moudul;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.utang.vervel.beans.AOG;
import com.utang.vervel.beans.Magnetism;
import com.utang.vervel.beans.Palstance;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.ui.SearchResultActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/4/5.
 */
public class ControlDeviceImp {
    private final Intent intentResult;
    private Context context;
    private Toast toast;

    public ControlDeviceImp(Context context) {
        this.context = context;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        intentResult = new Intent(context, SearchResultActivity.class);
    }

    public void searchPulseHis(ArrayList<Pulse> pulseArrayList) {
        intentResult.putExtra("instruct", "PulseHis");
        intentResult.putParcelableArrayListExtra("pulseArrayList", pulseArrayList);
        context.startActivity(intentResult);
    }

    public void searchAOG(ArrayList<AOG> aogArrayList) {
        intentResult.putExtra("instruct", "AOGHis");
        intentResult.putParcelableArrayListExtra("aogArrayList", aogArrayList);
        context.startActivity(intentResult);

    }

    public void searchMagnetism(ArrayList<Magnetism> magnetismArrayList) {
        intentResult.putExtra("instruct", "Magnetism");
        intentResult.putParcelableArrayListExtra("magnetismArrayList", magnetismArrayList);
        context.startActivity(intentResult);
    }

    public void searchPalstance(ArrayList<Palstance> palstanceArrayList) {
        intentResult.putExtra("instruct", "Palstance");
        intentResult.putParcelableArrayListExtra("palstanceArrayList", palstanceArrayList);
        context.startActivity(intentResult);
    }

    public void searchPressure(ArrayList<Pressure> pressureArrayList) {
        intentResult.putExtra("instruct", "Pressure");
        intentResult.putParcelableArrayListExtra("pressureArrayList", pressureArrayList);
        context.startActivity(intentResult);
    }

    public void showToast(String str) {
        if (toast.getView().getParent() != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
