package com.example.bookshelf;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class BookDetailsFragment extends Fragment {

    private static final String BOOK_KEY = "book";
    private Book book;

    Context c;

    TextView titleTextView, authorTextView, progresstext;
    ImageView coverImageView;
    Button playbutton;

    private bookdetailsinterface parentActivity;

    public BookDetailsFragment() {}

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();

        /*
         Our Book class implements the Parcelable interface
         therefore we can place one inside a bundle
         by using that put() method.
         */
        args.putParcelable(BOOK_KEY, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = (Book) getArguments().getParcelable(BOOK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        titleTextView = v.findViewById(R.id.titleTextView);
        authorTextView = v.findViewById(R.id.authorTextView);
        coverImageView = v.findViewById(R.id.coverImageView);
        playbutton = v.findViewById(R.id.btnPlay);

        /*
        Because this fragment can be created with or without
        a book to display when attached, we need to make sure
        we don't try to display a book if one isn't provided
         */
        if (book != null)
            displayBook(book);
        return v;
    }

    /*
    This method is used both internally and externally (from the activity)
    to display a book
     */
    public void displayBook(Book book) {
        final int id = book.getId();
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        // Picasso simplifies image loading from the web.
        // No need to download separately.
        Picasso.get().load(book.getCoverUrl()).into(coverImageView);

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((bookdetailsinterface)c).playButton(id);
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof BookDetailsFragment.bookdetailsinterface) {
            parentActivity = (BookDetailsFragment.bookdetailsinterface) context;
        } else {
            throw new RuntimeException("Main activit eeds to impliment bookdetailsinterface");
        }
        this.c = context;
    }

    interface bookdetailsinterface{
        void playButton(int id);
    }



}
