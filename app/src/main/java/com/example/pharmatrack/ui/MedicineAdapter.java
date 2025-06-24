package com.example.pharmatrack.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.Holder> {
    public interface Click {
        void onClick(MedicineItem m);
    }

    private List<MedicineItem> list;
    private Click cb;

    public MedicineAdapter(List<MedicineItem> l, Click c) {
        list = l;
        cb = c;
    }

    public void update(List<MedicineItem> l) {
        this.list = l;
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView t;

        Holder(View v) {
            super(v);
            t = v.findViewById(R.id.tvMed);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.row_medicine, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int i) {
        MedicineItem m = list.get(i);
        h.t.setText(m.name + " – " + m.qty);
        h.itemView.setOnClickListener(v -> cb.onClick(m));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}