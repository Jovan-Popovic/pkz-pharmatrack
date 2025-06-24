package com.example.pharmatrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PharmacistDao {
    private final SQLiteDatabase db;

    public PharmacistDao(Context ctx) {
        db = new PharmaDbHelper(ctx).getWritableDatabase();
    }

    public long add(String user, String pass, boolean admin) {
        ContentValues cv = new ContentValues();
        cv.put("username", user);
        cv.put("password", pass);
        cv.put("is_admin", admin ? 1 : 0);
        return db.insert("pharmacists", null, cv);
    }

    public int remove(long id) {
        return db.delete("pharmacists", "id=?", new String[]{String.valueOf(id)});
    }

    public boolean login(String user, String pass) {
        Cursor c = db.rawQuery("SELECT * FROM pharmacists WHERE username=? AND password=?", new String[]{user, pass});
        boolean ok = c.moveToFirst();
        c.close();
        return ok;
    }

    public boolean isAdmin(String user) {
        Cursor c = db.rawQuery("SELECT is_admin FROM pharmacists WHERE username=?", new String[]{user});
        boolean admin = false;
        if (c.moveToFirst()) admin = c.getInt(0) == 1;
        c.close();
        return admin;
    }

    public List<String> all() {
        List<String> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT username FROM pharmacists", null);
        while (c.moveToNext()) list.add(c.getString(0));
        c.close();
        return list;
    }
}