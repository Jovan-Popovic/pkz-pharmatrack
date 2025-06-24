package com.example.pharmatrack.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PharmaDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "pharmatrack.db";
    private static final int DB_VERSION = 1;

    public PharmaDbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pharmacists(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "is_admin INTEGER NOT NULL DEFAULT 0);");

        db.execSQL("CREATE TABLE medicines(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "manufacturer TEXT," +
                "quantity INTEGER NOT NULL DEFAULT 0," +
                "price REAL);");

        db.execSQL("CREATE TABLE dispense(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "medicine_id INTEGER NOT NULL," +
                        "qty INTEGER NOT NULL," +
                        "patient TEXT NOT NULL," +
                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(medicine_id) REFERENCES medicines(id));");

        // create default admin
        db.execSQL("INSERT INTO pharmacists(username,password,is_admin) VALUES('admin','admin',1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dispense");
        db.execSQL("DROP TABLE IF EXISTS medicines");
        db.execSQL("DROP TABLE IF EXISTS pharmacists");
        onCreate(db);
    }
}