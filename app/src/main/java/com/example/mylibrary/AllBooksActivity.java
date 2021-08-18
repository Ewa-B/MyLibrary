package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        // Create the Up button (left corner arrow)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //to start our animation (Create 'anim' folder and files in resources)
        //it doesn't work for some reason!!!
//       overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        adapter = new BookRecViewAdapter(this, "allBooks");
        booksRecView = findViewById(R.id.booksRecView);

        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAllBooks());

    }

    //override this method for our Up button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //id of the Up button
            case android.R.id.home:
        //to navigate back to mainActivity: create an Intent or use onBackPressed() method
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //this method will allow animation when go back button is pressed
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
//    }
}