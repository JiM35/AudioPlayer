package com.example.audioplayer;

import static com.example.audioplayer.MainActivity.musicFiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class AlbumDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName;
    ArrayList<MusicFiles> albumSongs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        recyclerView = findViewById(R.id.recyclerView);
        albumPhoto = findViewById(R.id.album_photo);
        albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for (int i = 0 ; i < musicFiles.size() ; i ++) {
            if (albumName.equals(musicFiles.get(i).getAlbum())); {
                albumSongs.add(j, musicFiles.get(i));
                j ++;
            }
        }
        byte[] image = new byte[0];
        try {
            image = getAlbumArt(albumSongs.get(0).getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (image != null) {
            Glide.with(this).load(image).into(albumPhoto);
        } else {
            Glide.with(this).load(R.drawable.bewedoc).into(albumPhoto);
        }

    }

    private byte [] getAlbumArt(String uri) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}