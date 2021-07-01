package com.example.a39_project_videoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a39_project_videoplayer.databinding.FullscreenBinding;


public class FullScreen extends Fragment {
    FullscreenBinding binding;
    String finalTimerString = "";
    String secondsString = "";
    int position;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Handler handler = new Handler();

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fullscreen, container, false);


        final Bundle bundle = getArguments();
        position = bundle.getInt("p");
        Uri uri = Uri.parse(bundle.getString("u"));
        binding.videoViewFS.setVideoURI(uri);

        binding.videoViewFS.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                timeDuration();
                binding.videoViewFS.seekTo(position);
                binding.videoViewFS.start();
                updateProgreeBar();
            }
        });

        binding.videoViewFS.setOnTouchListener(new View.OnTouchListener() {
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

        binding.btFullSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.WRAP_CONTENT;
                binding.getRoot().setLayoutParams(params);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        binding.sbPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.videoViewFS.seekTo(progress);
                    binding.sbPlay.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
            binding.sbPlay.setProgress(binding.videoViewFS.getCurrentPosition());
            binding.sbPlay.setMax(binding.videoViewFS.getDuration());
            handler.postDelayed(this, 0);
        }
    };
    private void timePlaying() {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = binding.videoViewFS.getCurrentPosition() / (1000 * 60 * 60);
        int minutes = (binding.videoViewFS.getCurrentPosition() % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (binding.videoViewFS.getCurrentPosition() % (1000 * 60 * 60)) % (1000 * 60) / 1000;
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
    public void timeDuration() {
        int duration = binding.videoViewFS.getDuration() / 1000;
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


