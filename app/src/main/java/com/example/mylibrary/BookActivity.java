package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavourites;
    private ImageView bookImage;

    public static final String BOOK_ID_KEY = "bookId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longDescription = "A young woman named Aomame follows a taxi driver’s enigmatic suggestion and begins to notice puzzling discrepancies in the world around her. " +
//                "\n"+
//                "She has entered, she realizes, a parallel existence, which she calls 1Q84 —“Q is for ‘question mark.’ A world that bears a question. ” " +
//                "\n" +
//                "Meanwhile, an aspiring writer named Tengo takes on a suspect ghostwriting project. He becomes so wrapped up with the work and its unusual author that, soon, his previously placid life begins to come unraveled."
//                + "\n" + "A love story, a mystery, a fantasy, a novel of self-discovery, a dystopia to rival George Orwell’s — 1Q84 is Haruki Murakami’s most ambitious undertaking yet: an instant best seller in his native Japan," +
//                "\n"+"and a tremendous feat of imagination from one of our most revered contemporary writers.";
//
//        //TODO: Get data from recycler view in here
//        Book book = new Book(1, "1Q84", "Haruki Murakami", 1350, "https://publishingperspectives.com/wp-content/uploads/2014/09/cover-1Q84.jpg",
//                "A work of maddening brilliance", longDescription);

        //get incoming intent
        Intent intent = getIntent();
        if(null != intent){
            // default value: if there is no id found
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToRead(incomingBook);
                    handleCurrentlyReading(incomingBook);
                    handleFavourite(incomingBook);
                }
            }
        }
    }


    /**
     * Enable and Disable button
     * Add the book to Already Read Book ArrayList
     * @param book
     */
    private void handleAlreadyRead(Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean exist = false;
        for (Book b : alreadyReadBooks){
            if (b.getId() == book.getId()) {
                exist = true;
            }
        }
        if(exist){
            btnAddToAlreadyRead.setEnabled(false);
        }else{
            btnAddToAlreadyRead.setOnClickListener(v -> {
                if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                    Toast.makeText(this, "Book Added to Already Read List", Toast.LENGTH_SHORT).show();
                    /**
                     * Navigate user to AlreadyReadBooks activity
                     * Disable button so can't add it twice
                     */
                    Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                    startActivity(intent);
//                    btnAddToAlreadyRead.setEnabled(false);
                }else {
                    Toast.makeText(this, "Something wrong happened, try one more time", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void handleWantToRead(Book book){
        ArrayList<Book> wantToRead = Utils.getInstance(this).getWantToReadBooks();
        boolean exist = false;
        for (Book b : wantToRead){
            if(b.getId() == book.getId()){
                exist = true;
            }
        }
        if(exist){
            btnAddToWantToRead.setEnabled(false);
        }else {
            btnAddToWantToRead.setOnClickListener(v -> {
                if(Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                    Toast.makeText(this, "Book added to Want to Read List", Toast.LENGTH_SHORT).show();
                    /**
                     * Navigate user to WantToRead activity
                     * Disable Want to read button
                     */
                    Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                    startActivity(intent);
//                    btnAddToWantToRead.setEnabled(false);
                }else {
                    Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void handleCurrentlyReading(Book book){
        ArrayList<Book> currentlyReading = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean exist = false;
        for (Book b : currentlyReading){
            if(b.getId() == book.getId()){
                exist = true;
            }
        }
        if(exist){
            btnAddToCurrentlyReading.setEnabled(false);
        }else{
            btnAddToCurrentlyReading.setOnClickListener(v -> {
                if(Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
                    Toast.makeText(this, "Book added to Currently reading list", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void handleFavourite(Book book){
        ArrayList<Book> favourites = Utils.getInstance(this).getFavouriteBooks();
        boolean exist = false;
        for (Book b : favourites){
            if (b.getId() == book.getId()){
                exist = true;
            }
        }
        if (exist) {
            btnAddToFavourites.setEnabled(false);
        } else {
            btnAddToFavourites.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToFavourites(book)){
                    Toast.makeText(this, "Book added to Favourites List", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, FavouritesActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initViews() {
        txtBookName = findViewById(R.id.txtNameValue);
        txtAuthor = findViewById(R.id.txtAuthorValue);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtLongDesc);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavourites = findViewById(R.id.btnAddToFavourites);
        bookImage = findViewById(R.id.bookImage);
    }
    private void setData(Book book){
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDescription());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }
}