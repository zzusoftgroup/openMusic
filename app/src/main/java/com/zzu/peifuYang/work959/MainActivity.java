package com.zzu.peifuYang.work959;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.wcy.lrcview.LrcView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton pre_music;
    private ImageButton next_music;
    private ImageButton pause_play;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pre_music = findViewById(R.id.pre_music);
        next_music = findViewById(R.id.next_music);
        pause_play = findViewById(R.id.pause_play);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initMediaPlayer();//初始化播放器 MediaPlayer
        }

        pre_music.setOnClickListener(this);

        next_music.setOnClickListener(this);

        pause_play.setOnClickListener(this);
        showLyric();

    }
    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "1.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else{
                    Toast.makeText(this, "拒绝权限，无法使用程序。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pause_play:
                //如果没在播放中，立刻开始播放。
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }else {
                    mediaPlayer.pause();
                }
                break;
            case R.id.next_music:
                //如果在播放中，立刻暂停。
                if(mediaPlayer.isPlaying()){
                   mediaPlayer.reset();
                }
                break;
            case R.id.pre_music:
                //如果在播放中，立刻停止。
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();//初始化播放器 MediaPlayer
                }
                break;
            default:
                break;
        }
    }

    public void showLyric(){
        LrcView lrcView = findViewById(R.id.lyric);
//        lrcView.loadLrcByUrl("http://pic.landun.tech/a55c89d2c0717124add9c5f70fb04074.lrc?download/27.%E6%9C%88%E5%9C%86%E6%9B%B2.lrc","utf-8");
        String mainLrcText = getLrcText("send_it_en.lrc");
        String secondLrcText = getLrcText("send_it_cn.lrc");
        lrcView.loadLrc(mainLrcText, secondLrcText);
        if (!lrcView.hasLrc())
        Toast.makeText(this, "歌词无效", Toast.LENGTH_LONG).show();

    }
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}


