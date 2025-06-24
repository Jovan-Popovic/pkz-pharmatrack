package com.example.pharmatrack.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;

import java.util.List;

public class PharmacistAdapter extends RecyclerView.Adapter<PharmacistAdapter.UserHolder> {

    public interface DeleteListener {
        void onDelete(String username);
    }

    private List<String> users;
    private final boolean isAdmin;
    private final DeleteListener listener;

    public PharmacistAdapter(List<String> users, boolean isAdmin, DeleteListener listener) {
        this.users = users;
        this.isAdmin = isAdmin;
        this.listener = listener;
    }

    public void update(List<String> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        String username = users.get(position);
        holder.nameTv.setText(username);

        if (isAdmin && !"admin".equalsIgnoreCase(username)) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.deleteBtn.setOnClickListener(v -> listener.onDelete(username));
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageButton deleteBtn;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tvUser);
            deleteBtn = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}