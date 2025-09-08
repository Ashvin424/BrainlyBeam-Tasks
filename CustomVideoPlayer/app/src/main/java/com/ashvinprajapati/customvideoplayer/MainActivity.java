package com.ashvinprajapati.customvideoplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 101;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private Button btnPrev, btnPlayPause, btnNext;
    ArrayList<File> videosList = new ArrayList<>();
    int currentVideo = 0;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        playerView = findViewById(R.id.playerView);
        btnPrev = findViewById(R.id.btnPrev);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnNext);

        btnPlayPause.setOnClickListener(v -> {
            if (isPlaying) {
                exoPlayer.pause();
                btnPlayPause.setText("Play");
                isPlaying = false;
            } else {
                exoPlayer.play();
                btnPlayPause.setText("Pause");
                isPlaying = true;
            }
        });

        btnNext.setOnClickListener(v -> {
            if (!videosList.isEmpty()) {
                currentVideo = (currentVideo + 1) % videosList.size();
                playVideo(currentVideo);
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (!videosList.isEmpty()) {
                currentVideo = (currentVideo - 1 + videosList.size()) % videosList.size();
                playVideo(currentVideo);
            }
        });

        if (!hasStoragePermission()){
            requestStoragePermission();
        }
        else {
            intializePlayer();
        }
    }

    private void intializePlayer() {

        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        loadVideos();

        if (!videosList.isEmpty()) {
            playVideo(currentVideo);
        }
    }

    private void playVideo(int currentVideo) {
        if (!videosList.isEmpty() && currentVideo < videosList.size()){
            Uri videoUri = Uri.fromFile(videosList.get(currentVideo));
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
            btnPlayPause.setText("Pause");
            isPlaying = true;
        }
    }

    private void loadVideos() {
        videosList.clear();
        Uri collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME
        };

        try (Cursor cursor = getContentResolver().query(collection, projection, null, null, MediaStore.Video.Media.DATE_ADDED + " DESC")){
            if (cursor != null) {
                int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                while (cursor.moveToNext()) {
                    String path = cursor.getString(dataColumn);
                    File file = new File(path);
                    if (file.exists()) {
                        videosList.add(file);
                    }
                }
            }
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_VIDEO}, STORAGE_PERMISSION_CODE);
        }
        else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intializePlayer();
            }
        }
    }
}