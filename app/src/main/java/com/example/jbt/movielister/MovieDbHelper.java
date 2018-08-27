package com.example.jbt.movielister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.RatingBar;

import java.util.ArrayList;


public class MovieDbHelper extends SQLiteOpenHelper {

    //decler colom names on database
    public static final String TABLE_NAME = "movies";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_IMAGE = "imageview";
    public static final String COL_DATE = "date";
    public static final String COL_RATING = "reting";
    public static final String COL_CHECK = "watched";


    public MovieDbHelper(Context context) {                     ///constractor
        super(context, "dataMovies", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creat the coloms on database

        String sql1 = String.format("create table %s ( %s integer primary key autoincrement, %s text, %s, text, %s text, %s text, %s real, %s integer)",
                TABLE_NAME, COL_ID, COL_TITLE, COL_OVERVIEW, COL_IMAGE, COL_DATE, COL_RATING, COL_CHECK);

//    String sql = String.format("create table %s ( %s integer primary key autoincrement, %s text, %s text, %s text, %s text, %s real, %s integer )",
//            TABLE_NAME, COL_ID, COL_TITLE, COL_OVERVIEW, COL_IMAGE, COL_DATE, COL_RATING, COL_CHECK);

        sqLiteDatabase.execSQL(sql1);//initiate the string to creat the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//in case of new version

    }

    public  void insertMovie (Movie movie){//method for new movie

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // Values variable that heples putting all the Movie features into dataBase
        ContentValues values = new ContentValues();

        values.put(COL_TITLE, movie.getTitle());
        values.put(COL_OVERVIEW, movie.getOverview());
        values.put(COL_IMAGE, movie.getImageview());
        values.put(COL_DATE, movie.getDate());
        values.put(COL_RATING,movie.getReting());
        values.put(COL_CHECK,movie.isCheck());

        //insert the values to the dataBase
        sqLiteDatabase.insert(TABLE_NAME,null,values);

        //allways close dataBase...
        sqLiteDatabase.close();


    }


    public ArrayList<Movie> getAllMovies() {//making movie list Array
        //make a new array
        ArrayList<Movie> movies = new ArrayList<>();

        //allows to read from the database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //cursor shows what line are we on the table in dataBsae
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()){

            long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
            String overview = cursor.getString(cursor.getColumnIndex(COL_OVERVIEW));
            String image = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
            String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
            float ratingBar = cursor.getFloat(cursor.getColumnIndex(COL_RATING));
            int check = cursor.getInt(cursor.getColumnIndex(COL_CHECK));
            boolean checkbol;
            if (check==1){
                 checkbol = true;
            }else {
                 checkbol = false;
            }


            movies.add(new Movie(id,title,overview,image,date,ratingBar,checkbol));

        }
        sqLiteDatabase.close();
        return movies;

    }

    public void deleteMovie (long id){//method to delete movie
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // delete all line in that id - all args..
        sqLiteDatabase.delete(TABLE_NAME,COL_ID+"="+id,null);

        sqLiteDatabase.close();

    }

    public void deleteAllMovies (){//method to clear all movies from database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // delete all line in that id - all args..
        sqLiteDatabase.delete(TABLE_NAME,null,null);

        sqLiteDatabase.close();

    }

    public void upDateMovie(Movie movie){//method to update a movie
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TITLE, movie.getTitle());
        values.put(COL_OVERVIEW, movie.getOverview());
        values.put(COL_IMAGE, movie.getImageview());
        values.put(COL_DATE, movie.getDate());
        values.put(COL_RATING,movie.getReting());
        values.put(COL_CHECK,movie.isCheck());


        sqLiteDatabase.update(TABLE_NAME,values,COL_ID+"="+movie.getId(),null);
        sqLiteDatabase.close();

    }

}
