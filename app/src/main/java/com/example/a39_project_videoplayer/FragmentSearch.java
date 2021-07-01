package com.example.a39_project_videoplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a39_project_videoplayer.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import FragmentCategory.APIManager;
import FragmentCategory.Category;
import FragmentCategory.CategoryAdapter;
import FragmentCategory.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {
    FragmentSearchBinding binding;
    TopFilmAdapter topFilmAdapter;
    List<VideoDescription> list,list2;
    TypeAdapter typeAdapter;
    ArrayAdapter arrayAdapter;

    public static FragmentSearch newInstance()
    {
        return new FragmentSearch();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);


        list = new ArrayList<>();
        list2 = new ArrayList<>();
        APIManager service = RetrofitClient.getClient(StringDefine.jsonCategory).create(APIManager.class);
        service.getTOPFILM().enqueue(new Callback<List<VideoDescription>>() {
            @Override
            public void onResponse(Call<List<VideoDescription>> call, Response<List<VideoDescription>> response) {

                typeAdapter = new TypeAdapter(response.body());

                RecyclerView.LayoutManager layoutManage2 = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                binding.rvSearch.setLayoutManager(layoutManage2);
                binding.rvSearch.setAdapter(topFilmAdapter);
            }

            @Override
            public void onFailure(Call<List<VideoDescription>> call, Throwable t) {
                Toast.makeText(getActivity(), "On Fail", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i =0; i < list.size();i++)
                {
                    if(list.get(i).getTitle().equalsIgnoreCase(binding.etSearch.getText().toString()))
                    {
                        list2.add(list.get(i));
                        typeAdapter = new TypeAdapter(list2);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                LinearLayoutManager.HORIZONTAL, false);
                        binding.rvSearch2.setLayoutManager(layoutManager);
                        binding.rvSearch2.setAdapter(typeAdapter);
                    }
                }
            }
        });




        return binding.getRoot();
    }
}
