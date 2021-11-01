package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text, title, release, overview;
    private ImageView backdrop, poster;
    private String movie_id = "", name = "", date = "", over = "", back="", post="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        name = intent.getStringExtra("name_movie");
        date = intent.getStringExtra("release_date");
        over = intent.getStringExtra("overview");
        back = intent.getStringExtra("backdrop");
        post = intent.getStringExtra("poster");

        lbl_text = findViewById(R.id.lbl_movie_details);
        title = findViewById(R.id.name_movie);
        release = findViewById(R.id.release_date);
        overview = findViewById(R.id.overview);
        backdrop = findViewById(R.id.backdrop_path);
        poster = findViewById(R.id.poster_image);



        Glide.with(this)
                .load(Const.IMG_URL + post)
                .into(poster);
        Glide.with(this)
                .load(Const.IMG_URL + back)
                .into(backdrop);

        title.setText(name);
        release.setText(date);
        overview.setText(over);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}