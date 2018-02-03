package com.example.poojajoshi.todolist_project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CompletedAdapter extends BaseAdapter {
    String [] titles;
    String [] timestamps;
    String [] descriptions;

    Context context;
    private static LayoutInflater inflater=null;

    // Create Custom Adapter
    public CompletedAdapter(CompletedTasks mainActivity, String[] titleList, String[] timestampList, String[] descriptionList) {
        // TODO Auto-generated constructor stub
        context = mainActivity;

        titles = titleList;
        timestamps = timestampList;
        descriptions = descriptionList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView textView_header;
        TextView textView_title;
        TextView textView_description;
        TextView textView_timeStamp;
        ImageView img_status;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Create the each Row Item and fill with the name and number list

        CompletedAdapter.Holder holder=new CompletedAdapter.Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        rowView.setMinimumHeight(200);
        rowView.setPadding(20, 5, 20, 5);

        holder.textView_header =(TextView) rowView.findViewById(R.id.textView3);
        holder.textView_title=(TextView) rowView.findViewById(R.id.textView2);
        holder.textView_description=(TextView) rowView.findViewById(R.id.textView1);
        holder.textView_timeStamp=(TextView) rowView.findViewById(R.id.textView);
        holder.img_status = (ImageView) rowView.findViewById(R.id.imageView);

        holder.textView_timeStamp.setText(timestamps[position]);
        holder.textView_title.setText(titles[position]);
        holder.textView_description.setText(descriptions[position]);
        holder.textView_header.setText(timestamps[position]);

        holder.img_status.setImageResource(R.drawable.complete);
        holder.img_status.setMaxHeight(20);
        holder.img_status.setMaxWidth(20);
        holder.img_status.setMinimumWidth(20);
        holder.img_status.setMinimumHeight(20);
        return rowView;
    }
}
