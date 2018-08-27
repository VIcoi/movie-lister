package com.example.jbt.movielister;

import android.animation.ObjectAnimator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listmovie1;
    private movieAdaptor arrayAdapter;
    private MovieDbHelper movieDbHelper;
    private float deg = 360;
    private Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listmovie1 = findViewById(R.id.movielist2); // find the list
        arrayAdapter = new movieAdaptor(this,R.layout.movie_list_item); // find adapter
        listmovie1.setAdapter(arrayAdapter); //connecting list to adapter
        movieDbHelper = new MovieDbHelper(this);// helper to engage with database

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        listmovie1.setOnItemClickListener(this);           //short click on item to edit it on movie act
        listmovie1.setOnItemLongClickListener(this);  // long click is a choice for   edit/delete the item

    }

    @Override
    public void onClick(View view) {


        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(R.string.addMovie);
        dialog.setMessage("What Do You Wonna Do?");
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Add Manual?", new DialogInterface.OnClickListener() {  // btn on dialog for edit on movieact
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity( new Intent(MainActivity.this,MovieActivity.class));

            }
        });

        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Add From Web?", new DialogInterface.OnClickListener() { // btn on dialog for webact to search a movie by name
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity( new Intent (MainActivity.this,WebActivity.class));

            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() { // // btn on dialog to exit dialog
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();

            }
        });
        dialog.show();// order for showing the dialog - without it the dialog will not pop


    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayAdapter.clear(); //when app starts clear the adapter - if not the list before would stay + the new list in next line
        arrayAdapter.addAll(movieDbHelper.getAllMovies()); // tells the adapter to get list for the database throw the helper

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("ival",""+i);

        Intent intent = new Intent(this,MovieActivity.class); // he call to get from this act to movie act
        intent.putExtra("movie",arrayAdapter.getItem(i));// put's the item clicked on in the intent throw the adapter
        startActivity(intent); //start's the intent
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {

        Log.i("ival",""+i);

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("DELETION?");
        dialog.setMessage("Welcome To Your Destruction");
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete?", new DialogInterface.OnClickListener() { // btn on dialog for deleting item clicked on form list
            @Override
            public void onClick(DialogInterface dialogInterface, int d) {

                movieDbHelper.deleteMovie(arrayAdapter.getItem(i).getId()); // deleting form database list
                arrayAdapter.remove(arrayAdapter.getItem(i)); // deleting form array

            }
        });

        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Update?", new DialogInterface.OnClickListener() { // btn on dialog for edit on movieact
            @Override
            public void onClick(DialogInterface dialogInterface, int d) {
                Intent intent = new Intent(MainActivity.this,MovieActivity.class);
                intent.putExtra("movie",arrayAdapter.getItem(i));// put's the item clicked on in the intent throw the adapter
                startActivity(intent);//start's the intent
            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Don't Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int d) {
                dialog.dismiss(); //closes dialog

            }
        });
        dialog.show(); // order for showing the dialog - without it the dialog will not pop

        return true; // if it where false the long click would make long and short click together - true make only long click
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menu bar
        getMenuInflater().inflate(R.menu.mymenu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Random a = new Random(); //random Var
        int b = a.nextInt(2);//int Var that changes (form 0-2) every time it get click
        switch (item.getItemId()) { // id of item clicked on

            case R.id.Exit: // id for exit
                finish(); // gets out of the app
                break;

            case R.id.menu:// id for menu

                ObjectAnimator animatorJava1 = ObjectAnimator.ofFloat(btnAdd, "rotation", deg);//this makes the btnadd rotate and every time you click it it rotates to the other direction
                animatorJava1.setDuration(2000); //duration time in this case is 2 sec
                animatorJava1.start();//start rotating

                if(deg == 360){
                    deg = 0;
                } else {
                    deg = 360;
                } ///// the condition above is from where the rotation starts 360 to 0 or 0 to 360 which make it right to left or left to right

                break;

            case R.id.Check_This_Out:

            if (b == 0) {
                    Toast.makeText(this, "It Does Not Do Anything.. HA HA", Toast.LENGTH_SHORT).show(); //stupid text to make you happy using the random int var b
                    break;
                }
            if (b == 1) {
                    Toast.makeText(this, "Unnecessary Button", Toast.LENGTH_SHORT).show(); //stupid text to make you happy using the random int var b
                    break;
                }
            if (b == 2) {
                    Toast.makeText(this, "Jeepers Creepers Mister Will You Stop?", Toast.LENGTH_SHORT).show(); //stupid text to make you happy using the random int var b
                    break;
                }

            case R.id.Delete_All_Movies://btn from dialog to delete all movies from list

                movieDbHelper.deleteAllMovies(); // delete from database
                arrayAdapter.clear(); // delete from array

                break;


        }
        return super.onOptionsItemSelected(item); //itet selected is initiated
    }
}
