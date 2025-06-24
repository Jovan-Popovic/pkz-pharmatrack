package com.example.pharmatrack.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;

public class AddEditMedicineActivity extends AppCompatActivity {
    MedicineDao dao;
    EditText n, m, q, p;
    long id = -1;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_add_edit_med);
        dao = new MedicineDao(this);
        n = findViewById(R.id.etName);
        m = findViewById(R.id.etManufacturer);
        q = findViewById(R.id.etQty);
        p = findViewById(R.id.etPrice);
        id = getIntent().getLongExtra("medId", -1);
        if (id != -1) load();
        findViewById(R.id.btnSave).setOnClickListener(v -> save());
    }

    void load() {
        for (MedicineItem mi : dao.all())
            if (mi.id == id) {
                n.setText(mi.name);
                m.setText(mi.manufacturer);
                q.setText(String.valueOf(mi.qty));
                p.setText(String.valueOf(mi.price));
            }
    }

    void save() {
        String name = n.getText().toString();
        int qty = Integer.parseInt(q.getText().toString());
        double price = Double.parseDouble(p.getText().toString());
        String manuf = m.getText().toString();
        if (id == -1) dao.add(name, manuf, qty, price);
        else dao.update(id, name, manuf, qty, price);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}