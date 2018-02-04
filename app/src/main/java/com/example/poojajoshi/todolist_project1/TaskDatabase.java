package com.example.poojajoshi.todolist_project1;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;
import java.lang.String;

public class TaskDatabase extends SQLiteOpenHelper implements Serializable{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "taskManager";

    // Contacts table name
    private static final String TABLE_TASKS = "new_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TASK_DATE = "date";
    private static final String KEY_STATUS = "status";

    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "( "
                    + "_id" + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT, "
                    + KEY_DESCRIPTION + " TEXT, " + KEY_TASK_DATE + " TEXT, "
                    + KEY_STATUS + " INTEGER )";
            db.execSQL(CREATE_TASK_TABLE);
        } catch (SQLiteException  e) {

        }
    }

    // Upgrading database
    // @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        Log.d("drop table", "drop");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        Log.d("drop table", "drop");

        // DATABASE_VERSION = newVersion;
        // Create tables again
        onCreate(db);
        Log.d("drop table", "drop");

    }

    // Adding new task
    public void addTask(TaskDetails task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_ID, task.getID());
        values.put(KEY_TITLE, task.getTask_name());
        values.put(KEY_DESCRIPTION, task.getTask_description());
        values.put(KEY_TASK_DATE, task.getTask_date());
        values.put(KEY_STATUS, task.getTask_status());
        // values.p

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        // db.close(); // Closing database connection
    }

    // get all tasks
    public Cursor getAllData () {

        String buildSQL = "SELECT _id, title, description, date, status FROM " + TABLE_TASKS + " ORDER BY date ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(buildSQL, null);
    }


    // Getting single contact
    public TaskDetails getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_ID,
                                KEY_TITLE, KEY_DESCRIPTION, KEY_TASK_DATE, KEY_STATUS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TaskDetails taskDetails = new TaskDetails(Integer.parseInt(cursor.getString(0)),
                                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                                    Integer.parseInt(cursor.getString(4)));
        // return task
        return taskDetails;
    }

    // Getting All Tasks
    public List<TaskDetails> getAllTasks() {
        List<TaskDetails> taskList = new ArrayList<TaskDetails>();

        // Select All Query
        String selectQuery = "SELECT _id, title, description, date, status FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskDetails task = new TaskDetails();
                // task.setID(Integer.parseInt(cursor.getString(0)));
                task.setTask_name(cursor.getString(1));
                task.setTask_description(cursor.getString(2));
                task.setTask_date(cursor.getString(3));
                task.setTask_status(Integer.parseInt(cursor.getString(4)));

                Log.i("title", cursor.getString(1));
                Log.i("description", cursor.getString(2));
                Log.i("date", cursor.getString(3));

                // Adding task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return task list
        return taskList;
    }

    // Getting completed tasks
    public List<TaskDetails> getAllCompletedTasks() {
        List<TaskDetails> taskList = new ArrayList<TaskDetails>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + "WHERE " + KEY_STATUS + "=1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Log.d("Cursor", "cursor");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskDetails task = new TaskDetails();
                // task.setID(Integer.parseInt(cursor.getString(0)));
                task.setTask_name(cursor.getString(1));
                task.setTask_description(cursor.getString(2));
                task.setTask_date(cursor.getString(3));
                task.setTask_status(Integer.parseInt(cursor.getString(4)));

                // Adding task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return  taskList;
    }

    public Cursor getAllCompletedData () {

        String buildSQL = "SELECT _id, title, description, date, status FROM " + TABLE_TASKS + "where " + KEY_STATUS + "=1";

        // Log.d(TAG, "getAllData SQL: " + buildSQL);

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(buildSQL, null);
    }

    // Getting task Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single task
    public int updateTask(TaskDetails taskDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, taskDetails.getTask_name());
        values.put(KEY_DESCRIPTION, taskDetails.getTask_description());
        values.put(KEY_TASK_DATE, taskDetails.getTask_date());
        values.put(KEY_STATUS, taskDetails.getTask_status());


        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(taskDetails.getID()) });
    }

    // Deleting single task
    public void deleteContact(TaskDetails taskDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(taskDetails.getID()) });
        db.close();
    }
}
