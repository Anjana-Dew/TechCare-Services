package com.example.techcare_services;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "TechCareSession";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_ROLE = "role";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TECHNICIAN_ID = "technician_id";


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context c) {
        pref = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setUserId(long id) {

        editor.putLong(KEY_USER_ID, id).apply();
    }

    public long getUserId() {

        return pref.getLong(KEY_USER_ID, -1);
    }

    public void setCustomerId(int id) {

        editor.putInt(KEY_CUSTOMER_ID, id).apply();
    }

    public int getCustomerId() {

        return pref.getInt(KEY_CUSTOMER_ID, -1);
    }

    public void setRole(String role) {

        editor.putString(KEY_ROLE, role).apply();
    }

    public void setFullName(String name) {

        editor.putString(KEY_FULL_NAME, name).apply();
    }

    public String getFullName() {

        return pref.getString(KEY_FULL_NAME, "");
    }
    public String getRole() {

        return pref.getString(KEY_ROLE, null);
    }
    public void setEmail(String email) {

        editor.putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {

        return pref.getString(KEY_EMAIL, "");
    }
    public void logout() {

        editor.clear().apply();
    }

    public void setTechnicianId(int id) {

        editor.putInt(KEY_TECHNICIAN_ID, id).apply();
    }

    public int getTechnicianId() {

        return pref.getInt(KEY_TECHNICIAN_ID, -1);
    }

}

