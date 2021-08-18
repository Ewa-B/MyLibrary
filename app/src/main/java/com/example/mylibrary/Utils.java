package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

//when implementing singleton we can have only one instance of that class
public class Utils {

//    shared preferences will serialize our objects and persistent our data after closing our application
//    in shared preferences you can save unstructured data
    //initialize it inside a constructor, get context in a constructor
    private SharedPreferences sharedPreferences;

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVOURITE_BOOKS = "favourite_books";


    //static instance of this class
    private static Utils instance;

//    private static ArrayList <Book> allBooks;
//    private static ArrayList <Book> alreadyReadBooks;
//    private static ArrayList <Book> wantToReadBooks;
//    private static ArrayList <Book> currentlyReadingBooks;
//    private static ArrayList <Book> favouriteBooks;
//


    //empty constructor (private)
    //initialize lists in a constructor
    private Utils(Context context) {

            //name: is the key for sharedPreferences
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);


        if (null == getAllBooks()){
//            allBooks = new ArrayList<>();
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAllBooks()){
            //alreadyReadBooks = new ArrayList<>();
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.apply();
        }
        if (null == getWantToReadBooks()){
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.apply();
        }
        if (null == getCurrentlyReadingBooks()) {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.apply();
        }
        if (null == getFavouriteBooks()) {
            editor.putString(FAVOURITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.apply();
        }
    }

    private void initData() {
        //TODO: add initial data

        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1, "1Q84", "Haruki Murakami", 1350, "https://publishingperspectives.com/wp-content/uploads/2014/09/cover-1Q84.jpg",
                "A work of maddening brilliance", "Long Description"));
        //remember to add an internet permission in a manifest file
        books.add(new Book(2, "1984", "George Orwell", 328, "https://anylang.net/sites/default/files/covers/1984.jpg",
                "A dystopian social science fiction", "Long Description"));
        books.add(new Book(3, "Pride and Prejudice", "Jane Austin", 416, "https://www.penguin.co.uk/content/dam/prh/books/183/183689/9780141199078.jpg.transform/PRHDesktopWide_small/image.jpg",
                "A romantic novel of manners", "Long Description"));

        // Add books to sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Convert java classes and arrayLists int to json file
        //json is a way of passing data between different applications regardless of the language
        //implement gson library in our dependencies -> to serialize and deserialize our objects

        //Create an instance of gson object
        Gson gson = new Gson();
        //add arrayList to shared preferences
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.apply();
    }



    public static Utils getInstance(Context context){
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getFavouriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS, null), type);
        return books;
    }

    public Book getBookById(int id){
        ArrayList<Book> books = getAllBooks();
        if (null != books){
            for (Book b : books){
                if (b.getId() == id){
                    return b;
                }
            }
        }

        return null;
    }
    public boolean addToAlreadyRead (Book b) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books){
            if(books.add(b)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
        //add method returns boolean if book is added successfully or not
//        return alreadyReadBooks.add(b);
    }
    public boolean addToWantToRead(Book b){
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books){
            if(books.add(b)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean addToFavourites(Book b){
        ArrayList<Book> books = getFavouriteBooks();
        if (null != books){
            if(books.add(b)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITE_BOOKS);
                editor.putString(FAVOURITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean addToCurrentlyReading(Book b){
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books){
            if(books.add(b)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean removeFromAlreadyRead(Book book){
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books){
            for (Book b : books){
               if(b.getId() == book.getId()){
                   if (books.remove(b)){
                       Gson gson = new Gson();
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.remove(ALREADY_READ_BOOKS);
                       editor.putString(ALREADY_READ_BOOKS, gson.toJson(ALREADY_READ_BOOKS));
                       editor.commit();
                       return true;
                   }
               }
            }
        }
        return false;
    }
    public boolean removeFromFavourites(Book book){
        ArrayList<Book> books = getFavouriteBooks();
        if (null != books){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITE_BOOKS);
                        editor.putString(FAVOURITE_BOOKS, gson.toJson(FAVOURITE_BOOKS));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromWantToRead(Book book){
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS, gson.toJson(WANT_TO_READ_BOOKS));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeCurrentlyReading(Book book){
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(CURRENTLY_READING_BOOKS));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
