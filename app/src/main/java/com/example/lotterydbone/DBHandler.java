package com.example.lotterydbone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

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
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        // Create tables again
        onCreate(db);
    }

    // ***** CRD (Create, Read, Delete) Operations ***** //

    // Entering a new player
    public void insertUserDetails(String name, String location) {
        // Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_PLAYER, null, cValues);
        db.close();
    }

    // Get the player details
    public ArrayList<HashMap<String, String>> getPlayers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location FROM " + TABLE_PLAYER + " order by lower (name)";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        return userList;
    }

    // Get only the player names
    public ArrayList<String> getPlayerNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> userNames = new ArrayList<>();
        String query = "SELECT name FROM " + TABLE_PLAYER + " order by lower (name)";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            userNames.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        }
        return userNames;
    }


    // Delete User Details
    public void deleteUser(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, KEY_ID + " = ?", new String[]{String.valueOf(userid)});
        db.close();
    }

}
