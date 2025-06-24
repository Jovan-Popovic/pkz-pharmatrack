package com.example.pharmatrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;

public class MedicineListActivity extends AppCompatActivity {
    MedicineDao dao; MedicineAdapter adapter;
    @Override protected void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.activity_medicines);
        dao = new MedicineDao(this);
        RecyclerView rv = findViewById(R.id.rvMeds);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(dao.all(), item-> edit(item.id));
        rv.setAdapter(adapter);
        findViewById(R.id.fabAddMed).setOnClickListener(v-> add());
        findViewById(R.id.fabDispense).setOnClickListener(v-> startActivity(new Intent(this, DispenseActivity.class)));
    }

    void add(){ startActivity(new Intent(this, AddEditMedicineActivity.class)); }
    void edit(long id){ Intent i=new Intent(this,AddEditMedicineActivity.class); i.putExtra("medId",id); startActivity(i);}
    @Override protected void onResume(){ super.onResume(); adapter.update(dao.all()); }
}