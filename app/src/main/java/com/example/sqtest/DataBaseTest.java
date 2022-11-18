package com.example.sqtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTest extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_AGE = "USER_AGE";
    public static final String COLUMN_ACTIVE_USER = "ACTIVE_USER";
    public static final String COLUMN_ID = "ID";

    public DataBaseTest(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    //This is called the first time a database is accessed. There should be code here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
            String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_AGE + " INT, " +
                    COLUMN_ACTIVE_USER + " BOOL)";

            db.execSQL(createTableStatement);
    }


    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(CustomerModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_USER_AGE, userModel.getAge());
        cv.put(COLUMN_ACTIVE_USER, userModel.isActive());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOne(CustomerModel customerModel) {
       //find customerModel in the database. if it is found, delete it and return true.
       //if it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

        public List<CustomerModel> getEveryone()
    {

            List<CustomerModel> returnList = new ArrayList<>();

            //get data from the database
            String queryString = "SELECT  * FROM " + USER_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new customer objects. put them in return list

            do {
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            int userAge = cursor.getInt(2);
            boolean userActive = cursor.getInt(3) == 1 ? true: false;

            CustomerModel newUser = new CustomerModel(userID, userName, userAge, userActive);
            returnList.add(newUser);



            } while (cursor.moveToNext());

            }
        else {
            //does not add anything to list
            }
            //close both the cursor and db when done
            cursor.close();
            db.close();
            return returnList;

        }

}

