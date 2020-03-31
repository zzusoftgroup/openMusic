package com.zzu.peifuYang.work959;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zzu.peifuYang.work959.util.MusicProgressBarUtil;

import java.util.Timer;

import me.wcy.lrcview.LrcView;

public class MainActivity extends Activity implements View.OnClickListener{
    private ImageButton pre_music;
    private ImageButton next_music;
    private ImageButton pause_play;
    private TextView totalTime_tv;
    private TextView currentTime_tv;
    private SeekBar seekBar;
    boolean isSeekbarChaning =false;
    Handler handler = new Handler(){};
    private Timer timer;
    private LrcView lrcView;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        填充所有控件 设置监听
        init_view();
        initMediaPlayer();


    }

    private void initMediaPlayer() {
//        检查权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        try {
            Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.goodbye);
            mediaPlayer.setDataSource(MainActivity.this,rawUri);//指定音频文件路径
            mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            setProgressBar();
            showLyric();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pause_play:
                //如果没在播放中，立刻开始播放。
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    Runnable runnable= new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer.isPlaying()){
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                int currentTime = mediaPlayer.getCurrentPosition()/1000;
                                currentTime_tv.setText(MusicProgressBarUtil.calculateTime(currentTime));
                                lrcView.updateTime(mediaPlayer.getCurrentPosition());
                                handler.postDelayed(this, 300);
                            }
                        }
                    };
                    handler.post(runnable);
//                    发送播放的消息
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
        lrcView.setLabel("无歌词");
        lrcView.setDraggable(true, new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                return true;
            }
        });
        String lrcText = MusicProgressBarUtil.getLrcText("秦洋-再见.lrc",this);
        lrcView.loadLrc(lrcText);
    }
    public void setProgressBar(){

        int totalTime = mediaPlayer.getDuration()/1000;
        totalTime_tv.setText(MusicProgressBarUtil.calculateTime(totalTime));
        int currentTime = mediaPlayer.getCurrentPosition()/1000;
        currentTime_tv.setText(MusicProgressBarUtil.calculateTime(currentTime));

        seekBar = findViewById(R.id.pb_music);
        seekBar.setMax(mediaPlayer.getDuration());
        //绑定监听器，监听拖动到指定位置
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentTime = mediaPlayer.getCurrentPosition()/1000;
                currentTime_tv.setText(MusicProgressBarUtil.calculateTime(currentTime));
            } 
            /*
             * 通知用户已经开始一个触摸拖动手势。
             * */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                int currentTime = mediaPlayer.getCurrentPosition()/1000;
//                currentTime_tv.setText(MusicProgressBarUtil.calculateTime(currentTime));
                isSeekbarChaning = true;
            }
            /*
             * 当手停止拖动进度条时执行该方法
             * 首先获取拖拽进度
             * 将进度对应设置给MediaPlayer
             * */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarChaning = false;
                mediaPlayer.seekTo(seekBar.getProgress());//在当前位置播放
                currentTime_tv.setText(MusicProgressBarUtil.calculateTime(mediaPlayer.getCurrentPosition() / 1000));
            }
        });


    }
    public void init_view(){
        pre_music = findViewById(R.id.pre_music);
        next_music = findViewById(R.id.next_music);
        pause_play = findViewById(R.id.pause_play);
        totalTime_tv = findViewById(R.id.total_time);
        currentTime_tv = findViewById(R.id.current_time);
        lrcView = findViewById(R.id.lyric);
        pre_music.setOnClickListener(this);

        next_music.setOnClickListener(this);

        pause_play.setOnClickListener(this);
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
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}


