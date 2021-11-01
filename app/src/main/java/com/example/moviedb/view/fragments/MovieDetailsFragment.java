package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Toast toast;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView lbl_text, title, release, overview, votee, totalvotee, taglinee, popularity, genres;
    private ImageView backdrop, poster;
    private LinearLayout linearproduction;
    private MovieViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        lbl_text = view.findViewById(R.id.lbl_movie_details);
        title = view.findViewById(R.id.name_movie);
        release = view.findViewById(R.id.release_date);
        overview = view.findViewById(R.id.overview);
        backdrop = view.findViewById(R.id.backdrop_path);
        poster = view.findViewById(R.id.poster_image);
        votee = view.findViewById(R.id.avg_vote);
        totalvotee = view.findViewById(R.id.total_vote);
        taglinee = view.findViewById(R.id.tagline);
        popularity = view.findViewById(R.id.popularity);
        genres = view.findViewById(R.id.genre);
        linearproduction = view.findViewById(R.id.LinearProduction);

        String movie_id = getArguments().getString("movie_id");
        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showMoviesResults);

        return view;
    }

    private Observer<Movies> showMoviesResults = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String judul = movies.getTitle();
            String tanggal = movies.getRelease_date();
            String tentang = movies.getOverview();
            Double hasilvote = movies.getVote_average();
            Integer totalvote = movies.getVote_count();
            String tagline = movies.getTagline();
            Double popular = movies.getPopularity();
            String genre ="";
            //genre
            for (int i=0; i<movies.getGenres().size(); i++){
                if (movies.getGenres().size()==1){
                    genre = genre+movies.getGenres().get(i).getName();
                }else if(movies.getGenres().size()==i+1){
                    genre = genre+movies.getGenres().get(i).getName();
                }else{
                    genre = genre+movies.getGenres().get(i).getName()+", ";
                }
            }

            //productionhouse
            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                ImageView image = new ImageView(linearproduction.getContext());
                String logo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String name = movies.getProduction_companies().get(i).getName();
                if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.ic_now_playing_24, getActivity().getTheme()));
                }else{
                    Glide.with(getActivity()).load(logo).into(image);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                lp.setMargins(20,0,20,0);
                image.setLayoutParams(lp);
                linearproduction.addView(image);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getContext(), name, Toast.LENGTH_SHORT);
                        toast.setText(name);
                        toast.show();
                    }
                });
            }
            genres.setText("Genre: "+genre);
            Glide.with(getActivity()).load(Const.IMG_URL+movies.getPoster_path()).into(poster);
            Glide.with(getActivity()).load(Const.IMG_URL+movies.getBackdrop_path()).into(backdrop);
            popularity.setText("Popularity: "+String.valueOf(popular));
            title.setText(judul);
            release.setText(tanggal);
            overview.setText(tentang);
            votee.setText("Rating: "+String.valueOf(hasilvote));
            totalvotee.setText(" ("+String.valueOf(totalvote)+")");
            taglinee.setText(tagline);
        }
    };
}