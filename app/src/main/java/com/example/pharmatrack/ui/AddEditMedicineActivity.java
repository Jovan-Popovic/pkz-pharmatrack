package com.example.pharmatrack.ui;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;
import com.example.pharmatrack.util.SessionManager;

import java.util.Locale;

public class AddEditMedicineActivity extends AppCompatActivity {

    private MedicineDao medicineDao;
    private EditText nameEt, manufacturerEt, quantityEt, priceEt, expiryEt;
    private Button saveBtn;
    private long medicineId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_med);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicineDao = new MedicineDao(this);

        nameEt = findViewById(R.id.etName);
        manufacturerEt = findViewById(R.id.etManufacturer);
        quantityEt = findViewById(R.id.etQty);
        priceEt = findViewById(R.id.etPrice);
        expiryEt = findViewById(R.id.etExpiry);
        saveBtn = findViewById(R.id.btnSave);

        expiryEt.setInputType(android.text.InputType.TYPE_NULL); // Prevent keyboard
        expiryEt.setFocusable(false); // Optional, makes it act like a button

        expiryEt.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (view, y, m, d) -> {
                // Format: YYYY-MM-DD
                String selectedDate = String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d);
                expiryEt.setText(selectedDate);
            }, year, month, day).show();
        });

        medicineId = getIntent().getLongExtra("id", -1);
        if (medicineId != -1) {
            populateForEdit();
            saveBtn.setText("Edit");
        }

        saveBtn.setOnClickListener(v -> save());
    }

    private void populateForEdit() {
        MedicineItem item = medicineDao.get(medicineId);
        if (item == null) return;
        nameEt.setText(item.name);
        manufacturerEt.setText(item.manufacturer);
        quantityEt.setText(String.valueOf(item.qty));
        priceEt.setText(String.valueOf(item.price));
        expiryEt.setText(item.expiry == null ? "" : item.expiry);
    }

    private void save() {
        String name = nameEt.getText().toString().trim();
        String manufacturer = manufacturerEt.getText().toString().trim();
        int qty = Integer.parseInt(quantityEt.getText().toString());
        double price = Double.parseDouble(priceEt.getText().toString());
        String expiry = expiryEt.getText().toString().trim();

        if (medicineId == -1) {
            medicineDao.add(name, manufacturer, qty, price, expiry);
            Toast.makeText(this, "Medicine added", Toast.LENGTH_SHORT).show();
        } else {
            medicineDao.update(medicineId, name, manufacturer, qty, price, expiry);
            Toast.makeText(this, "Medicine updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            new SessionManager(this).clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}