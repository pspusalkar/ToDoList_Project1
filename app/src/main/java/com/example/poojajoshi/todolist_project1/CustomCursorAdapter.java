package com.example.poojajoshi.todolist_project1;

import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class CustomCursorAdapter extends CursorAdapter implements Serializable {
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
        // super(context, c);
        c.moveToFirst();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textView_header =(TextView) view.findViewById(R.id.textView3);
        TextView textView_title=(TextView) view.findViewById(R.id.textView2);
        TextView textView_description=(TextView) view.findViewById(R.id.textView1);
        TextView textView_timeStamp=(TextView) view.findViewById(R.id.textView);
        ImageView img_status = (ImageView) view.findViewById(R.id.imageView);

        textView_timeStamp.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
        textView_title.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        textView_description.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        textView_header.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));

        int task_status = cursor.getInt(cursor.getColumnIndex("status"));
        if ( task_status == 1 ) {
            img_status.setImageResource(R.drawable.complete);
        } else {
            img_status.setImageResource(R.drawable.incomplete);
        }
    }
}
