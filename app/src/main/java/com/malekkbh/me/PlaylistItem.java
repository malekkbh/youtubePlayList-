package com.malekkbh.me;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaylistItem extends ChildViewHolder implements ExpandableListView.OnChildClickListener {

    private TextView descreptionTV;
    private ImageView itemImg ;

    public PlaylistItem(View itemView) {
        super(itemView);
        descreptionTV = itemView.findViewById(R.id.playListItemDescreption);
        itemImg = itemView.findViewById(R.id.playListItemImg) ;
    }

    public Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void onBind(PlayList playList) throws IOException {
        descreptionTV.setText(playList.getTitle());

        //URL url = new URL("https://i.ytimg.com/vi_webp/SbCpzWMWb68/mqdefault.webp") ;
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        itemImg.setImageBitmap(getbmpfromURL("https://i.ytimg.com/vi_webp/SbCpzWMWb68/mqdefault.webp"));
        Picasso.with(MainActivity.context)
                .load(playList.getImg())
                .into(itemImg);
    }

  

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Toast.makeText(MainActivity.context, "yes", Toast.LENGTH_SHORT).show();
        
        return false;
    }
}
