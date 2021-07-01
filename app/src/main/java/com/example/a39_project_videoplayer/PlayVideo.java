package com.example.a39_project_videoplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.a39_project_videoplayer.databinding.ActivityPlayVideoBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayVideo extends AppCompatActivity {
    ActivityPlayVideoBinding binding;
    private int position = 0, sb = 0;
    String finalTimerString = "";
    String secondsString = "";
    Boolean fullscreen = false;
    Handler handler = new Handler();
    AudioManager audioManager;
    int mShowLightness = 0, currentVolume, changeVolume;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String url = bundle.getString("u", "");
            String title = bundle.getString("t", "");
            String day = bundle.getString("d", "");
        }
        String url = bundle.getString("u");
        Uri uri = Uri.parse(url);
        binding.videoView.setVideoURI(uri);
        binding.videoView.requestFocus();

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }
        if (bundle.getInt("p", 0) != 0) {
            position = bundle.getInt("p");
        }
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.sbPlay.setMax(binding.videoView.getDuration());
                timeDuration();
                if (position > 0) {
                    binding.videoView.seekTo(position);
                } else {
                    binding.videoView.seekTo(0);
                }
                binding.videoView.start();
                updateProgreeBar();

            }
        });
        binding.tvTile.setText(bundle.getString("t"));
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateGetFromUrl = bundle.getString("d");
        final SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = formatter1.parse(dateGetFromUrl);
            String dateString = df.format(date1);
            binding.tvPublishDay.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        buttonclick();
        try {
            int bl = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            binding.tvValueBL.setText(String.valueOf(bl) + "%");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        binding.videoView.setOnTouchListener(new View.OnTouchListener() {
            float ex1, ex2, ey1, ey2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ex1 = event.getX();
                        ey1 = event.getY();
                        binding.blackGroundPlay.setVisibility(View.VISIBLE);
                    }
                    case MotionEvent.ACTION_MOVE: {
                        ex2 = event.getX();
                        ey2 = event.getY();

                        if (ex1 < binding.videoView.getWidth() / 2) {
                            binding.tvBrightness.setText(getResources().getText(R.string.text_volume));
                            binding.tvVolumeBright.setVisibility(View.VISIBLE);
                            binding.tvVolumeBright.postDelayed(new Runnable() {
                                public void run() {
                                    binding.tvVolumeBright.setVisibility(View.INVISIBLE);
                                }
                            }, 3000);
                            //trai
                            if (ey1 - ey2 > 100) {
                                currentVolume += 1;

                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                        currentVolume, 0);
                                binding.tvValueBL.setText(String.valueOf(currentVolume));
                                //Toast.makeText(context, "up", Toast.LENGTH_SHORT).show();
                            }
                            if (ey2 - ey1 > 100) {
                                currentVolume += -1;
                                if(currentVolume<0)
                                {
                                    currentVolume = 0;
                                }
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                        currentVolume, 0);
                                binding.tvValueBL.setText(String.valueOf(currentVolume));
                                // Toast.makeText(context, "Down", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            binding.tvBrightness.setText(getResources().getText(R.string.text_brightness));
                            binding.tvVolumeBright.setVisibility(View.VISIBLE);
                            binding.tvVolumeBright.postDelayed(new Runnable() {
                                public void run() {
                                    binding.tvVolumeBright.setVisibility(View.INVISIBLE);
                                }
                            }, 3000);
                            if (ey1 - ey2 > 100) {
                                mShowLightness += 1;
                                if (mShowLightness > 255)
                                    mShowLightness = 255;
                                WindowManager.LayoutParams lp = getWindow().getAttributes();
                                lp.screenBrightness = mShowLightness / 255f;
                                getWindow().setAttributes(lp);
                                binding.tvValueBL.setText(String.valueOf((mShowLightness * 100) / 255) + "%");
                            }
                            if (ey2 - ey1 > 100) {
                                mShowLightness += -1;
                                if (mShowLightness < 0)
                                    mShowLightness = 0;
                                WindowManager.LayoutParams lp = getWindow().getAttributes();
                                lp.screenBrightness = mShowLightness / 255f;
                                getWindow().setAttributes(lp);
                                binding.tvValueBL.setText(String.valueOf((mShowLightness * 100) / 255) + "%");
                            }
                        }
                        if (ex1 - ex2 > 100) {
                            if (binding.videoView.getCurrentPosition() <= 500) {
                                position = 0;
                                binding.videoView.seekTo(position);

                            } else {
                                position = binding.videoView.getCurrentPosition() - 500;
                                binding.videoView.seekTo(position);

                            }
                            //Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (ex2 - ex1 > 100) {
                            position = binding.videoView.getCurrentPosition() + 500;
                            binding.videoView.seekTo(position);

                            // Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                return true;
            }
        });

    }

    private void updateProgreeBar() {
        handler.postDelayed(updateTimeTask, 0);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            timePlaying();
            binding.sbPlay.setProgress(binding.videoView.getCurrentPosition());
            binding.sbPlay.setMax(binding.videoView.getDuration());
            handler.postDelayed(this, 0);
        }
    };

    private void timePlaying() {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = binding.videoView.getCurrentPosition() / (1000 * 60 * 60);
        int minutes = (binding.videoView.getCurrentPosition() % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (binding.videoView.getCurrentPosition() % (1000 * 60 * 60)) % (1000 * 60) / 1000;
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        binding.tvTimeStart.setText(finalTimerString);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void buttonclick() {
        binding.sbPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                binding.videoView.pause();
                handler.removeCallbacks(updateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateTimeTask);
                // forward or backward to certain seconds
                binding.videoView.seekTo(binding.sbPlay.getProgress());
                // update timer progress again
                updateProgreeBar();
                binding.videoView.start();

            }
        });

        binding.blackGroundPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.blackGroundPlay.setVisibility(View.INVISIBLE);
            }
        });

        binding.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.videoView.pause();
                binding.btPlay.setVisibility(View.INVISIBLE);
                binding.btPause.setVisibility(View.VISIBLE);
            }
        });
        binding.btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = binding.videoView.getCurrentPosition();
                binding.videoView.seekTo(position);
                binding.videoView.start();
                binding.btPlay.setVisibility(View.VISIBLE);
                binding.btPause.setVisibility(View.INVISIBLE);
            }
        });
        binding.btSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = binding.videoView.getCurrentPosition() + 10000;
                binding.videoView.seekTo(position);

            }
        });
        binding.btSkipPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.videoView.getCurrentPosition() <= 10000) {
                    position = 0;
                    binding.videoView.seekTo(position);

                } else {
                    position = binding.videoView.getCurrentPosition() - 10000;
                    binding.videoView.seekTo(position);

                }
            }
        });
        binding.btFullSc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {
                if (fullscreen) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.WRAP_CONTENT;
                    binding.getRoot().setLayoutParams(params);
                    fullscreen = false;
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    binding.getRoot().setLayoutParams(params);
                    fullscreen = true;
                }
            }

        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", binding.videoView.getCurrentPosition() + 1500);
        binding.videoView.pause();
    }

    public void timeDuration() {
        int duration = binding.videoView.getDuration() / 1000;
        int hours = duration / 3600;
        int minutes = (duration / 60) - (hours * 60);
        int seconds = duration - (hours * 3600) - (minutes * 60);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        binding.tvTimeEnd.setText(finalTimerString);
    }
}
