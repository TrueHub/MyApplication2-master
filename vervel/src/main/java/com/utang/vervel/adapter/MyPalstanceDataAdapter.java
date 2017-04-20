package com.utang.vervel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.utang.vervel.R;
import com.utang.vervel.beans.Palstance;
import com.utang.vervel.utils.DateUtils;
import java.util.ArrayList;

/**
 * Created by user on 2017/4/7.
 */
public class MyPalstanceDataAdapter extends BaseAdapter {
    private ArrayList<Palstance> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyPalstanceDataAdapter(ArrayList<Palstance> list, Context context) {
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_result, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Palstance obj = list.get(position);
        viewHolder.tv__result_time.setText(DateUtils.getDateToString(obj.getTime() * 1000));
        viewHolder.tv__result_x.setText(String.valueOf(obj.getVelX()));
        viewHolder.tv__result_y.setText(String.valueOf(obj.getVelY()));
        viewHolder.tv__result_z.setText(String.valueOf(obj.getVelZ()));
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv__result_time;
        public TextView tv__result_x;
        public TextView tv__result_y;
        public TextView tv__result_z;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv__result_time = (TextView) rootView.findViewById(R.id.tv__result_time);
            this.tv__result_x = (TextView) rootView.findViewById(R.id.tv__result_x);
            this.tv__result_y = (TextView) rootView.findViewById(R.id.tv__result_y);
            this.tv__result_z = (TextView) rootView.findViewById(R.id.tv__result_z);
        }

    }
}
