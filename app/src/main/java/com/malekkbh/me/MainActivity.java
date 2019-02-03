package com.malekkbh.me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.MailTo;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    static Context context  ;
    static LayoutInflater inflater ;
    RecyclerView recyclerView ;
    LinearLayoutManager layoutManager ;


    private String url =  "https://demo6483760.mockable.io/json.json" ;

    ProgressDialog pd ;
    private List<Genre> genres ;
    GenreAdapter adapter ;


    YouTubePlayerView youTubePlayerView ;

    ConstraintLayout linearLayout ;

    android.app.FragmentManager fm ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = this.getApplicationContext() ;
        new getdata().execute() ;

        fm = MainActivity.this.getFragmentManager() ;

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer) ;
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);


        linearLayout = findViewById(R.id.constrainlayout) ;


        //instantiate your adapter with the list of genres
        if (genres != null ) {
            adapter = new GenreAdapter(genres , youTubePlayerView , fm  , recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }






    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


                if( keyCode == KeyEvent.KEYCODE_BACK && recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    Log.e("shit" , "keydown") ;
                    return true ;
                }

        return super.onKeyDown(keyCode, event);

    }

//    @Override
//    public void onBackPressed() {
//        if(youTubePlayerView.getVisibility() == View.VISIBLE) {
//            youTubePlayerView.setVisibility(View.INVISIBLE);
//            Log.e("shit" , "backP") ;
//        }
//        super.onBackPressed();
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }




    private class getdata extends AsyncTask<Void, Void,  List<Genre> > {

        //properties:

        //ctor:
        public getdata(){
        }



        //Show progress:
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.show();
        }


        //runs in the background thread.
        //Thread job -> ArrayList
        @Override
        protected List<Genre> doInBackground(Void... voids) {
            List<Genre> result = new ArrayList<>();
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);


//            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject data = new JSONObject(jsonStr);

                    JSONArray playlists = (JSONArray) data.get("Playlists");
                    // looping through All Contacts

                    for (int i = 0; i < playlists.length(); i++) {

                        JSONObject c = playlists.getJSONObject(i);

                       String groupTitle = (String) c.get("ListTitle") + " ⇩";
                        JSONArray p = (JSONArray) c.get("ListItems");
                       List<PlayList> myP = new ArrayList<>() ;

                       for(int j = 0 ; j < p.length() ; j++) {

                           JSONObject obj = p.getJSONObject(j);

                           myP.add(new PlayList(
                                   (String) obj.get("Title") ,
                                 ((String) obj.get("link")).substring(33)  ,
                                  "https:" + ((String) obj.get("thumb")).substring(7)

                           )) ;
                           Log.e("tag" ,  ((String) obj.get("link")).substring(33)) ;
                        }


                        result.add(new Genre(groupTitle , myP)) ;
                    }
                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            } else {
//                Log.e(TAG, "Couldn't get json from server.");
            }

            return result;
        }

        //runs on the UI Thread
        @Override
        protected void onPostExecute(List<Genre> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pd.isShowing())
                pd.dismiss();
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
//                    android.R.layout.simple_dropdown_item_1line, result);
//
//            //   final View dialogView = getLayoutInflater().inflate(R.layout.fragment_add_area, null, false);
//
//            AutoCompleteTextView textView = (AutoCompleteTextView)
//                    dialogView.findViewById(R.id.etCity);
//            textView.setAdapter(adapter);

            MainActivity.this.genres = result ;
            if (genres != null ) {
                GenreAdapter adapter = new GenreAdapter(genres , youTubePlayerView , fm , recyclerView);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        }



    }//classGetdata





}
