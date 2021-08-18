package com.example.mylibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder>{

    public static final String TAG = "BookRecViewAdapter";

    private ArrayList<Book> books = new ArrayList<>();

    private Context mContext;
    private String parentActivity;

    public BookRecViewAdapter(Context mContext, String parentActivity) {

        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  BookRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        holder.txtBookName.setText(books.get(position).getName());

        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imgBook);
        
        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BookActivity.class);
            intent.putExtra(BOOK_ID_KEY, books.get(position).getId());
//            intent.putExtra("bookName", books.get(position).getName());
        mContext.startActivity(intent);
        });

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtDescription.setText(books.get(position).getShortDescription());

        if(books.get(position).isExpanded()){
            //add animation
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            if (parentActivity.equals("allBooks")){
                holder.btnDelete.setVisibility(View.GONE);
            }else if (parentActivity.equals("alreadyRead")){
                holder.btnDelete.setVisibility(View.VISIBLE);
                /**
                 Remove Book from an ArrayList in Utils class
                 Get a book name before we remove it
                 **/
                holder.btnDelete.setOnClickListener(v -> {
                    String bookName = books.get(position).getName();

                    // Create a simple dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete this book " + bookName + "?");
                    // Create positive button with onClickListener
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(Utils.getInstance(mContext).removeFromAlreadyRead(books.get(position))){
                                Toast.makeText(mContext, bookName + " removed.", Toast.LENGTH_SHORT).show();
                                //we also have to notify that the dataset has been changed
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(mContext, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // Create negative button
                    builder.setNegativeButton("No", (dialog, which) -> {
                        // This onClickListener doesn't have to do anything because we want to keep the book when pressing it
                    });

                    // After making positive and negative buttons we gave to create and show our builder
                    builder.create().show();

                });
            } else if (parentActivity.equals("wantToRead")){
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(v -> {
                    String bookName = books.get(position).getName();

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete this book " + bookName + "?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        if(Utils.getInstance(mContext).removeFromWantToRead(books.get(position))){
                            Toast.makeText(mContext, bookName + " removed.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(mContext, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                        // This onClickListener doesn't have to do anything because we want to keep the book when pressing it
                    });

                    // After making positive and negative buttons we gave to create and show our builder
                    builder.create().show();
                });

            }else if (parentActivity.equals("currentlyReading")){
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(v -> {
                    String bookName = books.get(position).getName();

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete this book " + bookName + "?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        if(Utils.getInstance(mContext).removeCurrentlyReading(books.get(position))){
                            Toast.makeText(mContext, bookName + " removed.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(mContext, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                        // This onClickListener doesn't have to do anything because we want to keep the book when pressing it
                    });

                    // After making positive and negative buttons we gave to create and show our builder
                    builder.create().show();
                });
            }else{
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(v -> {
                    String bookName = books.get(position).getName();

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete this book " + bookName + "?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        if(Utils.getInstance(mContext).removeFromFavourites(books.get(position))){
                            Toast.makeText(mContext, bookName + " removed.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(mContext, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                        // This onClickListener doesn't have to do anything because we want to keep the book when pressing it
                    });

                    // After making positive and negative buttons we gave to create and show our builder
                    builder.create().show();
                });
            }
        }else{
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView imgBook;
        private TextView txtBookName;

        private ImageView downArrow, upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView txtAuthor, txtDescription;

        private TextView btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtBookName = itemView.findViewById(R.id.txtBookName);

            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDescription = itemView.findViewById(R.id.txtShortDesc);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            downArrow.setOnClickListener(v -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            upArrow.setOnClickListener(v -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

        }
    }
}
