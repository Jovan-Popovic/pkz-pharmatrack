package com.example.pharmatrack.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;

import java.util.ArrayList;
import java.util.List;

public class DispenseActivity extends AppCompatActivity {
    MedicineDao dao; Spinner spMed; EditText etQty, etPatient;
    List<MedicineItem> meds = new ArrayList<>();

    @Override protected void onCreate(Bundle saved){ super.onCreate(saved); setContentView(R.layout.activity_dispense);
        dao=new MedicineDao(this);
        spMed=findViewById(R.id.spMed); etQty=findViewById(R.id.etQtyDisp); etPatient=findViewById(R.id.etPatient);
        refresh();
        findViewById(R.id.btnDisp).setOnClickListener(v->disp());
    }
    void refresh(){ meds=dao.all(); List<String> names=new ArrayList<>(); for(MedicineItem m:meds) names.add(m.name+" ("+m.qty+")");
        spMed.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names)); }
    void disp(){ int pos=spMed.getSelectedItemPosition(); int q=Integer.parseInt(etQty.getText().toString()); MedicineItem mi=meds.get(pos);
        if(q>mi.qty){ Toast.makeText(this,"Nema dovoljno zaliha", Toast.LENGTH_SHORT).show(); return; }
        dao.dispense(mi.id,q); Toast.makeText(this,"Izdato",Toast.LENGTH_SHORT).show(); finish(); }
}