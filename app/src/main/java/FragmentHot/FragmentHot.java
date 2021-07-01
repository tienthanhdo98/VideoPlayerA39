package FragmentHot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a39_project_videoplayer.R;
import com.example.a39_project_videoplayer.VideoDescription;
import com.example.a39_project_videoplayer.databinding.FragmentHotBinding;

import java.util.List;

public class FragmentHot extends Fragment {
    FragmentHotBinding binding;
    private List<VideoDescription> listVideo;
    private VideoAdapter videoAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static FragmentHot newInstance() {
        return new FragmentHot();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);

        return binding.getRoot();
    }

    /*class getJsonFromUrl extends AsyncTask {
        String jsonHot = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //binding.bar.setVisibility(View.VISIBLE);
            //isNetwork(MainActivity.this);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(StringDefine.jsonHotVideo);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                int byeChar;

                while ((byeChar = inputStream.read()) != -1) {
                    jsonHot += (char) byeChar;
                }
                //Log.d("KetQua", "kq tra ve: " + jsonHot);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //binding.bar.setVisibility(View.GONE);
            listVideo = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(jsonHot);
                for (int vt = 0; vt < jsonArray.length(); vt++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(vt);
                    String title = jsonObject.getString("title");
                    String avt = jsonObject.getString("avatar");
                    String video = jsonObject.getString("file_mp4");
                    String date = jsonObject.getString("date_published");
                    listVideo.add(new VideoDescription(title, avt, video, date));
                }
                videoAdapter = new VideoAdapter(listVideo);

            } catch (Exception e) {
                e.printStackTrace();
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            binding.rvHot.setLayoutManager(layoutManager);
            binding.rvHot.setAdapter(videoAdapter);

            videoAdapter.setiClickItem(new iClickItem() {
                @Override
                public void onClickTitle(String urlVideo, String title, String date) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    PlayVideo playVideo = new PlayVideo();
                    Bundle bundle = new Bundle();
                    bundle.putString("u", urlVideo);
                    bundle.putString("t", title);
                    bundle.putString("d", date);
                    playVideo.setArguments(bundle);
                    fragmentTransaction.replace(R.id.mainFragment, playVideo);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }*/
}



