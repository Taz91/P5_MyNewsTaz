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
import com.syc.utils.MyAdapter;
import com.syc.utils.RetrofitInstance;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopStoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopStoriesFragment extends Fragment {
    private static String API_KEY = "J0iJw0a8fdshubHztJsOJxEEg6hPstOG";
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

    private void loadData() {
        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<TopStoriesNYT> call = newsDataService.getTopStoriesNew("science", API_KEY);
                /*
                MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

                Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/shared/1.json?api-key=yourkey
                */


        call.enqueue(new Callback<TopStoriesNYT>(){

            @Override
            public void onResponse(Call<TopStoriesNYT> call, Response<TopStoriesNYT> response) {
                //Toast.makeText(getContext(), "Yesss c'est ok", Toast.LENGTH_LONG).show();

                List<TopResult> result = response.body().getResults();

                MyAdapter adapter = new MyAdapter(result , Glide.with(getView()));

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TopStoriesNYT> call, Throwable t) {
                // TODO : gestion message de retour : soit le site est inaccessible
                // y mettre une image ou un fragment spécifique ?


                Toast.makeText(getContext(), "Une erreur", Toast.LENGTH_LONG).show();
            }
        });

    }

}
