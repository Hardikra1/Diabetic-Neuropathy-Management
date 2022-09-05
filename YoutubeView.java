package com.example.diabetesmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeView extends YouTubeBaseActivity {

    private Button Exit;
    private Button Play;
    private YouTubePlayerView youTubePlayerView;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_view);

        youTubePlayerView =(YouTubePlayerView)findViewById(R.id.youtubePlayer_view);

        Exit =(Button)findViewById(R.id.exit);
        Play =(Button)findViewById(R.id.play);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("JAjZv41iUJU");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);


            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YoutubeView.this, "Directing back to about fragment",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
