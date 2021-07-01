package FragmentCategory;

import com.example.a39_project_videoplayer.VideoDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIManager {
    @GET("categoty")
    Call<List<Category>> getAnswer();

    @GET("HotVideo")
    Call<List<VideoDescription>> getVideo();

    @GET("Romantic")
    Call<List<VideoDescription>> getRomantic();
    @GET("TOPFILM")
    Call<List<VideoDescription>> getTOPFILM();
}
