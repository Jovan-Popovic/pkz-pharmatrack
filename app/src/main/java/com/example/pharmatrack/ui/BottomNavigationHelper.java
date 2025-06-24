package com.example.pharmatrack.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pharmatrack.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {
    public static void setup(BottomNavigationView nav, AppCompatActivity activity, String currentUser) {
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_medicines && !(activity instanceof MedicineListActivity)) {
                Intent intent = new Intent(activity, MedicineListActivity.class);
                intent.putExtra("currentUser", currentUser);
                activity.startActivity(intent);
                activity.finish();
            } else if (id == R.id.menu_users && !(activity instanceof PharmacistListActivity)) {
                Intent intent = new Intent(activity, PharmacistListActivity.class);
                intent.putExtra("currentUser", currentUser);
                activity.startActivity(intent);
                activity.finish();
            } else if (id == R.id.menu_prescribe && !(activity instanceof PrescribeMedicineActivity)) {
                Intent intent = new Intent(activity, PrescribeMedicineActivity.class);
                intent.putExtra("currentUser", currentUser);
                activity.startActivity(intent);
                activity.finish();
            }

            return true;
        });
    }
}