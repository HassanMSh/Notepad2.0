package com.example.notepad20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterLogin extends AppCompatActivity {

    EditText etEmail,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

    }

    public void Register(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://hassanshamseddine12010064android.000webhostapp.com/AddUser.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> Toast.makeText(RegisterLogin.this, response, Toast.LENGTH_SHORT).show(), error -> Toast.makeText(RegisterLogin.this, error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("EMAIL", etEmail.getText().toString());
                params.put("PASSWORD", etPassword.getText().toString());

                return params;
            }
        };

        queue.add(request);
    }


    public void Login(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://hassanshamseddine12010064android.000webhostapp.com/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            String s=response.trim();

            if(s.equalsIgnoreCase("success")) {

                Intent i = new Intent(RegisterLogin.this, MainActivity.class);
                i.putExtra("EMAIL", etEmail.getText().toString());
                startActivity(i);
            }
            else
                Toast.makeText(RegisterLogin.this, response, Toast.LENGTH_SHORT).show();

        }, error -> Toast.makeText(RegisterLogin.this, error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("EMAIL", etEmail.getText().toString());
                params.put("PASSWORD", etPassword.getText().toString());

                return params;
            }
        };

        queue.add(request);
    }
}
