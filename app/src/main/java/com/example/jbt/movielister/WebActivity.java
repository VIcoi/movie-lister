package com.example.jbt.movielister;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listWebSearch;
    private ArrayAdapter<Movie> MovieAdapter;
    private EditText editTextWebMovie;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        MovieAdapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1);//setting the adapter and style of the item on the list
        listWebSearch = findViewById(R.id.listWebSearch);
        listWebSearch.setAdapter(MovieAdapter);           //connecting adapter to list
        editTextWebMovie = findViewById(R.id.editTextWebMovie);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        listWebSearch.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String webUser = editTextWebMovie.getText().toString();// getting the text for search
        if (!webUser.isEmpty()){ //if not empty text

            MovieAdapter.clear();
            new vicTask1().execute(webUser);

        }else {//if empty text
            Toast.makeText(this, "Text Must Not Be Empty!", Toast.LENGTH_LONG).show();

        }



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//sending the movie picked to movieact
        Intent intent = new Intent(this,MovieActivity.class);
        intent.putExtra("movie", MovieAdapter.getItem(i));
        startActivity(intent);
    }


    public class vicTask1 extends AsyncTask<String,Void,ArrayList<Movie>>{



        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {


            HttpURLConnection connection = null;
            BufferedReader reader;
            StringBuilder builder = new StringBuilder();


            try {
                URL url = new URL("http://api.themoviedb.org/3/search/movie?api_key=07c664e1eda6cd9d46f1da1dbaefc959&query="+strings[0]);//api for search + the editext from user
                connection = (HttpURLConnection) url.openConnection();

                // if the connection one day dont wark..
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    return null;


                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));//gets all the text form the web

                //making the builder line by line throw the reader using a temporary string(line)
                String line = reader.readLine();
                while (line!= null){
                    builder.append(line);
                    line = reader.readLine();
                }
                // array list of movies that the user search
                ArrayList<Movie> MoviesArray = new ArrayList<>();

                //TODO
                JSONObject object = new JSONObject(builder.toString());//using the builder we got making the new json object

                // making an array out of the object(the array on the json key is "results")
                JSONArray titleArray = object.getJSONArray("results");

                for (int i = 0; i <titleArray.length() ; i++) {
                    // taking all the parameters needed for maikng a movie item from the array using every parameter's key
                    JSONObject jj = titleArray.getJSONObject(i);
                    String title = jj.getString("title");
                    String overview = jj.getString("overview");
                    String imageview = "https://image.tmdb.org/t/p/w500"+jj.getString("poster_path");
                    String date = jj.getString("release_date");
                    float ret = 0;
                    boolean check = false;


                    MoviesArray.add(new Movie(title,overview,imageview,date,ret,check));


                }

                return MoviesArray;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            if (movies!=null){
                MovieAdapter.addAll(movies);
            }else {
                Toast.makeText(WebActivity.this, "An ERRUR Has Occurred Please Try Again..", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.INVISIBLE);

        }


    }
}
