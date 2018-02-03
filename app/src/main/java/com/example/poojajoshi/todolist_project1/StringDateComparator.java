package com.example.poojajoshi.todolist_project1;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Comparator;

class StringDateComparator implements Comparator<String>
{
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public int compare(String lhs, String rhs)
    {
        try {
            return dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
        } catch(ParseException e) {

        }
        return 0;
    }
}