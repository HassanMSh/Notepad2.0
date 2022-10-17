package com.example.notepad20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvAllNotes;
    String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAllNotes = findViewById(R.id.lvAllNotes);

        Intent i = getIntent();
        userEmail = i.getStringExtra("EMAIL");


        ArrayList<Notes> notes = new ArrayList<>();

        ArrayAdapter<Notes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        lvAllNotes.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://hassanshamseddine12010064android.000webhostapp.com/getAllNotes.php?U="+userEmail;

        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            for (int i1 = 0; i1 < response.length(); i1++) {
                try {
                    JSONObject row = response.getJSONObject(i1);

                    int id = row.getInt("id");
                    String title = row.getString("title");
                    String text = row.getString("text");

                    Notes n = new Notes(id, title, text);
                    notes.add(n);

                    lvAllNotes.setOnItemClickListener((parent, view, position, x) -> {

                        Intent i11 =new Intent(MainActivity.this,Note.class);

                        i11.putExtra("ID", "" + notes.get(position).id);
                        i11.putExtra("EMAIL", userEmail);
                        startActivity(i11);
                    });

                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
            adapter.notifyDataSetChanged();
        }, null);

        queue.add(request);

    }

    public void addNote(View view) {
        Intent i = new Intent (this,addNewNote.class);
        i.putExtra("EMAIL", userEmail);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(i), 1000);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}