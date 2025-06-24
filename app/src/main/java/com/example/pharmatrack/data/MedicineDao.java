package com.example.pharmatrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicineDao {
    private final SQLiteDatabase db;

    public MedicineDao(Context ctx) {
        db = new PharmaDbHelper(ctx).getWritableDatabase();
    }

    public long add(String name, String manufacturer, int qty, double price) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("manufacturer", manufacturer);
        cv.put("quantity", qty);
        cv.put("price", price);
        return db.insert("medicines", null, cv);
    }

    public int update(long id, String name, String manufacturer, int qty, double price) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("manufacturer", manufacturer);
        cv.put("quantity", qty);
        cv.put("price", price);
        return db.update("medicines", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public int delete(long id) {
        return db.delete("medicines", "id=?", new String[]{String.valueOf(id)});
    }

    public List<MedicineItem> all() {
        List<MedicineItem> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id,name,manufacturer,quantity,price FROM medicines", null);
        while (c.moveToNext()) {
            list.add(new MedicineItem(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getDouble(4)));
        }
        c.close();
        return list;
    }

    public int dispense(long id, int qty) {
        db.execSQL("UPDATE medicines SET quantity = quantity - ? WHERE id=?", new Object[]{qty, id});
        return 1;
    }

    public static class MedicineItem {
        public long id; public String name, manufacturer; public int qty; public double price;
        public MedicineItem(long id, String name, String manufacturer, int qty, double price) {
            this.id=id;this.name=name;this.manufacturer=manufacturer;this.qty=qty;this.price=price;
        }
    }
}