package com.example.loadmorerecyclerview;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/10/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StudentsData";

    // Contacts table name
    private static final String TABLE_STUDENTS = "STUDENTS";

    private static final String KEY_ID = "id";
    private static final String KEY_STNAME = "studName";
    private static final String KEY_R_NO = "rollNumber";

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STNAME + " TEXT,"
                + KEY_R_NO + " INTEGER"
                + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        Log.w(TAG, "onUpgrade: Database upgrade" );
        // Create tables again
        onCreate(db);
    }
    //To add Student data
    public void addStudent(MstStudents mstStudents) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STNAME, mstStudents.getStudname());
        values.put(KEY_R_NO, mstStudents.getRollno());

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);
        Log.w(TAG, "addStudent: added student" );
        db.close(); // Closing database connection
    }

    // Getting single student
    MstStudents getSingleStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, null,
                KEY_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        MstStudents studDetail = new MstStudents(Integer.parseInt(cursor.getString(0)), cursor.getString(1),Integer.parseInt(cursor.getString(2)));
        // return studDetail
        return studDetail;
    }

    //Get only 10 Students records
    public List<MstStudents> getStudents(int j) {
        List<MstStudents> studList = new ArrayList<MstStudents>();
        //Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " LIMIT " + j +", 10  ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through 10 rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MstStudents stud = new MstStudents();
                stud.setId(Integer.parseInt(cursor.getString(0)));
                stud.setStudname(cursor.getString(1));
                stud.setRollno(Integer.parseInt(cursor.getString(2)));

                studList.add(stud);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.w(TAG, "getAllStudents: return list of students");
        // return list containing 10 student
        return studList;
    }
}
