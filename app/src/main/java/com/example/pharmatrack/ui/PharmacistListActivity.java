package com.example.pharmatrack.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.PharmacistDao;

public class PharmacistListActivity extends AppCompatActivity {
    PharmacistDao dao; ArrayAdapter<String> adapter;

    @Override protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_pharmacists);
        dao = new PharmacistDao(this);
        ListView list = findViewById(R.id.listPharmacists);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.all());
        list.setAdapter(adapter);
        list.setOnItemLongClickListener((p,v,idx,id)->{ remove(idx); return true;});
        findViewById(R.id.fabAddPharm).setOnClickListener(v-> dialogAdd());
    }

    void refresh(){ adapter.clear(); adapter.addAll(dao.all()); adapter.notifyDataSetChanged(); }

    void dialogAdd() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_pharmacist,null);
        EditText u=view.findViewById(R.id.dlgUser), p=view.findViewById(R.id.dlgPass);
        new AlertDialog.Builder(this).setTitle("Novi apotekar")
                .setView(view)
                .setPositiveButton("Snimi", (d,which)->{ dao.add(u.getText().toString(),p.getText().toString(),false); refresh();})
                .setNegativeButton("Odustani",null).show();
    }

    void remove(int pos){ new AlertDialog.Builder(this)
            .setMessage("Obrisati apotekara?")
            .setPositiveButton("Da",(d,w)->{ dao.remove(pos+1); refresh(); })
            .setNegativeButton("Ne",null).show(); }
}