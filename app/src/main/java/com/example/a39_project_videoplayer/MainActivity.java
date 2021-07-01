package com.example.a39_project_videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.a39_project_videoplayer.databinding.ActivityMainBinding;

import FragmentHot.FragmentHot;
import FragmentMain.Fragment_Main;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFragment, Fragment_Main.newInstance())
                    .commit();
            binding.tvHome.setTextColor(getResources().getColor(R.color.colorMenu));
        }

        binding.btHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvHot.setTextColor(getResources().getColor(R.color.colorMenu));
                binding.tvHistory.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvUser.setTextColor(getResources().getColor(R.color.colorText));

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit)
                        .replace(R.id.mainFragment, FragmentHot.newInstance())
                        .commit();

            }
        });
        binding.btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvHot.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvHistory.setTextColor(getResources().getColor(R.color.colorMenu));
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvUser.setTextColor(getResources().getColor(R.color.colorText));


            }
        });
        binding.btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvHot.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvHistory.setTextColor(getResources().getColor(R.color.colorText));
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorMenu));
                binding.tvUser.setTextColor(getResources().getColor(R.color.colorText));
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit)
                        .replace(R.id.mainFragment, Fragment_Main.newInstance())
                        .commit();

            }
        });
        binding.btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit)
                        .replace(R.id.containerFS, FragmentSearch.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });
        binding.btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit);
                PlayVideo1 playVideo1 = new PlayVideo1();
                Bundle bundle = new Bundle();
                bundle.putString("u", "t");
                bundle.putString("t", "d");
                bundle.putString("d", "f");
                playVideo1.setArguments(bundle);
                fragmentTransaction.replace(R.id.mainFragment, playVideo1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    public boolean isNetwork(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
    // interface kt mang
}

  /* binding.btTryConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJsonFromUrl getJsonFromUrl = new getJsonFromUrl();
                getJsonFromUrl.execute();
            }
        });*/