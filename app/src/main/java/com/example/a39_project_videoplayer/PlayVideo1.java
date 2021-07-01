package com.example.a39_project_videoplayer;

import android.annotation.SuppressLint;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.a39_project_videoplayer.databinding.PlayVideoLayoutBinding;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayVideo1 extends Fragment {
    private PlayVideoLayoutBinding binding;
    private int position = 0, sb = 0;
    String finalTimerString = "";
    String secondsString = "";
    Boolean fullscreen = false;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Handler handler = new Handler();


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.play_video_layout, container, false);

        buttonclick();

        final Bundle bundle = getArguments();

        binding.videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.blackGroundPlay.setVisibility(View.VISIBLE);
                return true;
            }
        });
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
        if (bundle.getString("u").equals("")) {
            String url = "https://dzbbmecpa0hd2.cloudfront.net/video/original/2019/04/19/13/1555655223_27e1a269b8b646ef.mp4";
        }
        String url = bundle.getString("u");
        Uri uri = Uri.parse(url);
        binding.videoView.setVideoURI(uri);
        binding.videoView.requestFocus();

        //name - day
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
        binding.btFullSc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {

                if (fullscreen) {
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.WRAP_CONTENT;
                    binding.getRoot().setLayoutParams(params);
                    fullscreen = false;
                } else {
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    binding.getRoot().setLayoutParams(params);
                    fullscreen = true;
                }
            }

        });


        return binding.getRoot();

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
//        // forward or backward to certain seconds
                binding.videoView.seekTo(binding.sbPlay.getProgress());
                // update timer progress again
                updateProgreeBar();
                    binding.videoView.start();

            }
        });
        binding.videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.blackGroundPlay.setVisibility(View.VISIBLE);
                return true;
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
                binding.videoView.start();
            }
        });
        binding.btSkipPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.videoView.getCurrentPosition() <= 10000) {
                    position = 0;
                    binding.videoView.seekTo(position);
                    binding.videoView.start();
                } else {
                    position = binding.videoView.getCurrentPosition() - 10000;
                    binding.videoView.seekTo(position);
                    binding.videoView.start();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", binding.videoView.getCurrentPosition());
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



