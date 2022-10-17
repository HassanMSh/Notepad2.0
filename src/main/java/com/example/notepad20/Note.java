package com.example.notepad20;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Note extends AppCompatActivity {
    EditText edTitle, edNote;
    Button btnSave, btnDel;
    String idT, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        edTitle = findViewById(R.id.edTitle);
        edNote = findViewById(R.id.edNote);

        btnSave = findViewById(R.id.btnSave);
        btnDel = findViewById(R.id.btnDel);

        Intent i = getIntent();
        idT = i.getStringExtra("ID");
        email = i.getStringExtra("EMAIL");

        RequestQueue queue = Volley.newRequestQueue(this);

        String urlShow = "https://hassanshamseddine12010064android.000webhostapp.com/getNote.php?id=" + idT + "&U=" + email;
        JsonArrayRequest request = new JsonArrayRequest(urlShow, response -> {
            try {
                JSONObject row = response.getJSONObject(0);

                String title = row.getString("title");
                String text = row.getString("text");

                Notes n = new Notes(0, title, text);

                edTitle.setText(n.getTitle());
                edNote.setText(n.getText());

            } catch (Exception ex) {
                Toast.makeText(Note.this, "" + ex, Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(Note.this, error.toString(), Toast.LENGTH_SHORT).show());

        queue.add(request);
    }

    public void saveNote(View view) {
        Bundle extras = getIntent().getExtras();
        idT = extras.getString("ID");
        email = extras.getString("EMAIL");

        RequestQueue edit = Volley.newRequestQueue(Note.this);

        String URL = "https://hassanshamseddine12010064android.000webhostapp.com/UpdateNote.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(Note.this, response, Toast.LENGTH_SHORT).show(), error -> Toast.makeText(Note.this, error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("title", edTitle.getText().toString());
                params.put("text", edNote.getText().toString());
                params.put("id", idT);
                params.put("email",email);

                return params;
            }
        };
        edit.add(request);

        Intent i = new Intent(Note.this,MainActivity.class);
        i.putExtra("EMAIL", email);
        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);

    }

    public void deleteNote(View view) {

        Bundle extras = getIntent().getExtras();
        idT = extras.getString("ID");
        email = extras.getString("EMAIL");

        RequestQueue delete = Volley.newRequestQueue(Note.this);

        if (TextUtils.isEmpty(edTitle.getText().toString())) {
            edTitle.setText("Empty Note");
            edTitle.setTypeface(null, Typeface.ITALIC);
        }

        String URL = "https://hassanshamseddine12010064android.000webhostapp.com/deleteNote.php?id="+ idT +"&U="+email;

        StringRequest request = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(Note.this, response, Toast.LENGTH_SHORT).show(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Note.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", idT);
                params.put("email", email);
                return params;
            }
        };
        delete.add(request);

        Intent i =new Intent(Note.this,MainActivity.class);
        i.putExtra("EMAIL", email);
        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        idT = extras.getString("ID");
        email = extras.getString("EMAIL");

        RequestQueue edit = Volley.newRequestQueue(Note.this);

        if (TextUtils.isEmpty(edTitle.getText().toString())) {
            edTitle.setText("Empty Note");
            edTitle.setTypeface(null, Typeface.ITALIC);
        }

        String URL = "https://hassanshamseddine12010064android.000webhostapp.com/UpdateNote.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(Note.this, response, Toast.LENGTH_SHORT).show(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Note.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("title", edTitle.getText().toString());
                params.put("text", edNote.getText().toString());
                params.put("id", idT);
                params.put("email", email);
                return params;
            }
        };
        edit.add(request);

        Intent i = new Intent(Note.this,MainActivity.class);
        i.putExtra("EMAIL", email);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);
    }
//lol
}