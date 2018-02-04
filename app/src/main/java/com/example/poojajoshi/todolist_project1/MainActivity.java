package com.example.poojajoshi.todolist_project1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;

import java.io.Serializable;
import java.util.List;
import android.database.Cursor;
import java.util.Collections;
import java.util.ArrayList;
import android.content.DialogInterface;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static transient TaskDatabase db = null;
    ListView listView;
    public static transient CustomCursorAdapter cursorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create database and add entries to database
        db = new TaskDatabase(this);
        SQLiteDatabase sqDatabase = db.getWritableDatabase();

        db.addTask(new TaskDetails("Pay Bill", "Mobile Bill", "19/03/2018", 0));
        db.addTask(new TaskDetails("Pay Bill", "Electricity Bill", "19/02/2018", 0));
        db.addTask(new TaskDetails("Pay EMI", "Home Loan EMI", "19/01/2018", 0));

        String selectQuery = "SELECT _id, title, description, date, status FROM new_table";
        Cursor c = sqDatabase.rawQuery(selectQuery, null);

        // get the list view handle
        listView = findViewById(R.id.listView);
        cursorAdapter = new CustomCursorAdapter(MainActivity.this, c, 0);
        cursorAdapter.notifyDataSetChanged();
        listView.setAdapter(cursorAdapter);

        // update the list view from database
        updateListViewFromDataBase();

        // set the list view item long press item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                // mark the task as completed
                TaskDetails task = db.getTask(i+1);
                task.setTask_status(1);
                db.updateTask(task);
                cursorAdapter.changeCursor(db.getAllData());

                return false;
            }
        });

        // set the list view item click listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //  edit the task
                // showTaskDialog(i, false);

                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                final View promptsView = li.inflate(R.layout.add_item_layout, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set add_item_layout.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // get the save button handle
                Button btn_save = (Button) promptsView.findViewById(R.id.button_save);

                // set the onclick listener for save button.
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set the custom dialog components - title, description, date of task and status
                        final EditText text_title = (EditText) alertDialog.findViewById(R.id.editText_title);
                        final EditText text_description = (EditText) alertDialog.findViewById(R.id.editText_description);
                        final DatePicker date_picker = (DatePicker) alertDialog.findViewById(R.id.datePicker);

                        String title = text_title.getText().toString();
                        String description = text_description.getText().toString();
                        String date = date_picker.getDayOfMonth() + "/" + date_picker.getMonth() + "/" + date_picker.getYear();

                        // validate the entered values
                        String validationMessage = "";
                        boolean isShowValidation = false;
                        if (title.isEmpty() || (title.equalsIgnoreCase("Title"))) {
                            validationMessage = "Task title canot be empty. Add the title";
                            isShowValidation = true;
                        }

                        if (description.isEmpty() || (description.equalsIgnoreCase("Description"))) {
                            validationMessage = "Task description cannot be empty. Add the description";
                            isShowValidation = true;
                        }

                        if (isShowValidation) {
                            AlertDialog.Builder validationDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);

                            validationDialogBuilder.setTitle("Enter the details");
                            validationDialogBuilder.setMessage(validationMessage);
                            validationDialogBuilder.setCancelable(true);

                            validationDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            // show dialog
                            AlertDialog alert = validationDialogBuilder.create();
                            alert.show();
                        } else {
                            TaskDetails task = db.getTask(i);
                            task.setTask_name(title);
                            task.setTask_description(description);
                            task.setTask_date(date);
                            task.setTask_status(0);
                            db.updateTask(task);
                            cursorAdapter.changeCursor(db.getAllData());

                            alertDialog.dismiss();
                        }
                    }
                });

                // set onclick listener for dialog cancel button
                Button button_cancel = (Button) promptsView.findViewById(R.id.button_cancel);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                // show dialog
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            // add new Task
            // showTaskDialog(0, true);

            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            final View promptsView = li.inflate(R.layout.add_item_layout, null);

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            // set add_item_layout.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            // get the save button handle
            Button btn_save = (Button) promptsView.findViewById(R.id.button_save);

            // set the onclick listener for save button.
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // set the custom dialog components - title, description, date of task and status
                    final EditText text_title = (EditText) alertDialog.findViewById(R.id.editText_title);
                    final EditText text_description = (EditText) alertDialog.findViewById(R.id.editText_description);
                    final DatePicker date_picker = (DatePicker) alertDialog.findViewById(R.id.datePicker);

                    String title = text_title.getText().toString();
                    String description = text_description.getText().toString();
                    String date = date_picker.getDayOfMonth() + "/" + date_picker.getMonth() + "/" + date_picker.getYear();

                    // validate the entered values
                    String validationMessage = "";
                    boolean isShowValidation = false;
                    if (title.isEmpty() || (title.equalsIgnoreCase("Title"))) {
                        validationMessage = "Task title canot be empty. Add the title";
                        isShowValidation = true;
                    }

                    if (description.isEmpty() || (description.equalsIgnoreCase("Description"))) {
                        validationMessage = "Task description cannot be empty. Add the description";
                        isShowValidation = true;
                    }

                    if (isShowValidation) {
                        AlertDialog.Builder validationDialogBuilder = new AlertDialog.Builder(
                                MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);

                        validationDialogBuilder.setTitle("Enter the details");
                        validationDialogBuilder.setMessage(validationMessage);
                        validationDialogBuilder.setCancelable(true);

                        validationDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        // show dialog
                        AlertDialog alert = validationDialogBuilder.create();
                        alert.show();
                    } else {

                        // create alert dialog
                        //  final AlertDialog alertDialog = alertDialogBuilder.create();

                        TaskDetails task = new TaskDetails(title, description, date, 0);
                        db.addTask(task);

                        // set the user entered data into list
                        updateListViewFromDataBase();

                        alertDialog.dismiss();
                    }
                }
            });

            // set onclick listener for dialog cancel button
            Button button_cancel = (Button) promptsView.findViewById(R.id.button_cancel);
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            // show dialog
            alertDialog.show();

            return true;
        } else if ( id == R.id.action_status ) {
            // show only completed tasks.
            Intent completedTaskIntent = new Intent(MainActivity.this, CompletedTasks.class);
            completedTaskIntent.putExtra("Database", db);
            // completedTaskIntent.putExtra("Complete", cursorAdapter);

            startActivity(completedTaskIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListViewFromDataBase() {

        cursorAdapter.changeCursor(db.getAllData());
    }

    void showTaskDialog(final int itemId, final boolean isAddTask) {
        // set add_item_layout.xml to alertdialog builder

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View dialogView = li.inflate(R.layout.add_item_layout, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText text_title = (EditText) alertDialog.findViewById(R.id.editText_title);
        final EditText text_description = (EditText) alertDialog.findViewById(R.id.editText_description);
        final DatePicker date_picker = (DatePicker) alertDialog.findViewById(R.id.datePicker);

        String title = text_title.getText().toString();
        String description = text_description.getText().toString();
        String date = date_picker.getDayOfMonth() + "/" + date_picker.getMonth() + "/" + date_picker.getYear();

        // get the save button handle
        Button btn_save = (Button) dialogView.findViewById(R.id.button_save);

        // set the onclick listener for save button.
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the custom dialog components - title, description, date of task and status
                final EditText text_title = (EditText) alertDialog.findViewById(R.id.editText_title);
                final EditText text_description = (EditText) alertDialog.findViewById(R.id.editText_description);
                final DatePicker date_picker = (DatePicker) alertDialog.findViewById(R.id.datePicker);

                String title = text_title.getText().toString();
                String description = text_description.getText().toString();
                String date = date_picker.getDayOfMonth() + "/" + date_picker.getMonth() + "/" + date_picker.getYear();

                // validate the entered values
                String validationMessage = "";
                boolean isShowValidation = false;
                if ( title.isEmpty() || (title.equalsIgnoreCase("Title") ) ) {
                    validationMessage = "Task title canot be empty. Add the title";
                    isShowValidation = true;
                }

                if ( description.isEmpty() || (description.equalsIgnoreCase("Description") ) ) {
                    validationMessage = "Task description cannot be empty. Add the description";
                    isShowValidation = true;
                }

                if ( isShowValidation ) {
                    AlertDialog.Builder validationDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);

                    validationDialogBuilder.setTitle("Enter the details");
                    validationDialogBuilder.setMessage(validationMessage);
                    validationDialogBuilder.setCancelable(true);

                    validationDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    // show dialog
                    AlertDialog alert = validationDialogBuilder.create();
                    alert.show();
                }

                if ( isAddTask ) {
                    // add new task
                    TaskDetails task = new TaskDetails(title, description, date, 0);
                    db.addTask(task);
                } else {
                    // edit task
                    TaskDetails task = db.getTask(itemId);
                    task.setTask_name(title);
                    task.setTask_description(description);
                    task.setTask_date(date);
                    task.setTask_status(0);

                    db.updateTask(task);
                    cursorAdapter.changeCursor(db.getAllData());
                }

                // set the user entered data into list
                updateListViewFromDataBase();
                alertDialog.dismiss();
            }
        });

        // set onclick listener for dialog cancel button
        Button button_cancel = (Button) dialogView.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
