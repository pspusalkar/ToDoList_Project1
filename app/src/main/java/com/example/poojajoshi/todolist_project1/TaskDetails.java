package com.example.poojajoshi.todolist_project1;

import java.io.Serializable;

public class TaskDetails {
    //private variables
    int task_id;
    String task_name;
    String task_description;
    String task_date;
    int task_status;

    // Empty constructor
    public TaskDetails(){

    }
    // constructor
    public TaskDetails(int id, String name, String description, String date, int status){
        this.task_id = id;
        this.task_name = name;
        this.task_description = description;
        this.task_date = date;
        this.task_status = status;
    }

    // constructor
    public TaskDetails(String name, String description, String date, int status){
        this.task_name = name;
        this.task_description = description;
        this.task_date = date;
        this.task_status = status;
    }
    // getting ID
    public int getID(){
        return this.task_id;
    }

    // setting id
    public void setID(int id){
        this.task_id = id;
    }

    // getting name
    public String getTask_name(){
        return this.task_name;
    }

    // setting name
    public void setTask_name(String name){
        this.task_name = name;
    }

    // getting task description
    public String getTask_description(){
        return this.task_description;
    }

    // setting task descrition
    public void setTask_description(String description){
        this.task_description = description;
    }

    // getting task description
    public String getTask_date(){
        return this.task_date;
    }

    // setting task descrition
    public void setTask_date(String date){
        this.task_date = date;
    }

    // getting ID
    public int getTask_status(){
        return this.task_status;
    }

    // setting id
    public void setTask_status(int status){
        this.task_status = status;
    }
}
