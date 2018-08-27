package com.example.jbt.movielister;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class movieAdaptor extends ArrayAdapter<Movie> {

    public movieAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
    }

    //custom adaptor to connect the view and data to each item on the MainList using a convertView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        ImageView imageView_item = convertView.findViewById(R.id.imageView_item);
        TextView itemMovieName = convertView.findViewById(R.id.itemMovieName);
        TextView itemMovieDate = convertView.findViewById(R.id.itemMovieDate);
        RatingBar movieRet = convertView.findViewById(R.id.ratingBar);
        CheckBox movieCheck = convertView.findViewById(R.id.checkBox);

//        final Movie mov = getItem(position);


        try {
            //use Picasso to convert url(text) to image with load()
            Picasso.with(getContext())
                    .load(getItem(position).getImageview())
                    .error(R.drawable.maneyvic)   //in case of on pic to the movie or error
                    .into(imageView_item);
        }catch (IllegalArgumentException e){
            imageView_item.setImageResource(R.drawable.maneyvic);//in case of error (connection or Picasso action)

        }

        itemMovieName.setText(getItem(position).getTitle());
        itemMovieDate.setText(getItem(position).getDate());
        movieRet.setRating(getItem(position).getReting());
        movieCheck.setChecked(getItem(position).isCheck());


        /// the next lines are for making the ratingbar background in different color depending on the rate
        float rate = getItem(position).getReting();

        if(rate >= 0.5 && rate <= 2){
            // color RED
            movieRet.setBackgroundColor(Color.RED);
        } else if(rate >= 2.1 && rate <= 4){
            // color yellow
            movieRet.setBackgroundColor(Color.parseColor("#FFFFFB00"));
        } else {
            // color GREEN
            movieRet.setBackgroundColor(Color.parseColor("#FF15FF00"));
        }


        return convertView;// after creating the itemView returns convertView with all the data
    }
}
