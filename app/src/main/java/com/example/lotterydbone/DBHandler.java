package com.example.lotterydbone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

    /*
    Usage of  program DB Browser for SQLite (http://sqlitebrowser.org/)
    Find database with the Device File Explorer (https://developer.android.com/studio/debug/device-file-explorer)

    See for details https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper

    If you want to execute something in database without concerning its output (e.g create/alter tables),
    then use execSQL, but if you are expecting some results in return against your query (e.g. select records)
    then use rawQuery.
    */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "playerdb.db";
    private static final String TABLE_PLAYER = "playerdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        // Create tables again
        onCreate(db);
    }

    // Necessary CRUD (Create, Read) Operations

    // Entering a new player
    public void insertUserDetails(String name, String location) {
        // Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        // Convenience method for inserting a row into the database: Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_PLAYER, null, cValues);
        db.close();
    }

    // Get the player details
    public ArrayList<HashMap<String, String>> getPlayers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location FROM " + TABLE_PLAYER + " order by name, location";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put(KEY_LOC, cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        cursor.close();
        return userList;
    }

    // Get only the player names
    public ArrayList<String> getPlayerNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> userNames = new ArrayList<>();
        String query = "SELECT name FROM " + TABLE_PLAYER + " order by lower (name)";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            userNames.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        }
        cursor.close();
        return userNames;
    }

}
