package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class AlreadyReadBookActivity extends AppCompatActivity {

    private RecyclerView recAlreadyRead;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read_book);

        recAlreadyRead = findViewById(R.id.recAlreadyRead);
        adapter = new BookRecViewAdapter(this, "alreadyRead");

        recAlreadyRead.setAdapter(adapter);
        recAlreadyRead.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAlreadyReadBooks());
    }

    @Override
    public void onBackPressed() {
        /**
         * Navigate the user to the main activity when pressing back key
         */
        Intent intent = new Intent(this, MainActivity.class);
        /**
         * By using those two flags on the intent we are clearing the back stack from previous activities
         * by pressing go-back button on the app
         */
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}