package com.example.pharmatrack.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineHolder> {

    public interface Listener {
        void onEdit(MedicineItem item);
        void onDelete(MedicineItem item);
    }

    private List<MedicineItem> medicineList;
    private final Listener listener;

    public MedicineAdapter(List<MedicineItem> list, Listener l) {
        medicineList = list;
        listener = l;
    }

    public void update(List<MedicineItem> list) {
        this.medicineList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicineHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medicine, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        MedicineItem medicine = medicineList.get(position);
        holder.titleTv.setText(medicine.name + " – " + medicine.qty);

        holder.editBtn.setOnClickListener(v -> listener.onEdit(medicine));
        holder.deleteBtn.setOnClickListener(v -> listener.onDelete(medicine));
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    static class MedicineHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        ImageButton editBtn, deleteBtn;

        public MedicineHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tvMed);
            editBtn = itemView.findViewById(R.id.btnEdit);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
        }
    }
}