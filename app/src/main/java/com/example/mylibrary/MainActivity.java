package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnAlreadyRead, btnWantToRead, btnCurrentlyReading, btnFavourite, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        /**
         * Call the constructor of Utils class and initialize our array lists
         */
        Utils.getInstance(this);

        btnAllBooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
            startActivity(intent);
        });

        btnAlreadyRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
            startActivity(intent);
        });
        btnWantToRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
            startActivity(intent);
        });
        btnFavourite.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });
        btnCurrentlyReading.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentlyReadingActivity.class);
            startActivity(intent);
        });
        btnAbout.setOnClickListener(v -> {
            // Create alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage("Design and Developed with Love by Ewa at github.com/Ewa-B\n" +
                    "Check my website for more awesome applications");
            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this on clickListener will dismiss the dialog, don't have to do anything
                }
            });
           builder.setPositiveButton("Visit", ((dialog, which) -> {
               Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
               intent.putExtra("url", "https://google.com/");
               startActivity(intent);
           }));
           builder.create().show();
        });
    }

    private void initViews(){
        btnAbout = findViewById(R.id.btnAbout);
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReading);
        btnFavourite = findViewById(R.id.btnFavourites);
        btnWantToRead = findViewById(R.id.btnWantToRead);
    }
}