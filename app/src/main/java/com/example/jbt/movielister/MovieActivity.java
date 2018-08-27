package com.example.jbt.movielister;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextTitle,editTextOverView,editTextIMAge,editTextDate;
    private ImageView imageMovie;
    private Movie movieFromIntent;
    private Button btnSave,btnUpdate,btnShow;
    private CheckBox checkBox2;
    private RatingBar ratingBar;
    private Boolean check;
    private float ret;
    private ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //findind all vers and btns
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextOverView = findViewById(R.id.editTextOverView);
        editTextIMAge = findViewById(R.id.editTextIMAge);
        editTextDate = findViewById(R.id.editTextDate);
        imageMovie = findViewById(R.id.imageMovie);
        checkBox2 = findViewById(R.id.checkBox2);
        ratingBar = findViewById(R.id.ratingBar2);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);


        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        //in case the user pressed accidentally on item from MainList or WebList and didnt wont to get to movie act
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();// closes this act
            }
        });



        movieFromIntent = (Movie) (getIntent().getSerializableExtra("movie")); //method for getting movie from intent

        if(movieFromIntent != null) { // in case theres no movie with intent (btnadd (manual case in main act))
            if (movieFromIntent.getId()!=0){ // if update a movie was picked
                // switching btnSave with btnUpdate
                btnSave.setVisibility(View.INVISIBLE); //btnSave go's INVISIBLE
                btnUpdate.setVisibility(View.VISIBLE); //btnUpdate go's VISIBLE
            }

            //put info about movie from intent in each case it belongs

            editTextTitle.setText(movieFromIntent.getTitle().toString());
            editTextOverView.setText(movieFromIntent.getOverview().toString());
            editTextIMAge.setText(movieFromIntent.getImageview().toString());
            editTextDate.setText(movieFromIntent.getDate().toString());
            checkBox2.setChecked(movieFromIntent.isCheck());
            ratingBar.setRating(movieFromIntent.getReting());

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnShow: //for image display of movie pic
                progressBar2.setVisibility(View.VISIBLE);

                String userMoviePic = editTextIMAge.getText().toString();//movie pic Url
                try{  //// you must have try and catch if it cannot get the pic in case Url is incorrect
                    Picasso.with(this)                  // Picasso in order to show the pic
                            .load(userMoviePic) //loading the Url
                            .error(R.drawable.maneyvic) // in case of error
                            .into(imageMovie); // where to put it


                }catch (IllegalArgumentException e){

                    //in case Url Empty make a toast aand put this pic instead
                    Toast.makeText(this, "Pic Path Must Not Be Empty!", Toast.LENGTH_LONG).show();

                    Picasso.with(this).load(R.drawable.maneyvic)
                    .into(imageMovie);

                }

                progressBar2.setVisibility(View.INVISIBLE); //the progress is so fast theres no time to see the bar <------------------------
                break;

            case R.id.btnSave:
                //TODO

                String movName,movOverview,movImage,movDate; //strings for the text from each Vars


                if (!editTextTitle.getText().toString().isEmpty()){ //if title case is not empty
                    movName = editTextTitle.getText().toString(); //get text from title
                }else {
                    movName = "";
                }

                if (!editTextOverView.getText().toString().isEmpty()){ //if overview case is not empty
                    movOverview = editTextOverView.getText().toString();//get text from overview
                }else {
                    movOverview = "";
                }

                if (!editTextIMAge.getText().toString().isEmpty()){ //if Url case is not empty
                     movImage = editTextIMAge.getText().toString();//get text from editTextIMAge
                }else {
                     movImage = ""; //TODO
                }

                if (!editTextDate.getText().toString().isEmpty()){ //if date case is not empty
                     movDate = editTextDate.getText().toString();//get text from editTextIMAg
                }else {
                    movDate = "";
                }

                ret = ratingBar.getRating(); //get rating from ratingBar (float)
                check = checkBox2.isChecked(); //get is checked or not (boolean)


                if (movName==""){
                    Toast.makeText(this, "MOVIE MUST HAVE A TITLE!!!", Toast.LENGTH_LONG).show(); //if movie title is empty make a toast without saving
                }else {
                    Movie newMovie = new Movie(movName,movOverview,movImage,movDate,ret,check); //making a movie object

                    MovieDbHelper helper = new MovieDbHelper(this);//make a helper to save no database
                    helper.insertMovie(newMovie); //using the helper insert the movie to database
                    finish();//closes the act
                }



                break;

            case R.id.btnUpdate:

                String movName1,movOverview1,movImage1,movDate1; //strings for the text from each Vars


                if (!editTextTitle.getText().toString().isEmpty()){ //if title case is not empty
                    movName1 = editTextTitle.getText().toString(); //get text from title
                }else {
                    movName1 = "";
                }

                if (!editTextOverView.getText().toString().isEmpty()){ //if overview case is not empty
                    movOverview1 = editTextOverView.getText().toString();//get text from overview
                }else {
                    movOverview1 = "";
                }

                if (!editTextIMAge.getText().toString().isEmpty()){ //if Url case is not empty
                    movImage1 = editTextIMAge.getText().toString();//get text from editTextIMAge
                }else {
                    movImage1 = "";
                }

                if (!editTextDate.getText().toString().isEmpty()){ //if date case is not empty
                    movDate1 = editTextDate.getText().toString();//get text from editTextIMAg
                }else {
                    movDate1 = "";
                }

                ret = ratingBar.getRating(); //get rating from ratingBar (float)

                check = checkBox2.isChecked(); //get is checked or not (boolean)



                Movie upDateMovie1 = new Movie(movieFromIntent.getId(),  movName1,movOverview1,movImage1,movDate1,ret,check); //making a movie object

                MovieDbHelper helper1 = new MovieDbHelper(this);//make a helper to save no database
                helper1.upDateMovie(upDateMovie1); //using the helper update the movie to database

                finish();//closes the act

                break;


        }


    }

    @Override
    protected void onDestroy() { //when act is closed(finish();)
        super.onDestroy();
        btnUpdate.setVisibility(View.INVISIBLE); //btnUpdate go's INVISIBLE
        btnSave.setVisibility(View.VISIBLE); //btnSave go's VISIBLE
    }
}
