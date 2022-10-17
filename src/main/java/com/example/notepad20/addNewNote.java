package com.example.notepad20;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class addNewNote extends AppCompatActivity {
    EditText edTitle, edNote;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewnote);

        edTitle = findViewById(R.id.edTitle);
        edNote = findViewById(R.id.edNote);


        Intent i=getIntent();
        email = i.getStringExtra("EMAIL");
    }

    public void newNote(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        if (TextUtils.isEmpty(edTitle.getText().toString()) && TextUtils.isEmpty(edNote.getText().toString())) {
            edTitle.setText("Empty Note");
            edTitle.setTypeface(null, Typeface.ITALIC);
        }

        String url = "https://hassanshamseddine12010064android.000webhostapp.com/AddNote.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> Toast.makeText(addNewNote.this, response, Toast.LENGTH_SHORT).show(), error -> Toast.makeText(addNewNote.this, error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("title", edTitle.getText().toString());
                params.put("text", edNote.getText().toString());
                params.put("email",email);

                return params;
            }
        };
        queue.add(request);

        Intent i = new Intent(addNewNote.this, MainActivity.class);

        i.putExtra("EMAIL", email);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);
    }

    public void onBackPressed() {

        Intent i = new Intent(addNewNote.this, MainActivity.class);

        i.putExtra("EMAIL", email);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);
    }
}
