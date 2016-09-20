package com.jana.overwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew Jeong on 2016-09-20.
 */
public class MainListAdapter extends ArrayAdapter<Target> {
    private Context mContext;

    public MainListAdapter(Context context, ArrayList<Target> targets) {
        super(context, R.layout.home_list_item, targets);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.home_list_item, parent, false);

        final Target targetItem = getItem(position);
        final TextView mName = (TextView) rowView.findViewById(R.id.list_item_name);
        final ImageView mImage = (ImageView) rowView.findViewById(R.id.list_item_picture);

        mName.setText(targetItem.getName());
        mImage.setBackgroundResource(targetItem.getImage());

        return rowView;
    }
}
