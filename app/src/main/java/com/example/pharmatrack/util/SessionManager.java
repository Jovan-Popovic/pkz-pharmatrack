package com.example.pharmatrack.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "pharma_session";
    private static final String KEY_USER = "current_user";
    private final SharedPreferences prefs;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String username) {
        prefs.edit().putString(KEY_USER, username).apply();
    }

    public String getUser() {
        return prefs.getString(KEY_USER, null);
    }

    public void clear() {
        prefs.edit().remove(KEY_USER).apply();
    }
}