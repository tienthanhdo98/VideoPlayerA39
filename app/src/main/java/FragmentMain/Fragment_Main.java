package FragmentMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a39_project_videoplayer.PlayVideo;
import com.example.a39_project_videoplayer.PlayVideo1;
import com.example.a39_project_videoplayer.SlideShowAdapter;
import com.example.a39_project_videoplayer.TypeAdapter;
import com.example.a39_project_videoplayer.R;
import com.example.a39_project_videoplayer.StringDefine;
import com.example.a39_project_videoplayer.TopFilmAdapter;
import com.example.a39_project_videoplayer.RomamticAdapter;
import com.example.a39_project_videoplayer.VideoDescription;
import com.example.a39_project_videoplayer.databinding.FragmentMainBinding;
import com.example.a39_project_videoplayer.iClickItem;

import java.util.ArrayList;
import java.util.List;

import FragmentCategory.APIManager;
import FragmentCategory.Category;
import FragmentCategory.CategoryAdapter;
import FragmentCategory.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Main extends Fragment {
    FragmentMainBinding binding;
    List<News> newsList;
    NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    TypeAdapter typeAdapter;
    TopFilmAdapter topFilmAdapter;
    SlideShowAdapter slideShowAdapter;
    RomamticAdapter romamticAdapter;
    List urlSS;

    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        newsList = new ArrayList<>();
        newsList.add(new News("Khi Ch??ng Ta Ch???n Nh???ng T???p ??o??n L???n Thay V?? Con Ng?????i, Th??? Gi???i S??? Tan Hoang Nh?? Trong Wall-E", "https://phephim.vn/wp-content/uploads/2020/06/9d67bee6edf5f76e1357fd98ad2d27f0-768x432.jpg", "saas"));
        newsList.add(new News("Michael B. Jordan K??u G???i Hollywood K??? Nhi???u C??u Chuy???n V??? Ng?????i Da ??en H??n", "https://phephim.vn/wp-content/uploads/2020/06/ebdcebf16e5c2183e78fe0195958cc8f-600x337.jpeg", "saas"));
        newsList.add(new News("???Nhi???m V??? B???t Kh??? Thi 7??? D??? Ki???n S??? Ti???p T???c ???????c S???n Xu???t V??o Th??ng 9 N??y", "https://phephim.vn/wp-content/uploads/2020/06/mission-impossible-ghost-protocol-simon-pegg-tom-cruise-600x337.jpg   ", "saas"));
        newsList.add(new News("The Irishman V?? Once Upon A Time In???Hollywood: ????nh D???u S??? Tho??i Tr??o C???a S???c M???nh ????n ??ng Trong T??c Ph???m ??i???n ???nh", "https://phephim.vn/wp-content/uploads/2020/06/the-irish-man-once-upon-a-time-in-hollywood.jpg", "saas"));

        newsAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager layoutManagerNews = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rvNews.setLayoutManager(layoutManagerNews);
        binding.rvNews.setAdapter(newsAdapter);

        urlSS = new ArrayList<>();
        urlSS.add("https://wallpaperplay.com/walls/full/c/b/4/188875.jpg");
        urlSS.add("https://wallpaperplay.com/walls/full/a/a/7/188877.jpg");
        urlSS.add("https://wallpaperplay.com/walls/full/5/6/6/188878.jpg");
        urlSS.add("https://wallpaperplay.com/walls/full/a/1/6/188881.jpg");
        slideShowAdapter = new SlideShowAdapter(getActivity(), urlSS);
        binding.vpSlideShow.setAdapter(slideShowAdapter);
        binding.vpSlideShow.setOffscreenPageLimit(3);


        APIManager service = RetrofitClient.getClient(StringDefine.jsonCategory).create(APIManager.class);
        service.getAnswer().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                categoryAdapter = new CategoryAdapter(response.body());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                binding.rvCategory.setLayoutManager(layoutManager);
                binding.rvCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "On Fail", Toast.LENGTH_SHORT).show();
            }
        });
        service.getTOPFILM().enqueue(new Callback<List<VideoDescription>>() {
            @Override
            public void onResponse(Call<List<VideoDescription>> call, Response<List<VideoDescription>> response) {
                topFilmAdapter = new TopFilmAdapter(response.body());
                RecyclerView.LayoutManager layoutManage2 = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                binding.rvOnGoing1.setLayoutManager(layoutManage2);
                binding.rvOnGoing1.setAdapter(topFilmAdapter);
            }

            @Override
            public void onFailure(Call<List<VideoDescription>> call, Throwable t) {
                Toast.makeText(getActivity(), "On Fail", Toast.LENGTH_SHORT).show();
            }
        });
        service.getVideo().enqueue(new Callback<List<VideoDescription>>() {
            @Override
            public void onResponse(Call<List<VideoDescription>> call, Response<List<VideoDescription>> response) {
                typeAdapter = new TypeAdapter(response.body());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                binding.rvOnGoing.setLayoutManager(layoutManager);
                binding.rvOnGoing.setAdapter(typeAdapter);
            }

            @Override
            public void onFailure(Call<List<VideoDescription>> call, Throwable t) {
                Toast.makeText(getActivity(), "On Fail", Toast.LENGTH_SHORT).show();
            }
        });
        service.getRomantic().enqueue(new Callback<List<VideoDescription>>() {
            @Override
            public void onResponse(Call<List<VideoDescription>> call, Response<List<VideoDescription>> response) {

                romamticAdapter = new RomamticAdapter(response.body());
                RecyclerView.LayoutManager layoutManage3 = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                binding.rvOnGoing2.setLayoutManager(layoutManage3);
                binding.rvOnGoing2.setAdapter(romamticAdapter);

                romamticAdapter.setiClickItem(new iClickItem() {
                    @Override
                    public void onClickItem(VideoDescription videoDescription) {
                        Intent playvideo = new Intent(getContext(), PlayVideo.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("u", videoDescription.getFileMp4());
                        bundle.putString("t", videoDescription.getTitle());
                        bundle.putString("d", videoDescription.getDatePublished());
                        playvideo.putExtras(bundle);
                        startActivity(playvideo);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<VideoDescription>> call, Throwable t) {
                Toast.makeText(getActivity(), "On Fail", Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();

    }


}
