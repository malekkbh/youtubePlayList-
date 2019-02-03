package com.malekkbh.me;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.FragmentManager ;

import static com.malekkbh.me.MainActivity.inflater;

public class GenreAdapter extends ExpandableRecyclerViewAdapter<GenreViewHolder, PlaylistItem>  {

//    LayoutInflater inflater = LayoutInflater.from(MainActivity.context) ;
    YouTubePlayerView youTubePlayerView ;
    android.app.FragmentManager fm ;
    RecyclerView recyclerView ;




    public GenreAdapter(List<? extends ExpandableGroup> groups , YouTubePlayerView youTubePlayerView ,android.app.FragmentManager fm , RecyclerView rv) {
        super(groups);
        this.youTubePlayerView = youTubePlayerView ;
        this.fm = fm ;
        this.recyclerView = rv ;
    }

    @Override
    public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)  {

        View v = inflater.inflate(R.layout.artist_layout , parent , false ) ;

        return new GenreViewHolder(v);

    }

    @Override
    public PlaylistItem onCreateChildViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.play_list_item , parent , false ) ;



        return new PlaylistItem(v);
    }





    @Override
    public void onBindChildViewHolder(PlaylistItem h , int flatPosition, final ExpandableGroup group, final int childIndex) {


        try {
            h.onBind( (PlayList)group.getItems().get(childIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                recyclerView.setVisibility(View.GONE);
//                youTubePlayerView.setVisibility(View.VISIBLE);
                youTubePlayerView.initialize(YouTubeCofing.getYouTubeApiKey(), new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.setFullscreen(false);
                        youTubePlayer.loadVideos(getLink(group.getItems()));
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e("fuck " , youTubeInitializationResult.toString()) ;

                    }
                });
            }
        });

    }

    public ArrayList<String> getLink (List<PlayList> playList) {
            ArrayList<String> links = new ArrayList<>() ;
        for (int i = 0; i < playList.size(); i++) {
            links.add(playList.get(i).getLink()) ;
        }
        return links ;
    }

    @Override
    public void onBindGroupViewHolder(GenreViewHolder h, int flatPosition, ExpandableGroup group) {
        h.setGenreTitle(group);

    }
}
