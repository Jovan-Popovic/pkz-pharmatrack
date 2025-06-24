package com.example.pharmatrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.PharmacistDao;
import com.example.pharmatrack.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PharmacistListActivity extends AppCompatActivity implements PharmacistAdapter.DeleteListener {

    private PharmacistDao pharmacistDao;
    private PharmacistAdapter adapter;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String currentUser = getIntent().getStringExtra("currentUser");
        pharmacistDao = new PharmacistDao(this);
        isAdmin = pharmacistDao.isAdmin(currentUser);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        BottomNavigationHelper.setup(nav, this, currentUser);

        RecyclerView recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PharmacistAdapter(pharmacistDao.all(), isAdmin, this);
        recyclerView.setAdapter(adapter);

        android.view.View addFab = findViewById(R.id.fabAddUser);
        if (!isAdmin) addFab.setVisibility(android.view.View.GONE);
        addFab.setOnClickListener(v -> showAddUserDialog());
    }

    private void showAddUserDialog() {
        if (!isAdmin) return;

        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_user, null);
        android.widget.EditText userEt = dialogView.findViewById(R.id.dialogUsername);
        android.widget.EditText passEt = dialogView.findViewById(R.id.dialogPassword);
        android.widget.CheckBox adminCb = dialogView.findViewById(R.id.dialogAdmin);

        new AlertDialog.Builder(this)
                .setTitle("Add user")
                .setView(dialogView)
                .setPositiveButton("Save", (d, w) -> {
                    String u = userEt.getText().toString().trim();
                    String p = passEt.getText().toString();
                    boolean admin = adminCb.isChecked();
                    pharmacistDao.add(u, p, admin);
                    adapter.update(pharmacistDao.all());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDelete(String username) {
        new AlertDialog.Builder(this)
                .setTitle("Delete user")
                .setMessage("Delete user " + username + "?")
                .setPositiveButton("Delete", (d, w) -> {
                    pharmacistDao.removeByUsername(username);
                    adapter.update(pharmacistDao.all());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(pharmacistDao.all());
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