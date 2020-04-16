package com.syc.ui;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.syc.R;
import com.syc.models.TopResult;
import com.syc.models.TopStoriesNYT;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.TopAdapter;
import com.syc.utils.RetrofitInstance;
import java.util.List;
import static com.syc.utils.Utils.getApiKey;
import static com.syc.utils.Utils.getSharedTopStoriesCategory;

/**
 * Created by Chazette Sylvain
 * content consumption of API NYT - Topstories, with only the section chosen in help menu
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link TopStoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopStoriesFragment extends Fragment {
    @BindView(R.id.rv_list)  RecyclerView rvList;

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newInstance() {
        TopStoriesFragment fragment = new TopStoriesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    /**
     * initializes the consumption of the API NYT - topstories
     * service parameter : only chosen section (by help menu)
     *
     */
    private void loadData() {
         /*
        TopStories  : https://api.nytimes.com/svc/topstories/v2/science.json?api-key=yourkey
                    : parameter = section => arts, automobiles, books, business, fashion, food, health, home, insider, magazine, movies, nyregion, obituaries,
                                             opinion, politics, realestate, science, sports, sundayreview, technology, theater, t-magazine, travel, upshot, us, world
        */
        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<TopStoriesNYT> call = newsDataService.getTopStoriesNew( getSharedTopStoriesCategory(), getApiKey() );

        call.enqueue(new Callback<TopStoriesNYT>(){

            @Override
            public void onResponse(Call<TopStoriesNYT> call, Response<TopStoriesNYT> response) {
                List<TopResult> result = response.body().getResults();

                TopAdapter adapter = new TopAdapter(result , Glide.with(getContext()), getContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TopStoriesNYT> call, Throwable t) {
                Toast.makeText(getContext(), "Error consumption of API NYT - TopStories", Toast.LENGTH_LONG).show();
            }
        });
    }
}
