package com.example.poojajoshi.todolist_project1;
import com.example.poojajoshi.todolist_project1.TaskDatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompletedTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the titles, description and date form intent
        /*
        String[] titlesList = getIntent().getStringExtra("title").split(",");
        String[] descriptionsList = getIntent().getStringExtra("description").split(",");
        String[] dateList = getIntent().getStringExtra("timeStamp").split(",");
        */
        final TaskDatabase db = (TaskDatabase) getIntent().getSerializableExtra("Database");
        // final CustomCursorAdapter cursorAdapter = (CustomCursorAdapter) getIntent().getSerializableExtra("Complete");
        // cursorAdapter.changeCursor(db.getAllCompletedData());

        ListView lView = findViewById(R.id.completed_listView);


        // final TaskDatabase db = new TaskDatabase(this);
//
        // final CompletedAdapter adapter = new CompletedAdapter(CompletedTasks.this, titlesList, dateList, descriptionsList);
        final CustomCursorAdapter cAdapter = new CustomCursorAdapter(CompletedTasks.this, db.getAllCompletedData(), 0);
        lView.setAdapter(cAdapter);

        // set the list view long click listener
        // on long click delete the item from list.
        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // adapter.titles.
                /*
                db.deleteContact(db.getTask(i));
                cAdapter.changeCursor(db.getAllCompletedData());
                */
                return false;
            }
        });
 	}
}	